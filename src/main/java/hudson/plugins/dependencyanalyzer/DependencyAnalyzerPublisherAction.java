package hudson.plugins.dependencyanalyzer;

import hudson.model.Action;

public class DependencyAnalyzerPublisherAction implements Action {

	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	public String getIconFileName() {
		return "/plugin/dependencyanalyser/dependencies.png";
	}

	public String getUrlName() {
		return "dependencyanalyzer";
	}

}
