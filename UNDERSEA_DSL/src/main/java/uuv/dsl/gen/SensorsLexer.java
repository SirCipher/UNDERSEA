// Generated from Sensors.g4 by ANTLR 4.7.2

package uuv.dsl.gen;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SensorsLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, ASSIGN = 5, SERVER_HOST = 6, SENSORS = 7, BEHAVIOUR_FILE = 8,
            BHV_FILE = 9, PORT_START = 10, SIMULATION_TIME = 11, TIME_WINDOW = 12, SIMULATION_SPEED = 13,
            SPEED = 14, SENSOR = 15, NAME = 16, RATE = 17, CHANGE = 18, RELIABILITY = 19, SLCOMMENT = 20,
            ID = 21, BEGL = 22, ENDL = 23, SEP = 24, INT = 25, DOUBLE = 26, IP = 27, OCTET = 28, STRING = 29,
            WS = 30;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2 \u0120\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2" +
                    "\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3" +
                    "\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t" +
                    "\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3" +
                    "\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f" +
                    "\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3" +
                    "\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16" +
                    "\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20" +
                    "\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22" +
                    "\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24" +
                    "\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u00d9\n\25\f\25\16" +
                    "\25\u00dc\13\25\3\25\5\25\u00df\n\25\3\25\3\25\3\25\3\25\3\26\3\26\7\26" +
                    "\u00e7\n\26\f\26\16\26\u00ea\13\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32" +
                    "\6\32\u00f3\n\32\r\32\16\32\u00f4\3\33\5\33\u00f8\n\33\3\33\3\33\3\33" +
                    "\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35" +
                    "\3\35\3\35\5\35\u010d\n\35\3\36\3\36\7\36\u0111\n\36\f\36\16\36\u0114" +
                    "\13\36\3\36\3\36\3\37\6\37\u0119\n\37\r\37\16\37\u011a\3\37\3\37\3 \3" +
                    " \3\u00da\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16" +
                    "\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34" +
                    "\67\359\36;\37= ?\2\3\2\7\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\4\2$$^^\5" +
                    "\2\13\f\17\17\"\"\2\u0127\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2" +
                    "\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2" +
                    "\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3" +
                    "\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2" +
                    "\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67" +
                    "\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\3A\3\2\2\2\5H\3\2\2\2\7J\3\2" +
                    "\2\2\tL\3\2\2\2\13N\3\2\2\2\rP\3\2\2\2\17U\3\2\2\2\21]\3\2\2\2\23l\3\2" +
                    "\2\2\25r\3\2\2\2\27}\3\2\2\2\31\u008d\3\2\2\2\33\u0099\3\2\2\2\35\u00aa" +
                    "\3\2\2\2\37\u00b0\3\2\2\2!\u00b7\3\2\2\2#\u00bc\3\2\2\2%\u00c1\3\2\2\2" +
                    "\'\u00c8\3\2\2\2)\u00d4\3\2\2\2+\u00e4\3\2\2\2-\u00eb\3\2\2\2/\u00ed\3" +
                    "\2\2\2\61\u00ef\3\2\2\2\63\u00f2\3\2\2\2\65\u00f7\3\2\2\2\67\u00fc\3\2" +
                    "\2\29\u010c\3\2\2\2;\u010e\3\2\2\2=\u0118\3\2\2\2?\u011e\3\2\2\2AB\7U" +
                    "\2\2BC\7G\2\2CD\7P\2\2DE\7U\2\2EF\7Q\2\2FG\7T\2\2G\4\3\2\2\2HI\7}\2\2" +
                    "I\6\3\2\2\2JK\7\177\2\2K\b\3\2\2\2LM\7<\2\2M\n\3\2\2\2NO\7?\2\2O\f\3\2" +
                    "\2\2PQ\7j\2\2QR\7q\2\2RS\7u\2\2ST\7v\2\2T\16\3\2\2\2UV\7u\2\2VW\7g\2\2" +
                    "WX\7p\2\2XY\7u\2\2YZ\7q\2\2Z[\7t\2\2[\\\7u\2\2\\\20\3\2\2\2]^\7d\2\2^" +
                    "_\7g\2\2_`\7j\2\2`a\7c\2\2ab\7x\2\2bc\7k\2\2cd\7q\2\2de\7w\2\2ef\7t\2" +
                    "\2fg\7\"\2\2gh\7h\2\2hi\7k\2\2ij\7n\2\2jk\7g\2\2k\22\3\2\2\2lm\5+\26\2" +
                    "mn\7\60\2\2no\7d\2\2op\7j\2\2pq\7x\2\2q\24\3\2\2\2rs\7r\2\2st\7q\2\2t" +
                    "u\7t\2\2uv\7v\2\2vw\7\"\2\2wx\7u\2\2xy\7v\2\2yz\7c\2\2z{\7t\2\2{|\7v\2" +
                    "\2|\26\3\2\2\2}~\7u\2\2~\177\7k\2\2\177\u0080\7o\2\2\u0080\u0081\7w\2" +
                    "\2\u0081\u0082\7n\2\2\u0082\u0083\7c\2\2\u0083\u0084\7v\2\2\u0084\u0085" +
                    "\7k\2\2\u0085\u0086\7q\2\2\u0086\u0087\7p\2\2\u0087\u0088\7\"\2\2\u0088" +
                    "\u0089\7v\2\2\u0089\u008a\7k\2\2\u008a\u008b\7o\2\2\u008b\u008c\7g\2\2" +
                    "\u008c\30\3\2\2\2\u008d\u008e\7v\2\2\u008e\u008f\7k\2\2\u008f\u0090\7" +
                    "o\2\2\u0090\u0091\7g\2\2\u0091\u0092\7\"\2\2\u0092\u0093\7y\2\2\u0093" +
                    "\u0094\7k\2\2\u0094\u0095\7p\2\2\u0095\u0096\7f\2\2\u0096\u0097\7q\2\2" +
                    "\u0097\u0098\7y\2\2\u0098\32\3\2\2\2\u0099\u009a\7u\2\2\u009a\u009b\7" +
                    "k\2\2\u009b\u009c\7o\2\2\u009c\u009d\7w\2\2\u009d\u009e\7n\2\2\u009e\u009f" +
                    "\7c\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\7k\2\2\u00a1\u00a2\7q\2\2\u00a2" +
                    "\u00a3\7p\2\2\u00a3\u00a4\7\"\2\2\u00a4\u00a5\7u\2\2\u00a5\u00a6\7r\2" +
                    "\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7g\2\2\u00a8\u00a9\7f\2\2\u00a9\34\3" +
                    "\2\2\2\u00aa\u00ab\7u\2\2\u00ab\u00ac\7r\2\2\u00ac\u00ad\7g\2\2\u00ad" +
                    "\u00ae\7g\2\2\u00ae\u00af\7f\2\2\u00af\36\3\2\2\2\u00b0\u00b1\7u\2\2\u00b1" +
                    "\u00b2\7g\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b4\7u\2\2\u00b4\u00b5\7q\2\2" +
                    "\u00b5\u00b6\7t\2\2\u00b6 \3\2\2\2\u00b7\u00b8\7p\2\2\u00b8\u00b9\7c\2" +
                    "\2\u00b9\u00ba\7o\2\2\u00ba\u00bb\7g\2\2\u00bb\"\3\2\2\2\u00bc\u00bd\7" +
                    "t\2\2\u00bd\u00be\7c\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0\7g\2\2\u00c0$" +
                    "\3\2\2\2\u00c1\u00c2\7e\2\2\u00c2\u00c3\7j\2\2\u00c3\u00c4\7c\2\2\u00c4" +
                    "\u00c5\7p\2\2\u00c5\u00c6\7i\2\2\u00c6\u00c7\7g\2\2\u00c7&\3\2\2\2\u00c8" +
                    "\u00c9\7t\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7n\2\2\u00cb\u00cc\7k\2\2" +
                    "\u00cc\u00cd\7c\2\2\u00cd\u00ce\7d\2\2\u00ce\u00cf\7k\2\2\u00cf\u00d0" +
                    "\7n\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7v\2\2\u00d2\u00d3\7{\2\2\u00d3" +
                    "(\3\2\2\2\u00d4\u00d5\7\61\2\2\u00d5\u00d6\7\61\2\2\u00d6\u00da\3\2\2" +
                    "\2\u00d7\u00d9\13\2\2\2\u00d8\u00d7\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da" +
                    "\u00db\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3\2" +
                    "\2\2\u00dd\u00df\7\17\2\2\u00de\u00dd\3\2\2\2\u00de\u00df\3\2\2\2\u00df" +
                    "\u00e0\3\2\2\2\u00e0\u00e1\7\f\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\b\25" +
                    "\2\2\u00e3*\3\2\2\2\u00e4\u00e8\t\2\2\2\u00e5\u00e7\t\3\2\2\u00e6\u00e5" +
                    "\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9" +
                    ",\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ec\7]\2\2\u00ec.\3\2\2\2\u00ed" +
                    "\u00ee\7_\2\2\u00ee\60\3\2\2\2\u00ef\u00f0\7.\2\2\u00f0\62\3\2\2\2\u00f1" +
                    "\u00f3\t\4\2\2\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f2\3\2" +
                    "\2\2\u00f4\u00f5\3\2\2\2\u00f5\64\3\2\2\2\u00f6\u00f8\5\63\32\2\u00f7" +
                    "\u00f6\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\7\60" +
                    "\2\2\u00fa\u00fb\5\63\32\2\u00fb\66\3\2\2\2\u00fc\u00fd\59\35\2\u00fd" +
                    "\u00fe\7\60\2\2\u00fe\u00ff\59\35\2\u00ff\u0100\7\60\2\2\u0100\u0101\5" +
                    "9\35\2\u0101\u0102\7\60\2\2\u0102\u0103\59\35\2\u01038\3\2\2\2\u0104\u0105" +
                    "\5? \2\u0105\u0106\5? \2\u0106\u0107\5? \2\u0107\u010d\3\2\2\2\u0108\u0109" +
                    "\5? \2\u0109\u010a\5? \2\u010a\u010d\3\2\2\2\u010b\u010d\5? \2\u010c\u0104" +
                    "\3\2\2\2\u010c\u0108\3\2\2\2\u010c\u010b\3\2\2\2\u010d:\3\2\2\2\u010e" +
                    "\u0112\7$\2\2\u010f\u0111\n\5\2\2\u0110\u010f\3\2\2\2\u0111\u0114\3\2" +
                    "\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0115\3\2\2\2\u0114" +
                    "\u0112\3\2\2\2\u0115\u0116\7$\2\2\u0116<\3\2\2\2\u0117\u0119\t\6\2\2\u0118" +
                    "\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2" +
                    "\2\2\u011b\u011c\3\2\2\2\u011c\u011d\b\37\2\2\u011d>\3\2\2\2\u011e\u011f" +
                    "\4\62;\2\u011f@\3\2\2\2\13\2\u00da\u00de\u00e8\u00f4\u00f7\u010c\u0112" +
                    "\u011a\3\b\2\2";
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

    public SensorsLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "ASSIGN", "SERVER_HOST", "SENSORS", "BEHAVIOUR_FILE",
                "BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED",
                "SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT",
                "ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", "STRING",
                "WS", "DIGIT"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'SENSOR'", "'{'", "'}'", "':'", "'='", "'host'", "'sensors'",
                "'behaviour file'", null, "'port start'", "'simulation time'", "'time window'",
                "'simulation speed'", "'speed'", "'sensor'", "'name'", "'rate'", "'change'",
                "'reliability'", null, null, "'['", "']'", "','"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SENSORS", "BEHAVIOUR_FILE",
                "BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED",
                "SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT",
                "ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", "STRING",
                "WS"
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
        return "Sensors.g4";
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