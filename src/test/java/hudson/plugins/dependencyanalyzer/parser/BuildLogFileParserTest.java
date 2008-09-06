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

public class BuildLogFileParserTest extends AbstractParserTestUtils {

	BuildLogFileParser parser;
	
	@Before
	public void setUp() {
		parser = new BuildLogFileParser();
	}
	
	@Test
	public void testGetDependencyAnalyseSectionNotPresent() throws Exception {
		File file = getFile("log_build_without_dependency_analyse");
		
		parser.parseLogFile(file);
		
		Assert.assertNull("No dependency:analyse block must be found", parser.getDependencyAnalyseBlock());
		
	}

	@Test
	public void testGetDependencyAnalyseSectionPresent() throws Exception {
		File file = getFile("log_build_with_dependency_analyse");
		
		parser.parseLogFile(file);
		
		String result = parser.getDependencyAnalyseBlock();
		System.out.println(result);

		Assert.assertNotNull("dependency:analyze block must be found", result);

		List lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 6, lines.size());
		
	}

	
}
