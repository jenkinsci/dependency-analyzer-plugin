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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Vincent Sellier
 * 
 */
public class DependencyAnalyzerPublisher extends Publisher {
	public static final Logger LOGGER = Logger
			.getLogger(DependencyAnalyzerPublisher.class.getName());

	private static final String RESULT_FILE_NAME = "dependencies-analysis.ser.gz";
	
	public static final Descriptor<Publisher> DESCRIPTOR = new DependencyAnalyzerPublisherDescriptor();

	@DataBoundConstructor
	public DependencyAnalyzerPublisher() {

	}

	public Descriptor<Publisher> getDescriptor() {
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

		// Construct the build result
		BuildResult analysis = DependencyAnalyzerResultBuilder
				.buildResult((MavenModuleSetBuild) build);

		// Persist the result in the current build
		persistResult(build, analysis);
		
		build.getActions().add(new DependencyAnalyzerPublisherAction(build));
		
		return super.perform(build, launcher, listener);
	}

	private void persistResult(AbstractBuild<?, ?> build, BuildResult analysis) throws IOException {
		File file = new File(build.getRootDir(), RESULT_FILE_NAME);
		
		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		
		out.writeObject(analysis);
		out.flush();
		out.close();
	}
	
}
