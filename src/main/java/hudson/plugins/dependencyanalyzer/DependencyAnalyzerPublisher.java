package hudson.plugins.dependencyanalyzer;

import hudson.Launcher;
import hudson.maven.MavenModuleSetBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.plugins.dependencyanalyzer.persistence.BuildResultSerializer;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Recorder to read the module log file and store dependency aalyze result.
 *
 * @author Vincent Sellier
 *
 */
public class DependencyAnalyzerPublisher extends Recorder {

	private static final Logger LOGGER = Logger.getLogger(DependencyAnalyzerPublisher.class.getName());

	/**
	 * Default constructor.
	 */
	@DataBoundConstructor
	public DependencyAnalyzerPublisher() {
	}

	/** {@inheritDoc} */
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.STEP;
	}

	/** {@inheritDoc} */
	@Override
	public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException,
	IOException {

		Result result = build.getResult();
		if (!(Result.SUCCESS.equals(result) || Result.UNSTABLE.equals(result))) {
			LOGGER.info("Build is not successful, no dependency analysis.");
			return false;
		}

		// Construct the build result
		BuildResult analysis = DependencyAnalyzerResultBuilder.getInstance().buildResult((MavenModuleSetBuild) build);

		// persist this analysis for this build
		BuildResultSerializer.getInstance().serialize(build.getRootDir(), analysis);

		build.getActions().add(new DependencyAnalyzerPublisherAction(build, analysis));

		return true;
	}

}
