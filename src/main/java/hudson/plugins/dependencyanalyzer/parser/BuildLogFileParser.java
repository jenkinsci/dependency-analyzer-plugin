package hudson.plugins.dependencyanalyzer.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Cut the log file in sections for each maven goals.
 * 
 * @author Vincent Sellier
 * 
 */
public class BuildLogFileParser {
	private final static String LOG_LEVEL_REGEX = "^\\[[A-Z]*\\] ";
	private final static Pattern GOAL_START = Pattern.compile(LOG_LEVEL_REGEX
			+ "\\[.*:.*\\]$");
	private final static Pattern END_OF_BUILD = Pattern.compile(LOG_LEVEL_REGEX
			+ "[-]*$");

	private enum Goal {
		DEPENDENCY_ANALYSE(LOG_LEVEL_REGEX + "\\[dependency:analyze\\]$");

		private Pattern pattern;

		private Goal(String regex) {
			pattern = Pattern.compile(regex);
		}

		public Pattern getPattern() {
			return pattern;
		}

		public static Goal getMatchingGoal(String line) {
			Goal[] goals = Goal.values();

			for (Goal goal : goals) {
				Pattern pattern = goal.pattern;
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					return goal;
				}
			}
			return null;
		}
	}

	private boolean parsed = false;
	private Map<Goal, String> goalsLog = new HashMap<Goal, String>();

	public void parseLogFile(File logFile) throws IOException {
		FileInputStream input = new FileInputStream(logFile);

		List<String> lines = (List<String>) IOUtils.readLines(input);

		for (int i = 0; i < lines.size();) {
			String line = lines.get(i++);

			Goal goal = Goal.getMatchingGoal(line);
			if (goal != null) {
				StringBuilder section = new StringBuilder();

				// Pass the search section to only keep content of the section
				line = lines.get(i++);

				do {
					section.append(line).append("\n");
					line = lines.get(i++);
				} while (!(GOAL_START.matcher(line).matches() || END_OF_BUILD
								.matcher(line).matches()));

				goalsLog.put(goal, section.toString());
			}
		}

		parsed = true;
	}

	private String getContent(Goal goal) {
		if (!parsed) {
			throw new RuntimeException("No log file was parsed");
		}

		return goalsLog.get(goal);
	}

	public String getDependencyAnalyseBlock() {
		return getContent(Goal.DEPENDENCY_ANALYSE);
	}

}
