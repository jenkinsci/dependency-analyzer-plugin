package hudson.plugins.dependencyanalyzer.result;

/**
 * All dependency problem types.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 *
 */
public enum DependencyProblemType {
	/**
	 * Item to identify used but undeclared dependencies.
	 */
	UNDECLARED,
	/**
	 * Item to identify declared but not used dependencies.
	 */
	UNUSED;
}
