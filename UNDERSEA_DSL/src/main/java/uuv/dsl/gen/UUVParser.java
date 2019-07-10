// Generated from UUV.g4 by ANTLR 4.7.2

  package uuv.dsl.gen;
  import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UUVParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ASSIGN=6, SERVER_HOST=7, SERVER_PORT=8, 
		SIMULATION_TIME=9, TIME_WINDOW=10, SIMULATION_SPEED=11, SPEED=12, SENSOR=13, 
		NAME=14, RATE=15, CHANGE=16, RELIABILITY=17, SLCOMMENT=18, ID=19, INT=20, 
		DOUBLE=21, IP=22, OCTET=23, STRING=24, WS=25;
	public static final int
		RULE_model = 0, RULE_simulation = 1, RULE_invocation = 2, RULE_host = 3, 
		RULE_port = 4, RULE_speed = 5, RULE_uuv = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "simulation", "invocation", "host", "port", "speed", "uuv"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'host'", "'port'", 
			"'simulation time'", "'time window'", "'simulation speed'", "'speed'", 
			"'sensor'", "'name'", "'rate'", "'change'", "'reliability'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SERVER_PORT", 
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

	@Override
	public String getGrammarFileName() { return "UUV.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		Set<String> types = new HashSet<String>() {{add("T");}};
		boolean istype() { return types.contains(getCurrentToken().getText()); }

	public UUVParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModelContext extends ParserRuleContext {
		public SimulationContext simulation() {
			return getRuleContext(SimulationContext.class,0);
		}
		public InvocationContext invocation() {
			return getRuleContext(InvocationContext.class,0);
		}
		public HostContext host() {
			return getRuleContext(HostContext.class,0);
		}
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public UuvContext uuv() {
			return getRuleContext(UuvContext.class,0);
		}
		public SpeedContext speed() {
			return getRuleContext(SpeedContext.class,0);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SIMULATION_TIME:
				{
				setState(14);
				simulation();
				}
				break;
			case TIME_WINDOW:
				{
				setState(15);
				invocation();
				}
				break;
			case SERVER_HOST:
				{
				setState(16);
				host();
				}
				break;
			case SERVER_PORT:
				{
				setState(17);
				port();
				}
				break;
			case T__1:
				{
				setState(18);
				uuv();
				}
				break;
			case SIMULATION_SPEED:
				{
				setState(19);
				speed();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimulationContext extends ParserRuleContext {
		public Token value;
		public TerminalNode SIMULATION_TIME() { return getToken(UUVParser.SIMULATION_TIME, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public SimulationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simulation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterSimulation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitSimulation(this);
		}
	}

	public final SimulationContext simulation() throws RecognitionException {
		SimulationContext _localctx = new SimulationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_simulation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			match(SIMULATION_TIME);
			setState(23);
			match(ASSIGN);
			setState(24);
			((SimulationContext)_localctx).value = match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InvocationContext extends ParserRuleContext {
		public Token value;
		public TerminalNode TIME_WINDOW() { return getToken(UUVParser.TIME_WINDOW, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(UUVParser.DOUBLE, 0); }
		public InvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitInvocation(this);
		}
	}

	public final InvocationContext invocation() throws RecognitionException {
		InvocationContext _localctx = new InvocationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(TIME_WINDOW);
			setState(27);
			match(ASSIGN);
			setState(28);
			((InvocationContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==DOUBLE) ) {
				((InvocationContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HostContext extends ParserRuleContext {
		public Token value;
		public TerminalNode SERVER_HOST() { return getToken(UUVParser.SERVER_HOST, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode IP() { return getToken(UUVParser.IP, 0); }
		public HostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_host; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterHost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitHost(this);
		}
	}

	public final HostContext host() throws RecognitionException {
		HostContext _localctx = new HostContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(SERVER_HOST);
			setState(31);
			match(ASSIGN);
			setState(32);
			((HostContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==IP) ) {
				((HostContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortContext extends ParserRuleContext {
		public Token value;
		public TerminalNode SERVER_PORT() { return getToken(UUVParser.SERVER_PORT, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitPort(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(SERVER_PORT);
			setState(35);
			match(ASSIGN);
			setState(36);
			((PortContext)_localctx).value = match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SpeedContext extends ParserRuleContext {
		public Token value;
		public TerminalNode SIMULATION_SPEED() { return getToken(UUVParser.SIMULATION_SPEED, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public SpeedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_speed; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterSpeed(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitSpeed(this);
		}
	}

	public final SpeedContext speed() throws RecognitionException {
		SpeedContext _localctx = new SpeedContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_speed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(SIMULATION_SPEED);
			setState(39);
			match(ASSIGN);
			setState(40);
			((SpeedContext)_localctx).value = match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UuvContext extends ParserRuleContext {
		public Token name;
		public Token min;
		public Token max;
		public Token steps;
		public Token value;
		public Token sensors;
		public List<TerminalNode> NAME() { return getTokens(UUVParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(UUVParser.NAME, i);
		}
		public List<TerminalNode> ASSIGN() { return getTokens(UUVParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(UUVParser.ASSIGN, i);
		}
		public List<TerminalNode> SPEED() { return getTokens(UUVParser.SPEED); }
		public TerminalNode SPEED(int i) {
			return getToken(UUVParser.SPEED, i);
		}
		public List<TerminalNode> SERVER_PORT() { return getTokens(UUVParser.SERVER_PORT); }
		public TerminalNode SERVER_PORT(int i) {
			return getToken(UUVParser.SERVER_PORT, i);
		}
		public List<TerminalNode> SENSOR() { return getTokens(UUVParser.SENSOR); }
		public TerminalNode SENSOR(int i) {
			return getToken(UUVParser.SENSOR, i);
		}
		public List<TerminalNode> ID() { return getTokens(UUVParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(UUVParser.ID, i);
		}
		public List<TerminalNode> INT() { return getTokens(UUVParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(UUVParser.INT, i);
		}
		public List<TerminalNode> DOUBLE() { return getTokens(UUVParser.DOUBLE); }
		public TerminalNode DOUBLE(int i) {
			return getToken(UUVParser.DOUBLE, i);
		}
		public UuvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uuv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterUuv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitUuv(this);
		}
	}

	public final UuvContext uuv() throws RecognitionException {
		UuvContext _localctx = new UuvContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_uuv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__1);
			setState(43);
			match(T__2);
			setState(64); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(64);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(44);
					match(NAME);
					setState(45);
					match(ASSIGN);
					setState(46);
					((UuvContext)_localctx).name = match(ID);
					}
					break;
				case SPEED:
					{
					setState(47);
					match(SPEED);
					setState(48);
					match(ASSIGN);
					setState(49);
					((UuvContext)_localctx).min = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==DOUBLE) ) {
						((UuvContext)_localctx).min = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(50);
					match(T__3);
					setState(51);
					((UuvContext)_localctx).max = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==DOUBLE) ) {
						((UuvContext)_localctx).max = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(52);
					match(T__3);
					setState(53);
					((UuvContext)_localctx).steps = match(INT);
					}
					break;
				case SERVER_PORT:
					{
					setState(54);
					match(SERVER_PORT);
					setState(55);
					match(ASSIGN);
					setState(56);
					((UuvContext)_localctx).value = match(INT);
					}
					break;
				case SENSOR:
					{
					setState(57);
					match(SENSOR);
					setState(58);
					match(ASSIGN);
					setState(60); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(59);
						((UuvContext)_localctx).sensors = match(ID);
						}
						}
						setState(62); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==ID );
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(66); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SERVER_PORT) | (1L << SPEED) | (1L << SENSOR) | (1L << NAME))) != 0) );
			setState(68);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33I\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\2\3\2\3\2\5\2"+
		"\27\n\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\6\b?\n\b\r\b\16\b@\6\bC\n\b\r\b\16\bD\3\b\3\b\3\b"+
		"\2\2\t\2\4\6\b\n\f\16\2\4\3\2\26\27\4\2\3\3\30\30\2K\2\26\3\2\2\2\4\30"+
		"\3\2\2\2\6\34\3\2\2\2\b \3\2\2\2\n$\3\2\2\2\f(\3\2\2\2\16,\3\2\2\2\20"+
		"\27\5\4\3\2\21\27\5\6\4\2\22\27\5\b\5\2\23\27\5\n\6\2\24\27\5\16\b\2\25"+
		"\27\5\f\7\2\26\20\3\2\2\2\26\21\3\2\2\2\26\22\3\2\2\2\26\23\3\2\2\2\26"+
		"\24\3\2\2\2\26\25\3\2\2\2\27\3\3\2\2\2\30\31\7\13\2\2\31\32\7\b\2\2\32"+
		"\33\7\26\2\2\33\5\3\2\2\2\34\35\7\f\2\2\35\36\7\b\2\2\36\37\t\2\2\2\37"+
		"\7\3\2\2\2 !\7\t\2\2!\"\7\b\2\2\"#\t\3\2\2#\t\3\2\2\2$%\7\n\2\2%&\7\b"+
		"\2\2&\'\7\26\2\2\'\13\3\2\2\2()\7\r\2\2)*\7\b\2\2*+\7\26\2\2+\r\3\2\2"+
		"\2,-\7\4\2\2-B\7\5\2\2./\7\20\2\2/\60\7\b\2\2\60C\7\25\2\2\61\62\7\16"+
		"\2\2\62\63\7\b\2\2\63\64\t\2\2\2\64\65\7\6\2\2\65\66\t\2\2\2\66\67\7\6"+
		"\2\2\67C\7\26\2\289\7\n\2\29:\7\b\2\2:C\7\26\2\2;<\7\17\2\2<>\7\b\2\2"+
		"=?\7\25\2\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B.\3\2\2"+
		"\2B\61\3\2\2\2B8\3\2\2\2B;\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EF\3\2"+
		"\2\2FG\7\7\2\2G\17\3\2\2\2\6\26@BD";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}