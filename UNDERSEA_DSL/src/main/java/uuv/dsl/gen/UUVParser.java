// Generated from UUV.g4 by ANTLR 4.7.2

package uuv.dsl.gen;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UUVParser extends Parser {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, ASSIGN = 7, SERVER_HOST = 8,
            SERVER_PORT = 9, SIMULATION_TIME = 10, TIME_WINDOW = 11, SIMULATION_SPEED = 12,
            SPEED = 13, SENSOR = 14, NAME = 15, RATE = 16, CHANGE = 17, RELIABILITY = 18, SLCOMMENT = 19,
            ID = 20, INT = 21, DOUBLE = 22, IP = 23, OCTET = 24, STRING = 25, WS = 26;
    public static final int
            RULE_model = 0, RULE_simulation = 1, RULE_invocation = 2, RULE_host = 3,
            RULE_port = 4, RULE_speed = 5, RULE_sensor = 6, RULE_uuv = 7, RULE_change = 8;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\34n\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2" +
                    "\3\2\3\2\3\2\3\2\6\2\34\n\2\r\2\16\2\35\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3" +
                    "\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b" +
                    "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\6\b@\n\b\r\b\16\bA\6\bD\n\b\r\b\16\bE\3\b" +
                    "\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3" +
                    "\t\3\t\6\t\\\n\t\r\t\16\t]\6\t`\n\t\r\t\16\ta\3\t\3\t\3\n\3\n\3\n\3\n" +
                    "\3\n\3\n\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\4\3\2\27\30\4\2\3\3" +
                    "\31\31\2u\2\33\3\2\2\2\4\37\3\2\2\2\6#\3\2\2\2\b\'\3\2\2\2\n+\3\2\2\2" +
                    "\f/\3\2\2\2\16\63\3\2\2\2\20I\3\2\2\2\22e\3\2\2\2\24\34\5\4\3\2\25\34" +
                    "\5\6\4\2\26\34\5\b\5\2\27\34\5\n\6\2\30\34\5\20\t\2\31\34\5\f\7\2\32\34" +
                    "\5\16\b\2\33\24\3\2\2\2\33\25\3\2\2\2\33\26\3\2\2\2\33\27\3\2\2\2\33\30" +
                    "\3\2\2\2\33\31\3\2\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3\2\2\2\35\36" +
                    "\3\2\2\2\36\3\3\2\2\2\37 \7\f\2\2 !\7\t\2\2!\"\7\27\2\2\"\5\3\2\2\2#$" +
                    "\7\r\2\2$%\7\t\2\2%&\t\2\2\2&\7\3\2\2\2\'(\7\n\2\2()\7\t\2\2)*\t\3\2\2" +
                    "*\t\3\2\2\2+,\7\13\2\2,-\7\t\2\2-.\7\27\2\2.\13\3\2\2\2/\60\7\16\2\2\60" +
                    "\61\7\t\2\2\61\62\7\27\2\2\62\r\3\2\2\2\63\64\7\4\2\2\64C\7\5\2\2\65\66" +
                    "\7\21\2\2\66\67\7\t\2\2\67D\7\26\2\289\7\22\2\29:\7\t\2\2:D\t\2\2\2;<" +
                    "\7\24\2\2<=\7\t\2\2=D\t\2\2\2>@\5\22\n\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2" +
                    "AB\3\2\2\2BD\3\2\2\2C\65\3\2\2\2C8\3\2\2\2C;\3\2\2\2C?\3\2\2\2DE\3\2\2" +
                    "\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\7\6\2\2H\17\3\2\2\2IJ\7\7\2\2J_\7\5" +
                    "\2\2KL\7\21\2\2LM\7\t\2\2M`\7\26\2\2NO\7\17\2\2OP\7\t\2\2PQ\t\2\2\2QR" +
                    "\7\b\2\2RS\t\2\2\2ST\7\b\2\2T`\7\27\2\2UV\7\13\2\2VW\7\t\2\2W`\7\27\2" +
                    "\2XY\7\20\2\2Y[\7\t\2\2Z\\\5\16\b\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2\2]^\3" +
                    "\2\2\2^`\3\2\2\2_K\3\2\2\2_N\3\2\2\2_U\3\2\2\2_X\3\2\2\2`a\3\2\2\2a_\3" +
                    "\2\2\2ab\3\2\2\2bc\3\2\2\2cd\7\6\2\2d\21\3\2\2\2ef\7\23\2\2fg\7\t\2\2" +
                    "gh\t\2\2\2hi\7\b\2\2ij\t\2\2\2jk\7\b\2\2kl\t\2\2\2l\23\3\2\2\2\n\33\35" +
                    "ACE]_a";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    Set<String> types = new HashSet<String>() {{
        add("T");
    }};

    public UUVParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "model", "simulation", "invocation", "host", "port", "speed", "sensor",
                "uuv", "change"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'localhost'", "'SENSOR'", "'{'", "'}'", "'UUV'", "':'", "'='",
                "'host'", "'port'", "'simulation time'", "'time window'", "'simulation speed'",
                "'speed'", "'sensor'", "'name'", "'rate'", "'change'", "'reliability'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, null, null, "ASSIGN", "SERVER_HOST", "SERVER_PORT",
                "SIMULATION_TIME", "TIME_WINDOW", "SIMULATION_SPEED", "SPEED", "SENSOR",
                "NAME", "RATE", "CHANGE", "RELIABILITY", "SLCOMMENT", "ID", "INT", "DOUBLE",
                "IP", "OCTET", "STRING", "WS"
        };
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
    public String getGrammarFileName() {
        return "UUV.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    boolean istype() {
        return types.contains(getCurrentToken().getText());
    }

    public final ModelContext model() throws RecognitionException {
        ModelContext _localctx = new ModelContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_model);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(25);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        setState(25);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case SIMULATION_TIME: {
                                setState(18);
                                simulation();
                            }
                            break;
                            case TIME_WINDOW: {
                                setState(19);
                                invocation();
                            }
                            break;
                            case SERVER_HOST: {
                                setState(20);
                                host();
                            }
                            break;
                            case SERVER_PORT: {
                                setState(21);
                                port();
                            }
                            break;
                            case T__4: {
                                setState(22);
                                uuv();
                            }
                            break;
                            case SIMULATION_SPEED: {
                                setState(23);
                                speed();
                            }
                            break;
                            case T__1: {
                                setState(24);
                                sensor();
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(27);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << SERVER_HOST) | (1L << SERVER_PORT) | (1L << SIMULATION_TIME) | (1L << TIME_WINDOW) | (1L << SIMULATION_SPEED))) != 0));
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SimulationContext simulation() throws RecognitionException {
        SimulationContext _localctx = new SimulationContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_simulation);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(29);
                match(SIMULATION_TIME);
                setState(30);
                match(ASSIGN);
                setState(31);
                ((SimulationContext) _localctx).value = match(INT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final InvocationContext invocation() throws RecognitionException {
        InvocationContext _localctx = new InvocationContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_invocation);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(33);
                match(TIME_WINDOW);
                setState(34);
                match(ASSIGN);
                setState(35);
                ((InvocationContext) _localctx).value = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == INT || _la == DOUBLE)) {
                    ((InvocationContext) _localctx).value = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final HostContext host() throws RecognitionException {
        HostContext _localctx = new HostContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_host);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(37);
                match(SERVER_HOST);
                setState(38);
                match(ASSIGN);
                setState(39);
                ((HostContext) _localctx).value = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == T__0 || _la == IP)) {
                    ((HostContext) _localctx).value = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final PortContext port() throws RecognitionException {
        PortContext _localctx = new PortContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_port);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(41);
                match(SERVER_PORT);
                setState(42);
                match(ASSIGN);
                setState(43);
                ((PortContext) _localctx).value = match(INT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SpeedContext speed() throws RecognitionException {
        SpeedContext _localctx = new SpeedContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_speed);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(45);
                match(SIMULATION_SPEED);
                setState(46);
                match(ASSIGN);
                setState(47);
                ((SpeedContext) _localctx).value = match(INT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SensorContext sensor() throws RecognitionException {
        SensorContext _localctx = new SensorContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_sensor);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(49);
                match(T__1);
                setState(50);
                match(T__2);
                setState(65);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        setState(65);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case NAME: {
                                setState(51);
                                match(NAME);
                                setState(52);
                                match(ASSIGN);
                                setState(53);
                                ((SensorContext) _localctx).name = match(ID);
                            }
                            break;
                            case RATE: {
                                setState(54);
                                match(RATE);
                                setState(55);
                                match(ASSIGN);
                                setState(56);
                                ((SensorContext) _localctx).rate = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == INT || _la == DOUBLE)) {
                                    ((SensorContext) _localctx).rate = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                            break;
                            case RELIABILITY: {
                                setState(57);
                                match(RELIABILITY);
                                setState(58);
                                match(ASSIGN);
                                setState(59);
                                ((SensorContext) _localctx).reliability = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == INT || _la == DOUBLE)) {
                                    ((SensorContext) _localctx).reliability = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                            break;
                            case CHANGE: {
                                setState(61);
                                _errHandler.sync(this);
                                _alt = 1;
                                do {
                                    switch (_alt) {
                                        case 1: {
                                            {
                                                setState(60);
                                                change();
                                            }
                                        }
                                        break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                    setState(63);
                                    _errHandler.sync(this);
                                    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                                } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(67);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NAME) | (1L << RATE) | (1L << CHANGE) | (1L << RELIABILITY))) != 0));
                setState(69);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final UuvContext uuv() throws RecognitionException {
        UuvContext _localctx = new UuvContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_uuv);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(71);
                match(T__4);
                setState(72);
                match(T__2);
                setState(93);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        setState(93);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case NAME: {
                                setState(73);
                                match(NAME);
                                setState(74);
                                match(ASSIGN);
                                setState(75);
                                ((UuvContext) _localctx).name = match(ID);
                            }
                            break;
                            case SPEED: {
                                setState(76);
                                match(SPEED);
                                setState(77);
                                match(ASSIGN);
                                setState(78);
                                ((UuvContext) _localctx).min = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == INT || _la == DOUBLE)) {
                                    ((UuvContext) _localctx).min = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(79);
                                match(T__5);
                                setState(80);
                                ((UuvContext) _localctx).max = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == INT || _la == DOUBLE)) {
                                    ((UuvContext) _localctx).max = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(81);
                                match(T__5);
                                setState(82);
                                ((UuvContext) _localctx).steps = match(INT);
                            }
                            break;
                            case SERVER_PORT: {
                                setState(83);
                                match(SERVER_PORT);
                                setState(84);
                                match(ASSIGN);
                                setState(85);
                                ((UuvContext) _localctx).value = match(INT);
                            }
                            break;
                            case SENSOR: {
                                setState(86);
                                match(SENSOR);
                                setState(87);
                                match(ASSIGN);
                                setState(89);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                do {
                                    {
                                        {
                                            setState(88);
                                            ((UuvContext) _localctx).sensors = sensor();
                                        }
                                    }
                                    setState(91);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                } while (_la == T__1);
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(95);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SERVER_PORT) | (1L << SPEED) | (1L << SENSOR) | (1L << NAME))) != 0));
                setState(97);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ChangeContext change() throws RecognitionException {
        ChangeContext _localctx = new ChangeContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_change);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(99);
                match(CHANGE);
                setState(100);
                match(ASSIGN);
                setState(101);
                ((ChangeContext) _localctx).begin = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == INT || _la == DOUBLE)) {
                    ((ChangeContext) _localctx).begin = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(102);
                match(T__5);
                setState(103);
                ((ChangeContext) _localctx).end = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == INT || _la == DOUBLE)) {
                    ((ChangeContext) _localctx).end = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(104);
                match(T__5);
                setState(105);
                ((ChangeContext) _localctx).value = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == INT || _la == DOUBLE)) {
                    ((ChangeContext) _localctx).value = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ModelContext extends ParserRuleContext {
        public ModelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<SimulationContext> simulation() {
            return getRuleContexts(SimulationContext.class);
        }

        public SimulationContext simulation(int i) {
            return getRuleContext(SimulationContext.class, i);
        }

        public List<InvocationContext> invocation() {
            return getRuleContexts(InvocationContext.class);
        }

        public InvocationContext invocation(int i) {
            return getRuleContext(InvocationContext.class, i);
        }

        public List<HostContext> host() {
            return getRuleContexts(HostContext.class);
        }

        public HostContext host(int i) {
            return getRuleContext(HostContext.class, i);
        }

        public List<PortContext> port() {
            return getRuleContexts(PortContext.class);
        }

        public PortContext port(int i) {
            return getRuleContext(PortContext.class, i);
        }

        public List<UuvContext> uuv() {
            return getRuleContexts(UuvContext.class);
        }

        public UuvContext uuv(int i) {
            return getRuleContext(UuvContext.class, i);
        }

        public List<SpeedContext> speed() {
            return getRuleContexts(SpeedContext.class);
        }

        public SpeedContext speed(int i) {
            return getRuleContext(SpeedContext.class, i);
        }

        public List<SensorContext> sensor() {
            return getRuleContexts(SensorContext.class);
        }

        public SensorContext sensor(int i) {
            return getRuleContext(SensorContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_model;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterModel(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitModel(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitModel(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SimulationContext extends ParserRuleContext {
        public Token value;

        public SimulationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SIMULATION_TIME() {
            return getToken(UUVParser.SIMULATION_TIME, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public TerminalNode INT() {
            return getToken(UUVParser.INT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_simulation;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterSimulation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitSimulation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitSimulation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class InvocationContext extends ParserRuleContext {
        public Token value;

        public InvocationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode TIME_WINDOW() {
            return getToken(UUVParser.TIME_WINDOW, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public TerminalNode INT() {
            return getToken(UUVParser.INT, 0);
        }

        public TerminalNode DOUBLE() {
            return getToken(UUVParser.DOUBLE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_invocation;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterInvocation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitInvocation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitInvocation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class HostContext extends ParserRuleContext {
        public Token value;

        public HostContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SERVER_HOST() {
            return getToken(UUVParser.SERVER_HOST, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public TerminalNode IP() {
            return getToken(UUVParser.IP, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_host;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterHost(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitHost(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitHost(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PortContext extends ParserRuleContext {
        public Token value;

        public PortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SERVER_PORT() {
            return getToken(UUVParser.SERVER_PORT, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public TerminalNode INT() {
            return getToken(UUVParser.INT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_port;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterPort(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitPort(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitPort(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SpeedContext extends ParserRuleContext {
        public Token value;

        public SpeedContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SIMULATION_SPEED() {
            return getToken(UUVParser.SIMULATION_SPEED, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public TerminalNode INT() {
            return getToken(UUVParser.INT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_speed;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterSpeed(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitSpeed(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitSpeed(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SensorContext extends ParserRuleContext {
        public Token name;
        public Token rate;
        public Token reliability;

        public SensorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> NAME() {
            return getTokens(UUVParser.NAME);
        }

        public TerminalNode NAME(int i) {
            return getToken(UUVParser.NAME, i);
        }

        public List<TerminalNode> ASSIGN() {
            return getTokens(UUVParser.ASSIGN);
        }

        public TerminalNode ASSIGN(int i) {
            return getToken(UUVParser.ASSIGN, i);
        }

        public List<TerminalNode> RATE() {
            return getTokens(UUVParser.RATE);
        }

        public TerminalNode RATE(int i) {
            return getToken(UUVParser.RATE, i);
        }

        public List<TerminalNode> RELIABILITY() {
            return getTokens(UUVParser.RELIABILITY);
        }

        public TerminalNode RELIABILITY(int i) {
            return getToken(UUVParser.RELIABILITY, i);
        }

        public List<TerminalNode> ID() {
            return getTokens(UUVParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(UUVParser.ID, i);
        }

        public List<TerminalNode> INT() {
            return getTokens(UUVParser.INT);
        }

        public TerminalNode INT(int i) {
            return getToken(UUVParser.INT, i);
        }

        public List<TerminalNode> DOUBLE() {
            return getTokens(UUVParser.DOUBLE);
        }

        public TerminalNode DOUBLE(int i) {
            return getToken(UUVParser.DOUBLE, i);
        }

        public List<ChangeContext> change() {
            return getRuleContexts(ChangeContext.class);
        }

        public ChangeContext change(int i) {
            return getRuleContext(ChangeContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_sensor;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterSensor(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitSensor(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitSensor(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UuvContext extends ParserRuleContext {
        public Token name;
        public Token min;
        public Token max;
        public Token steps;
        public Token value;
        public SensorContext sensors;

        public UuvContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> NAME() {
            return getTokens(UUVParser.NAME);
        }

        public TerminalNode NAME(int i) {
            return getToken(UUVParser.NAME, i);
        }

        public List<TerminalNode> ASSIGN() {
            return getTokens(UUVParser.ASSIGN);
        }

        public TerminalNode ASSIGN(int i) {
            return getToken(UUVParser.ASSIGN, i);
        }

        public List<TerminalNode> SPEED() {
            return getTokens(UUVParser.SPEED);
        }

        public TerminalNode SPEED(int i) {
            return getToken(UUVParser.SPEED, i);
        }

        public List<TerminalNode> SERVER_PORT() {
            return getTokens(UUVParser.SERVER_PORT);
        }

        public TerminalNode SERVER_PORT(int i) {
            return getToken(UUVParser.SERVER_PORT, i);
        }

        public List<TerminalNode> SENSOR() {
            return getTokens(UUVParser.SENSOR);
        }

        public TerminalNode SENSOR(int i) {
            return getToken(UUVParser.SENSOR, i);
        }

        public List<TerminalNode> ID() {
            return getTokens(UUVParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(UUVParser.ID, i);
        }

        public List<TerminalNode> INT() {
            return getTokens(UUVParser.INT);
        }

        public TerminalNode INT(int i) {
            return getToken(UUVParser.INT, i);
        }

        public List<TerminalNode> DOUBLE() {
            return getTokens(UUVParser.DOUBLE);
        }

        public TerminalNode DOUBLE(int i) {
            return getToken(UUVParser.DOUBLE, i);
        }

        public List<SensorContext> sensor() {
            return getRuleContexts(SensorContext.class);
        }

        public SensorContext sensor(int i) {
            return getRuleContext(SensorContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_uuv;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterUuv(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitUuv(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitUuv(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ChangeContext extends ParserRuleContext {
        public Token begin;
        public Token end;
        public Token value;

        public ChangeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode CHANGE() {
            return getToken(UUVParser.CHANGE, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(UUVParser.ASSIGN, 0);
        }

        public List<TerminalNode> INT() {
            return getTokens(UUVParser.INT);
        }

        public TerminalNode INT(int i) {
            return getToken(UUVParser.INT, i);
        }

        public List<TerminalNode> DOUBLE() {
            return getTokens(UUVParser.DOUBLE);
        }

        public TerminalNode DOUBLE(int i) {
            return getToken(UUVParser.DOUBLE, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_change;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).enterChange(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UUVListener) ((UUVListener) listener).exitChange(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof UUVVisitor) return ((UUVVisitor<? extends T>) visitor).visitChange(this);
            else return visitor.visitChildren(this);
        }
    }
}