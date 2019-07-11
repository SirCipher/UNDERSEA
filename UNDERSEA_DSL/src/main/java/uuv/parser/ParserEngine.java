package uuv.parser;

import auxiliary.DSLException;
import auxiliary.Utility;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import uuv.dsl.SensorListener;
import uuv.dsl.UUVListener;
import uuv.dsl.gen.SensorsParser;
import uuv.dsl.gen.UUVLexer;
import uuv.dsl.gen.UUVParser;
import uuv.properties.SimulationProperties;

import java.io.FileNotFoundException;

@SuppressWarnings("Duplicates")
public class ParserEngine {

    public static String propertiesFile = "resources/config.properties";
    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();
    private static String controllerDir;
    private static String missionDir;
    private static String configFile;
    private static String sensorsFile;

    public static void main(String[] args) throws FileNotFoundException {
        parseCommandLineArguments(args);
        runUUVListener();
        runSensorListener();

        simulationProperties.validateEnvironmentValues();
    }

    private static void parseCommandLineArguments(String[] args) throws FileNotFoundException {
        if (args.length != 4) {
            throw new DSLException("Incorrect number of arguments! Please fix this error!\n" +
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

    private static UUVParser createUUVParser(String source) {
        // create a CharStream that reads from standard input
        // TODO: Update
        ANTLRInputStream input = new ANTLRInputStream(source);

        // create a lexer that feeds off of input CharStream
        UUVLexer lexer = new UUVLexer(input);

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
        UUVParser parser = new UUVParser(tokens);

        // add error listener to parser and lexer
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        return parser;
    }

    private static void runUUVListener() throws FileNotFoundException {
        String source = Utility.readFile(configFile);

        //create parser
        UUVParser parser = createUUVParser(source);

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        UUVListener listener = new UUVListener();
        listener.setSimulationProperties(simulationProperties);

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);

        // generate moos files
        // TODO: Add MOOS file generation
//        properties.generateMoosBlocks();

        System.out.println("UUV configuration file parsed successfully\n");
    }

    private static SensorsParser createSensorParser(String source) {
        // create a CharStream that reads from standard input
        // TODO: Update
        ANTLRInputStream input = new ANTLRInputStream(source);

        // create a lexer that feeds off of input CharStream
        UUVLexer lexer = new UUVLexer(input);

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

    private static void runSensorListener() throws FileNotFoundException {
        String source = Utility.readFile(sensorsFile);

        //create parser
        SensorsParser parser = createSensorParser(source);

        // begin parsing at model rule
        ParseTree tree = parser.model();

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        // Create a listener
        SensorListener listener = new SensorListener();
        listener.setSimulationProperties(simulationProperties);

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);

        System.out.println("Sensor configuration file parsed successfully\n");
    }

}
