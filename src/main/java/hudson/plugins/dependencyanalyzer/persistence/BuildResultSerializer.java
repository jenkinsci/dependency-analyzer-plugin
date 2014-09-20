package hudson.plugins.dependencyanalyzer.persistence;

import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.util.XStream2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * Utility class to persist analysis result into the build root directory.
 *
 * @author Vincent Sellier
 * @author Etienne Jouvin
 *
 */
public final class BuildResultSerializer {

	private static final BuildResultSerializer INSTANCE = new BuildResultSerializer();

	private static final String RESULT_FILE_NAME = "dependencies-analysis.xml";

	/**
	 * @return Default instance.
	 */
	public static BuildResultSerializer getInstance() {
		return INSTANCE;
	}

	private final XStream stream = new XStream2();

	/**
	 * Private constructor.
	 */
	private BuildResultSerializer() {
	}

	/**
	 * Deserialize the result for the passed directory.
	 *
	 * @param directory Folder where the file should exists.
	 * @return Dependency analyze result for the build.
	 * @throws IOException File reading exception.
	 */
	public BuildResult deserialize(File directory) throws IOException {
		FileReader reader = new FileReader(this.getFile(directory));

		BuildResult result = (BuildResult) this.stream.fromXML(reader);

		return result;
	}

	/**
	 * Build the serialized file instance.
	 *
	 * @param directory Folder where the file should exists.
	 * @return File instance for the serialized file.
	 */
	private File getFile(File directory) {
		return new File(directory, RESULT_FILE_NAME);
	}

	/**
	 * Serialize into an xml file the result into a given directory.
	 *
	 * @param directory Folder where to store the file.
	 * @param result Dependency analyze result o serialize.
	 * @throws IOException File writing exception.
	 */
	public void serialize(File directory, BuildResult result) throws IOException {
		FileWriter writer = new FileWriter(this.getFile(directory));

		this.stream.toXML(result, writer);

		writer.flush();
		writer.close();
	}

}
