package hudson.plugins.dependencyanalyzer;

import hudson.model.AbstractBuild;
import hudson.model.Action;

public class DependencyAnalyzerPublisherAction implements Action {

	private AbstractBuild<?, ?> build;
	
	public DependencyAnalyzerPublisherAction(AbstractBuild<?, ?> build) {
		this.build = build;
	}
	
	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	public String getIconFileName() {
		return "/plugin/dependencyanalyser/dependencies.png";
	}

	public String getUrlName() {
		return "dependencyanalyzer";
	}

	public AbstractBuild<?, ?> getBuild() {
		return build;
	}
	
}
