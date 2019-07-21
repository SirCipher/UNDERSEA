// Generated from UUV.g4 by ANTLR 4.7.2

package undersea.uuv.dsl.gen;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UUVLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, ASSIGN = 6, MISSION_NAME = 7, NAME = 8,
            TIME_WINDOW = 9, SERVER_HOST = 10, SENSORS = 11, DOUBLE = 12, PORT_START = 13, SENSOR_PORT = 14,
            SIMULATION_TIME = 15, SIMULATION_SPEED = 16, INT = 17, IP = 18, OCTET = 19, BEGL = 20,
            ENDL = 21, SEP = 22, SPEED = 23, ID = 24, WS = 25, ErrorCharacter = 26;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\34\u00f3\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3" +
                    "\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b" +
                    "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3" +
                    "\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3" +
                    "\f\3\f\3\f\3\f\3\f\3\f\3\r\5\r|\n\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3" +
                    "\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3" +
                    "\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3" +
                    "\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3" +
                    "\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3" +
                    "\21\3\21\3\21\3\22\6\22\u00c1\n\22\r\22\16\22\u00c2\3\23\3\23\3\23\3\23" +
                    "\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25" +
                    "\5\25\u00d7\n\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31" +
                    "\3\31\3\32\3\32\6\32\u00e7\n\32\r\32\16\32\u00e8\3\33\6\33\u00ec\n\33" +
                    "\r\33\16\33\u00ed\3\33\3\33\3\34\3\34\2\2\35\3\3\5\4\7\5\t\6\13\7\r\b" +
                    "\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\2)\25" +
                    "+\26-\27/\30\61\31\63\32\65\33\67\34\3\2\6\3\2\62;\5\2C\\aac|\6\2\62;" +
                    "C\\aac|\5\2\13\f\17\17\"\"\2\u00f7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2" +
                    "\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3" +
                    "\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2" +
                    "\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2" +
                    "\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2" +
                    "\2\2\39\3\2\2\2\5C\3\2\2\2\7G\3\2\2\2\tI\3\2\2\2\13K\3\2\2\2\rM\3\2\2" +
                    "\2\17O\3\2\2\2\21\\\3\2\2\2\23a\3\2\2\2\25m\3\2\2\2\27r\3\2\2\2\31{\3" +
                    "\2\2\2\33\u0080\3\2\2\2\35\u0092\3\2\2\2\37\u009e\3\2\2\2!\u00ae\3\2\2" +
                    "\2#\u00c0\3\2\2\2%\u00c4\3\2\2\2\'\u00cc\3\2\2\2)\u00d6\3\2\2\2+\u00d8" +
                    "\3\2\2\2-\u00da\3\2\2\2/\u00dc\3\2\2\2\61\u00de\3\2\2\2\63\u00e4\3\2\2" +
                    "\2\65\u00eb\3\2\2\2\67\u00f1\3\2\2\29:\7n\2\2:;\7q\2\2;<\7e\2\2<=\7c\2" +
                    "\2=>\7n\2\2>?\7j\2\2?@\7q\2\2@A\7u\2\2AB\7v\2\2B\4\3\2\2\2CD\7W\2\2DE" +
                    "\7W\2\2EF\7X\2\2F\6\3\2\2\2GH\7}\2\2H\b\3\2\2\2IJ\7<\2\2J\n\3\2\2\2KL" +
                    "\7\177\2\2L\f\3\2\2\2MN\7?\2\2N\16\3\2\2\2OP\7o\2\2PQ\7k\2\2QR\7u\2\2" +
                    "RS\7u\2\2ST\7k\2\2TU\7q\2\2UV\7p\2\2VW\7\"\2\2WX\7p\2\2XY\7c\2\2YZ\7o" +
                    "\2\2Z[\7g\2\2[\20\3\2\2\2\\]\7p\2\2]^\7c\2\2^_\7o\2\2_`\7g\2\2`\22\3\2" +
                    "\2\2ab\7v\2\2bc\7k\2\2cd\7o\2\2de\7g\2\2ef\7\"\2\2fg\7y\2\2gh\7k\2\2h" +
                    "i\7p\2\2ij\7f\2\2jk\7q\2\2kl\7y\2\2l\24\3\2\2\2mn\7j\2\2no\7q\2\2op\7" +
                    "u\2\2pq\7v\2\2q\26\3\2\2\2rs\7u\2\2st\7g\2\2tu\7p\2\2uv\7u\2\2vw\7q\2" +
                    "\2wx\7t\2\2xy\7u\2\2y\30\3\2\2\2z|\5#\22\2{z\3\2\2\2{|\3\2\2\2|}\3\2\2" +
                    "\2}~\7\60\2\2~\177\5#\22\2\177\32\3\2\2\2\u0080\u0081\7u\2\2\u0081\u0082" +
                    "\7g\2\2\u0082\u0083\7t\2\2\u0083\u0084\7x\2\2\u0084\u0085\7g\2\2\u0085" +
                    "\u0086\7t\2\2\u0086\u0087\7\"\2\2\u0087\u0088\7r\2\2\u0088\u0089\7q\2" +
                    "\2\u0089\u008a\7t\2\2\u008a\u008b\7v\2\2\u008b\u008c\7\"\2\2\u008c\u008d" +
                    "\7u\2\2\u008d\u008e\7v\2\2\u008e\u008f\7c\2\2\u008f\u0090\7t\2\2\u0090" +
                    "\u0091\7v\2\2\u0091\34\3\2\2\2\u0092\u0093\7u\2\2\u0093\u0094\7g\2\2\u0094" +
                    "\u0095\7p\2\2\u0095\u0096\7u\2\2\u0096\u0097\7q\2\2\u0097\u0098\7t\2\2" +
                    "\u0098\u0099\7\"\2\2\u0099\u009a\7r\2\2\u009a\u009b\7q\2\2\u009b\u009c" +
                    "\7t\2\2\u009c\u009d\7v\2\2\u009d\36\3\2\2\2\u009e\u009f\7u\2\2\u009f\u00a0" +
                    "\7k\2\2\u00a0\u00a1\7o\2\2\u00a1\u00a2\7w\2\2\u00a2\u00a3\7n\2\2\u00a3" +
                    "\u00a4\7c\2\2\u00a4\u00a5\7v\2\2\u00a5\u00a6\7k\2\2\u00a6\u00a7\7q\2\2" +
                    "\u00a7\u00a8\7p\2\2\u00a8\u00a9\7\"\2\2\u00a9\u00aa\7v\2\2\u00aa\u00ab" +
                    "\7k\2\2\u00ab\u00ac\7o\2\2\u00ac\u00ad\7g\2\2\u00ad \3\2\2\2\u00ae\u00af" +
                    "\7u\2\2\u00af\u00b0\7k\2\2\u00b0\u00b1\7o\2\2\u00b1\u00b2\7w\2\2\u00b2" +
                    "\u00b3\7n\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7k\2\2" +
                    "\u00b6\u00b7\7q\2\2\u00b7\u00b8\7p\2\2\u00b8\u00b9\7\"\2\2\u00b9\u00ba" +
                    "\7u\2\2\u00ba\u00bb\7r\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7g\2\2\u00bd" +
                    "\u00be\7f\2\2\u00be\"\3\2\2\2\u00bf\u00c1\t\2\2\2\u00c0\u00bf\3\2\2\2" +
                    "\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3$\3" +
                    "\2\2\2\u00c4\u00c5\5)\25\2\u00c5\u00c6\7\60\2\2\u00c6\u00c7\5)\25\2\u00c7" +
                    "\u00c8\7\60\2\2\u00c8\u00c9\5)\25\2\u00c9\u00ca\7\60\2\2\u00ca\u00cb\5" +
                    ")\25\2\u00cb&\3\2\2\2\u00cc\u00cd\4\62;\2\u00cd(\3\2\2\2\u00ce\u00cf\5" +
                    "\'\24\2\u00cf\u00d0\5\'\24\2\u00d0\u00d1\5\'\24\2\u00d1\u00d7\3\2\2\2" +
                    "\u00d2\u00d3\5\'\24\2\u00d3\u00d4\5\'\24\2\u00d4\u00d7\3\2\2\2\u00d5\u00d7" +
                    "\5\'\24\2\u00d6\u00ce\3\2\2\2\u00d6\u00d2\3\2\2\2\u00d6\u00d5\3\2\2\2" +
                    "\u00d7*\3\2\2\2\u00d8\u00d9\7]\2\2\u00d9,\3\2\2\2\u00da\u00db\7_\2\2\u00db" +
                    ".\3\2\2\2\u00dc\u00dd\7.\2\2\u00dd\60\3\2\2\2\u00de\u00df\7u\2\2\u00df" +
                    "\u00e0\7r\2\2\u00e0\u00e1\7g\2\2\u00e1\u00e2\7g\2\2\u00e2\u00e3\7f\2\2" +
                    "\u00e3\62\3\2\2\2\u00e4\u00e6\t\3\2\2\u00e5\u00e7\t\4\2\2\u00e6\u00e5" +
                    "\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9" +
                    "\64\3\2\2\2\u00ea\u00ec\t\5\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed\3\2\2" +
                    "\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0" +
                    "\b\33\2\2\u00f0\66\3\2\2\2\u00f1\u00f2\13\2\2\2\u00f28\3\2\2\2\b\2{\u00c2" +
                    "\u00d6\u00e8\u00ed\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public UUVLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "T__4", "ASSIGN", "MISSION_NAME", "NAME",
                "TIME_WINDOW", "SERVER_HOST", "SENSORS", "DOUBLE", "PORT_START", "SENSOR_PORT",
                "SIMULATION_TIME", "SIMULATION_SPEED", "INT", "IP", "DIGIT", "OCTET",
                "BEGL", "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'mission name'",
                "'name'", "'time window'", "'host'", "'sensors'", null, "'server port start'",
                "'sensor port'", "'simulation time'", "'simulation speed'", null, null,
                null, "'['", "']'", "','", "'speed'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, null, "ASSIGN", "MISSION_NAME", "NAME",
                "TIME_WINDOW", "SERVER_HOST", "SENSORS", "DOUBLE", "PORT_START", "SENSOR_PORT",
                "SIMULATION_TIME", "SIMULATION_SPEED", "INT", "IP", "OCTET", "BEGL",
                "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
        };
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "UUV.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}