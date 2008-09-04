package hudson.plugins.dependencyanalyzer.parser;

import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildLogFileParserTest {

	BuildLogFileParser parser;
	
	@Before
	public void setUp() {
		parser = new BuildLogFileParser();
	}
	
	@Test
	public void testGetDependencyAnalyseSectionOneModuleNotPresent() throws Exception {
		File file = getFile("log_build_without_dependency_analyse");
		
		parser.parseLogFile(file);
		
		Assert.assertNull("No dependency:analyse block must be found", parser.getDependencyAnalyseBlock());
		
	}

	@Test
	public void testGetDependencyAnalyseSectionOneModulePresent() throws Exception {
		File file = getFile("log_build_with_dependency_analyse");
		
		parser.parseLogFile(file);
		
		String result = parser.getDependencyAnalyseBlock();
		System.out.println(result);

		Assert.assertNotNull("dependency:analyze block must be found", result);

		List lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 6, lines.size());
		
		
		
	}

	
	/**
	 * return a file searching in the classpath
	 * 
	 * @param fileName the name of the file relative to the classpath 
	 * @return the file
	 * @throws Exception 
	 */
	private File getFile(String fileName) throws Exception {
		System.out.println("searching for file " + fileName);
		Enumeration<URL> fileURL = this.getClass().getClassLoader().getResources(fileName);
		
		String fullFileName = fileURL.nextElement().getFile();
		System.out.println("File name : " + fullFileName);
		File file = new File(fullFileName);
		return file;
	}
	
}
