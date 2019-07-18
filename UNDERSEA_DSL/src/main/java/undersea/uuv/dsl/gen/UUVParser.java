// Generated from UUV.g4 by ANTLR 4.7.2

package undersea.uuv.dsl.gen;
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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ASSIGN=6, MISSION_NAME=7, NAME=8, 
		TIME_WINDOW=9, SERVER_HOST=10, BEHAVIOUR_FILE=11, BHV_FILE=12, SENSORS=13, 
		DOUBLE=14, PORT_START=15, SIMULATION_TIME=16, SIMULATION_SPEED=17, INT=18, 
		IP=19, OCTET=20, BEGL=21, ENDL=22, SEP=23, SPEED=24, ID=25, WS=26, ErrorCharacter=27;
	public static final int
		RULE_model = 0, RULE_missionName = 1, RULE_portStart = 2, RULE_simulation = 3, 
		RULE_speed = 4, RULE_invocation = 5, RULE_host = 6, RULE_list = 7, RULE_elems = 8, 
		RULE_elem = 9, RULE_uuv = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "missionName", "portStart", "simulation", "speed", "invocation", 
			"host", "list", "elems", "elem", "uuv"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'mission name'", 
			"'name'", "'time window'", "'host'", "'behaviour file'", null, "'sensors'", 
			null, "'server port start'", "'simulation time'", "'simulation speed'", 
			null, null, null, "'['", "']'", "','", "'speed'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "MISSION_NAME", "NAME", 
			"TIME_WINDOW", "SERVER_HOST", "BEHAVIOUR_FILE", "BHV_FILE", "SENSORS", 
			"DOUBLE", "PORT_START", "SIMULATION_TIME", "SIMULATION_SPEED", "INT", 
			"IP", "OCTET", "BEGL", "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
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

	public UUVParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModelContext extends ParserRuleContext {
		public List<MissionNameContext> missionName() {
			return getRuleContexts(MissionNameContext.class);
		}
		public MissionNameContext missionName(int i) {
			return getRuleContext(MissionNameContext.class,i);
		}
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
		public List<SpeedContext> speed() {
			return getRuleContexts(SpeedContext.class);
		}
		public SpeedContext speed(int i) {
			return getRuleContext(SpeedContext.class,i);
		}
		public List<UuvContext> uuv() {
			return getRuleContexts(UuvContext.class);
		}
		public UuvContext uuv(int i) {
			return getRuleContext(UuvContext.class,i);
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(33); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(33);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MISSION_NAME:
					{
					setState(22);
					missionName();
					}
					break;
				case PORT_START:
					{
					setState(23);
					portStart();
					}
					break;
				case SIMULATION_TIME:
					{
					setState(24);
					simulation();
					}
					break;
				case TIME_WINDOW:
					{
					setState(25);
					invocation();
					}
					break;
				case SERVER_HOST:
					{
					setState(26);
					host();
					}
					break;
				case SIMULATION_SPEED:
					{
					setState(27);
					speed();
					}
					break;
				case T__1:
					{
					setState(29); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(28);
							uuv();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(31); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(35); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << MISSION_NAME) | (1L << TIME_WINDOW) | (1L << SERVER_HOST) | (1L << PORT_START) | (1L << SIMULATION_TIME) | (1L << SIMULATION_SPEED))) != 0) );
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

	public static class MissionNameContext extends ParserRuleContext {
		public Token value;
		public TerminalNode MISSION_NAME() { return getToken(UUVParser.MISSION_NAME, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode ID() { return getToken(UUVParser.ID, 0); }
		public MissionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_missionName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterMissionName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitMissionName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitMissionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MissionNameContext missionName() throws RecognitionException {
		MissionNameContext _localctx = new MissionNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_missionName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(MISSION_NAME);
			setState(38);
			match(ASSIGN);
			setState(39);
			((MissionNameContext)_localctx).value = match(ID);
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
		enterRule(_localctx, 4, RULE_portStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(PORT_START);
			setState(42);
			match(ASSIGN);
			setState(43);
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
		enterRule(_localctx, 6, RULE_simulation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(SIMULATION_TIME);
			setState(46);
			match(ASSIGN);
			setState(47);
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
		enterRule(_localctx, 8, RULE_speed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(SIMULATION_SPEED);
			setState(50);
			match(ASSIGN);
			setState(51);
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
		enterRule(_localctx, 10, RULE_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(TIME_WINDOW);
			setState(54);
			match(ASSIGN);
			setState(55);
			((InvocationContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==DOUBLE || _la==INT) ) {
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
		enterRule(_localctx, 12, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(SERVER_HOST);
			setState(58);
			match(ASSIGN);
			setState(59);
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
			setState(61);
			match(BEGL);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(62);
				elems();
				}
			}

			setState(65);
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
			setState(67);
			elem();
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEP) {
				{
				{
				setState(68);
				match(SEP);
				setState(69);
				elem();
				}
				}
				setState(74);
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
			setState(75);
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
		public TerminalNode NAME() { return getToken(UUVParser.NAME, 0); }
		public List<TerminalNode> ASSIGN() { return getTokens(UUVParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(UUVParser.ASSIGN, i);
		}
		public TerminalNode SPEED() { return getToken(UUVParser.SPEED, 0); }
		public TerminalNode BEHAVIOUR_FILE() { return getToken(UUVParser.BEHAVIOUR_FILE, 0); }
		public TerminalNode SENSORS() { return getToken(UUVParser.SENSORS, 0); }
		public TerminalNode ENDL() { return getToken(UUVParser.ENDL, 0); }
		public TerminalNode ID() { return getToken(UUVParser.ID, 0); }
		public List<TerminalNode> INT() { return getTokens(UUVParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(UUVParser.INT, i);
		}
		public TerminalNode BHV_FILE() { return getToken(UUVParser.BHV_FILE, 0); }
		public TerminalNode BEGL() { return getToken(UUVParser.BEGL, 0); }
		public List<TerminalNode> DOUBLE() { return getTokens(UUVParser.DOUBLE); }
		public TerminalNode DOUBLE(int i) {
			return getToken(UUVParser.DOUBLE, i);
		}
		public ElemsContext elems() {
			return getRuleContext(ElemsContext.class,0);
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
			setState(77);
			match(T__1);
			setState(78);
			match(T__2);
			setState(79);
			match(NAME);
			setState(80);
			match(ASSIGN);
			setState(81);
			((UuvContext)_localctx).name = match(ID);
			setState(82);
			match(SPEED);
			setState(83);
			match(ASSIGN);
			setState(84);
			((UuvContext)_localctx).min = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==DOUBLE || _la==INT) ) {
				((UuvContext)_localctx).min = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(85);
			match(T__3);
			setState(86);
			((UuvContext)_localctx).max = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==DOUBLE || _la==INT) ) {
				((UuvContext)_localctx).max = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(87);
			match(T__3);
			setState(88);
			((UuvContext)_localctx).steps = match(INT);
			setState(89);
			match(BEHAVIOUR_FILE);
			setState(90);
			match(ASSIGN);
			setState(91);
			((UuvContext)_localctx).behaviourFile = match(BHV_FILE);
			setState(92);
			match(SENSORS);
			setState(93);
			match(ASSIGN);
			setState(94);
			((UuvContext)_localctx).sensors = match(BEGL);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(95);
				elems();
				}
			}

			setState(98);
			match(ENDL);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35h\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2 \n\2\r\2\16\2!\6\2$\n\2\r\2\16"+
		"\2%\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\5\tB\n\t\3\t\3\t\3\n\3\n\3\n\7"+
		"\nI\n\n\f\n\16\nL\13\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fc\n\f\3\f\3\f\3\f\3\f\2\2\r"+
		"\2\4\6\b\n\f\16\20\22\24\26\2\4\4\2\20\20\24\24\4\2\3\3\25\25\2g\2#\3"+
		"\2\2\2\4\'\3\2\2\2\6+\3\2\2\2\b/\3\2\2\2\n\63\3\2\2\2\f\67\3\2\2\2\16"+
		";\3\2\2\2\20?\3\2\2\2\22E\3\2\2\2\24M\3\2\2\2\26O\3\2\2\2\30$\5\4\3\2"+
		"\31$\5\6\4\2\32$\5\b\5\2\33$\5\f\7\2\34$\5\16\b\2\35$\5\n\6\2\36 \5\26"+
		"\f\2\37\36\3\2\2\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"$\3\2\2\2#\30\3\2"+
		"\2\2#\31\3\2\2\2#\32\3\2\2\2#\33\3\2\2\2#\34\3\2\2\2#\35\3\2\2\2#\37\3"+
		"\2\2\2$%\3\2\2\2%#\3\2\2\2%&\3\2\2\2&\3\3\2\2\2\'(\7\t\2\2()\7\b\2\2)"+
		"*\7\33\2\2*\5\3\2\2\2+,\7\21\2\2,-\7\b\2\2-.\7\24\2\2.\7\3\2\2\2/\60\7"+
		"\22\2\2\60\61\7\b\2\2\61\62\7\24\2\2\62\t\3\2\2\2\63\64\7\23\2\2\64\65"+
		"\7\b\2\2\65\66\7\24\2\2\66\13\3\2\2\2\678\7\13\2\289\7\b\2\29:\t\2\2\2"+
		":\r\3\2\2\2;<\7\f\2\2<=\7\b\2\2=>\t\3\2\2>\17\3\2\2\2?A\7\27\2\2@B\5\22"+
		"\n\2A@\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\7\30\2\2D\21\3\2\2\2EJ\5\24\13\2"+
		"FG\7\31\2\2GI\5\24\13\2HF\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\23\3"+
		"\2\2\2LJ\3\2\2\2MN\7\33\2\2N\25\3\2\2\2OP\7\4\2\2PQ\7\5\2\2QR\7\n\2\2"+
		"RS\7\b\2\2ST\7\33\2\2TU\7\32\2\2UV\7\b\2\2VW\t\2\2\2WX\7\6\2\2XY\t\2\2"+
		"\2YZ\7\6\2\2Z[\7\24\2\2[\\\7\r\2\2\\]\7\b\2\2]^\7\16\2\2^_\7\17\2\2_`"+
		"\7\b\2\2`b\7\27\2\2ac\5\22\n\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\7\30\2"+
		"\2ef\7\7\2\2f\27\3\2\2\2\b!#%AJb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}