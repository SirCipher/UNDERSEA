package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.factory.FactoryProvider;
import com.type2labs.undersea.dsl.uuv.gen.SensorsLexer;
import com.type2labs.undersea.dsl.uuv.gen.SensorsParser;
import com.type2labs.undersea.dsl.uuv.gen.UUVLexer;
import com.type2labs.undersea.dsl.uuv.gen.UUVParser;
import com.type2labs.undersea.dsl.uuv.listener.SensorListener;
import com.type2labs.undersea.dsl.uuv.listener.UUVListener;
import com.type2labs.undersea.dsl.uuv.properties.EnvironmentProperties;
import com.type2labs.undersea.utility.Utility;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserEngine {

    private static final Logger logger = LogManager.getLogger(ParserEngine.class);

    private static final EnvironmentProperties environmentProperties = EnvironmentProperties.getInstance();
    static String buildDir;
    static String missionIncludesDir;
    static String missionDir;
    private static String propertiesFile;
    private static String configFile;
    private static File missionDirectory;
    private static String controllerDir;
    private static String sensorsFile;
    private static boolean errorsFound = false;

    private static SensorsParser createSensorParser() throws IOException {
        // create a CharStream that reads from standard input
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

    private static UUVParser createUUVParser() throws IOException {
        // create a CharStream that reads from standard input
        CharStream codePointCharStream = CharStreams.fromFileName(configFile);

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

    public static EnvironmentProperties main(String[] args) throws IOException {
        parseCommandLineArguments(args);

        runSensorListener();
        runUUVListener();

        String missionName =
                environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.MISSION_NAME);

        missionDir += File.separator + missionName;
        buildDir += File.separator + missionName;

        EnvironmentBuilder.initDirectories(missionDir, buildDir, missionIncludesDir);

        logger.info("Parsed: " + environmentProperties.getAgents().size() + " agents");
        logger.info("Parsed: " + FactoryProvider.getSensorFactory().getSensors().size() + " sensors");
        logger.info("Environment values: " + environmentProperties.getEnvironmentValues().toString() + "\n");

        environmentProperties.validateEnvironmentValues();

        if (errorsFound) {
            throw new DSLException("Errors found while parsing configuration files");
        } else {
            logger.info("Successfully parsed configuration files");
            MoosConfigurationWriter.run();
            EnvironmentBuilder.build();
            return environmentProperties;
        }
    }

    private static void parseCommandLineArguments(String[] args) throws FileNotFoundException {
        if (args.length != 7) {
            throw new DSLException(
                    "Incorrect number of arguments! Please fix this error!\n" +
                            "\tArg 1) Mission configuration file (e.g., mission.config)\n" +
                            "\tArg 2) Sensor configuration file (e.g., sensors.config\n" +
                            "\tArg 3) Controller directory (e.g., UUV_Controller)\n" +
                            "\tArg 4) Mission directory (e.g., moos-ivp-extend/missions/uuvExemplar)\n" +
                            "\tArg 5) Config.properties file" +
                            "\tArg 6) Output dir (e.g., build/resources)" +
                            "\tArg 7) Mission include dir (e.g. mission-includes/)"

            );
        }

        if (Utility.fileExists(args[4], false, false)) {
            propertiesFile = args[4];
            Utility.setProperties(new File(ParserEngine.propertiesFile));
        }

        if (Utility.fileExists(args[0], false, false)) {
            configFile = args[0];
            missionDirectory = new File(configFile).getParentFile();
        }

        if (Utility.fileExists(args[1], false, false)) {
            sensorsFile = args[1];
        }

        if (Utility.fileExists(args[2], true, true)) {
            controllerDir = args[2];
        }

        if (Utility.fileExists(args[3], true, true)) {
            missionDir = args[3];
        }

        if (Utility.fileExists(args[5], true, true)) {
            buildDir = args[5];
        }

        if (Utility.fileExists(args[6], true, false)) {
            missionIncludesDir = args[6];
        }
    }

    private static void runSensorListener() throws IOException {
        //create parser
        SensorsParser parser = createSensorParser();

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        SensorListener listener = new SensorListener();

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);
    }

    private static void runUUVListener() throws IOException {
        //create parser
        UUVParser parser = createUUVParser();

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        UUVListener listener = new UUVListener();
        listener.setEnvironmentProperties(environmentProperties);

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);
    }

}
