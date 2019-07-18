// Generated from Sensors.g4 by ANTLR 4.7.2

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
public class SensorsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ASSIGN=5, SERVER_HOST=6, SENSORS=7, BEHAVIOUR_FILE=8, 
		BHV_FILE=9, PORT_START=10, SIMULATION_TIME=11, TIME_WINDOW=12, SIMULATION_SPEED=13, 
		SPEED=14, SENSOR=15, NAME=16, RATE=17, CHANGE=18, RELIABILITY=19, SLCOMMENT=20, 
		ID=21, BEGL=22, ENDL=23, SEP=24, INT=25, DOUBLE=26, IP=27, OCTET=28, STRING=29, 
		WS=30;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "ASSIGN", "SERVER_HOST", "SENSORS", "BEHAVIOUR_FILE", 
			"BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", 
			"SPEED", "SENSOR", "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", 
			"ID", "BEGL", "ENDL", "SEP", "INT", "DOUBLE", "IP", "OCTET", "STRING", 
			"WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'SENSOR'", "'{'", "'}'", "':'", "'='", "'host'", "'sensors'", 
			"'behaviour file'", null, "'serverPort start'", "'simulation time'", 
			"'time window'", "'simulation speed'", "'speed'", "'sensor'", "'name'", 
			"'rate'", "'change'", "'reliability'", null, null, "'['", "']'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SENSORS", "BEHAVIOUR_FILE", 
			"BHV_FILE", "PORT_START", "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2 \u0126\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\7\25\u00df\n\25\f\25\16\25\u00e2\13\25\3\25\5\25\u00e5\n\25\3\25"+
		"\3\25\3\25\3\25\3\26\3\26\7\26\u00ed\n\26\f\26\16\26\u00f0\13\26\3\27"+
		"\3\27\3\30\3\30\3\31\3\31\3\32\6\32\u00f9\n\32\r\32\16\32\u00fa\3\33\5"+
		"\33\u00fe\n\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0113\n\35\3\36\3\36\7\36"+
		"\u0117\n\36\f\36\16\36\u011a\13\36\3\36\3\36\3\37\6\37\u011f\n\37\r\37"+
		"\16\37\u0120\3\37\3\37\3 \3 \3\u00e0\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?\2\3\2\7\5\2C\\aac|\6\2\62"+
		";C\\aac|\3\2\62;\4\2$$^^\5\2\13\f\17\17\"\"\2\u012d\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\3"+
		"A\3\2\2\2\5H\3\2\2\2\7J\3\2\2\2\tL\3\2\2\2\13N\3\2\2\2\rP\3\2\2\2\17U"+
		"\3\2\2\2\21]\3\2\2\2\23l\3\2\2\2\25r\3\2\2\2\27\u0083\3\2\2\2\31\u0093"+
		"\3\2\2\2\33\u009f\3\2\2\2\35\u00b0\3\2\2\2\37\u00b6\3\2\2\2!\u00bd\3\2"+
		"\2\2#\u00c2\3\2\2\2%\u00c7\3\2\2\2\'\u00ce\3\2\2\2)\u00da\3\2\2\2+\u00ea"+
		"\3\2\2\2-\u00f1\3\2\2\2/\u00f3\3\2\2\2\61\u00f5\3\2\2\2\63\u00f8\3\2\2"+
		"\2\65\u00fd\3\2\2\2\67\u0102\3\2\2\29\u0112\3\2\2\2;\u0114\3\2\2\2=\u011e"+
		"\3\2\2\2?\u0124\3\2\2\2AB\7U\2\2BC\7G\2\2CD\7P\2\2DE\7U\2\2EF\7Q\2\2F"+
		"G\7T\2\2G\4\3\2\2\2HI\7}\2\2I\6\3\2\2\2JK\7\177\2\2K\b\3\2\2\2LM\7<\2"+
		"\2M\n\3\2\2\2NO\7?\2\2O\f\3\2\2\2PQ\7j\2\2QR\7q\2\2RS\7u\2\2ST\7v\2\2"+
		"T\16\3\2\2\2UV\7u\2\2VW\7g\2\2WX\7p\2\2XY\7u\2\2YZ\7q\2\2Z[\7t\2\2[\\"+
		"\7u\2\2\\\20\3\2\2\2]^\7d\2\2^_\7g\2\2_`\7j\2\2`a\7c\2\2ab\7x\2\2bc\7"+
		"k\2\2cd\7q\2\2de\7w\2\2ef\7t\2\2fg\7\"\2\2gh\7h\2\2hi\7k\2\2ij\7n\2\2"+
		"jk\7g\2\2k\22\3\2\2\2lm\5+\26\2mn\7\60\2\2no\7d\2\2op\7j\2\2pq\7x\2\2"+
		"q\24\3\2\2\2rs\7u\2\2st\7g\2\2tu\7t\2\2uv\7x\2\2vw\7g\2\2wx\7t\2\2xy\7"+
		"R\2\2yz\7q\2\2z{\7t\2\2{|\7v\2\2|}\7\"\2\2}~\7u\2\2~\177\7v\2\2\177\u0080"+
		"\7c\2\2\u0080\u0081\7t\2\2\u0081\u0082\7v\2\2\u0082\26\3\2\2\2\u0083\u0084"+
		"\7u\2\2\u0084\u0085\7k\2\2\u0085\u0086\7o\2\2\u0086\u0087\7w\2\2\u0087"+
		"\u0088\7n\2\2\u0088\u0089\7c\2\2\u0089\u008a\7v\2\2\u008a\u008b\7k\2\2"+
		"\u008b\u008c\7q\2\2\u008c\u008d\7p\2\2\u008d\u008e\7\"\2\2\u008e\u008f"+
		"\7v\2\2\u008f\u0090\7k\2\2\u0090\u0091\7o\2\2\u0091\u0092\7g\2\2\u0092"+
		"\30\3\2\2\2\u0093\u0094\7v\2\2\u0094\u0095\7k\2\2\u0095\u0096\7o\2\2\u0096"+
		"\u0097\7g\2\2\u0097\u0098\7\"\2\2\u0098\u0099\7y\2\2\u0099\u009a\7k\2"+
		"\2\u009a\u009b\7p\2\2\u009b\u009c\7f\2\2\u009c\u009d\7q\2\2\u009d\u009e"+
		"\7y\2\2\u009e\32\3\2\2\2\u009f\u00a0\7u\2\2\u00a0\u00a1\7k\2\2\u00a1\u00a2"+
		"\7o\2\2\u00a2\u00a3\7w\2\2\u00a3\u00a4\7n\2\2\u00a4\u00a5\7c\2\2\u00a5"+
		"\u00a6\7v\2\2\u00a6\u00a7\7k\2\2\u00a7\u00a8\7q\2\2\u00a8\u00a9\7p\2\2"+
		"\u00a9\u00aa\7\"\2\2\u00aa\u00ab\7u\2\2\u00ab\u00ac\7r\2\2\u00ac\u00ad"+
		"\7g\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af\7f\2\2\u00af\34\3\2\2\2\u00b0\u00b1"+
		"\7u\2\2\u00b1\u00b2\7r\2\2\u00b2\u00b3\7g\2\2\u00b3\u00b4\7g\2\2\u00b4"+
		"\u00b5\7f\2\2\u00b5\36\3\2\2\2\u00b6\u00b7\7u\2\2\u00b7\u00b8\7g\2\2\u00b8"+
		"\u00b9\7p\2\2\u00b9\u00ba\7u\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc\7t\2\2"+
		"\u00bc \3\2\2\2\u00bd\u00be\7p\2\2\u00be\u00bf\7c\2\2\u00bf\u00c0\7o\2"+
		"\2\u00c0\u00c1\7g\2\2\u00c1\"\3\2\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7"+
		"c\2\2\u00c4\u00c5\7v\2\2\u00c5\u00c6\7g\2\2\u00c6$\3\2\2\2\u00c7\u00c8"+
		"\7e\2\2\u00c8\u00c9\7j\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7p\2\2\u00cb"+
		"\u00cc\7i\2\2\u00cc\u00cd\7g\2\2\u00cd&\3\2\2\2\u00ce\u00cf\7t\2\2\u00cf"+
		"\u00d0\7g\2\2\u00d0\u00d1\7n\2\2\u00d1\u00d2\7k\2\2\u00d2\u00d3\7c\2\2"+
		"\u00d3\u00d4\7d\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7n\2\2\u00d6\u00d7"+
		"\7k\2\2\u00d7\u00d8\7v\2\2\u00d8\u00d9\7{\2\2\u00d9(\3\2\2\2\u00da\u00db"+
		"\7\61\2\2\u00db\u00dc\7\61\2\2\u00dc\u00e0\3\2\2\2\u00dd\u00df\13\2\2"+
		"\2\u00de\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e0\u00de"+
		"\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e5\7\17\2\2"+
		"\u00e4\u00e3\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7"+
		"\7\f\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\b\25\2\2\u00e9*\3\2\2\2\u00ea"+
		"\u00ee\t\2\2\2\u00eb\u00ed\t\3\2\2\u00ec\u00eb\3\2\2\2\u00ed\u00f0\3\2"+
		"\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef,\3\2\2\2\u00f0\u00ee"+
		"\3\2\2\2\u00f1\u00f2\7]\2\2\u00f2.\3\2\2\2\u00f3\u00f4\7_\2\2\u00f4\60"+
		"\3\2\2\2\u00f5\u00f6\7.\2\2\u00f6\62\3\2\2\2\u00f7\u00f9\t\4\2\2\u00f8"+
		"\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2"+
		"\2\2\u00fb\64\3\2\2\2\u00fc\u00fe\5\63\32\2\u00fd\u00fc\3\2\2\2\u00fd"+
		"\u00fe\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\7\60\2\2\u0100\u0101\5"+
		"\63\32\2\u0101\66\3\2\2\2\u0102\u0103\59\35\2\u0103\u0104\7\60\2\2\u0104"+
		"\u0105\59\35\2\u0105\u0106\7\60\2\2\u0106\u0107\59\35\2\u0107\u0108\7"+
		"\60\2\2\u0108\u0109\59\35\2\u01098\3\2\2\2\u010a\u010b\5? \2\u010b\u010c"+
		"\5? \2\u010c\u010d\5? \2\u010d\u0113\3\2\2\2\u010e\u010f\5? \2\u010f\u0110"+
		"\5? \2\u0110\u0113\3\2\2\2\u0111\u0113\5? \2\u0112\u010a\3\2\2\2\u0112"+
		"\u010e\3\2\2\2\u0112\u0111\3\2\2\2\u0113:\3\2\2\2\u0114\u0118\7$\2\2\u0115"+
		"\u0117\n\5\2\2\u0116\u0115\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2"+
		"\2\2\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0118\3\2\2\2\u011b"+
		"\u011c\7$\2\2\u011c<\3\2\2\2\u011d\u011f\t\6\2\2\u011e\u011d\3\2\2\2\u011f"+
		"\u0120\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0123\b\37\2\2\u0123>\3\2\2\2\u0124\u0125\4\62;\2\u0125@\3"+
		"\2\2\2\13\2\u00e0\u00e4\u00ee\u00fa\u00fd\u0112\u0118\u0120\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}