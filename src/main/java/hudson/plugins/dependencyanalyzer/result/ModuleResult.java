package hudson.plugins.dependencyanalyzer.result;

import hudson.maven.ModuleName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ModuleResult implements Serializable {
	private static final long serialVersionUID = -6461651211214230477L;

	String displayName;
	ModuleName moduleName;
	
	Map<DependencyProblemType, List<String>> dependencyProblems;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ModuleName getModuleName() {
		return moduleName;
	}

	public void setModuleName(ModuleName moduleName) {
		this.moduleName = moduleName;
	}

	public Map<DependencyProblemType, List<String>> getDependencyProblems() {
		return dependencyProblems;
	}

	public Integer getUnusedDependenciesCount() {
		List<String> dependencies = getUnusedDependencies();
		
		return dependencies == null ? 0 : dependencies.size();
	}
	
	public Integer getUndeclaredDependenciesCount() {
		List<String> dependencies = getUndeclaredDependencies();
		
		return dependencies == null ? 0 : dependencies.size();
	}
	
	public List<String> getUnusedDependencies() {
		return dependencyProblems.get(DependencyProblemType.UNUSED);
	}
	
	public List<String> getUndeclaredDependencies() {
		return dependencyProblems.get(DependencyProblemType.UNDECLARED);
	}
	
	public void setDependencyProblems(
			Map<DependencyProblemType, List<String>> dependencyProblems) {
		this.dependencyProblems = dependencyProblems;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
