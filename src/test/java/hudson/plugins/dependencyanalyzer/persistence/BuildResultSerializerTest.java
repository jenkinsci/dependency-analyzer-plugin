package hudson.plugins.dependencyanalyzer.persistence;

import hudson.maven.ModuleName;
import hudson.plugins.dependencyanalyzer.result.BuildResult;
import hudson.plugins.dependencyanalyzer.result.DependencyProblemType;
import hudson.plugins.dependencyanalyzer.result.ModuleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

public class BuildResultSerializerTest {
	
	@Test
	public void testSerializeDeserialize() throws Exception {
		BuildResult result = new BuildResult();
		ModuleResult module = new ModuleResult();
		result.addResult(module);
		module.setDisplayName("name1");
		module.setModuleName(new ModuleName("group1", "artifact1"));
		
		Map<DependencyProblemType, List<String>> problemsMap = new HashMap<DependencyProblemType, List<String>>();
	
		List<String> problems = new ArrayList<String>();
		problems.add("pb1");
		problems.add("pb2");
		problemsMap.put(DependencyProblemType.UNDECLARED, problems);
		problemsMap.put(DependencyProblemType.UNUSED, new ArrayList<String>(problems));
		
		module.setDependencyProblems(problemsMap);
		
		String tmpDir = System.getProperty("java.io.tmpdir");
		System.out.println(tmpDir);
		
		BuildResultSerializer.serialize(tmpDir, result);
		BuildResult result2 = BuildResultSerializer.deserialize(tmpDir);
		
		System.out.println(result.toString());
		System.out.println(result2.toString());
		
		Assert.assertEquals("Initial and serialize object are differents", result.toString(), result2.toString());  
	}
	
}
