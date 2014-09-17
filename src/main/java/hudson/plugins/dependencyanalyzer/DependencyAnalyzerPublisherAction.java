package hudson.plugins.dependencyanalyzer;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.plugins.dependencyanalyzer.persistence.BuildResultSerializer;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.plugins.dependencyanalyzer.result.DisplayBuildResult;
import hudson.plugins.dependencyanalyzer.result.DisplayModuleResult;
import hudson.plugins.dependencyanalyzer.result.ModuleResult;
import hudson.plugins.dependencyanalyzer.result.ModuleResultTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

/**
 * Recorder to read the module log file and store dependency aalyze result.
 *
 * @author Vincent Sellier
 *
 */
public class DependencyAnalyzerPublisherAction implements Action {

	private static final String A_MODULE_URL = "dependencyanalyzer";
	private static final String B_PLUGIN_URL = "/plugin/" + A_MODULE_URL;
	private static final String C_IMAGES_URL = B_PLUGIN_URL + "/img";
	private static final String ICON_NAME = "dependency.png";
	private static final String ICON_URL = C_IMAGES_URL + "/" + ICON_NAME;

	private static final Logger LOGGER = Logger.getLogger(DependencyAnalyzerPublisherAction.class.getName());

	// This field is marked transient to avoid build descriptor pollution
	private transient DisplayBuildResult analysis;
	private AbstractBuild<?, ?> build;

	/**
	 * Default construtor.
	 */
	public DependencyAnalyzerPublisherAction() {
		super();
	}

	/**
	 * Constructor the build and its dependency analyze result.
	 *
	 * @param build Target build.
	 * @param buildResult Dependency analyze result for the build.
	 */
	public DependencyAnalyzerPublisherAction(AbstractBuild<?, ?> build, BuildResult buildResult) {
		this.build = build;
		this.analysis = this.transformAnalysis(buildResult);
	}

	/**
	 * @return the build.
	 */
	public AbstractBuild<?, ?> getBuild() {
		return this.build;
	}

	/**
	 * Get results to display. If not already build, the serialized file is read.
	 *
	 * @return Dependency analyze result for the build.
	 */
	public DisplayBuildResult getBuildResult() {
		if (this.analysis == null) {
			// we are consulting a previous build, getting result from file
			try {
				BuildResult buildResult = BuildResultSerializer.getInstance().deserialize(this.build.getRootDir());
				this.analysis = this.transformAnalysis(buildResult);
			} catch (IOException e) {
				LOGGER.severe("Error getting result from disk");
			}
		}

		return this.analysis;
	}

	/** {@inheritDoc} */
	public String getDisplayName() {
		return Messages.dependencyanalyzer_name();
	}

	/** {@inheritDoc} */
	public String getIconFileName() {
		return ICON_URL;
	}

	/** {@inheritDoc} */
	public String getUrlName() {
		return A_MODULE_URL;
	}

	/**
	 * Transform the dependency analyze result, read from maven log or the serialized file, in instance for the display.
	 *
	 * @param buildResult Build dependency analyze result to transform.
	 * @return Build dependency analyze result for the display.
	 */
	private DisplayBuildResult transformAnalysis(BuildResult buildResult) {
		DisplayBuildResult res = null;

		if (buildResult != null) {
			res = new DisplayBuildResult();
			ModuleResultTransformer transformer = ModuleResultTransformer.getInstance();

			/* Transform build result into an instance for the display. */
			List<DisplayModuleResult> modules = null;
			List<ModuleResult> buildModules = buildResult.getModules();
			if (CollectionUtils.isNotEmpty(buildModules)) {
				modules = new ArrayList<DisplayModuleResult>(buildModules.size());
				CollectionUtils.collect(buildModules, transformer, modules);
			}

			res.setModules(modules);
		}

		return res;
	}

}
