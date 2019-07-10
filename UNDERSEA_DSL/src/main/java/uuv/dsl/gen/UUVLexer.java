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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, ASSIGN=7, SERVER_HOST=8, 
		SERVER_PORT=9, SIMULATION_TIME=10, TIME_WINDOW=11, SIMULATION_SPEED=12, 
		SPEED=13, SENSOR=14, NAME=15, RATE=16, CHANGE=17, RELIABILITY=18, SLCOMMENT=19, 
		ID=20, INT=21, DOUBLE=22, IP=23, OCTET=24, STRING=25, WS=26;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "ASSIGN", "SERVER_HOST", 
			"SERVER_PORT", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", 
			"SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", 
			"ID", "INT", "DOUBLE", "IP", "OCTET", "STRING", "WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "','", "'}'", "'='", "'host'", 
			"'port'", "'simulation time'", "'time window'", "'simulation speed'", 
			"'speed'", "'sensor'", "'name'", "'rate'", "'change'", "'reliability'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SERVER_PORT", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\34\u00f8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u00b7\n\24\f\24\16\24\u00ba"+
		"\13\24\3\24\5\24\u00bd\n\24\3\24\3\24\3\24\3\24\3\25\3\25\7\25\u00c5\n"+
		"\25\f\25\16\25\u00c8\13\25\3\26\6\26\u00cb\n\26\r\26\16\26\u00cc\3\27"+
		"\5\27\u00d0\n\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u00e5\n\31\3\32\3\32\7\32"+
		"\u00e9\n\32\f\32\16\32\u00ec\13\32\3\32\3\32\3\33\6\33\u00f1\n\33\r\33"+
		"\16\33\u00f2\3\33\3\33\3\34\3\34\3\u00b8\2\35\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"+
		"+\27-\30/\31\61\32\63\33\65\34\67\2\3\2\7\5\2C\\aac|\6\2\62;C\\aac|\3"+
		"\2\62;\4\2$$^^\5\2\13\f\17\17\"\"\2\u00ff\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\39\3\2\2\2\5C\3\2\2\2\7G\3\2\2\2\tI\3\2\2\2\13K\3\2\2\2"+
		"\rM\3\2\2\2\17O\3\2\2\2\21Q\3\2\2\2\23V\3\2\2\2\25[\3\2\2\2\27k\3\2\2"+
		"\2\31w\3\2\2\2\33\u0088\3\2\2\2\35\u008e\3\2\2\2\37\u0095\3\2\2\2!\u009a"+
		"\3\2\2\2#\u009f\3\2\2\2%\u00a6\3\2\2\2\'\u00b2\3\2\2\2)\u00c2\3\2\2\2"+
		"+\u00ca\3\2\2\2-\u00cf\3\2\2\2/\u00d4\3\2\2\2\61\u00e4\3\2\2\2\63\u00e6"+
		"\3\2\2\2\65\u00f0\3\2\2\2\67\u00f6\3\2\2\29:\7n\2\2:;\7q\2\2;<\7e\2\2"+
		"<=\7c\2\2=>\7n\2\2>?\7j\2\2?@\7q\2\2@A\7u\2\2AB\7v\2\2B\4\3\2\2\2CD\7"+
		"W\2\2DE\7W\2\2EF\7X\2\2F\6\3\2\2\2GH\7}\2\2H\b\3\2\2\2IJ\7<\2\2J\n\3\2"+
		"\2\2KL\7.\2\2L\f\3\2\2\2MN\7\177\2\2N\16\3\2\2\2OP\7?\2\2P\20\3\2\2\2"+
		"QR\7j\2\2RS\7q\2\2ST\7u\2\2TU\7v\2\2U\22\3\2\2\2VW\7r\2\2WX\7q\2\2XY\7"+
		"t\2\2YZ\7v\2\2Z\24\3\2\2\2[\\\7u\2\2\\]\7k\2\2]^\7o\2\2^_\7w\2\2_`\7n"+
		"\2\2`a\7c\2\2ab\7v\2\2bc\7k\2\2cd\7q\2\2de\7p\2\2ef\7\"\2\2fg\7v\2\2g"+
		"h\7k\2\2hi\7o\2\2ij\7g\2\2j\26\3\2\2\2kl\7v\2\2lm\7k\2\2mn\7o\2\2no\7"+
		"g\2\2op\7\"\2\2pq\7y\2\2qr\7k\2\2rs\7p\2\2st\7f\2\2tu\7q\2\2uv\7y\2\2"+
		"v\30\3\2\2\2wx\7u\2\2xy\7k\2\2yz\7o\2\2z{\7w\2\2{|\7n\2\2|}\7c\2\2}~\7"+
		"v\2\2~\177\7k\2\2\177\u0080\7q\2\2\u0080\u0081\7p\2\2\u0081\u0082\7\""+
		"\2\2\u0082\u0083\7u\2\2\u0083\u0084\7r\2\2\u0084\u0085\7g\2\2\u0085\u0086"+
		"\7g\2\2\u0086\u0087\7f\2\2\u0087\32\3\2\2\2\u0088\u0089\7u\2\2\u0089\u008a"+
		"\7r\2\2\u008a\u008b\7g\2\2\u008b\u008c\7g\2\2\u008c\u008d\7f\2\2\u008d"+
		"\34\3\2\2\2\u008e\u008f\7u\2\2\u008f\u0090\7g\2\2\u0090\u0091\7p\2\2\u0091"+
		"\u0092\7u\2\2\u0092\u0093\7q\2\2\u0093\u0094\7t\2\2\u0094\36\3\2\2\2\u0095"+
		"\u0096\7p\2\2\u0096\u0097\7c\2\2\u0097\u0098\7o\2\2\u0098\u0099\7g\2\2"+
		"\u0099 \3\2\2\2\u009a\u009b\7t\2\2\u009b\u009c\7c\2\2\u009c\u009d\7v\2"+
		"\2\u009d\u009e\7g\2\2\u009e\"\3\2\2\2\u009f\u00a0\7e\2\2\u00a0\u00a1\7"+
		"j\2\2\u00a1\u00a2\7c\2\2\u00a2\u00a3\7p\2\2\u00a3\u00a4\7i\2\2\u00a4\u00a5"+
		"\7g\2\2\u00a5$\3\2\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8\7g\2\2\u00a8\u00a9"+
		"\7n\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7c\2\2\u00ab\u00ac\7d\2\2\u00ac"+
		"\u00ad\7k\2\2\u00ad\u00ae\7n\2\2\u00ae\u00af\7k\2\2\u00af\u00b0\7v\2\2"+
		"\u00b0\u00b1\7{\2\2\u00b1&\3\2\2\2\u00b2\u00b3\7\61\2\2\u00b3\u00b4\7"+
		"\61\2\2\u00b4\u00b8\3\2\2\2\u00b5\u00b7\13\2\2\2\u00b6\u00b5\3\2\2\2\u00b7"+
		"\u00ba\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00bc\3\2"+
		"\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bd\7\17\2\2\u00bc\u00bb\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\7\f\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\u00c1\b\24\2\2\u00c1(\3\2\2\2\u00c2\u00c6\t\2\2\2\u00c3\u00c5"+
		"\t\3\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7*\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c9\u00cb\t\4\2\2"+
		"\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd"+
		"\3\2\2\2\u00cd,\3\2\2\2\u00ce\u00d0\5+\26\2\u00cf\u00ce\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\7\60\2\2\u00d2\u00d3\5"+
		"+\26\2\u00d3.\3\2\2\2\u00d4\u00d5\5\61\31\2\u00d5\u00d6\7\60\2\2\u00d6"+
		"\u00d7\5\61\31\2\u00d7\u00d8\7\60\2\2\u00d8\u00d9\5\61\31\2\u00d9\u00da"+
		"\7\60\2\2\u00da\u00db\5\61\31\2\u00db\60\3\2\2\2\u00dc\u00dd\5\67\34\2"+
		"\u00dd\u00de\5\67\34\2\u00de\u00df\5\67\34\2\u00df\u00e5\3\2\2\2\u00e0"+
		"\u00e1\5\67\34\2\u00e1\u00e2\5\67\34\2\u00e2\u00e5\3\2\2\2\u00e3\u00e5"+
		"\5\67\34\2\u00e4\u00dc\3\2\2\2\u00e4\u00e0\3\2\2\2\u00e4\u00e3\3\2\2\2"+
		"\u00e5\62\3\2\2\2\u00e6\u00ea\7$\2\2\u00e7\u00e9\n\5\2\2\u00e8\u00e7\3"+
		"\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"\u00ed\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed\u00ee\7$\2\2\u00ee\64\3\2\2\2"+
		"\u00ef\u00f1\t\6\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f0"+
		"\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\b\33\2\2"+
		"\u00f5\66\3\2\2\2\u00f6\u00f7\4\62;\2\u00f78\3\2\2\2\13\2\u00b8\u00bc"+
		"\u00c6\u00cc\u00cf\u00e4\u00ea\u00f2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}