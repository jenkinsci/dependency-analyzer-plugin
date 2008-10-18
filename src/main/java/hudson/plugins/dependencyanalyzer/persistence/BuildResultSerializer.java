package hudson.plugins.dependencyanalyzer.persistence;

import hudson.model.AbstractBuild;
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
 */
public class BuildResultSerializer {
	public static final String RESULT_FILE_NAME = "dependencies-analysis.xml";
	private static final XStream stream = new XStream2();
	
	/**
	 * Serialize into an xml file the result into a given directory
	 */
	public static void serialize(File directory, BuildResult result) throws IOException {
		FileWriter writer = new FileWriter(getFile(directory));
		
		stream.toXML(result, writer);

		writer.flush();
		writer.close();		
	}
	
	/**
	 * Deserialize the result for the passed directory
	 * 
	 */
	public static BuildResult deserialize(File directory) throws IOException {
		FileReader reader = new FileReader(getFile(directory));
		
		BuildResult result = (BuildResult) stream.fromXML(reader);

		return result;
	}
	
	private static File getFile(File directory) {
		File fileName = new File(directory, RESULT_FILE_NAME);
		return fileName;
	}
}
