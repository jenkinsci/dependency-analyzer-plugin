package hudson.plugins.dependencyanalyzer;

public interface Const {
	public final static String MODULE_URL = "dependencyanalyzer";
	public final static String PLUGIN_URL = "/plugin/" + MODULE_URL;
	public final static String IMAGES_URL = PLUGIN_URL + "/img";

	public final static String ICON_NAME = "dependency.png";
	public final static String ICON_URL = IMAGES_URL + "/" + ICON_NAME;
}
