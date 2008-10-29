package hudson.plugins.dependencyanalyzer.parser;

import hudson.plugins.dependencyanalyzer.result.DependencyProblemType;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Parse the content of dependency:* sections and organize the detected
 * problems.
 * 
 * @author Vincent Sellier
 */
public class DependencyAnalysisParser {

	private final static Pattern ARTIFACT_PATTERN = Pattern.compile(".*:.*:.*:.*:.*");
	
	public static enum DependencyProblemTypesDetection {
		UNUSED(DependencyProblemType.UNUSED, ".*Unused declared.*"), 
		UNDECLARED(DependencyProblemType.UNDECLARED, ".*Used undeclared.*");

		private Pattern pattern;
		private DependencyProblemType problemType;

		private DependencyProblemTypesDetection(DependencyProblemType problemType, String regex) {
			this.problemType = problemType;
			pattern = Pattern.compile(regex);
		}

		private DependencyProblemType getProblemType() {
			return problemType;
		}
		
		public static DependencyProblemType matchAny(String line) {
			for (DependencyProblemTypesDetection problem : DependencyProblemTypesDetection
					.values()) {
				if (problem.pattern.matcher(line).matches()) {
					return problem.getProblemType();
				}
			}
			return null;
		}
	};

	static public Map<DependencyProblemType, List<String>> parseDependencyAnalyzeSection(
			String content) throws IOException {
		Map<DependencyProblemType, List<String>> result = new HashMap<DependencyProblemType, List<String>>();

		List<String> lines = IOUtils.readLines(new StringReader(content));

		DependencyProblemType currentProblemType = null;
		for (String line : lines) {
			if (!StringUtils.isBlank(line)) {
				DependencyProblemType problemType = DependencyProblemTypesDetection
						.matchAny(line);
				if (problemType != null) {
					currentProblemType = problemType;
				} else {
					if (currentProblemType != null && ARTIFACT_PATTERN.matcher(line).matches()) {
						List<String> problems = result.get(currentProblemType);
						if (problems == null) {
							problems = new ArrayList<String>();
							result.put(currentProblemType, problems);
						}
						// removing log level
						problems.add(line.substring(line.lastIndexOf(']') + 1)
								.trim());
					}
				}
			}
		}

		return result;
	}
}
