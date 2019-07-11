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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ASSIGN=6, SERVER_HOST=7, SENSORS=8, 
		SERVER_PORT=9, SIMULATION_TIME=10, TIME_WINDOW=11, SIMULATION_SPEED=12, 
		SPEED=13, SENSOR=14, NAME=15, RATE=16, CHANGE=17, RELIABILITY=18, SLCOMMENT=19, 
		ID=20, BEGL=21, ENDL=22, SEP=23, INT=24, DOUBLE=25, IP=26, OCTET=27, STRING=28, 
		WS=29;
	public static final int
		RULE_model = 0, RULE_simulation = 1, RULE_invocation = 2, RULE_host = 3, 
		RULE_port = 4, RULE_speed = 5, RULE_list = 6, RULE_elems = 7, RULE_elem = 8, 
		RULE_uuv = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "simulation", "invocation", "host", "port", "speed", "list", 
			"elems", "elem", "uuv"
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
		public List<SimulationContext> simulation() {
			return getRuleContexts(SimulationContext.class);
		}
		public SimulationContext simulation(int i) {
			return getRuleContext(SimulationContext.class,i);
		}
		public List<InvocationContext> invocation() {
			return getRuleContexts(InvocationContext.class);
		}
		public InvocationContext invocation(int i) {
			return getRuleContext(InvocationContext.class,i);
		}
		public List<HostContext> host() {
			return getRuleContexts(HostContext.class);
		}
		public HostContext host(int i) {
			return getRuleContext(HostContext.class,i);
		}
		public List<PortContext> port() {
			return getRuleContexts(PortContext.class);
		}
		public PortContext port(int i) {
			return getRuleContext(PortContext.class,i);
		}
		public List<UuvContext> uuv() {
			return getRuleContexts(UuvContext.class);
		}
		public UuvContext uuv(int i) {
			return getRuleContext(UuvContext.class,i);
		}
		public List<SpeedContext> speed() {
			return getRuleContexts(SpeedContext.class);
		}
		public SpeedContext speed(int i) {
			return getRuleContext(SpeedContext.class,i);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitModel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(26);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SIMULATION_TIME:
					{
					setState(20);
					simulation();
					}
					break;
				case TIME_WINDOW:
					{
					setState(21);
					invocation();
					}
					break;
				case SERVER_HOST:
					{
					setState(22);
					host();
					}
					break;
				case SERVER_PORT:
					{
					setState(23);
					port();
					}
					break;
				case T__1:
					{
					setState(24);
					uuv();
					}
					break;
				case SIMULATION_SPEED:
					{
					setState(25);
					speed();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(28); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << SERVER_HOST) | (1L << SERVER_PORT) | (1L << SIMULATION_TIME) | (1L << TIME_WINDOW) | (1L << SIMULATION_SPEED))) != 0) );
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitSimulation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimulationContext simulation() throws RecognitionException {
		SimulationContext _localctx = new SimulationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_simulation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(SIMULATION_TIME);
			setState(31);
			match(ASSIGN);
			setState(32);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvocationContext invocation() throws RecognitionException {
		InvocationContext _localctx = new InvocationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(TIME_WINDOW);
			setState(35);
			match(ASSIGN);
			setState(36);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitHost(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HostContext host() throws RecognitionException {
		HostContext _localctx = new HostContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(SERVER_HOST);
			setState(39);
			match(ASSIGN);
			setState(40);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(SERVER_PORT);
			setState(43);
			match(ASSIGN);
			setState(44);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitSpeed(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpeedContext speed() throws RecognitionException {
		SpeedContext _localctx = new SpeedContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_speed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(SIMULATION_SPEED);
			setState(47);
			match(ASSIGN);
			setState(48);
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

	public static class ListContext extends ParserRuleContext {
		public TerminalNode BEGL() { return getToken(UUVParser.BEGL, 0); }
		public TerminalNode ENDL() { return getToken(UUVParser.ENDL, 0); }
		public ElemsContext elems() {
			return getRuleContext(ElemsContext.class,0);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(BEGL);
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(51);
				elems();
				}
			}

			setState(54);
			match(ENDL);
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

	public static class ElemsContext extends ParserRuleContext {
		public List<ElemContext> elem() {
			return getRuleContexts(ElemContext.class);
		}
		public ElemContext elem(int i) {
			return getRuleContext(ElemContext.class,i);
		}
		public List<TerminalNode> SEP() { return getTokens(UUVParser.SEP); }
		public TerminalNode SEP(int i) {
			return getToken(UUVParser.SEP, i);
		}
		public ElemsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elems; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterElems(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitElems(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitElems(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElemsContext elems() throws RecognitionException {
		ElemsContext _localctx = new ElemsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_elems);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			elem();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEP) {
				{
				{
				setState(57);
				match(SEP);
				setState(58);
				elem();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ElemContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(UUVParser.ID, 0); }
		public ElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElemContext elem() throws RecognitionException {
		ElemContext _localctx = new ElemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(ID);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitUuv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UuvContext uuv() throws RecognitionException {
		UuvContext _localctx = new UuvContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_uuv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__1);
			setState(67);
			match(T__2);
			setState(81); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(81);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(68);
					match(NAME);
					setState(69);
					match(ASSIGN);
					setState(70);
					((UuvContext)_localctx).name = match(ID);
					}
					break;
				case SPEED:
					{
					setState(71);
					match(SPEED);
					setState(72);
					match(ASSIGN);
					setState(73);
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
					setState(74);
					match(T__3);
					setState(75);
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
					setState(76);
					match(T__3);
					setState(77);
					((UuvContext)_localctx).steps = match(INT);
					}
					break;
				case SERVER_PORT:
					{
					setState(78);
					match(SERVER_PORT);
					setState(79);
					match(ASSIGN);
					setState(80);
					((UuvContext)_localctx).value = match(INT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(83); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SERVER_PORT) | (1L << SPEED) | (1L << NAME))) != 0) );
			setState(85);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\37Z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\6\2\35\n\2\r\2\16\2\36\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\5\b\67"+
		"\n\b\3\b\3\b\3\t\3\t\3\t\7\t>\n\t\f\t\16\tA\13\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\6\13T\n\13"+
		"\r\13\16\13U\3\13\3\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\4\3\2\32\33"+
		"\4\2\3\3\34\34\2Z\2\34\3\2\2\2\4 \3\2\2\2\6$\3\2\2\2\b(\3\2\2\2\n,\3\2"+
		"\2\2\f\60\3\2\2\2\16\64\3\2\2\2\20:\3\2\2\2\22B\3\2\2\2\24D\3\2\2\2\26"+
		"\35\5\4\3\2\27\35\5\6\4\2\30\35\5\b\5\2\31\35\5\n\6\2\32\35\5\24\13\2"+
		"\33\35\5\f\7\2\34\26\3\2\2\2\34\27\3\2\2\2\34\30\3\2\2\2\34\31\3\2\2\2"+
		"\34\32\3\2\2\2\34\33\3\2\2\2\35\36\3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2"+
		"\37\3\3\2\2\2 !\7\f\2\2!\"\7\b\2\2\"#\7\32\2\2#\5\3\2\2\2$%\7\r\2\2%&"+
		"\7\b\2\2&\'\t\2\2\2\'\7\3\2\2\2()\7\t\2\2)*\7\b\2\2*+\t\3\2\2+\t\3\2\2"+
		"\2,-\7\13\2\2-.\7\b\2\2./\7\32\2\2/\13\3\2\2\2\60\61\7\16\2\2\61\62\7"+
		"\b\2\2\62\63\7\32\2\2\63\r\3\2\2\2\64\66\7\27\2\2\65\67\5\20\t\2\66\65"+
		"\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\7\30\2\29\17\3\2\2\2:?\5\22\n\2;"+
		"<\7\31\2\2<>\5\22\n\2=;\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@\21\3\2"+
		"\2\2A?\3\2\2\2BC\7\26\2\2C\23\3\2\2\2DE\7\4\2\2ES\7\5\2\2FG\7\21\2\2G"+
		"H\7\b\2\2HT\7\26\2\2IJ\7\17\2\2JK\7\b\2\2KL\t\2\2\2LM\7\6\2\2MN\t\2\2"+
		"\2NO\7\6\2\2OT\7\32\2\2PQ\7\13\2\2QR\7\b\2\2RT\7\32\2\2SF\3\2\2\2SI\3"+
		"\2\2\2SP\3\2\2\2TU\3\2\2\2US\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\7\7\2\2X\25"+
		"\3\2\2\2\b\34\36\66?SU";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}