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
		SERVER_PORT=9, SIMULATION_TIME=10, TIME_WINDOW=11, SIMULATION_SPEED=12, 
		SPEED=13, SENSOR=14, NAME=15, RATE=16, CHANGE=17, RELIABILITY=18, SLCOMMENT=19, 
		ID=20, BEGL=21, ENDL=22, SEP=23, INT=24, DOUBLE=25, IP=26, OCTET=27, STRING=28, 
		WS=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "ASSIGN", "SERVER_HOST", "SENSORS", 
			"SERVER_PORT", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", 
			"SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", 
			"ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", "STRING", 
			"WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'host'", "'sensors'", 
			"'port'", "'simulation time'", "'time window'", "'simulation speed'", 
			"'speed'", "'sensor'", "'name'", "'rate'", "'change'", "'reliability'", 
			null, null, "'['", "']'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SENSORS", 
			"SERVER_PORT", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", 
			"SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", 
			"ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", "STRING", 
			"WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u010a\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\7\24\u00c3\n\24\f\24\16\24\u00c6\13\24\3\24\5"+
		"\24\u00c9\n\24\3\24\3\24\3\24\3\24\3\25\3\25\7\25\u00d1\n\25\f\25\16\25"+
		"\u00d4\13\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\6\31\u00dd\n\31\r\31\16"+
		"\31\u00de\3\32\5\32\u00e2\n\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u00f7\n\34"+
		"\3\35\3\35\7\35\u00fb\n\35\f\35\16\35\u00fe\13\35\3\35\3\35\3\36\6\36"+
		"\u0103\n\36\r\36\16\36\u0104\3\36\3\36\3\37\3\37\3\u00c4\2 \3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37=\2\3\2\7"+
		"\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\4\2$$^^\5\2\13\f\17\17\"\"\2\u0111"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\3?\3\2\2\2\5I\3\2\2\2\7M\3\2\2\2\tO\3\2\2\2\13Q\3\2\2\2\rS\3\2"+
		"\2\2\17U\3\2\2\2\21Z\3\2\2\2\23b\3\2\2\2\25g\3\2\2\2\27w\3\2\2\2\31\u0083"+
		"\3\2\2\2\33\u0094\3\2\2\2\35\u009a\3\2\2\2\37\u00a1\3\2\2\2!\u00a6\3\2"+
		"\2\2#\u00ab\3\2\2\2%\u00b2\3\2\2\2\'\u00be\3\2\2\2)\u00ce\3\2\2\2+\u00d5"+
		"\3\2\2\2-\u00d7\3\2\2\2/\u00d9\3\2\2\2\61\u00dc\3\2\2\2\63\u00e1\3\2\2"+
		"\2\65\u00e6\3\2\2\2\67\u00f6\3\2\2\29\u00f8\3\2\2\2;\u0102\3\2\2\2=\u0108"+
		"\3\2\2\2?@\7n\2\2@A\7q\2\2AB\7e\2\2BC\7c\2\2CD\7n\2\2DE\7j\2\2EF\7q\2"+
		"\2FG\7u\2\2GH\7v\2\2H\4\3\2\2\2IJ\7W\2\2JK\7W\2\2KL\7X\2\2L\6\3\2\2\2"+
		"MN\7}\2\2N\b\3\2\2\2OP\7<\2\2P\n\3\2\2\2QR\7\177\2\2R\f\3\2\2\2ST\7?\2"+
		"\2T\16\3\2\2\2UV\7j\2\2VW\7q\2\2WX\7u\2\2XY\7v\2\2Y\20\3\2\2\2Z[\7u\2"+
		"\2[\\\7g\2\2\\]\7p\2\2]^\7u\2\2^_\7q\2\2_`\7t\2\2`a\7u\2\2a\22\3\2\2\2"+
		"bc\7r\2\2cd\7q\2\2de\7t\2\2ef\7v\2\2f\24\3\2\2\2gh\7u\2\2hi\7k\2\2ij\7"+
		"o\2\2jk\7w\2\2kl\7n\2\2lm\7c\2\2mn\7v\2\2no\7k\2\2op\7q\2\2pq\7p\2\2q"+
		"r\7\"\2\2rs\7v\2\2st\7k\2\2tu\7o\2\2uv\7g\2\2v\26\3\2\2\2wx\7v\2\2xy\7"+
		"k\2\2yz\7o\2\2z{\7g\2\2{|\7\"\2\2|}\7y\2\2}~\7k\2\2~\177\7p\2\2\177\u0080"+
		"\7f\2\2\u0080\u0081\7q\2\2\u0081\u0082\7y\2\2\u0082\30\3\2\2\2\u0083\u0084"+
		"\7u\2\2\u0084\u0085\7k\2\2\u0085\u0086\7o\2\2\u0086\u0087\7w\2\2\u0087"+
		"\u0088\7n\2\2\u0088\u0089\7c\2\2\u0089\u008a\7v\2\2\u008a\u008b\7k\2\2"+
		"\u008b\u008c\7q\2\2\u008c\u008d\7p\2\2\u008d\u008e\7\"\2\2\u008e\u008f"+
		"\7u\2\2\u008f\u0090\7r\2\2\u0090\u0091\7g\2\2\u0091\u0092\7g\2\2\u0092"+
		"\u0093\7f\2\2\u0093\32\3\2\2\2\u0094\u0095\7u\2\2\u0095\u0096\7r\2\2\u0096"+
		"\u0097\7g\2\2\u0097\u0098\7g\2\2\u0098\u0099\7f\2\2\u0099\34\3\2\2\2\u009a"+
		"\u009b\7u\2\2\u009b\u009c\7g\2\2\u009c\u009d\7p\2\2\u009d\u009e\7u\2\2"+
		"\u009e\u009f\7q\2\2\u009f\u00a0\7t\2\2\u00a0\36\3\2\2\2\u00a1\u00a2\7"+
		"p\2\2\u00a2\u00a3\7c\2\2\u00a3\u00a4\7o\2\2\u00a4\u00a5\7g\2\2\u00a5 "+
		"\3\2\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8\7c\2\2\u00a8\u00a9\7v\2\2\u00a9"+
		"\u00aa\7g\2\2\u00aa\"\3\2\2\2\u00ab\u00ac\7e\2\2\u00ac\u00ad\7j\2\2\u00ad"+
		"\u00ae\7c\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7i\2\2\u00b0\u00b1\7g\2\2"+
		"\u00b1$\3\2\2\2\u00b2\u00b3\7t\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7n\2"+
		"\2\u00b5\u00b6\7k\2\2\u00b6\u00b7\7c\2\2\u00b7\u00b8\7d\2\2\u00b8\u00b9"+
		"\7k\2\2\u00b9\u00ba\7n\2\2\u00ba\u00bb\7k\2\2\u00bb\u00bc\7v\2\2\u00bc"+
		"\u00bd\7{\2\2\u00bd&\3\2\2\2\u00be\u00bf\7\61\2\2\u00bf\u00c0\7\61\2\2"+
		"\u00c0\u00c4\3\2\2\2\u00c1\u00c3\13\2\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6"+
		"\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6"+
		"\u00c4\3\2\2\2\u00c7\u00c9\7\17\2\2\u00c8\u00c7\3\2\2\2\u00c8\u00c9\3"+
		"\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\7\f\2\2\u00cb\u00cc\3\2\2\2\u00cc"+
		"\u00cd\b\24\2\2\u00cd(\3\2\2\2\u00ce\u00d2\t\2\2\2\u00cf\u00d1\t\3\2\2"+
		"\u00d0\u00cf\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3"+
		"\3\2\2\2\u00d3*\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00d6\7]\2\2\u00d6,"+
		"\3\2\2\2\u00d7\u00d8\7_\2\2\u00d8.\3\2\2\2\u00d9\u00da\7.\2\2\u00da\60"+
		"\3\2\2\2\u00db\u00dd\t\4\2\2\u00dc\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		"\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\62\3\2\2\2\u00e0\u00e2\5\61\31"+
		"\2\u00e1\u00e0\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4"+
		"\7\60\2\2\u00e4\u00e5\5\61\31\2\u00e5\64\3\2\2\2\u00e6\u00e7\5\67\34\2"+
		"\u00e7\u00e8\7\60\2\2\u00e8\u00e9\5\67\34\2\u00e9\u00ea\7\60\2\2\u00ea"+
		"\u00eb\5\67\34\2\u00eb\u00ec\7\60\2\2\u00ec\u00ed\5\67\34\2\u00ed\66\3"+
		"\2\2\2\u00ee\u00ef\5=\37\2\u00ef\u00f0\5=\37\2\u00f0\u00f1\5=\37\2\u00f1"+
		"\u00f7\3\2\2\2\u00f2\u00f3\5=\37\2\u00f3\u00f4\5=\37\2\u00f4\u00f7\3\2"+
		"\2\2\u00f5\u00f7\5=\37\2\u00f6\u00ee\3\2\2\2\u00f6\u00f2\3\2\2\2\u00f6"+
		"\u00f5\3\2\2\2\u00f78\3\2\2\2\u00f8\u00fc\7$\2\2\u00f9\u00fb\n\5\2\2\u00fa"+
		"\u00f9\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2"+
		"\2\2\u00fd\u00ff\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0100\7$\2\2\u0100"+
		":\3\2\2\2\u0101\u0103\t\6\2\2\u0102\u0101\3\2\2\2\u0103\u0104\3\2\2\2"+
		"\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107"+
		"\b\36\2\2\u0107<\3\2\2\2\u0108\u0109\4\62;\2\u0109>\3\2\2\2\13\2\u00c4"+
		"\u00c8\u00d2\u00de\u00e1\u00f6\u00fc\u0104\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}