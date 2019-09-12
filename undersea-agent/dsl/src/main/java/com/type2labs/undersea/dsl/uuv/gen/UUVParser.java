/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Generated from UUV.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;
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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ASSIGN=6, ACTIVE=7, BOOL=8, MISSION_NAME=9, 
		NAME=10, TIME_WINDOW=11, SERVER_HOST=12, SENSORS=13, DOUBLE=14, PORT_START=15, 
		SENSOR_PORT=16, SIMULATION_TIME=17, SIMULATION_SPEED=18, INT=19, IP=20, 
		OCTET=21, BEGL=22, ENDL=23, SEP=24, SPEED=25, ID=26, WS=27, ErrorCharacter=28;
	public static final int
		RULE_model = 0, RULE_sensorPort = 1, RULE_missionName = 2, RULE_portStart = 3, 
		RULE_simulation = 4, RULE_speed = 5, RULE_invocation = 6, RULE_host = 7, 
		RULE_list = 8, RULE_elems = 9, RULE_elem = 10, RULE_uuv = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "sensorPort", "missionName", "portStart", "simulation", "speed", 
			"invocation", "host", "list", "elems", "elem", "uuv"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'active'", 
			null, "'mission name'", "'name'", "'time window'", "'host'", "'sensors'", 
			null, "'server port start'", "'sensor port'", "'simulation time'", "'simulation speed'", 
			null, null, null, "'['", "']'", "','", "'speed'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ASSIGN", "ACTIVE", "BOOL", "MISSION_NAME", 
			"NAME", "TIME_WINDOW", "SERVER_HOST", "SENSORS", "DOUBLE", "PORT_START", 
			"SENSOR_PORT", "SIMULATION_TIME", "SIMULATION_SPEED", "INT", "IP", "OCTET", 
			"BEGL", "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
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
		public List<SensorPortContext> sensorPort() {
			return getRuleContexts(SensorPortContext.class);
		}
		public SensorPortContext sensorPort(int i) {
			return getRuleContext(SensorPortContext.class,i);
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
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(36);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MISSION_NAME:
					{
					setState(24);
					missionName();
					}
					break;
				case PORT_START:
					{
					setState(25);
					portStart();
					}
					break;
				case SENSOR_PORT:
					{
					setState(26);
					sensorPort();
					}
					break;
				case SIMULATION_TIME:
					{
					setState(27);
					simulation();
					}
					break;
				case TIME_WINDOW:
					{
					setState(28);
					invocation();
					}
					break;
				case SERVER_HOST:
					{
					setState(29);
					host();
					}
					break;
				case SIMULATION_SPEED:
					{
					setState(30);
					speed();
					}
					break;
				case T__1:
					{
					setState(32); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(31);
							uuv();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(34); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << MISSION_NAME) | (1L << TIME_WINDOW) | (1L << SERVER_HOST) | (1L << PORT_START) | (1L << SENSOR_PORT) | (1L << SIMULATION_TIME) | (1L << SIMULATION_SPEED))) != 0) );
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

	public static class SensorPortContext extends ParserRuleContext {
		public Token value;
		public TerminalNode SENSOR_PORT() { return getToken(UUVParser.SENSOR_PORT, 0); }
		public TerminalNode ASSIGN() { return getToken(UUVParser.ASSIGN, 0); }
		public TerminalNode INT() { return getToken(UUVParser.INT, 0); }
		public SensorPortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sensorPort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).enterSensorPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UUVListener ) ((UUVListener)listener).exitSensorPort(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UUVVisitor ) return ((UUVVisitor<? extends T>)visitor).visitSensorPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SensorPortContext sensorPort() throws RecognitionException {
		SensorPortContext _localctx = new SensorPortContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sensorPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(SENSOR_PORT);
			setState(41);
			match(ASSIGN);
			setState(42);
			((SensorPortContext)_localctx).value = match(INT);
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
		enterRule(_localctx, 4, RULE_missionName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(MISSION_NAME);
			setState(45);
			match(ASSIGN);
			setState(46);
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
		enterRule(_localctx, 6, RULE_portStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(PORT_START);
			setState(49);
			match(ASSIGN);
			setState(50);
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
		enterRule(_localctx, 8, RULE_simulation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(SIMULATION_TIME);
			setState(53);
			match(ASSIGN);
			setState(54);
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
		enterRule(_localctx, 10, RULE_speed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(SIMULATION_SPEED);
			setState(57);
			match(ASSIGN);
			setState(58);
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
		enterRule(_localctx, 12, RULE_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(TIME_WINDOW);
			setState(61);
			match(ASSIGN);
			setState(62);
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
		enterRule(_localctx, 14, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(SERVER_HOST);
			setState(65);
			match(ASSIGN);
			setState(66);
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
		enterRule(_localctx, 16, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(BEGL);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(69);
				elems();
				}
			}

			setState(72);
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
		enterRule(_localctx, 18, RULE_elems);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			elem();
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEP) {
				{
				{
				setState(75);
				match(SEP);
				setState(76);
				elem();
				}
				}
				setState(81);
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
		enterRule(_localctx, 20, RULE_elem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
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
		public Token active;
		public Token sensors;
		public TerminalNode NAME() { return getToken(UUVParser.NAME, 0); }
		public List<TerminalNode> ASSIGN() { return getTokens(UUVParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(UUVParser.ASSIGN, i);
		}
		public TerminalNode SPEED() { return getToken(UUVParser.SPEED, 0); }
		public TerminalNode ACTIVE() { return getToken(UUVParser.ACTIVE, 0); }
		public TerminalNode SENSORS() { return getToken(UUVParser.SENSORS, 0); }
		public TerminalNode ENDL() { return getToken(UUVParser.ENDL, 0); }
		public TerminalNode ID() { return getToken(UUVParser.ID, 0); }
		public List<TerminalNode> INT() { return getTokens(UUVParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(UUVParser.INT, i);
		}
		public TerminalNode BOOL() { return getToken(UUVParser.BOOL, 0); }
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
		enterRule(_localctx, 22, RULE_uuv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__1);
			setState(85);
			match(T__2);
			setState(86);
			match(NAME);
			setState(87);
			match(ASSIGN);
			setState(88);
			((UuvContext)_localctx).name = match(ID);
			setState(89);
			match(SPEED);
			setState(90);
			match(ASSIGN);
			setState(91);
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
			setState(92);
			match(T__3);
			setState(93);
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
			setState(94);
			match(T__3);
			setState(95);
			((UuvContext)_localctx).steps = match(INT);
			setState(96);
			match(ACTIVE);
			setState(97);
			match(ASSIGN);
			setState(98);
			((UuvContext)_localctx).active = match(BOOL);
			setState(99);
			match(SENSORS);
			setState(100);
			match(ASSIGN);
			setState(101);
			((UuvContext)_localctx).sensors = match(BEGL);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(102);
				elems();
				}
			}

			setState(105);
			match(ENDL);
			setState(106);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36o\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2#\n\2\r\2\16\2$\6\2"+
		"\'\n\2\r\2\16\2(\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\5\n"+
		"I\n\n\3\n\3\n\3\13\3\13\3\13\7\13P\n\13\f\13\16\13S\13\13\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\5\rj\n\r\3\r\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\4"+
		"\2\20\20\25\25\4\2\3\3\26\26\2n\2&\3\2\2\2\4*\3\2\2\2\6.\3\2\2\2\b\62"+
		"\3\2\2\2\n\66\3\2\2\2\f:\3\2\2\2\16>\3\2\2\2\20B\3\2\2\2\22F\3\2\2\2\24"+
		"L\3\2\2\2\26T\3\2\2\2\30V\3\2\2\2\32\'\5\6\4\2\33\'\5\b\5\2\34\'\5\4\3"+
		"\2\35\'\5\n\6\2\36\'\5\16\b\2\37\'\5\20\t\2 \'\5\f\7\2!#\5\30\r\2\"!\3"+
		"\2\2\2#$\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%\'\3\2\2\2&\32\3\2\2\2&\33\3\2\2"+
		"\2&\34\3\2\2\2&\35\3\2\2\2&\36\3\2\2\2&\37\3\2\2\2& \3\2\2\2&\"\3\2\2"+
		"\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)\3\3\2\2\2*+\7\22\2\2+,\7\b\2\2,-\7"+
		"\25\2\2-\5\3\2\2\2./\7\13\2\2/\60\7\b\2\2\60\61\7\34\2\2\61\7\3\2\2\2"+
		"\62\63\7\21\2\2\63\64\7\b\2\2\64\65\7\25\2\2\65\t\3\2\2\2\66\67\7\23\2"+
		"\2\678\7\b\2\289\7\25\2\29\13\3\2\2\2:;\7\24\2\2;<\7\b\2\2<=\7\25\2\2"+
		"=\r\3\2\2\2>?\7\r\2\2?@\7\b\2\2@A\t\2\2\2A\17\3\2\2\2BC\7\16\2\2CD\7\b"+
		"\2\2DE\t\3\2\2E\21\3\2\2\2FH\7\30\2\2GI\5\24\13\2HG\3\2\2\2HI\3\2\2\2"+
		"IJ\3\2\2\2JK\7\31\2\2K\23\3\2\2\2LQ\5\26\f\2MN\7\32\2\2NP\5\26\f\2OM\3"+
		"\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\25\3\2\2\2SQ\3\2\2\2TU\7\34\2\2"+
		"U\27\3\2\2\2VW\7\4\2\2WX\7\5\2\2XY\7\f\2\2YZ\7\b\2\2Z[\7\34\2\2[\\\7\33"+
		"\2\2\\]\7\b\2\2]^\t\2\2\2^_\7\6\2\2_`\t\2\2\2`a\7\6\2\2ab\7\25\2\2bc\7"+
		"\t\2\2cd\7\b\2\2de\7\n\2\2ef\7\17\2\2fg\7\b\2\2gi\7\30\2\2hj\5\24\13\2"+
		"ih\3\2\2\2ij\3\2\2\2jk\3\2\2\2kl\7\31\2\2lm\7\7\2\2m\31\3\2\2\2\b$&(H"+
		"Qi";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}