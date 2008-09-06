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

public class DependencyAnalyzerResultBuilder {
	public static BuildResult buildResult(MavenModuleSetBuild build)
			throws IOException {
		BuildResult result = new BuildResult();

		Map<MavenModule, List<MavenBuild>> moduleBuilds = ((MavenModuleSetBuild) build)
				.getModuleBuilds();

		Iterator<MavenModule> iterator = moduleBuilds.keySet().iterator();

		BuildResult analysisResult = new BuildResult();

		while (iterator.hasNext()) {
			List<MavenBuild> builds = moduleBuilds.get(iterator.next());
			MavenBuild moduleBuild = builds.get(0);

			File logFile = moduleBuild.getLogFile();
			MavenModule mavenModule = moduleBuild.getProject();

			ModuleResult moduleResult = buildModuleResult(mavenModule, logFile);

		}

		return result;
	}

	private static ModuleResult buildModuleResult(MavenModule module,
			File logFile) throws IOException {
		ModuleResult moduleResult = new ModuleResult();

		BuildLogFileParser logFileParser = new BuildLogFileParser();
		logFileParser.parseLogFile(logFile);

		// extracting dependency section from log file
		String dependencySection = logFileParser.getDependencyAnalyseBlock();

		// extracting informations from dependency section
		Map<DependencyProblemType, List<String>> dependencyProblems = DependencyAnalysisParser
				.parseDependencyAnalyzeSection(dependencySection);

		// populating result
		moduleResult.setModuleName(module.getModuleName());
		moduleResult.setDisplayName(module.getDisplayName());
		moduleResult.setDependencyProblems(dependencyProblems);
		
		return moduleResult;
	}
}
