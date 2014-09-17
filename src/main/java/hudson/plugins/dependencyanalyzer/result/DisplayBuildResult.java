package hudson.plugins.dependencyanalyzer.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Dependency analyze result for a build. Class used for the display. Copy BuildResult in order to backward
 * compatibility. In previous version, artifact informations were displayed as a bloc. The new version split
 * informations in array and the serializing may conflict.
 *
 * @author Etienne Jouvin
 *
 */
public class DisplayBuildResult {

	private List<DisplayModuleResult> modules = new ArrayList<DisplayModuleResult>();

	/**
	 * Get all modules results for the build.
	 *
	 * @return Modules results.
	 */
	public List<DisplayModuleResult> getModules() {
		return this.modules;
	}

	/**
	 * Add dependency analyze result for a module in the build.
	 *
	 * @param modules Module result to add.
	 */
	public void setModules(List<DisplayModuleResult> modules) {
		this.modules = modules;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
