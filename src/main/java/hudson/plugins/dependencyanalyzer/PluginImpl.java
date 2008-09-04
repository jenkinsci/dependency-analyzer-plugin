package hudson.plugins.dependencyanalyzer;

import hudson.Plugin;
import hudson.tasks.BuildStep;

import java.util.logging.Logger;

/**
 * Entry point of a plugin.
 *
 * <p>
 * There must be one {@link Plugin} class in each plugin.
 * See javadoc of {@link Plugin} for more about what can be done on this class.
 *
 * @author Kohsuke Kawaguchi
 * @plugin 
 */
public class PluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(PluginImpl.class.getName());

    public void start() throws Exception {
    	LOGGER.info("Starting dependency analyzer plugin...");
    	
        // plugins normally extend Hudson by providing custom implementations
        // of 'extension points'. In this example, we'll add one builder.
        BuildStep.PUBLISHERS.add(DependencyAnalyzerPublisher.DESCRIPTOR	);
    }
}
