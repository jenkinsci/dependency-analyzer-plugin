/**
 * 
 */
package hudson.plugins.dependencyanalyzer;

import hudson.Extension;
import hudson.maven.AbstractMavenProject;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

/**
 * @author Vincent Sellier
 * 
 */
@Extension
public class DependencyAnalyzerPublisherDescriptor extends
		BuildStepDescriptor<Publisher> {

	public DependencyAnalyzerPublisherDescriptor() {
		super(DependencyAnalyzerPublisher.class);
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {
		return AbstractMavenProject.class.isAssignableFrom(jobType);
	}

	@Override
	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

}
