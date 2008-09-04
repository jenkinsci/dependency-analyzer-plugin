package hudson.plugins.dependencyanalyzer.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Parse the content of dependency:* sections and organize the detected
 * problems.
 * 
 * @author Vincent Sellier
 */
public class DependencyAnalysisParser {

	public static enum DependencyProblemTypes {
		UNUSED(".*Unused declared.*"), UNDECLARED(".*Used undeclared.*");

		private Pattern pattern;

		private DependencyProblemTypes(String regex) {
			pattern = Pattern.compile(regex);
		}

		public static DependencyProblemTypes matchAny(String line) {
			for (DependencyProblemTypes problem : DependencyProblemTypes
					.values()) {
				if (problem.pattern.matcher(line).matches()) {
					return problem;
				}
			}
			return null;
		}
	};

	public Map<DependencyProblemTypes, List<String>> parseDependencyAnalyzeSection(
			String content) throws IOException {
		Map<DependencyProblemTypes, List<String>> result = new HashMap<DependencyProblemTypes, List<String>>();

		List<String> lines = IOUtils.readLines(new StringReader(content));

		DependencyProblemTypes currentProblemType = null;
		for (String line : lines) {
			DependencyProblemTypes problemType = DependencyProblemTypes
					.matchAny(line);
			if (problemType != null) {
				currentProblemType = problemType;
			} else {
				if (currentProblemType != null) {
					List<String> problems = result.get(currentProblemType);
					if (problems == null) {
						problems = new ArrayList<String>();
						result.put(currentProblemType, problems);
					}
					// removing log level
					problems.add(line.substring(line.lastIndexOf(']')).trim());
				}
			}
		}

		return result;
	}
}
