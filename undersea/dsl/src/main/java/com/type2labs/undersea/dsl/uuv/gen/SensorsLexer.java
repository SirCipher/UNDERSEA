// Generated from Sensors.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SensorsLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, NAME = 5, ASSIGN = 6, CHANGE = 7, RELIABILITY = 8,
            RATE = 9, SENSORS = 10, DOUBLE = 11, INT = 12, ID = 13, WS = 14, ErrorCharacter = 15;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21s\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3" +
                    "\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7" +
                    "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3" +
                    "\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f" +
                    "\5\fY\n\f\3\f\3\f\3\f\3\r\3\r\3\16\6\16a\n\16\r\16\16\16b\3\17\3\17\6" +
                    "\17g\n\17\r\17\16\17h\3\20\6\20l\n\20\r\20\16\20m\3\20\3\20\3\21\3\21" +
                    "\2\2\22\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\16" +
                    "\35\17\37\20!\21\3\2\6\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17" +
                    "\"\"\2u\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2" +
                    "\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3" +
                    "\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5" +
                    "*\3\2\2\2\7,\3\2\2\2\t.\3\2\2\2\13\60\3\2\2\2\r\65\3\2\2\2\17\67\3\2\2" +
                    "\2\21>\3\2\2\2\23J\3\2\2\2\25O\3\2\2\2\27X\3\2\2\2\31]\3\2\2\2\33`\3\2" +
                    "\2\2\35d\3\2\2\2\37k\3\2\2\2!q\3\2\2\2#$\7U\2\2$%\7G\2\2%&\7P\2\2&\'\7" +
                    "U\2\2\'(\7Q\2\2()\7T\2\2)\4\3\2\2\2*+\7}\2\2+\6\3\2\2\2,-\7\177\2\2-\b" +
                    "\3\2\2\2./\7<\2\2/\n\3\2\2\2\60\61\7p\2\2\61\62\7c\2\2\62\63\7o\2\2\63" +
                    "\64\7g\2\2\64\f\3\2\2\2\65\66\7?\2\2\66\16\3\2\2\2\678\7e\2\289\7j\2\2" +
                    "9:\7c\2\2:;\7p\2\2;<\7i\2\2<=\7g\2\2=\20\3\2\2\2>?\7t\2\2?@\7g\2\2@A\7" +
                    "n\2\2AB\7k\2\2BC\7c\2\2CD\7d\2\2DE\7k\2\2EF\7n\2\2FG\7k\2\2GH\7v\2\2H" +
                    "I\7{\2\2I\22\3\2\2\2JK\7t\2\2KL\7c\2\2LM\7v\2\2MN\7g\2\2N\24\3\2\2\2O" +
                    "P\7u\2\2PQ\7g\2\2QR\7p\2\2RS\7u\2\2ST\7q\2\2TU\7t\2\2UV\7u\2\2V\26\3\2" +
                    "\2\2WY\5\33\16\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z[\7\60\2\2[\\\5\33\16\2" +
                    "\\\30\3\2\2\2]^\4\62;\2^\32\3\2\2\2_a\t\2\2\2`_\3\2\2\2ab\3\2\2\2b`\3" +
                    "\2\2\2bc\3\2\2\2c\34\3\2\2\2df\t\3\2\2eg\t\4\2\2fe\3\2\2\2gh\3\2\2\2h" +
                    "f\3\2\2\2hi\3\2\2\2i\36\3\2\2\2jl\t\5\2\2kj\3\2\2\2lm\3\2\2\2mk\3\2\2" +
                    "\2mn\3\2\2\2no\3\2\2\2op\b\20\2\2p \3\2\2\2qr\13\2\2\2r\"\3\2\2\2\7\2" +
                    "Xbhm\3\b\2\2";
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

    public SensorsLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'SENSOR'", "'{'", "'}'", "':'", "'name'", "'='", "'change'", "'reliability'",
                "'rate'", "'sensors'"
        };
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "NAME", "ASSIGN", "CHANGE", "RELIABILITY",
                "RATE", "SENSORS", "DOUBLE", "DIGIT", "INT", "ID", "WS", "ErrorCharacter"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, "NAME", "ASSIGN", "CHANGE", "RELIABILITY",
                "RATE", "SENSORS", "DOUBLE", "INT", "ID", "WS", "ErrorCharacter"
        };
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
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String getGrammarFileName() {
        return "Sensors.g4";
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}