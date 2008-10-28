package hudson.plugins.dependencyanalyzer;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.plugins.dependencyanalyzer.persistence.BuildResultSerializer;
import hudson.plugins.dependencyanalyzer.result.BuildResult;

import java.io.IOException;
import java.util.logging.Logger;

public class DependencyAnalyzerPublisherAction implements Action {
	private final static Logger LOGGER = Logger
			.getLogger(DependencyAnalyzerPublisherAction.class.getName());

	private AbstractBuild<?, ?> build;
	// This field is marked transient to avoid build descriptor pollution
	private transient BuildResult analysis;

	public DependencyAnalyzerPublisherAction() {
		super();
	}

	public DependencyAnalyzerPublisherAction(AbstractBuild<?, ?> build,
			BuildResult analysis) {
		this.build = build;
		this.analysis = analysis;
	}

	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	public String getIconFileName() {
		return "/plugin/dependencyanalyzer/img/dependencies.png";
	}

	public String getUrlName() {
		return "dependencyanalyzer";
	}

	public AbstractBuild<?, ?> getBuild() {
		return build;
	}

	public BuildResult getBuildResult() {
		if (analysis == null) {
			// we are consulting a previous build, getting result from file
			try {
				analysis = BuildResultSerializer.deserialize(build.getRootDir());
			} catch (IOException e) {
				LOGGER.severe("Error getting result from disk");
			}
		}
		return analysis;
	}
}
