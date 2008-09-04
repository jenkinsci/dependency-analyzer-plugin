package hudson.plugins.dependencyanalyzer.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleResult implements Serializable {
	private static final long serialVersionUID = -6461651211214230477L;

	String artifactId;
	String moduleName;
	List<String> undeclaredDependencies = new ArrayList<String>();
	List<String> unusedDependencies = new ArrayList<String>();

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<String> getUndeclaredDependencies() {
		return Collections.unmodifiableList(undeclaredDependencies);
	}

	public void addUndeclaredDependencies(String undeclaredDependency) {
		undeclaredDependencies.add(undeclaredDependency);
	}

	public List<String> getUnusedDependencies() {
		return Collections.unmodifiableList(unusedDependencies);
	}

	public void addUnusedDependencies(String unusedDependency) {
		unusedDependencies.add(unusedDependency);
	}

}
