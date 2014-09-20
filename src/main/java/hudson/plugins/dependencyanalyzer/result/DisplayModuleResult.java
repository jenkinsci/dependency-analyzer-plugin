package hudson.plugins.dependencyanalyzer.result;

import hudson.maven.ModuleName;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Dependency analyze result for a module. Class used for the display. Copy ModuleResult in order to backward
 * compatibility. In previous version, artifact informations were displayed as a bloc. The new version split
 * informations in array and the serializing may conflict.
 *
 * @author Etienne Jouvin
 *
 */
public class DisplayModuleResult {

	private Map<DependencyProblemType, List<Dependency>> dependencyProblems;
	private String displayName;
	private ModuleName moduleName;

	/**
	 * @return the dependencyProblems.
	 */
	public Map<DependencyProblemType, List<Dependency>> getDependencyProblems() {
		return this.dependencyProblems;
	}

	/**
	 * @return the displayName.
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * @return the moduleName.
	 */
	public ModuleName getModuleName() {
		return this.moduleName;
	}

	/**
	 * @return Used dependencies, but not declared, list.
	 */
	public List<Dependency> getUndeclaredDependencies() {
		return this.dependencyProblems == null ? null : this.dependencyProblems.get(DependencyProblemType.UNDECLARED);
	}

	/**
	 * @return The number for used dependencies but not declared.
	 */
	public Integer getUndeclaredDependenciesCount() {
		List<Dependency> dependencies = this.getUndeclaredDependencies();

		return dependencies == null ? 0 : dependencies.size();
	}

	/**
	 * @return Declared dependencies, but unused, list.
	 */
	public List<Dependency> getUnusedDependencies() {
		return this.dependencyProblems == null ? null : this.dependencyProblems.get(DependencyProblemType.UNUSED);
	}

	/**
	 * @return The number for declared dependencies but not used.
	 */
	public Integer getUnusedDependenciesCount() {
		List<Dependency> dependencies = this.getUnusedDependencies();

		return dependencies == null ? 0 : dependencies.size();
	}

	/**
	 * @param dependencyProblems the dependencyProblems to set.
	 */
	public void setDependencyProblems(Map<DependencyProblemType, List<Dependency>> dependencyProblems) {
		this.dependencyProblems = dependencyProblems;
	}

	/**
	 * @param displayName the displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param moduleName the moduleName to set.
	 */
	public void setModuleName(ModuleName moduleName) {
		this.moduleName = moduleName;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
