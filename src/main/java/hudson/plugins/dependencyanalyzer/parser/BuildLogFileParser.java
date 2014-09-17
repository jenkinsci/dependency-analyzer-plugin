package hudson.plugins.dependencyanalyzer.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Cut the log file in sections for each maven goals.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 */
public class BuildLogFileParser {

	/**
	 * Internal enumeration to register a goal and the regular expression for the starting section of the goal.
	 */
	private enum Goal {
		DEPENDENCY_ANALYSE(A_LOG_LEVEL_REGEX + "\\[dependency:analyze(-only)?( \\{execution: [^\\}]+\\}){0,1}\\]$");

		private Pattern pattern;

		/**
		 * @param regex Regular expression for the goal.
		 */
		private Goal(String regex) {
			this.pattern = Pattern.compile(regex);
		}

		/**
		 * @return the pattern.
		 */
		public Pattern getPattern() {
			return this.pattern;
		}

	}

	private static final String A_LOG_LEVEL_REGEX = "\\[(INFO|WARNING)\\] ";
	private static final Pattern END_OF_BUILD = Pattern.compile(A_LOG_LEVEL_REGEX + "[-]*$");
	private static final Pattern GOAL_START = Pattern.compile(A_LOG_LEVEL_REGEX + "\\[.*:.*\\]$");
	private static final Logger LOGGER = Logger.getLogger(BuildLogFileParser.class.getName());

	// To limit selection to maven output (filtering [HUDSON] tags)
	private static final Pattern MAVEN_OUTPUT = Pattern.compile(A_LOG_LEVEL_REGEX + ".*");

	private Map<Goal, String> goalsLog = new HashMap<Goal, String>();

	private boolean parsed = false;

	/**
	 * Retrieve log content extracted for a goal.
	 *
	 * @param goal Source goal.
	 * @return Goal content extracted from the log file.
	 */
	private String getContent(Goal goal) {
		if (!this.parsed) {
			throw new RuntimeException("No log file was parsed");
		}

		return this.goalsLog.get(goal);
	}

	/**
	 * @return Content extracted from the log file for the goal dependency analyze.
	 */
	public String getDependencyAnalyseBlock() {
		return this.getContent(Goal.DEPENDENCY_ANALYSE);
	}

	/**
	 * Find a matching goal from a line, according the goal regular expression.
	 *
	 * @param line The line to control.
	 * @return Found goal.
	 */
	private Goal getMatchingGoal(String line) {
		Goal res = null;
		Goal[] goals = Goal.values();

		Pattern pattern;
		Matcher matcher;
		for (Goal goal : goals) {
			pattern = goal.getPattern();
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				res = goal;
				break;
			}
		}

		return res;
	}

	/**
	 * Parse log file to find dependency analyze informations.
	 *
	 * @param logFile Log file to parge.
	 * @throws IOException File reading error.
	 */
	public void parseLogFile(File logFile) throws IOException {
		LOGGER.fine("Parsing " + logFile.getAbsolutePath());
		FileInputStream input = new FileInputStream(logFile);

		List<String> lines = IOUtils.readLines(input);

		Iterator<String> lineIterator = lines.iterator();

		/* Init the parsed flag to false for this file. */
		this.parsed = false;
		while (lineIterator.hasNext()) {
			String line = lineIterator.next();

			Goal goal = this.getMatchingGoal(line);
			if (goal != null) {
				StringBuilder section = new StringBuilder();

				// Pass the search section to only keep content of the section
				Matcher mavenMatcher;
				while (lineIterator.hasNext() && !this.parsed) {
					line = lineIterator.next();

					if (GOAL_START.matcher(line).find() || END_OF_BUILD.matcher(line).find()) {
						this.parsed = true;
					} else {
						mavenMatcher = MAVEN_OUTPUT.matcher(line);
						if (mavenMatcher.find()) {
							section.append(mavenMatcher.group()).append("\n");
						}
					}

				}

				this.goalsLog.put(goal, section.toString());
			}
		}

		this.parsed = true;
	}

}
