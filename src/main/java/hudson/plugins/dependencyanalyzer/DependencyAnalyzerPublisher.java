/**
 * 
 */
package hudson.plugins.dependencyanalyzer;

import hudson.Launcher;
import hudson.maven.MavenBuild;
import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSetBuild;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Project;
import hudson.model.Result;
import hudson.plugins.dependencyanalyzer.parser.BuildLogFileParser;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.tasks.Publisher;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.model.Describable#getDescriptor()
	 */
	public Descriptor<Publisher> getDescriptor() {
		LOGGER.info("******** getDescriptor");
		return DESCRIPTOR;
	}

	@Override
	public Action getProjectAction(Project project) {
		LOGGER.info("******** getProjectAction : Project : " + project + " "
				+ super.getProjectAction(project));

		return super.getProjectAction(project);
	}

	@Override
	public boolean needsToRunAfterFinalized() {
		LOGGER.info("******** needsToRunAfterFinalized :"
				+ super.needsToRunAfterFinalized());
		return super.needsToRunAfterFinalized();
	}

	@Override
	public boolean prebuild(Build build, BuildListener listener) {
		LOGGER.info("******** preBuild :" + build + " " + listener + " "
				+ super.prebuild(build, listener));

		return super.prebuild(build, listener);
	}

	@Override
	public Action getProjectAction(AbstractProject<?, ?> project) {
		LOGGER.info("******** getProjectAction :" + project + " "
				+ super.getProjectAction(project));

		return super.getProjectAction(project);
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		LOGGER.info("******** perform :" + build + " " + launcher + " "
				+ listener + " " + super.perform(build, launcher, listener));

		Result result = build.getResult();
		if (!Result.SUCCESS.equals(result)) {
			LOGGER
					.info("******* build is not successfull, no dependencies analysis.");
			return false;
		}

		BuildResult analysis = DependencyAnalyzerResultBuilder
				.buildResult((MavenModuleSetBuild) build);

		return super.perform(build, launcher, listener);
	}

	@Override
	public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
		LOGGER.info("********* preBuild " + build + " " + listener + " "
				+ super.prebuild(build, listener));
		return super.prebuild(build, listener);
	}

}
