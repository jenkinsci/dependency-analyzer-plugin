package hudson.plugins.dependencyanalyzer.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

/**
 * Transform ModuleResult, read from the maven log or the serialized file, into a DisplayModuleResult.
 *
 * @author Etienne Jouvin
 *
 */
public final class ModuleResultTransformer implements Transformer {

	private static final ModuleResultTransformer INSTANCE = new ModuleResultTransformer();

	/**
	 * @return Singleton instance.
	 */
	public static ModuleResultTransformer getInstance() {
		return INSTANCE;
	}

	/**
	 * Private constructor.
	 */
	private ModuleResultTransformer() {
	}

	/** {@inheritDoc} */
	public Object transform(Object input) {
		DisplayModuleResult res = null;

		if (input instanceof ModuleResult) {
			ModuleResult tmp = (ModuleResult) input;
			res = new DisplayModuleResult();

			/* Copy module informations. */
			res.setDisplayName(tmp.getDisplayName());
			res.setModuleName(tmp.getModuleName());

			/* Copy and transform dependencies informations. */
			Map<DependencyProblemType, List<String>> tmpDepProbs = tmp.getDependencyProblems();
			Map<DependencyProblemType, List<Dependency>> dependencyProblems = null;
			if (tmpDepProbs != null) {
				dependencyProblems = new HashMap<DependencyProblemType, List<Dependency>>(tmpDepProbs.size());

				DependencyTransformer transformer = DependencyTransformer.getInstance();
				List<Dependency> dependencies;
				for (Map.Entry<DependencyProblemType, List<String>> entry : tmpDepProbs.entrySet()) {
					/* For each dependency problem, transform informations from String to Array. */
					/* In log, informations are read as */
					/* org.apache.maven:maven-model:jar:2.0.2:compile */
					/* Should be cut into parts to display as an array in the page. */

					dependencies = new ArrayList<Dependency>();
					CollectionUtils.collect(entry.getValue(), transformer, dependencies);

					dependencyProblems.put(entry.getKey(), dependencies);
				}
			}

			res.setDependencyProblems(dependencyProblems);
		}

		return res;
	}

}
