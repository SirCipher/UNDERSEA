// Generated from Sensors.g4 by ANTLR 4.7.2

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
public class SensorsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ASSIGN=5, SERVER_HOST=6, SERVER_PORT=7, 
		SIMULATION_TIME=8, TIME_WINDOW=9, SIMULATION_SPEED=10, SPEED=11, SENSOR=12, 
		NAME=13, RATE=14, CHANGE=15, RELIABILITY=16, SLCOMMENT=17, ID=18, INT=19, 
		DOUBLE=20, IP=21, OCTET=22, STRING=23, WS=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "ASSIGN", "SERVER_HOST", "SERVER_PORT", 
			"SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", "SPEED", "SENSOR", 
			"NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", "ID", "INT", "DOUBLE", 
			"IP", "OCTET", "STRING", "WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'SENSOR'", "'{'", "'}'", "':'", "'='", "'host'", "'port'", "'simulation time'", 
			"'time window'", "'simulation speed'", "'speed'", "'sensor'", "'name'", 
			"'rate'", "'change'", "'reliability'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SERVER_PORT", 
			"SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", "SPEED", "SENSOR", 
			"NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", "ID", "INT", "DOUBLE", 
			"IP", "OCTET", "STRING", "WS"
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


	public SensorsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Sensors.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\u00eb\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\7\22\u00aa\n\22\f\22\16\22\u00ad"+
		"\13\22\3\22\5\22\u00b0\n\22\3\22\3\22\3\22\3\22\3\23\3\23\7\23\u00b8\n"+
		"\23\f\23\16\23\u00bb\13\23\3\24\6\24\u00be\n\24\r\24\16\24\u00bf\3\25"+
		"\5\25\u00c3\n\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00d8\n\27\3\30\3\30\7\30"+
		"\u00dc\n\30\f\30\16\30\u00df\13\30\3\30\3\30\3\31\6\31\u00e4\n\31\r\31"+
		"\16\31\u00e5\3\31\3\31\3\32\3\32\3\u00ab\2\33\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"+
		"+\27-\30/\31\61\32\63\2\3\2\7\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\4\2$$"+
		"^^\5\2\13\f\17\17\"\"\2\u00f2\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3\65\3\2\2\2\5<\3\2\2\2\7"+
		">\3\2\2\2\t@\3\2\2\2\13B\3\2\2\2\rD\3\2\2\2\17I\3\2\2\2\21N\3\2\2\2\23"+
		"^\3\2\2\2\25j\3\2\2\2\27{\3\2\2\2\31\u0081\3\2\2\2\33\u0088\3\2\2\2\35"+
		"\u008d\3\2\2\2\37\u0092\3\2\2\2!\u0099\3\2\2\2#\u00a5\3\2\2\2%\u00b5\3"+
		"\2\2\2\'\u00bd\3\2\2\2)\u00c2\3\2\2\2+\u00c7\3\2\2\2-\u00d7\3\2\2\2/\u00d9"+
		"\3\2\2\2\61\u00e3\3\2\2\2\63\u00e9\3\2\2\2\65\66\7U\2\2\66\67\7G\2\2\67"+
		"8\7P\2\289\7U\2\29:\7Q\2\2:;\7T\2\2;\4\3\2\2\2<=\7}\2\2=\6\3\2\2\2>?\7"+
		"\177\2\2?\b\3\2\2\2@A\7<\2\2A\n\3\2\2\2BC\7?\2\2C\f\3\2\2\2DE\7j\2\2E"+
		"F\7q\2\2FG\7u\2\2GH\7v\2\2H\16\3\2\2\2IJ\7r\2\2JK\7q\2\2KL\7t\2\2LM\7"+
		"v\2\2M\20\3\2\2\2NO\7u\2\2OP\7k\2\2PQ\7o\2\2QR\7w\2\2RS\7n\2\2ST\7c\2"+
		"\2TU\7v\2\2UV\7k\2\2VW\7q\2\2WX\7p\2\2XY\7\"\2\2YZ\7v\2\2Z[\7k\2\2[\\"+
		"\7o\2\2\\]\7g\2\2]\22\3\2\2\2^_\7v\2\2_`\7k\2\2`a\7o\2\2ab\7g\2\2bc\7"+
		"\"\2\2cd\7y\2\2de\7k\2\2ef\7p\2\2fg\7f\2\2gh\7q\2\2hi\7y\2\2i\24\3\2\2"+
		"\2jk\7u\2\2kl\7k\2\2lm\7o\2\2mn\7w\2\2no\7n\2\2op\7c\2\2pq\7v\2\2qr\7"+
		"k\2\2rs\7q\2\2st\7p\2\2tu\7\"\2\2uv\7u\2\2vw\7r\2\2wx\7g\2\2xy\7g\2\2"+
		"yz\7f\2\2z\26\3\2\2\2{|\7u\2\2|}\7r\2\2}~\7g\2\2~\177\7g\2\2\177\u0080"+
		"\7f\2\2\u0080\30\3\2\2\2\u0081\u0082\7u\2\2\u0082\u0083\7g\2\2\u0083\u0084"+
		"\7p\2\2\u0084\u0085\7u\2\2\u0085\u0086\7q\2\2\u0086\u0087\7t\2\2\u0087"+
		"\32\3\2\2\2\u0088\u0089\7p\2\2\u0089\u008a\7c\2\2\u008a\u008b\7o\2\2\u008b"+
		"\u008c\7g\2\2\u008c\34\3\2\2\2\u008d\u008e\7t\2\2\u008e\u008f\7c\2\2\u008f"+
		"\u0090\7v\2\2\u0090\u0091\7g\2\2\u0091\36\3\2\2\2\u0092\u0093\7e\2\2\u0093"+
		"\u0094\7j\2\2\u0094\u0095\7c\2\2\u0095\u0096\7p\2\2\u0096\u0097\7i\2\2"+
		"\u0097\u0098\7g\2\2\u0098 \3\2\2\2\u0099\u009a\7t\2\2\u009a\u009b\7g\2"+
		"\2\u009b\u009c\7n\2\2\u009c\u009d\7k\2\2\u009d\u009e\7c\2\2\u009e\u009f"+
		"\7d\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7n\2\2\u00a1\u00a2\7k\2\2\u00a2"+
		"\u00a3\7v\2\2\u00a3\u00a4\7{\2\2\u00a4\"\3\2\2\2\u00a5\u00a6\7\61\2\2"+
		"\u00a6\u00a7\7\61\2\2\u00a7\u00ab\3\2\2\2\u00a8\u00aa\13\2\2\2\u00a9\u00a8"+
		"\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac"+
		"\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\7\17\2\2\u00af\u00ae\3"+
		"\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\f\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b4\b\22\2\2\u00b4$\3\2\2\2\u00b5\u00b9\t\2\2\2"+
		"\u00b6\u00b8\t\3\2\2\u00b7\u00b6\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7"+
		"\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba&\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc"+
		"\u00be\t\4\2\2\u00bd\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00bd\3\2"+
		"\2\2\u00bf\u00c0\3\2\2\2\u00c0(\3\2\2\2\u00c1\u00c3\5\'\24\2\u00c2\u00c1"+
		"\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\7\60\2\2"+
		"\u00c5\u00c6\5\'\24\2\u00c6*\3\2\2\2\u00c7\u00c8\5-\27\2\u00c8\u00c9\7"+
		"\60\2\2\u00c9\u00ca\5-\27\2\u00ca\u00cb\7\60\2\2\u00cb\u00cc\5-\27\2\u00cc"+
		"\u00cd\7\60\2\2\u00cd\u00ce\5-\27\2\u00ce,\3\2\2\2\u00cf\u00d0\5\63\32"+
		"\2\u00d0\u00d1\5\63\32\2\u00d1\u00d2\5\63\32\2\u00d2\u00d8\3\2\2\2\u00d3"+
		"\u00d4\5\63\32\2\u00d4\u00d5\5\63\32\2\u00d5\u00d8\3\2\2\2\u00d6\u00d8"+
		"\5\63\32\2\u00d7\u00cf\3\2\2\2\u00d7\u00d3\3\2\2\2\u00d7\u00d6\3\2\2\2"+
		"\u00d8.\3\2\2\2\u00d9\u00dd\7$\2\2\u00da\u00dc\n\5\2\2\u00db\u00da\3\2"+
		"\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		"\u00e0\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e1\7$\2\2\u00e1\60\3\2\2\2"+
		"\u00e2\u00e4\t\6\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3"+
		"\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\b\31\2\2"+
		"\u00e8\62\3\2\2\2\u00e9\u00ea\4\62;\2\u00ea\64\3\2\2\2\13\2\u00ab\u00af"+
		"\u00b9\u00bf\u00c2\u00d7\u00dd\u00e5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}