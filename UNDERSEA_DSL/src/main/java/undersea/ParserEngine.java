package undersea;

import undersea.auxiliary.Utility;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import undersea.uuv.dsl.SensorListener;
import undersea.uuv.dsl.UUVListener;
import undersea.uuv.dsl.gen.SensorsLexer;
import undersea.uuv.dsl.gen.SensorsParser;
import undersea.uuv.dsl.gen.UUVLexer;
import undersea.uuv.dsl.gen.UUVParser;
import undersea.uuv.properties.SimulationProperties;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserEngine {

    public static String propertiesFile = "resources/config.properties";
    static String controllerDir;
    static String missionDir;
    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();
    private static String configFile;
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
                System.out.printf("\t%s at (%d, %d)%n", msg, line, charPositionInLine);
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
                System.out.printf("\t%s at (%d, %d)%n", msg, line, charPositionInLine);
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

    public static void main(String[] args) throws IOException {
        parseCommandLineArguments(args);
        runSensorListener();
        runUUVListener();

        simulationProperties.validateEnvironmentValues();

        if (errorsFound) {
            System.out.println("Errors found while parsing configuration files");
        } else {
            System.out.println("Successfully parsed configuration files");
            MoosConfigurationWriter.run();
            EnvironmentBuilder.build(controllerDir, missionDir, configFile, sensorsFile);
        }
    }

    private static void parseCommandLineArguments(String[] args) throws FileNotFoundException {
        if (args.length != 4) {
            throw new DSLException(
                    "Incorrect number of arguments! Please fix this error!\n" +
                            "\tArg 1) configuration file (e.g., mission.config)\n" +
                            "\tArg 2) sensor configuration file (e.g., sensors.config\n" +
                            "\tArg 3) controller directory (e.g., UUV_Controller)\n" +
                            "\tArg 4) mission directory (e.g., moos-ivp-extend/missions/uuvExemplar)\n"
            );
        }

        if (Utility.fileExists(args[0])) {
            configFile = args[0];
        }

        if (Utility.fileExists(args[1])) {
            sensorsFile = args[1];
        }

        if (Utility.fileExists(args[2])) {
            controllerDir = args[2];
        }

        if (Utility.fileExists(args[3])) {
            missionDir = args[3];
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
        listener.setSimulationProperties(simulationProperties);

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);
    }

}
