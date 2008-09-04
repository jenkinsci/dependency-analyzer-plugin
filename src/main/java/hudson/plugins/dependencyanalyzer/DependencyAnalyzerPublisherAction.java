package hudson.plugins.dependencyanalyzer;

import hudson.model.Action;

public class DependencyAnalyzerPublisherAction implements Action {

	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	public String getBaseUrl() {
		String url = DependencyAnalyzerPublisher.class.getPackage().getName().replace('.', '/');
		return url;
	}
	
	public String getIconFileName() {
		return "/plugin/dependencyanalyser/dependencies.png";
	}

	public String getUrlName() {
		return "/plugin/dependencyanalyser/";
	}

}
