package hudson.plugins.dependencyanalyzer;

import hudson.Extension;
import hudson.maven.AbstractMavenProject;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

/**
 * Plugin descriptor.
 *
 * @author Vincent Sellier
 *
 */
@Extension
public class DependencyAnalyzerPublisherDescriptor extends BuildStepDescriptor<Publisher> {

	/**
	 * Default constructor.
	 */
	public DependencyAnalyzerPublisherDescriptor() {
		super(DependencyAnalyzerPublisher.class);
	}

	/** {@inheritDoc} */
	@Override
	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {
		return AbstractMavenProject.class.isAssignableFrom(jobType);
	}

}
