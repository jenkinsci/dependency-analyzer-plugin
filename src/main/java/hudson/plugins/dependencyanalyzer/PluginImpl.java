package hudson.plugins.dependencyanalyzer;

import hudson.Plugin;
import hudson.tasks.BuildStep;

import java.util.logging.Logger;

public class PluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(PluginImpl.class.getName());

    public void start() throws Exception {
    	LOGGER.info("Starting dependency analyzer plugin...");
    	
        BuildStep.PUBLISHERS.add(DependencyAnalyzerPublisher.DESCRIPTOR	);
    }
}
