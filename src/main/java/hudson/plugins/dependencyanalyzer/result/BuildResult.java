package hudson.plugins.dependencyanalyzer.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Dependency analyze result for a build.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 *
 */
public class BuildResult implements Serializable {

	private static final long serialVersionUID = -1384220307801729741L;

	private List<ModuleResult> modulesResults = new ArrayList<ModuleResult>();

	/**
	 * Add dependency analyze result for a module in the build.
	 *
	 * @param result Module result to add.
	 */
	public void addResult(ModuleResult result) {
		this.modulesResults.add(result);
	}

	/**
	 * Get all modules results for the build.
	 *
	 * @return Modules results.
	 */
	public List<ModuleResult> getModules() {
		return this.modulesResults;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
