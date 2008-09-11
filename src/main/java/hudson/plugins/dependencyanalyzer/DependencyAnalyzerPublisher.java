/**
 * 
 */
package hudson.plugins.dependencyanalyzer;

import hudson.Launcher;
import hudson.maven.MavenModuleSetBuild;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Result;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.tasks.Publisher;

import java.io.IOException;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Vincent Sellier
 * 
 */
public class DependencyAnalyzerPublisher extends Publisher {
	public static final Logger LOGGER = Logger
			.getLogger(DependencyAnalyzerPublisher.class.getName());

	public static final Descriptor<Publisher> DESCRIPTOR = new DependencyAnalyzerPublisherDescriptor();

	@DataBoundConstructor
	public DependencyAnalyzerPublisher() {

	}

	public Descriptor<Publisher> getDescriptor() {
		LOGGER.info("******** getDescriptor");
		return DESCRIPTOR;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {

		Result result = build.getResult();
		if (!Result.SUCCESS.equals(result)) {
			LOGGER
					.info("Build is not successfull, no dependencies analysis.");
			return false;
		}

		BuildResult analysis = DependencyAnalyzerResultBuilder
				.buildResult((MavenModuleSetBuild) build);

		return super.perform(build, launcher, listener);
	}

}
