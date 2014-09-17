package hudson.plugins.dependencyanalyzer.parser;

import hudson.plugins.dependencyanalyzer.result.DependencyProblemType;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Parse the content of dependency:* sections and organize the detected problems.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 */
public final class DependencyAnalysisParser {

	/**
	 * Internal enumeration to detect dependency problem type starting bloc in log file.
	 */
	public enum DependencyProblemTypesDetection {
		/**
		 * Definition to detect starting bloc for used but not declared dependencies.
		 */
		UNDECLARED(DependencyProblemType.UNDECLARED, ".*Used undeclared.*"),
		/**
		 * Definition to detect starting bloc for declared but unused dependencies.
		 */
		UNUSED(DependencyProblemType.UNUSED, ".*Unused declared.*");

		private Pattern pattern;

		private DependencyProblemType problemType;

		private DependencyProblemTypesDetection(DependencyProblemType problemType, String regex) {
			this.problemType = problemType;
			this.pattern = Pattern.compile(regex);
		}

		private DependencyProblemType getProblemType() {
			return this.problemType;
		}
	}

	private static final Pattern ARTIFACT_PATTERN = Pattern.compile("[ \\t].*:.*:.*:.*:.*");
	private static final DependencyAnalysisParser INSTANCE = new DependencyAnalysisParser();

	/**
	 * @return Default instance.
	 */
	public static DependencyAnalysisParser getInstance() {
		return INSTANCE;
	};

	/**
	 * Private constructor.
	 */
	private DependencyAnalysisParser() {
	}

	/**
	 * Find a matching dependency problem type from a line, according the goal regular expression.
	 *
	 * @param line The line to control.
	 * @return Found dependency problem type.
	 */
	private DependencyProblemType matchAny(String line) {
		DependencyProblemType res = null;

		for (DependencyProblemTypesDetection problem : DependencyProblemTypesDetection.values()) {
			if (problem.pattern.matcher(line).matches()) {
				res = problem.getProblemType();
				break;
			}
		}

		return res;
	}

	/**
	 * Parse log content for the goal. Extract informations for used but not declared dependencies, and declared and not
	 * used dependency.
	 *
	 * @param content Goal content to parse.
	 * @return List of dependency according the problem type.
	 * @throws IOException Content reading error.
	 */
	public Map<DependencyProblemType, List<String>> parseDependencyAnalyzeSection(String content) throws IOException {
		Map<DependencyProblemType, List<String>> result = new HashMap<DependencyProblemType, List<String>>();

		List<String> lines = IOUtils.readLines(new StringReader(content));

		Matcher artifactPattern;
		DependencyProblemType currentProblemType = null;
		DependencyProblemType problemType;
		for (String line : lines) {
			if (!StringUtils.isBlank(line)) {
				problemType = this.matchAny(line);
				if (problemType != null) {
					currentProblemType = problemType;
				} else if (currentProblemType != null) {
					artifactPattern = ARTIFACT_PATTERN.matcher(line);
					if (artifactPattern.find()) {
						List<String> problems = result.get(currentProblemType);
						if (problems == null) {
							problems = new ArrayList<String>();
							result.put(currentProblemType, problems);
						}
						problems.add(artifactPattern.group().trim());
					}
				}
			}
		}

		return result;
	}

}
