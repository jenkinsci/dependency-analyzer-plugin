package hudson.plugins.dependencyanalyzer;

import hudson.maven.MavenBuild;
import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSetBuild;
import hudson.plugins.dependencyanalyzer.parser.BuildLogFileParser;
import hudson.plugins.dependencyanalyzer.parser.DependencyAnalysisParser;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.plugins.dependencyanalyzer.result.DependencyProblemType;
import hudson.plugins.dependencyanalyzer.result.ModuleResult;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

/**
 * Build dependency analyze result for a Maven job.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 *
 */
public final class DependencyAnalyzerResultBuilder {

	private static final DependencyAnalyzerResultBuilder INSTANCE = new DependencyAnalyzerResultBuilder();
	private static final Logger LOGGER = Logger.getLogger(DependencyAnalyzerResultBuilder.class.toString());

	/**
	 * @return Default instance.
	 */
	public static DependencyAnalyzerResultBuilder getInstance() {
		return INSTANCE;
	}

	/**
	 * Private constructor.
	 */
	private DependencyAnalyzerResultBuilder() {
	}

	/**
	 * Read the execution log file for a module and extract dependency analyze result.
	 *
	 * @param module Source module.
	 * @param logFile Execution log file linked to the module.
	 * @return Dependency analyze result for the build.
	 * @throws IOException File reading and writing exception.
	 */
	private ModuleResult buildModuleResult(MavenModule module, File logFile) throws IOException {
		ModuleResult moduleResult = new ModuleResult();

		BuildLogFileParser logFileParser = new BuildLogFileParser();
		logFileParser.parseLogFile(logFile);

		// extracting dependency section from log file
		String dependencySection = logFileParser.getDependencyAnalyseBlock();

		if (StringUtils.isBlank(dependencySection)) {
			LOGGER.info("No dependency section found. Add dependency:analyze on your job configuration.");
			return moduleResult;
		}

		// extracting informations from dependency section
		Map<DependencyProblemType, List<String>> dependencyProblems = DependencyAnalysisParser.getInstance()
				.parseDependencyAnalyzeSection(dependencySection);

		// populating result
		moduleResult.setModuleName(module.getModuleName());
		moduleResult.setDisplayName(module.getDisplayName());
		moduleResult.setDependencyProblems(dependencyProblems);

		return moduleResult;
	}

	/**
	 * Build dependency analyze result for all module inside a build.
	 *
	 * @param build Source build.
	 * @return Dependency analyze result for the build, includes all module inside the build.
	 * @throws IOException File reading and writing exception.
	 */
	public BuildResult buildResult(MavenModuleSetBuild build) throws IOException {
		Map<MavenModule, List<MavenBuild>> moduleBuilds = build.getModuleBuilds();

		Iterator<MavenModule> iterator = moduleBuilds.keySet().iterator();

		BuildResult analysisResult = new BuildResult();

		while (iterator.hasNext()) {
			List<MavenBuild> builds = moduleBuilds.get(iterator.next());
			// Desactivated modules have no builds
			if (builds.size() > 0) {
				MavenBuild moduleBuild = builds.get(0);

				File logFile = moduleBuild.getLogFile();
				MavenModule mavenModule = moduleBuild.getProject();

				ModuleResult moduleResult = this.buildModuleResult(mavenModule, logFile);

				analysisResult.addResult(moduleResult);
			}
		}

		return analysisResult;
	}

}
