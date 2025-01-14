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

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UUVLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, ASSIGN = 6, ACTIVE = 7, BOOL = 8, MISSION_NAME = 9,
            NAME = 10, TIME_WINDOW = 11, SERVER_HOST = 12, SENSORS = 13, DOUBLE = 14, PORT_START = 15,
            SENSOR_PORT = 16, SIMULATION_TIME = 17, SIMULATION_SPEED = 18, INT = 19, IP = 20,
            OCTET = 21, BEGL = 22, ENDL = 23, SEP = 24, SPEED = 25, ID = 26, WS = 27, ErrorCharacter = 28;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u0109\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\3" +
                    "\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7" +
                    "\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5" +
                    "\td\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13" +
                    "\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3" +
                    "\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\5\17\u0092" +
                    "\n\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20" +
                    "\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21" +
                    "\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22" +
                    "\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23" +
                    "\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\6\24\u00d7" +
                    "\n\24\r\24\16\24\u00d8\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3" +
                    "\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00ed\n\27\3\30\3\30" +
                    "\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\6\34\u00fd" +
                    "\n\34\r\34\16\34\u00fe\3\35\6\35\u0102\n\35\r\35\16\35\u0103\3\35\3\35" +
                    "\3\36\3\36\2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31" +
                    "\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\2-\27/\30\61\31\63\32\65\33" +
                    "\67\349\35;\36\3\2\6\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17" +
                    "\"\"\2\u010e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2" +
                    "\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2" +
                    "\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2" +
                    "\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2-\3\2\2\2\2/\3\2\2" +
                    "\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3" +
                    "\2\2\2\3=\3\2\2\2\5G\3\2\2\2\7K\3\2\2\2\tM\3\2\2\2\13O\3\2\2\2\rQ\3\2" +
                    "\2\2\17S\3\2\2\2\21c\3\2\2\2\23e\3\2\2\2\25r\3\2\2\2\27w\3\2\2\2\31\u0083" +
                    "\3\2\2\2\33\u0088\3\2\2\2\35\u0091\3\2\2\2\37\u0096\3\2\2\2!\u00a8\3\2" +
                    "\2\2#\u00b4\3\2\2\2%\u00c4\3\2\2\2\'\u00d6\3\2\2\2)\u00da\3\2\2\2+\u00e2" +
                    "\3\2\2\2-\u00ec\3\2\2\2/\u00ee\3\2\2\2\61\u00f0\3\2\2\2\63\u00f2\3\2\2" +
                    "\2\65\u00f4\3\2\2\2\67\u00fa\3\2\2\29\u0101\3\2\2\2;\u0107\3\2\2\2=>\7" +
                    "n\2\2>?\7q\2\2?@\7e\2\2@A\7c\2\2AB\7n\2\2BC\7j\2\2CD\7q\2\2DE\7u\2\2E" +
                    "F\7v\2\2F\4\3\2\2\2GH\7W\2\2HI\7W\2\2IJ\7X\2\2J\6\3\2\2\2KL\7}\2\2L\b" +
                    "\3\2\2\2MN\7<\2\2N\n\3\2\2\2OP\7\177\2\2P\f\3\2\2\2QR\7?\2\2R\16\3\2\2" +
                    "\2ST\7c\2\2TU\7e\2\2UV\7v\2\2VW\7k\2\2WX\7x\2\2XY\7g\2\2Y\20\3\2\2\2Z" +
                    "[\7h\2\2[\\\7c\2\2\\]\7n\2\2]^\7u\2\2^d\7g\2\2_`\7v\2\2`a\7t\2\2ab\7w" +
                    "\2\2bd\7g\2\2cZ\3\2\2\2c_\3\2\2\2d\22\3\2\2\2ef\7o\2\2fg\7k\2\2gh\7u\2" +
                    "\2hi\7u\2\2ij\7k\2\2jk\7q\2\2kl\7p\2\2lm\7\"\2\2mn\7p\2\2no\7c\2\2op\7" +
                    "o\2\2pq\7g\2\2q\24\3\2\2\2rs\7p\2\2st\7c\2\2tu\7o\2\2uv\7g\2\2v\26\3\2" +
                    "\2\2wx\7v\2\2xy\7k\2\2yz\7o\2\2z{\7g\2\2{|\7\"\2\2|}\7y\2\2}~\7k\2\2~" +
                    "\177\7p\2\2\177\u0080\7f\2\2\u0080\u0081\7q\2\2\u0081\u0082\7y\2\2\u0082" +
                    "\30\3\2\2\2\u0083\u0084\7j\2\2\u0084\u0085\7q\2\2\u0085\u0086\7u\2\2\u0086" +
                    "\u0087\7v\2\2\u0087\32\3\2\2\2\u0088\u0089\7u\2\2\u0089\u008a\7g\2\2\u008a" +
                    "\u008b\7p\2\2\u008b\u008c\7u\2\2\u008c\u008d\7q\2\2\u008d\u008e\7t\2\2" +
                    "\u008e\u008f\7u\2\2\u008f\34\3\2\2\2\u0090\u0092\5\'\24\2\u0091\u0090" +
                    "\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\7\60\2\2" +
                    "\u0094\u0095\5\'\24\2\u0095\36\3\2\2\2\u0096\u0097\7u\2\2\u0097\u0098" +
                    "\7g\2\2\u0098\u0099\7t\2\2\u0099\u009a\7x\2\2\u009a\u009b\7g\2\2\u009b" +
                    "\u009c\7t\2\2\u009c\u009d\7\"\2\2\u009d\u009e\7r\2\2\u009e\u009f\7q\2" +
                    "\2\u009f\u00a0\7t\2\2\u00a0\u00a1\7v\2\2\u00a1\u00a2\7\"\2\2\u00a2\u00a3" +
                    "\7u\2\2\u00a3\u00a4\7v\2\2\u00a4\u00a5\7c\2\2\u00a5\u00a6\7t\2\2\u00a6" +
                    "\u00a7\7v\2\2\u00a7 \3\2\2\2\u00a8\u00a9\7u\2\2\u00a9\u00aa\7g\2\2\u00aa" +
                    "\u00ab\7p\2\2\u00ab\u00ac\7u\2\2\u00ac\u00ad\7q\2\2\u00ad\u00ae\7t\2\2" +
                    "\u00ae\u00af\7\"\2\2\u00af\u00b0\7r\2\2\u00b0\u00b1\7q\2\2\u00b1\u00b2" +
                    "\7t\2\2\u00b2\u00b3\7v\2\2\u00b3\"\3\2\2\2\u00b4\u00b5\7u\2\2\u00b5\u00b6" +
                    "\7k\2\2\u00b6\u00b7\7o\2\2\u00b7\u00b8\7w\2\2\u00b8\u00b9\7n\2\2\u00b9" +
                    "\u00ba\7c\2\2\u00ba\u00bb\7v\2\2\u00bb\u00bc\7k\2\2\u00bc\u00bd\7q\2\2" +
                    "\u00bd\u00be\7p\2\2\u00be\u00bf\7\"\2\2\u00bf\u00c0\7v\2\2\u00c0\u00c1" +
                    "\7k\2\2\u00c1\u00c2\7o\2\2\u00c2\u00c3\7g\2\2\u00c3$\3\2\2\2\u00c4\u00c5" +
                    "\7u\2\2\u00c5\u00c6\7k\2\2\u00c6\u00c7\7o\2\2\u00c7\u00c8\7w\2\2\u00c8" +
                    "\u00c9\7n\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7v\2\2\u00cb\u00cc\7k\2\2" +
                    "\u00cc\u00cd\7q\2\2\u00cd\u00ce\7p\2\2\u00ce\u00cf\7\"\2\2\u00cf\u00d0" +
                    "\7u\2\2\u00d0\u00d1\7r\2\2\u00d1\u00d2\7g\2\2\u00d2\u00d3\7g\2\2\u00d3" +
                    "\u00d4\7f\2\2\u00d4&\3\2\2\2\u00d5\u00d7\t\2\2\2\u00d6\u00d5\3\2\2\2\u00d7" +
                    "\u00d8\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9(\3\2\2\2" +
                    "\u00da\u00db\5-\27\2\u00db\u00dc\7\60\2\2\u00dc\u00dd\5-\27\2\u00dd\u00de" +
                    "\7\60\2\2\u00de\u00df\5-\27\2\u00df\u00e0\7\60\2\2\u00e0\u00e1\5-\27\2" +
                    "\u00e1*\3\2\2\2\u00e2\u00e3\4\62;\2\u00e3,\3\2\2\2\u00e4\u00e5\5+\26\2" +
                    "\u00e5\u00e6\5+\26\2\u00e6\u00e7\5+\26\2\u00e7\u00ed\3\2\2\2\u00e8\u00e9" +
                    "\5+\26\2\u00e9\u00ea\5+\26\2\u00ea\u00ed\3\2\2\2\u00eb\u00ed\5+\26\2\u00ec" +
                    "\u00e4\3\2\2\2\u00ec\u00e8\3\2\2\2\u00ec\u00eb\3\2\2\2\u00ed.\3\2\2\2" +
                    "\u00ee\u00ef\7]\2\2\u00ef\60\3\2\2\2\u00f0\u00f1\7_\2\2\u00f1\62\3\2\2" +
                    "\2\u00f2\u00f3\7.\2\2\u00f3\64\3\2\2\2\u00f4\u00f5\7u\2\2\u00f5\u00f6" +
                    "\7r\2\2\u00f6\u00f7\7g\2\2\u00f7\u00f8\7g\2\2\u00f8\u00f9\7f\2\2\u00f9" +
                    "\66\3\2\2\2\u00fa\u00fc\t\3\2\2\u00fb\u00fd\t\4\2\2\u00fc\u00fb\3\2\2" +
                    "\2\u00fd\u00fe\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff8" +
                    "\3\2\2\2\u0100\u0102\t\5\2\2\u0101\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103" +
                    "\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b\35" +
                    "\2\2\u0106:\3\2\2\2\u0107\u0108\13\2\2\2\u0108<\3\2\2\2\t\2c\u0091\u00d8" +
                    "\u00ec\u00fe\u0103\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

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

    public UUVLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "T__4", "ASSIGN", "ACTIVE", "BOOL", "MISSION_NAME",
                "NAME", "TIME_WINDOW", "SERVER_HOST", "SENSORS", "DOUBLE", "PORT_START",
                "SENSOR_PORT", "SIMULATION_TIME", "SIMULATION_SPEED", "INT", "IP", "DIGIT",
                "OCTET", "BEGL", "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'localhost'", "'UUV'", "'{'", "':'", "'}'", "'='", "'active'",
                null, "'mission name'", "'name'", "'time window'", "'host'", "'sensors'",
                null, "'server port start'", "'sensor port'", "'simulation time'", "'simulation speed'",
                null, null, null, "'['", "']'", "','", "'speed'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, null, "ASSIGN", "ACTIVE", "BOOL", "MISSION_NAME",
                "NAME", "TIME_WINDOW", "SERVER_HOST", "SENSORS", "DOUBLE", "PORT_START",
                "SENSOR_PORT", "SIMULATION_TIME", "SIMULATION_SPEED", "INT", "IP", "OCTET",
                "BEGL", "ENDL", "SEP", "SPEED", "ID", "WS", "ErrorCharacter"
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}