/**
 * 
 */
package hudson.plugins.dependencyanalyzer;

import hudson.maven.AbstractMavenProject;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

import java.util.logging.Logger;

/**
 * @author Vincent Sellier
 * 
 */
public class DependencyAnalyzerPublisherDescriptor extends
		BuildStepDescriptor<Publisher> {

	private static final Logger LOGGER = Logger.getLogger(PluginImpl.class
			.getName());

	protected DependencyAnalyzerPublisherDescriptor() {
		super(DependencyAnalyzerPublisher.class);
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {

		boolean applicable = AbstractMavenProject.class
				.isAssignableFrom(jobType);

		return applicable;
	}

	@Override
	public String getDisplayName() {
		LOGGER.info("********* getDisplayName");
		return Messages.dependencyanalyzer_name();
	}

	@Override
	public String getConfigPage() {
		LOGGER.info("******** getConfigPage : " + super.getConfigPage());

		return super.getConfigPage();
	}

	@Override
	public String getGlobalConfigPage() {
		String page = super.getGlobalConfigPage();

		LOGGER.info("******** global config page : " + page);

		return page;
	}

	@Override
	public String getHelpFile() {
		LOGGER.info("******** getHelpFile : " + super.getHelpFile());

		return super.getHelpFile();
	}

}
