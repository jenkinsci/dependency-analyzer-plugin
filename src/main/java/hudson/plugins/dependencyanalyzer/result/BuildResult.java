package hudson.plugins.dependencyanalyzer.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuildResult implements Serializable {
	private static final long serialVersionUID = -1384220307801729741L;

	List<ModuleResult> modulesResults = new ArrayList<ModuleResult>();
	
	public void addResult(ModuleResult result) {
		modulesResults.add(result);
	}
	
}
