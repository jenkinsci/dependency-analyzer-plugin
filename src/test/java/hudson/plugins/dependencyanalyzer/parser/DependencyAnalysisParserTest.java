package hudson.plugins.dependencyanalyzer.parser;

import hudson.plugins.dependencyanalyzer.result.DependencyProblemType;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DependencyAnalysisParserTest extends AbstractParserTestUtils {
	private final static String UNUSED_DEPENDENCY_1 = "org.apache.maven:maven-artifact-manager:jar:2.0:compile";
	private final static String UNUSED_DEPENDENCY_2 = "org.apache.maven:maven-artifact:jar:2.0:compile";
	private final static String UNDECLARED_DEPENDENCY_1 = "org.apache.maven:maven-model:jar:2.0.2:compile";
	private final static String UNDECLARED_DEPENDENCY_2 = "org.codehaus.plexus:plexus-utils:jar:1.1:compile";
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParseDependencyAnalyzeSection() throws Exception {
		File file = getFile("dependency_analyze_section_1");
		FileReader reader = new FileReader(file);

		String content = IOUtils.toString(reader);

		Map<DependencyProblemType, List<String>> result = DependencyAnalysisParser
				.parseDependencyAnalyzeSection(content);

		Assert.assertEquals(
				"Must have unsused declared and used undeclared dependencies",
				2, result.size());

		List<String> list = result.get(DependencyProblemType.UNUSED);
		Assert.assertEquals("Must have 2 unused declared dependencies", 2,
				list.size());
		Assert.assertTrue(
						"Unused declared dependencies must contains " + UNUSED_DEPENDENCY_1,
						list.contains(UNUSED_DEPENDENCY_1));
		Assert.assertTrue(
				"Unused declared dependencies must contains " + UNUSED_DEPENDENCY_2,
				list.contains(UNUSED_DEPENDENCY_2));
		
		list = result.get(DependencyProblemType.UNDECLARED);
		Assert.assertEquals("Must have 2 undeclared used dependencies", 2,
				list.size());
		Assert.assertTrue(
						"Used undeclared dependencies must contains " + UNDECLARED_DEPENDENCY_1,
						list.contains(UNDECLARED_DEPENDENCY_1));
		Assert.assertTrue(
				"Used undeclared dependencies must contains " + UNDECLARED_DEPENDENCY_2,
				list.contains(UNDECLARED_DEPENDENCY_2));
	}

}
