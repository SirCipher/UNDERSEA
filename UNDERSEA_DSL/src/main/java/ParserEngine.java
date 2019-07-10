package main;

import auxiliary.DSLException;
import auxiliary.Utility;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import uuv.dsl.UUVListener;
import uuv.dsl.gen.UUVLexer;
import uuv.dsl.gen.UUVParser;
import uuv.dsl.gen.UUVVisitor;
import uuv.properties.UUVproperties;

import java.io.FileNotFoundException;

public class ParserEngine {

    public static String propertiesFile = "resources/config.properties";
    public static String configFile;
    public static String controllerDir;
    public static String missionDir;


    public static void main(String args[]) {
        try {
            parseCommandLineArguments(args);

            String source = Utility.readFile(configFile);

            ParserEngine engine = new ParserEngine();

            //run listener/visitor
            UUVproperties properties = engine.runListerner(source);

            //generate moos files
            properties.generateMoosBlocks();

            System.out.println("Configuration file parsed successfully\n");
            System.exit(1);

        } catch (DSLException | FileNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(0);
        }
    }


    private static void parseCommandLineArguments(String[] args) throws DSLException, FileNotFoundException {
        if (args.length != 3)
            throw new DSLException("Incorrect number of arguments! Please fix this error!\n" +
                    "\tArg 1) configuration file (e.g., mission.config)\n" +
                    "\tArg 2) controller directory (e.g., UUV_Controller)\n" +
                    "\tArg 3) mission directory (e.g., moos-ivp-extend/missions/uuvExemplar)\n"
            );


        if (Utility.fileExists(args[0]))
            configFile = args[0];

        if (Utility.fileExists(args[1]))
            controllerDir = args[1];

        if (Utility.fileExists(args[2]))
            missionDir = args[2];
    }


    /**
     * Create parser
     *
     * @param source
     * @return
     */
    private UUVParser createParser(String source) {
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(source);
        // create a lexer that feeds off of input CharStream
        UUVLexer lexer = new UUVLexer(input);
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create error parser
        BaseErrorListener errorListener = createErrorListener();
        // create a parser that feeds off the tokens buffer
        UUVParser parser = new UUVParser(tokens);
        //add error listerner to parser and lexer
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        return parser;

    }


    /**
     * Create error listener
     *
     * @return
     */
    private BaseErrorListener createErrorListener() {
        BaseErrorListener errorListener = new BaseErrorListener() {

            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, @Nullable RecognitionException e) {
                //Print the syntax error 
                System.out.printf("\t%s at (%d, %d)%n", msg, line, charPositionInLine);
            }
        };
        return errorListener;
    }


    /**
     * Run visitor
     *
     * @param source
     */
    @SuppressWarnings("unused")
    private void runVisitor(String source) {
        //create parser
        UUVParser parser = createParser(source);
        // begin parsing at prog rule
        ParseTree tree = parser.model();
        //Create the visitor
        UUVVisitor visitor = null;//new UUVVisitor();
        // and visit the nodes
        visitor.visit(tree);
    }


    /**
     * Run listener
     *
     * @param source
     */
    @SuppressWarnings("unused")
    private UUVproperties runListerner(String source) {
        //create parser
        UUVParser parser = createParser(source);
        // begin parsing at model rule
        ParseTree tree = parser.model();
        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Create a listener
        UUVListener listener = new UUVListener();
        // Walk the tree created during the parse, trigger callbacks
        walker.walk(listener, tree);

        UUVproperties properties = null;
        try {
            properties = listener.getProperties();
            properties.checkProperties();
        } catch (DSLException e) {
            System.err.println("ERROR: \t" + e.getMessage());
            System.exit(0);
        }

        return properties;
    }

}
