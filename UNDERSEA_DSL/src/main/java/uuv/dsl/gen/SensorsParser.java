// Generated from Sensors.g4 by ANTLR 4.7.2

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
public class SensorsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ASSIGN=5, SERVER_HOST=6, SERVER_PORT=7, 
		SIMULATION_TIME=8, TIME_WINDOW=9, SIMULATION_SPEED=10, SPEED=11, SENSOR=12, 
		NAME=13, RATE=14, CHANGE=15, RELIABILITY=16, SLCOMMENT=17, ID=18, INT=19, 
		DOUBLE=20, IP=21, OCTET=22, STRING=23, WS=24;
	public static final int
		RULE_model = 0, RULE_sensor = 1, RULE_change = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "sensor", "change"
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

	@Override
	public String getGrammarFileName() { return "Sensors.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SensorsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModelContext extends ParserRuleContext {
		public List<SensorContext> sensor() {
			return getRuleContexts(SensorContext.class);
		}
		public SensorContext sensor(int i) {
			return getRuleContext(SensorContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).exitModel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SensorsVisitor ) return ((SensorsVisitor<? extends T>)visitor).visitModel(this);
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
			setState(7); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(6);
				sensor();
				}
				}
				setState(9); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
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

	public static class SensorContext extends ParserRuleContext {
		public Token name;
		public Token rate;
		public Token reliability;
		public List<TerminalNode> NAME() { return getTokens(SensorsParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(SensorsParser.NAME, i);
		}
		public List<TerminalNode> ASSIGN() { return getTokens(SensorsParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(SensorsParser.ASSIGN, i);
		}
		public List<TerminalNode> RATE() { return getTokens(SensorsParser.RATE); }
		public TerminalNode RATE(int i) {
			return getToken(SensorsParser.RATE, i);
		}
		public List<TerminalNode> RELIABILITY() { return getTokens(SensorsParser.RELIABILITY); }
		public TerminalNode RELIABILITY(int i) {
			return getToken(SensorsParser.RELIABILITY, i);
		}
		public List<TerminalNode> ID() { return getTokens(SensorsParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(SensorsParser.ID, i);
		}
		public List<TerminalNode> INT() { return getTokens(SensorsParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(SensorsParser.INT, i);
		}
		public List<TerminalNode> DOUBLE() { return getTokens(SensorsParser.DOUBLE); }
		public TerminalNode DOUBLE(int i) {
			return getToken(SensorsParser.DOUBLE, i);
		}
		public List<ChangeContext> change() {
			return getRuleContexts(ChangeContext.class);
		}
		public ChangeContext change(int i) {
			return getRuleContext(ChangeContext.class,i);
		}
		public SensorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sensor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).enterSensor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).exitSensor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SensorsVisitor ) return ((SensorsVisitor<? extends T>)visitor).visitSensor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SensorContext sensor() throws RecognitionException {
		SensorContext _localctx = new SensorContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sensor);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(11);
			match(T__0);
			setState(12);
			match(T__1);
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(27);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(13);
					match(NAME);
					setState(14);
					match(ASSIGN);
					setState(15);
					((SensorContext)_localctx).name = match(ID);
					}
					break;
				case RATE:
					{
					setState(16);
					match(RATE);
					setState(17);
					match(ASSIGN);
					setState(18);
					((SensorContext)_localctx).rate = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==DOUBLE) ) {
						((SensorContext)_localctx).rate = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case RELIABILITY:
					{
					setState(19);
					match(RELIABILITY);
					setState(20);
					match(ASSIGN);
					setState(21);
					((SensorContext)_localctx).reliability = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==DOUBLE) ) {
						((SensorContext)_localctx).reliability = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case CHANGE:
					{
					setState(23); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(22);
							change();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(25); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NAME) | (1L << RATE) | (1L << CHANGE) | (1L << RELIABILITY))) != 0) );
			setState(31);
			match(T__2);
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

	public static class ChangeContext extends ParserRuleContext {
		public Token begin;
		public Token end;
		public Token value;
		public TerminalNode CHANGE() { return getToken(SensorsParser.CHANGE, 0); }
		public TerminalNode ASSIGN() { return getToken(SensorsParser.ASSIGN, 0); }
		public List<TerminalNode> INT() { return getTokens(SensorsParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(SensorsParser.INT, i);
		}
		public List<TerminalNode> DOUBLE() { return getTokens(SensorsParser.DOUBLE); }
		public TerminalNode DOUBLE(int i) {
			return getToken(SensorsParser.DOUBLE, i);
		}
		public ChangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_change; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).enterChange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SensorsListener ) ((SensorsListener)listener).exitChange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SensorsVisitor ) return ((SensorsVisitor<? extends T>)visitor).visitChange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChangeContext change() throws RecognitionException {
		ChangeContext _localctx = new ChangeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_change);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(CHANGE);
			setState(34);
			match(ASSIGN);
			setState(35);
			((ChangeContext)_localctx).begin = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==DOUBLE) ) {
				((ChangeContext)_localctx).begin = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(36);
			match(T__3);
			setState(37);
			((ChangeContext)_localctx).end = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==DOUBLE) ) {
				((ChangeContext)_localctx).end = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(38);
			match(T__3);
			setState(39);
			((ChangeContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==DOUBLE) ) {
				((ChangeContext)_localctx).value = (Token)_errHandler.recoverInline(this);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32,\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\3\2\6\2\n\n\2\r\2\16\2\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\6\3\32\n\3\r\3\16\3\33\6\3\36\n\3\r\3\16\3\37\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\2\2\5\2\4\6\2\3\3\2\25\26\2.\2\t\3"+
		"\2\2\2\4\r\3\2\2\2\6#\3\2\2\2\b\n\5\4\3\2\t\b\3\2\2\2\n\13\3\2\2\2\13"+
		"\t\3\2\2\2\13\f\3\2\2\2\f\3\3\2\2\2\r\16\7\3\2\2\16\35\7\4\2\2\17\20\7"+
		"\17\2\2\20\21\7\7\2\2\21\36\7\24\2\2\22\23\7\20\2\2\23\24\7\7\2\2\24\36"+
		"\t\2\2\2\25\26\7\22\2\2\26\27\7\7\2\2\27\36\t\2\2\2\30\32\5\6\4\2\31\30"+
		"\3\2\2\2\32\33\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\36\3\2\2\2\35\17"+
		"\3\2\2\2\35\22\3\2\2\2\35\25\3\2\2\2\35\31\3\2\2\2\36\37\3\2\2\2\37\35"+
		"\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\"\7\5\2\2\"\5\3\2\2\2#$\7\21\2\2$%\7\7"+
		"\2\2%&\t\2\2\2&\'\7\6\2\2\'(\t\2\2\2()\7\6\2\2)*\t\2\2\2*\7\3\2\2\2\6"+
		"\13\33\35\37";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}