// Generated from UUV.g4 by ANTLR 4.7.2

  package uuv.dsl.gen;

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
			"'behaviour file'", null, "'port start'", "'simulation time'", "'time window'", 
			"'simulation speed'", "'speed'", "'sensor'", "'name'", "'rate'", "'change'", 
			"'reliability'", null, null, "'['", "']'", "','"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2!\u0129\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\7\26\u00e2\n\26\f\26\16\26\u00e5\13\26\3\26\5\26"+
		"\u00e8\n\26\3\26\3\26\3\26\3\26\3\27\3\27\7\27\u00f0\n\27\f\27\16\27\u00f3"+
		"\13\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\6\33\u00fc\n\33\r\33\16\33\u00fd"+
		"\3\34\5\34\u0101\n\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0116\n\36\3\37\3\37"+
		"\7\37\u011a\n\37\f\37\16\37\u011d\13\37\3\37\3\37\3 \6 \u0122\n \r \16"+
		" \u0123\3 \3 \3!\3!\3\u00e3\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\2\3\2\7\5\2C\\aac|\6\2\62;C\\aa"+
		"c|\3\2\62;\4\2$$^^\5\2\13\f\17\17\"\"\2\u0130\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\3C\3\2\2\2\5M\3\2\2\2\7Q\3\2\2\2\tS\3\2\2\2\13U\3\2\2\2\rW\3\2\2"+
		"\2\17Y\3\2\2\2\21^\3\2\2\2\23f\3\2\2\2\25u\3\2\2\2\27{\3\2\2\2\31\u0086"+
		"\3\2\2\2\33\u0096\3\2\2\2\35\u00a2\3\2\2\2\37\u00b3\3\2\2\2!\u00b9\3\2"+
		"\2\2#\u00c0\3\2\2\2%\u00c5\3\2\2\2\'\u00ca\3\2\2\2)\u00d1\3\2\2\2+\u00dd"+
		"\3\2\2\2-\u00ed\3\2\2\2/\u00f4\3\2\2\2\61\u00f6\3\2\2\2\63\u00f8\3\2\2"+
		"\2\65\u00fb\3\2\2\2\67\u0100\3\2\2\29\u0105\3\2\2\2;\u0115\3\2\2\2=\u0117"+
		"\3\2\2\2?\u0121\3\2\2\2A\u0127\3\2\2\2CD\7n\2\2DE\7q\2\2EF\7e\2\2FG\7"+
		"c\2\2GH\7n\2\2HI\7j\2\2IJ\7q\2\2JK\7u\2\2KL\7v\2\2L\4\3\2\2\2MN\7W\2\2"+
		"NO\7W\2\2OP\7X\2\2P\6\3\2\2\2QR\7}\2\2R\b\3\2\2\2ST\7<\2\2T\n\3\2\2\2"+
		"UV\7\177\2\2V\f\3\2\2\2WX\7?\2\2X\16\3\2\2\2YZ\7j\2\2Z[\7q\2\2[\\\7u\2"+
		"\2\\]\7v\2\2]\20\3\2\2\2^_\7u\2\2_`\7g\2\2`a\7p\2\2ab\7u\2\2bc\7q\2\2"+
		"cd\7t\2\2de\7u\2\2e\22\3\2\2\2fg\7d\2\2gh\7g\2\2hi\7j\2\2ij\7c\2\2jk\7"+
		"x\2\2kl\7k\2\2lm\7q\2\2mn\7w\2\2no\7t\2\2op\7\"\2\2pq\7h\2\2qr\7k\2\2"+
		"rs\7n\2\2st\7g\2\2t\24\3\2\2\2uv\5-\27\2vw\7\60\2\2wx\7d\2\2xy\7j\2\2"+
		"yz\7x\2\2z\26\3\2\2\2{|\7r\2\2|}\7q\2\2}~\7t\2\2~\177\7v\2\2\177\u0080"+
		"\7\"\2\2\u0080\u0081\7u\2\2\u0081\u0082\7v\2\2\u0082\u0083\7c\2\2\u0083"+
		"\u0084\7t\2\2\u0084\u0085\7v\2\2\u0085\30\3\2\2\2\u0086\u0087\7u\2\2\u0087"+
		"\u0088\7k\2\2\u0088\u0089\7o\2\2\u0089\u008a\7w\2\2\u008a\u008b\7n\2\2"+
		"\u008b\u008c\7c\2\2\u008c\u008d\7v\2\2\u008d\u008e\7k\2\2\u008e\u008f"+
		"\7q\2\2\u008f\u0090\7p\2\2\u0090\u0091\7\"\2\2\u0091\u0092\7v\2\2\u0092"+
		"\u0093\7k\2\2\u0093\u0094\7o\2\2\u0094\u0095\7g\2\2\u0095\32\3\2\2\2\u0096"+
		"\u0097\7v\2\2\u0097\u0098\7k\2\2\u0098\u0099\7o\2\2\u0099\u009a\7g\2\2"+
		"\u009a\u009b\7\"\2\2\u009b\u009c\7y\2\2\u009c\u009d\7k\2\2\u009d\u009e"+
		"\7p\2\2\u009e\u009f\7f\2\2\u009f\u00a0\7q\2\2\u00a0\u00a1\7y\2\2\u00a1"+
		"\34\3\2\2\2\u00a2\u00a3\7u\2\2\u00a3\u00a4\7k\2\2\u00a4\u00a5\7o\2\2\u00a5"+
		"\u00a6\7w\2\2\u00a6\u00a7\7n\2\2\u00a7\u00a8\7c\2\2\u00a8\u00a9\7v\2\2"+
		"\u00a9\u00aa\7k\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7p\2\2\u00ac\u00ad"+
		"\7\"\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af\7r\2\2\u00af\u00b0\7g\2\2\u00b0"+
		"\u00b1\7g\2\2\u00b1\u00b2\7f\2\2\u00b2\36\3\2\2\2\u00b3\u00b4\7u\2\2\u00b4"+
		"\u00b5\7r\2\2\u00b5\u00b6\7g\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7f\2\2"+
		"\u00b8 \3\2\2\2\u00b9\u00ba\7u\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7p\2"+
		"\2\u00bc\u00bd\7u\2\2\u00bd\u00be\7q\2\2\u00be\u00bf\7t\2\2\u00bf\"\3"+
		"\2\2\2\u00c0\u00c1\7p\2\2\u00c1\u00c2\7c\2\2\u00c2\u00c3\7o\2\2\u00c3"+
		"\u00c4\7g\2\2\u00c4$\3\2\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7c\2\2\u00c7"+
		"\u00c8\7v\2\2\u00c8\u00c9\7g\2\2\u00c9&\3\2\2\2\u00ca\u00cb\7e\2\2\u00cb"+
		"\u00cc\7j\2\2\u00cc\u00cd\7c\2\2\u00cd\u00ce\7p\2\2\u00ce\u00cf\7i\2\2"+
		"\u00cf\u00d0\7g\2\2\u00d0(\3\2\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7g\2"+
		"\2\u00d3\u00d4\7n\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7c\2\2\u00d6\u00d7"+
		"\7d\2\2\u00d7\u00d8\7k\2\2\u00d8\u00d9\7n\2\2\u00d9\u00da\7k\2\2\u00da"+
		"\u00db\7v\2\2\u00db\u00dc\7{\2\2\u00dc*\3\2\2\2\u00dd\u00de\7\61\2\2\u00de"+
		"\u00df\7\61\2\2\u00df\u00e3\3\2\2\2\u00e0\u00e2\13\2\2\2\u00e1\u00e0\3"+
		"\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4"+
		"\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00e8\7\17\2\2\u00e7\u00e6\3"+
		"\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\7\f\2\2\u00ea"+
		"\u00eb\3\2\2\2\u00eb\u00ec\b\26\2\2\u00ec,\3\2\2\2\u00ed\u00f1\t\2\2\2"+
		"\u00ee\u00f0\t\3\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef"+
		"\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2.\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4"+
		"\u00f5\7]\2\2\u00f5\60\3\2\2\2\u00f6\u00f7\7_\2\2\u00f7\62\3\2\2\2\u00f8"+
		"\u00f9\7.\2\2\u00f9\64\3\2\2\2\u00fa\u00fc\t\4\2\2\u00fb\u00fa\3\2\2\2"+
		"\u00fc\u00fd\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\66"+
		"\3\2\2\2\u00ff\u0101\5\65\33\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2"+
		"\u0101\u0102\3\2\2\2\u0102\u0103\7\60\2\2\u0103\u0104\5\65\33\2\u0104"+
		"8\3\2\2\2\u0105\u0106\5;\36\2\u0106\u0107\7\60\2\2\u0107\u0108\5;\36\2"+
		"\u0108\u0109\7\60\2\2\u0109\u010a\5;\36\2\u010a\u010b\7\60\2\2\u010b\u010c"+
		"\5;\36\2\u010c:\3\2\2\2\u010d\u010e\5A!\2\u010e\u010f\5A!\2\u010f\u0110"+
		"\5A!\2\u0110\u0116\3\2\2\2\u0111\u0112\5A!\2\u0112\u0113\5A!\2\u0113\u0116"+
		"\3\2\2\2\u0114\u0116\5A!\2\u0115\u010d\3\2\2\2\u0115\u0111\3\2\2\2\u0115"+
		"\u0114\3\2\2\2\u0116<\3\2\2\2\u0117\u011b\7$\2\2\u0118\u011a\n\5\2\2\u0119"+
		"\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011c\3\2"+
		"\2\2\u011c\u011e\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u011f\7$\2\2\u011f"+
		">\3\2\2\2\u0120\u0122\t\6\2\2\u0121\u0120\3\2\2\2\u0122\u0123\3\2\2\2"+
		"\u0123\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126"+
		"\b \2\2\u0126@\3\2\2\2\u0127\u0128\4\62;\2\u0128B\3\2\2\2\13\2\u00e3\u00e7"+
		"\u00f1\u00fd\u0100\u0115\u011b\u0123\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}