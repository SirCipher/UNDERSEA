package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.factory.FactoryProvider;
import com.type2labs.undersea.dsl.uuv.gen.SensorsLexer;
import com.type2labs.undersea.dsl.uuv.gen.SensorsParser;
import com.type2labs.undersea.dsl.uuv.gen.UUVLexer;
import com.type2labs.undersea.dsl.uuv.gen.UUVParser;
import com.type2labs.undersea.dsl.uuv.listener.SensorListener;
import com.type2labs.undersea.dsl.uuv.listener.UUVListener;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ParserEngine {

    private static final Logger logger = LogManager.getLogger(ParserEngine.class);
    public static Properties properties;
    private final EnvironmentProperties environmentProperties = new EnvironmentProperties();
    private boolean errorsFound = false;

    public ParserEngine(Properties runnerProperties) {
        ParserEngine.properties = runnerProperties;
        EnvironmentProperties.setRunnerProperties(runnerProperties);
    }

    public EnvironmentProperties getEnvironmentProperties() {
        return environmentProperties;
    }

    private SensorsParser createSensorParser() throws IOException {
        // create a CharStream that reads from standard input

        String sensorsFile = Utility.getProperty(ParserEngine.properties, "config.sensors");
        CharStream codePointCharStream = CharStreams.fromFileName(sensorsFile);

        // create a lexer that feeds off of input CharStream
        SensorsLexer lexer = new SensorsLexer(codePointCharStream);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create error parser
        BaseErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, RecognitionException e) {
                //Print the syntax error
                logger.error(String.format("\t%s at (%d, %d)%n", msg, line, charPositionInLine));
            }
        };

        // create a parser that feeds off the tokens buffer
        SensorsParser parser = new SensorsParser(tokens);

        // add error listener to parser and lexer
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        return parser;
    }

    private UUVParser createUUVParser() throws IOException {
        // create a CharStream that reads from standard input
        String missionFile = Utility.getProperty(ParserEngine.properties, "config.mission");
        CharStream codePointCharStream = CharStreams.fromFileName(missionFile);

        // create a lexer that feeds off of input CharStream
        UUVLexer lexer = new UUVLexer(codePointCharStream);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create error parser
        BaseErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, RecognitionException e) {
                //Print the syntax error
                logger.error(String.format("\t%s at (%d, %d)%n", msg, line, charPositionInLine));
                errorsFound = true;
            }
        };

        // create a parser that feeds off the tokens buffer
        UUVParser parser = new UUVParser(tokens);

        // add error listener to parser and lexer
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        return parser;
    }

    public EnvironmentProperties parseMission() throws IOException {
        runSensorListener();
        runUUVListener();

        String missionName =
                environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.MISSION_NAME);

        String buildDir = Utility.getProperty(ParserEngine.properties, "config.output");
        buildDir += File.separator + missionName;
        ParserEngine.properties.setProperty("config.output", buildDir);

        String missionIncludesDir = Utility.getProperty(ParserEngine.properties, "config.includes");

        EnvironmentBuilder.setEnvironmentProperties(environmentProperties);
        EnvironmentBuilder.initDirectories(buildDir, missionIncludesDir);

        logger.info("Parsed: " + environmentProperties.getAgents().size() + " agents");
        logger.info("Parsed: " + FactoryProvider.getSensorFactory().getSensors().size() + " sensors");
        logger.info("Environment values: " + environmentProperties.getEnvironmentValues().toString() + "\n");

        environmentProperties.validateEnvironmentValues();

        if (errorsFound) {
            throw new UnderseaException("Errors found while parsing configuration files");
        } else {
            logger.info("Successfully parsed configuration files");

            MoosConfigurationWriter.init(environmentProperties);
            MoosConfigurationWriter.run();

            return environmentProperties;
        }
    }

    public void generateFiles() {
        EnvironmentBuilder.build();
    }

    private void runSensorListener() throws IOException {
        //create parser
        SensorsParser parser = createSensorParser();

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parseMission tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        SensorListener listener = new SensorListener();

        // Walk the tree created during the parseMission, trigger callbacks
        walker.walk(listener, tree);
    }

    private void runUUVListener() throws IOException {
        //create parser
        UUVParser parser = createUUVParser();

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parseMission tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        UUVListener listener = new UUVListener();
        listener.setEnvironmentProperties(environmentProperties);

        // Walk the tree created during the parseMission, trigger callbacks
        walker.walk(listener, tree);
    }

}
