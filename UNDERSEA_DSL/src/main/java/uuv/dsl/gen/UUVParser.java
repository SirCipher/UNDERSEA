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
		BEHAVIOUR_FILE=9, BHV_FILE=10, PORT_START=11, SERVER_PORT=12, SIMULATION_TIME=13, 
		TIME_WINDOW=14, SIMULATION_SPEED=15, SPEED=16, SENSOR=17, NAME=18, RATE=19, 
		CHANGE=20, RELIABILITY=21, SLCOMMENT=22, ID=23, BEGL=24, ENDL=25, SEP=26, 
		INT=27, DOUBLE=28, IP=29, OCTET=30, STRING=31, WS=32;
	public static final int
		RULE_model = 0, RULE_portStart = 1, RULE_simulation = 2, RULE_invocation = 3, 
		RULE_host = 4, RULE_port = 5, RULE_speed = 6, RULE_list = 7, RULE_elems = 8, 
		RULE_elem = 9, RULE_uuv = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "portStart", "simulation", "invocation", "host", "port", "speed", 
			"list", "elems", "elem", "uuv"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'host'", "'sensors'", 
			"'behaviour file'", null, "'port start'", "'port'", "'simulation time'", 
			"'time window'", "'simulation speed'", "'speed'", "'sensor'", "'name'", 
			"'rate'", "'change'", "'reliability'", null, null, "'['", "']'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SENSORS", 
			"BEHAVIOUR_FILE", "BHV_FILE", "PORT_START", "SERVER_PORT", "SIMULATION_TIME", 
			"TIME_WINDOW", "SIMULATION_SPEED", "SPEED", "SENSOR", "NAME", "RATE", 
			"CHANGE", "RELIABILITY", "SLCOMMENT", "ID", "BEGL", "ENDL", "SEP", "INT", 
			"DOUBLE", "IP", "OCTET", "STRING", "WS"
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
		public List<PortStartContext> portStart() {
			return getRuleContexts(PortStartContext.class);
		}
		public PortStartContext portStart(int i) {
			return getRuleContext(PortStartContext.class,i);
		}
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
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(29);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PORT_START:
					{
					setState(22);
					portStart();
					}
					break;
				case SIMULATION_TIME:
					{
					setState(23);
					simulation();
					}
					break;
				case TIME_WINDOW:
					{
					setState(24);
					invocation();
					}
					break;
				case SERVER_HOST:
					{
					setState(25);
					host();
					}
					break;
				case SERVER_PORT:
					{
					setState(26);
					port();
					}
					break;
				case T__1:
					{
					setState(27);
					uuv();
					}
					break;
				case SIMULATION_SPEED:
					{
					setState(28);
					speed();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << SERVER_HOST) | (1L << PORT_START) | (1L << SERVER_PORT) | (1L << SIMULATION_TIME) | (1L << TIME_WINDOW) | (1L << SIMULATION_SPEED))) != 0) );
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

	public static class PortStartContext extends ParserRuleContext {
		public Token value;
		public TerminalNode PORT_START() { return getToken(UUVParser.PORT_START, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public PortStartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portStart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterPortStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitPortStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitPortStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortStartContext portStart() throws RecognitionException {
		PortStartContext _localctx = new PortStartContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_portStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(PORT_START);
			setState(34);
			match(ASSIGN);
			setState(35);
			((PortStartContext)_localctx).value = match(INT);
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
		enterRule(_localctx, 4, RULE_simulation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(SIMULATION_TIME);
			setState(38);
			match(ASSIGN);
			setState(39);
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
		enterRule(_localctx, 6, RULE_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(TIME_WINDOW);
			setState(42);
			match(ASSIGN);
			setState(43);
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
		enterRule(_localctx, 8, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(SERVER_HOST);
			setState(46);
			match(ASSIGN);
			setState(47);
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
		enterRule(_localctx, 10, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(SERVER_PORT);
			setState(50);
			match(ASSIGN);
			setState(51);
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
		enterRule(_localctx, 12, RULE_speed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(SIMULATION_SPEED);
			setState(54);
			match(ASSIGN);
			setState(55);
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
		enterRule(_localctx, 14, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(BEGL);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(58);
				elems();
				}
			}

			setState(61);
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
		enterRule(_localctx, 16, RULE_elems);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			elem();
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEP) {
				{
				{
				setState(64);
				match(SEP);
				setState(65);
				elem();
				}
				}
				setState(70);
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
		enterRule(_localctx, 18, RULE_elem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
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
		public Token behaviourFile;
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
		public List<TerminalNode> BEHAVIOUR_FILE() { return getTokens(UUVParser.BEHAVIOUR_FILE); }
		public TerminalNode BEHAVIOUR_FILE(int i) {
			return getToken(UUVParser.BEHAVIOUR_FILE, i);
		}
		public List<TerminalNode> SENSORS() { return getTokens(UUVParser.SENSORS); }
		public TerminalNode SENSORS(int i) {
			return getToken(UUVParser.SENSORS, i);
		}
		public List<TerminalNode> ENDL() { return getTokens(UUVParser.ENDL); }
		public TerminalNode ENDL(int i) {
			return getToken(UUVParser.ENDL, i);
		}
		public List<TerminalNode> ID() { return getTokens(UUVParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(UUVParser.ID, i);
		}
		public List<TerminalNode> INT() { return getTokens(UUVParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(UUVParser.INT, i);
		}
		public List<TerminalNode> BHV_FILE() { return getTokens(UUVParser.BHV_FILE); }
		public TerminalNode BHV_FILE(int i) {
			return getToken(UUVParser.BHV_FILE, i);
		}
		public List<TerminalNode> BEGL() { return getTokens(UUVParser.BEGL); }
		public TerminalNode BEGL(int i) {
			return getToken(UUVParser.BEGL, i);
		}
		public List<TerminalNode> DOUBLE() { return getTokens(UUVParser.DOUBLE); }
		public TerminalNode DOUBLE(int i) {
			return getToken(UUVParser.DOUBLE, i);
		}
		public List<ElemsContext> elems() {
			return getRuleContexts(ElemsContext.class);
		}
		public ElemsContext elems(int i) {
			return getRuleContext(ElemsContext.class,i);
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
		enterRule(_localctx, 20, RULE_uuv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__1);
			setState(74);
			match(T__2);
			setState(95); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(95);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(75);
					match(NAME);
					setState(76);
					match(ASSIGN);
					setState(77);
					((UuvContext)_localctx).name = match(ID);
					}
					break;
				case SPEED:
					{
					setState(78);
					match(SPEED);
					setState(79);
					match(ASSIGN);
					setState(80);
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
					setState(81);
					match(T__3);
					setState(82);
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
					setState(83);
					match(T__3);
					setState(84);
					((UuvContext)_localctx).steps = match(INT);
					}
					break;
				case BEHAVIOUR_FILE:
					{
					setState(85);
					match(BEHAVIOUR_FILE);
					setState(86);
					match(ASSIGN);
					setState(87);
					((UuvContext)_localctx).behaviourFile = match(BHV_FILE);
					}
					break;
				case SENSORS:
					{
					setState(88);
					match(SENSORS);
					setState(89);
					match(ASSIGN);
					setState(90);
					((UuvContext)_localctx).sensors = match(BEGL);
					setState(92);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ID) {
						{
						setState(91);
						elems();
						}
					}

					setState(94);
					match(ENDL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(97); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SENSORS) | (1L << BEHAVIOUR_FILE) | (1L << SPEED) | (1L << NAME))) != 0) );
			setState(99);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"h\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2 \n\2\r\2\16\2!\3\3\3\3\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\5\t>\n\t\3\t\3\t\3\n\3\n\3\n\7\nE\n\n\f\n\16\nH\13"+
		"\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\5\f_\n\f\3\f\6\fb\n\f\r\f\16\fc\3\f\3\f\3\f\2\2\r\2"+
		"\4\6\b\n\f\16\20\22\24\26\2\4\3\2\35\36\4\2\3\3\37\37\2j\2\37\3\2\2\2"+
		"\4#\3\2\2\2\6\'\3\2\2\2\b+\3\2\2\2\n/\3\2\2\2\f\63\3\2\2\2\16\67\3\2\2"+
		"\2\20;\3\2\2\2\22A\3\2\2\2\24I\3\2\2\2\26K\3\2\2\2\30 \5\4\3\2\31 \5\6"+
		"\4\2\32 \5\b\5\2\33 \5\n\6\2\34 \5\f\7\2\35 \5\26\f\2\36 \5\16\b\2\37"+
		"\30\3\2\2\2\37\31\3\2\2\2\37\32\3\2\2\2\37\33\3\2\2\2\37\34\3\2\2\2\37"+
		"\35\3\2\2\2\37\36\3\2\2\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\3\3\2\2\2"+
		"#$\7\r\2\2$%\7\b\2\2%&\7\35\2\2&\5\3\2\2\2\'(\7\17\2\2()\7\b\2\2)*\7\35"+
		"\2\2*\7\3\2\2\2+,\7\20\2\2,-\7\b\2\2-.\t\2\2\2.\t\3\2\2\2/\60\7\t\2\2"+
		"\60\61\7\b\2\2\61\62\t\3\2\2\62\13\3\2\2\2\63\64\7\16\2\2\64\65\7\b\2"+
		"\2\65\66\7\35\2\2\66\r\3\2\2\2\678\7\21\2\289\7\b\2\29:\7\35\2\2:\17\3"+
		"\2\2\2;=\7\32\2\2<>\5\22\n\2=<\3\2\2\2=>\3\2\2\2>?\3\2\2\2?@\7\33\2\2"+
		"@\21\3\2\2\2AF\5\24\13\2BC\7\34\2\2CE\5\24\13\2DB\3\2\2\2EH\3\2\2\2FD"+
		"\3\2\2\2FG\3\2\2\2G\23\3\2\2\2HF\3\2\2\2IJ\7\31\2\2J\25\3\2\2\2KL\7\4"+
		"\2\2La\7\5\2\2MN\7\24\2\2NO\7\b\2\2Ob\7\31\2\2PQ\7\22\2\2QR\7\b\2\2RS"+
		"\t\2\2\2ST\7\6\2\2TU\t\2\2\2UV\7\6\2\2Vb\7\35\2\2WX\7\13\2\2XY\7\b\2\2"+
		"Yb\7\f\2\2Z[\7\n\2\2[\\\7\b\2\2\\^\7\32\2\2]_\5\22\n\2^]\3\2\2\2^_\3\2"+
		"\2\2_`\3\2\2\2`b\7\33\2\2aM\3\2\2\2aP\3\2\2\2aW\3\2\2\2aZ\3\2\2\2bc\3"+
		"\2\2\2ca\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\7\7\2\2f\27\3\2\2\2\t\37!=F^ac";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}