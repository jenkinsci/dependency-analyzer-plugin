package hudson.plugins.dependencyanalyzer.parser;

import java.io.File;
import java.io.StringReader;
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
	public void testGetDependencyAnalyzeSectionNotPresent() throws Exception {
		File file = getFile("log_build_without_dependency_analyze");

		parser.parseLogFile(file);

		Assert.assertNull("No dependency:analyze block must be found", parser.getDependencyAnalyseBlock());

	}

	@Test
	public void testGetDependencyAnalyzeSectionEmpty() throws Exception {
		File file = getFile("log_build_with_empty_dependency_analyze");

		parser.parseLogFile(file);

		Assert.assertNotNull("No dependency:analyze block found", parser.getDependencyAnalyseBlock());

		file = getFile("log_build_with_empty_dependency_analyze_color");

		parser.parseLogFile(file);

		Assert.assertNotNull("No dependency:analyze block found", parser.getDependencyAnalyseBlock());
	}

	@Test
	public void testGetDependencyAnalyseSectionEmptyWithExecutionId() throws Exception {
		File file = getFile("log_build_with_empty_dependency_analyze_with_execution_id");

		parser.parseLogFile(file);

		Assert.assertNotNull("No dependency:analyze block found", parser.getDependencyAnalyseBlock());

		file = getFile("log_build_with_empty_dependency_analyze_with_execution_id_color");

		parser.parseLogFile(file);

		Assert.assertNotNull("No dependency:analyze block found", parser.getDependencyAnalyseBlock());
	}

	@Test
	public void testGetDependencyAnalyzeSectionPresent() throws Exception {
		File file = getFile("log_build_with_dependency_analyze");

		parser.parseLogFile(file);

		String result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze block must be found", result);

		List<String> lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 6, lines.size());

		file = getFile("log_build_with_dependency_analyze_color");

		parser.parseLogFile(file);

		result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze block must be found", result);

		lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 6, lines.size());
	}

	@Test
	public void testGetDependencyAnalyzeOnlySectionPresent() throws Exception {
		File file = getFile("log_build_with_dependency_analyze_only");

		parser.parseLogFile(file);

		String result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze-only block must be found", result);

		List<String> lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 10, lines.size());

		file = getFile("log_build_with_dependency_analyze_only_color");

		parser.parseLogFile(file);

		result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze-only block must be found", result);

		lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 10, lines.size());

	}

	@Test
	public void testGetDependencyAnalyseSectionPresentWithExecutionId() throws Exception {
		File file = getFile("log_build_with_dependency_analyze_with_execution_id");

		parser.parseLogFile(file);

		String result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze block must be found", result);

		List<String> lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 3, lines.size());

		file = getFile("log_build_with_dependency_analyze_with_execution_id_color");

		parser.parseLogFile(file);

		result = parser.getDependencyAnalyseBlock();

		Assert.assertNotNull("dependency:analyze block must be found", result);
		lines = IOUtils.readLines(new StringReader(result));
		Assert.assertEquals("Wrong number of line returned, ", 3, lines.size());

	}

}
