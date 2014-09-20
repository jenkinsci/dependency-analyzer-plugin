package hudson.plugins.dependencyanalyzer.result;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

/**
 * Transform the dependency result, read from the maven log or the serialized file, into Dependency instance.
 *
 * @author Etienne Jouvin
 *
 */
public final class DependencyTransformer implements Transformer {

	private static final DependencyTransformer INSTANCE = new DependencyTransformer();
	private static final int POS_ARTIFACT = 1;
	private static final int POS_GROUPID = 0;
	private static final int POS_SCOPE = 4;
	private static final int POS_TYPE = 2;
	private static final int POS_VERSION = 3;

	/**
	 * @return Singleton instance.
	 */
	public static DependencyTransformer getInstance() {
		return INSTANCE;
	}

	/**
	 * Private constructor.
	 */
	private DependencyTransformer() {
	}

	/**
	 * Get value from an array.
	 *
	 * @param array Source array.
	 * @param index Index where to retrieve the value.
	 * @return Value or empty string.
	 */
	private String getValue(String[] array, int index) {
		String res = index >= array.length ? StringUtils.EMPTY : array[index];
		return res == null ? StringUtils.EMPTY : res;
	}

	/** {@inheritDoc} */
	public Object transform(Object input) {
		Dependency res = null;

		if (input instanceof String) {
			String[] informations = ((String) input).split(":");

			res = new Dependency();
			res.setArtifact(this.getValue(informations, POS_ARTIFACT));
			res.setGroupId(this.getValue(informations, POS_GROUPID));
			res.setScope(this.getValue(informations, POS_SCOPE));
			res.setType(this.getValue(informations, POS_TYPE));
			res.setVersion(this.getValue(informations, POS_VERSION));
		}

		return res;
	}

}
