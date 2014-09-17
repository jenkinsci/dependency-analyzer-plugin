package hudson.plugins.dependencyanalyzer.result;

/**
 * Dependency informations.
 *
 * @author Etienne Jouvin
 *
 */
public class Dependency {

	private String artifact;
	private String groupId;
	private String scope;
	private String type;
	private String version;

	/**
	 * @return the artifact.
	 */
	public String getArtifact() {
		return this.artifact;
	}

	/**
	 * @return the groupId.
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * @return the scope.
	 */
	public String getScope() {
		return this.scope;
	}

	/**
	 * @return the type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the version.
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param artifact the artifact to set.
	 */
	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	/**
	 * @param groupId the groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @param scope the scope to set.
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @param type the type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param version the version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
