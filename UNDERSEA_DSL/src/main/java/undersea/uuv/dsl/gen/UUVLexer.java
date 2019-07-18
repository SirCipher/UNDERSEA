// Generated from UUV.g4 by ANTLR 4.7.2

  package undersea.uuv.dsl.gen;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UUVLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ASSIGN=6, SERVER_HOST=7, SENSORS=8, 
		BEHAVIOUR_FILE=9, BHV_FILE=10, PORT_START=11, SIMULATION_TIME=12, TIME_WINDOW=13, 
		SIMULATION_SPEED=14, SPEED=15, SENSOR=16, NAME=17, RATE=18, CHANGE=19, 
		RELIABILITY=20, SLCOMMENT=21, ID=22, BEGL=23, ENDL=24, SEP=25, INT=26, 
		DOUBLE=27, IP=28, OCTET=29, STRING=30, WS=31;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "ASSIGN", "SERVER_HOST", "SENSORS", 
			"BEHAVIOUR_FILE", "BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", 
			"SIMULATION_SPEED", "SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", 
			"SLCOMMENT", "ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", 
			"STRING", "WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'host'", "'sensors'", 
			"'behaviour file'", null, "'serverPort start'", "'simulation time'", 
			"'time window'", "'simulation speed'", "'speed'", "'sensor'", "'name'", 
			"'rate'", "'change'", "'reliability'", null, null, "'['", "']'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SENSORS", 
			"BEHAVIOUR_FILE", "BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", 
			"SIMULATION_SPEED", "SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", 
			"SLCOMMENT", "ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", 
			"STRING", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public UUVLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "UUV.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2!\u012f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u00e8\n\26\f\26\16\26"+
		"\u00eb\13\26\3\26\5\26\u00ee\n\26\3\26\3\26\3\26\3\26\3\27\3\27\7\27\u00f6"+
		"\n\27\f\27\16\27\u00f9\13\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\6\33\u0102"+
		"\n\33\r\33\16\33\u0103\3\34\5\34\u0107\n\34\3\34\3\34\3\34\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5"+
		"\36\u011c\n\36\3\37\3\37\7\37\u0120\n\37\f\37\16\37\u0123\13\37\3\37\3"+
		"\37\3 \6 \u0128\n \r \16 \u0129\3 \3 \3!\3!\3\u00e9\2\"\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\2\3\2\7"+
		"\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\4\2$$^^\5\2\13\f\17\17\"\"\2\u0136"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\3C\3\2\2\2\5M\3\2\2\2\7Q\3\2\2\2\tS\3\2\2"+
		"\2\13U\3\2\2\2\rW\3\2\2\2\17Y\3\2\2\2\21^\3\2\2\2\23f\3\2\2\2\25u\3\2"+
		"\2\2\27{\3\2\2\2\31\u008c\3\2\2\2\33\u009c\3\2\2\2\35\u00a8\3\2\2\2\37"+
		"\u00b9\3\2\2\2!\u00bf\3\2\2\2#\u00c6\3\2\2\2%\u00cb\3\2\2\2\'\u00d0\3"+
		"\2\2\2)\u00d7\3\2\2\2+\u00e3\3\2\2\2-\u00f3\3\2\2\2/\u00fa\3\2\2\2\61"+
		"\u00fc\3\2\2\2\63\u00fe\3\2\2\2\65\u0101\3\2\2\2\67\u0106\3\2\2\29\u010b"+
		"\3\2\2\2;\u011b\3\2\2\2=\u011d\3\2\2\2?\u0127\3\2\2\2A\u012d\3\2\2\2C"+
		"D\7n\2\2DE\7q\2\2EF\7e\2\2FG\7c\2\2GH\7n\2\2HI\7j\2\2IJ\7q\2\2JK\7u\2"+
		"\2KL\7v\2\2L\4\3\2\2\2MN\7W\2\2NO\7W\2\2OP\7X\2\2P\6\3\2\2\2QR\7}\2\2"+
		"R\b\3\2\2\2ST\7<\2\2T\n\3\2\2\2UV\7\177\2\2V\f\3\2\2\2WX\7?\2\2X\16\3"+
		"\2\2\2YZ\7j\2\2Z[\7q\2\2[\\\7u\2\2\\]\7v\2\2]\20\3\2\2\2^_\7u\2\2_`\7"+
		"g\2\2`a\7p\2\2ab\7u\2\2bc\7q\2\2cd\7t\2\2de\7u\2\2e\22\3\2\2\2fg\7d\2"+
		"\2gh\7g\2\2hi\7j\2\2ij\7c\2\2jk\7x\2\2kl\7k\2\2lm\7q\2\2mn\7w\2\2no\7"+
		"t\2\2op\7\"\2\2pq\7h\2\2qr\7k\2\2rs\7n\2\2st\7g\2\2t\24\3\2\2\2uv\5-\27"+
		"\2vw\7\60\2\2wx\7d\2\2xy\7j\2\2yz\7x\2\2z\26\3\2\2\2{|\7u\2\2|}\7g\2\2"+
		"}~\7t\2\2~\177\7x\2\2\177\u0080\7g\2\2\u0080\u0081\7t\2\2\u0081\u0082"+
		"\7R\2\2\u0082\u0083\7q\2\2\u0083\u0084\7t\2\2\u0084\u0085\7v\2\2\u0085"+
		"\u0086\7\"\2\2\u0086\u0087\7u\2\2\u0087\u0088\7v\2\2\u0088\u0089\7c\2"+
		"\2\u0089\u008a\7t\2\2\u008a\u008b\7v\2\2\u008b\30\3\2\2\2\u008c\u008d"+
		"\7u\2\2\u008d\u008e\7k\2\2\u008e\u008f\7o\2\2\u008f\u0090\7w\2\2\u0090"+
		"\u0091\7n\2\2\u0091\u0092\7c\2\2\u0092\u0093\7v\2\2\u0093\u0094\7k\2\2"+
		"\u0094\u0095\7q\2\2\u0095\u0096\7p\2\2\u0096\u0097\7\"\2\2\u0097\u0098"+
		"\7v\2\2\u0098\u0099\7k\2\2\u0099\u009a\7o\2\2\u009a\u009b\7g\2\2\u009b"+
		"\32\3\2\2\2\u009c\u009d\7v\2\2\u009d\u009e\7k\2\2\u009e\u009f\7o\2\2\u009f"+
		"\u00a0\7g\2\2\u00a0\u00a1\7\"\2\2\u00a1\u00a2\7y\2\2\u00a2\u00a3\7k\2"+
		"\2\u00a3\u00a4\7p\2\2\u00a4\u00a5\7f\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7"+
		"\7y\2\2\u00a7\34\3\2\2\2\u00a8\u00a9\7u\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab"+
		"\7o\2\2\u00ab\u00ac\7w\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae\7c\2\2\u00ae"+
		"\u00af\7v\2\2\u00af\u00b0\7k\2\2\u00b0\u00b1\7q\2\2\u00b1\u00b2\7p\2\2"+
		"\u00b2\u00b3\7\"\2\2\u00b3\u00b4\7u\2\2\u00b4\u00b5\7r\2\2\u00b5\u00b6"+
		"\7g\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7f\2\2\u00b8\36\3\2\2\2\u00b9\u00ba"+
		"\7u\2\2\u00ba\u00bb\7r\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7g\2\2\u00bd"+
		"\u00be\7f\2\2\u00be \3\2\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7g\2\2\u00c1"+
		"\u00c2\7p\2\2\u00c2\u00c3\7u\2\2\u00c3\u00c4\7q\2\2\u00c4\u00c5\7t\2\2"+
		"\u00c5\"\3\2\2\2\u00c6\u00c7\7p\2\2\u00c7\u00c8\7c\2\2\u00c8\u00c9\7o"+
		"\2\2\u00c9\u00ca\7g\2\2\u00ca$\3\2\2\2\u00cb\u00cc\7t\2\2\u00cc\u00cd"+
		"\7c\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7g\2\2\u00cf&\3\2\2\2\u00d0\u00d1"+
		"\7e\2\2\u00d1\u00d2\7j\2\2\u00d2\u00d3\7c\2\2\u00d3\u00d4\7p\2\2\u00d4"+
		"\u00d5\7i\2\2\u00d5\u00d6\7g\2\2\u00d6(\3\2\2\2\u00d7\u00d8\7t\2\2\u00d8"+
		"\u00d9\7g\2\2\u00d9\u00da\7n\2\2\u00da\u00db\7k\2\2\u00db\u00dc\7c\2\2"+
		"\u00dc\u00dd\7d\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7n\2\2\u00df\u00e0"+
		"\7k\2\2\u00e0\u00e1\7v\2\2\u00e1\u00e2\7{\2\2\u00e2*\3\2\2\2\u00e3\u00e4"+
		"\7\61\2\2\u00e4\u00e5\7\61\2\2\u00e5\u00e9\3\2\2\2\u00e6\u00e8\13\2\2"+
		"\2\u00e7\u00e6\3\2\2\2\u00e8\u00eb\3\2\2\2\u00e9\u00ea\3\2\2\2\u00e9\u00e7"+
		"\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00ee\7\17\2\2"+
		"\u00ed\u00ec\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0"+
		"\7\f\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\b\26\2\2\u00f2,\3\2\2\2\u00f3"+
		"\u00f7\t\2\2\2\u00f4\u00f6\t\3\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2"+
		"\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8.\3\2\2\2\u00f9\u00f7"+
		"\3\2\2\2\u00fa\u00fb\7]\2\2\u00fb\60\3\2\2\2\u00fc\u00fd\7_\2\2\u00fd"+
		"\62\3\2\2\2\u00fe\u00ff\7.\2\2\u00ff\64\3\2\2\2\u0100\u0102\t\4\2\2\u0101"+
		"\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104\66\3\2\2\2\u0105\u0107\5\65\33\2\u0106\u0105\3\2\2\2\u0106"+
		"\u0107\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0109\7\60\2\2\u0109\u010a\5"+
		"\65\33\2\u010a8\3\2\2\2\u010b\u010c\5;\36\2\u010c\u010d\7\60\2\2\u010d"+
		"\u010e\5;\36\2\u010e\u010f\7\60\2\2\u010f\u0110\5;\36\2\u0110\u0111\7"+
		"\60\2\2\u0111\u0112\5;\36\2\u0112:\3\2\2\2\u0113\u0114\5A!\2\u0114\u0115"+
		"\5A!\2\u0115\u0116\5A!\2\u0116\u011c\3\2\2\2\u0117\u0118\5A!\2\u0118\u0119"+
		"\5A!\2\u0119\u011c\3\2\2\2\u011a\u011c\5A!\2\u011b\u0113\3\2\2\2\u011b"+
		"\u0117\3\2\2\2\u011b\u011a\3\2\2\2\u011c<\3\2\2\2\u011d\u0121\7$\2\2\u011e"+
		"\u0120\n\5\2\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u011f\3\2"+
		"\2\2\u0121\u0122\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124"+
		"\u0125\7$\2\2\u0125>\3\2\2\2\u0126\u0128\t\6\2\2\u0127\u0126\3\2\2\2\u0128"+
		"\u0129\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2"+
		"\2\2\u012b\u012c\b \2\2\u012c@\3\2\2\2\u012d\u012e\4\62;\2\u012eB\3\2"+
		"\2\2\13\2\u00e9\u00ed\u00f7\u0103\u0106\u011b\u0121\u0129\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}