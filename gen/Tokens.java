// Generated from D:/juanh/Documents/Proyectos/tfg/src/main/antlr\Tokens.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Tokens extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NewLine=1, CommentOrEncoding=2, Group=3, Star=4, Div=5, Pow=6, Minus=7, 
		Plus=8, Less=9, LessEqual=10, Greater=11, GreaterEqual=12, Equal=13, TwoEqual=14, 
		NotEqual=15, Exclamation=16, DataEquationOp=17, StringAssignOp=18, TwoDots=19, 
		OpenBracket=20, CloseBracket=21, OpenSquareBracket=22, CloseSquareBracket=23, 
		RightArrow=24, TwoSidesArrow=25, Comma=26, Semicolon=27, VerticalBar=28, 
		Dolar=29, At=30, Ignore=31, Except=32, Id=33, FloatingConstNumber=34, 
		FractionalConstant=35, ExponentPart=36, DigitSeq=37, StringLiteral=38, 
		StringConst=39, Whitespace=40, Backslash=41, INFO_UNIT=42, OtherCaracter=43, 
		SketchesDelimiter=44, Condition=45, Implies=46, Test_input=47, Macro=48, 
		EndMacro=49, And=50, Or=51, Delayp=52, Tabbed_array=53, Graph=54, Title=55, 
		Xaxis=56, Xlabel=57, Xdiv=58, Yaxis=59, Ylabel=60, Ydiv=61, Xmin=62, Xmax=63, 
		No_legend=64, Scale=65, Var=66, Ymin=67, Ymax=68, Line_width=69, Metada_separator=70, 
		ViewDelimier=71, Sketch_phrase=72, Sketch_version=73, Keyword=74;
	public static final int
		VIEWS=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN", "VIEWS"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"NewLine", "CommentOrEncoding", "Group", "Star", "Div", "Pow", "Minus", 
			"Plus", "Less", "LessEqual", "Greater", "GreaterEqual", "Equal", "TwoEqual", 
			"NotEqual", "Exclamation", "DataEquationOp", "StringAssignOp", "TwoDots", 
			"OpenBracket", "CloseBracket", "OpenSquareBracket", "CloseSquareBracket", 
			"RightArrow", "TwoSidesArrow", "Comma", "Semicolon", "VerticalBar", "Dolar", 
			"At", "Ignore", "Except", "Id", "IdChar", "Nondigit", "Digit", "NonzeroDigit", 
			"FloatingConstNumber", "FractionalConstant", "ExponentPart", "DigitSeq", 
			"StringLiteral", "StringConst", "Whitespace", "Backslash", "INFO_UNIT", 
			"OtherCaracter", "SketchesDelimiter", "Condition", "Implies", "Test_input", 
			"Macro", "EndMacro", "And", "Or", "Delayp", "Tabbed_array", "Graph", 
			"Title", "Xaxis", "Xlabel", "Xdiv", "Yaxis", "Ylabel", "Ydiv", "Xmin", 
			"Xmax", "No_legend", "Scale", "Var", "Ymin", "Ymax", "Line_width", "Metada_separator", 
			"ViewDelimier", "Sketch_phrase", "Sketch_version", "Keyword"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'*'", "'/'", "'^'", "'-'", "'+'", "'<'", "'<='", 
			"'>'", "'>='", "'='", "'=='", "'<>'", "'!'", "':='", "':IS:'", "':'", 
			"'('", "')'", "'['", "']'", "'->'", "'<->'", "','", "';'", "'|'", "'$'", 
			"'@'", "':IGNORE:'", "':EXCEPT:'", null, null, null, null, null, null, 
			null, null, null, null, null, "'///---'", "':THE CONDITION:'", "':IMPLIES:'", 
			"':TEST INPUT:'", "':MACRO:'", "':END OF MACRO:'", "':AND:'", "':OR:'", 
			"'DELAYP('", "'TABBED ARRAY('", "':GRAPH'", "':TITLE'", "':X-AXIS'", 
			"':X-LABEL'", "':X-DIV'", "':Y-AXIS'", "':Y-LABEL'", "':Y-DIV'", "':X-MIN'", 
			"':X-MAX'", "':NO-LEGEND'", "':SCALE'", "':VAR'", "':Y-MIN'", "':Y-MAX'", 
			"':LINE-WIDTH'", "':L\u007F<%^E!@'", "'---///'", "'Sketch information - do not modify anything except names'", 
			"'V300  Do not put anything below this section - it will be ignored'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NewLine", "CommentOrEncoding", "Group", "Star", "Div", "Pow", 
			"Minus", "Plus", "Less", "LessEqual", "Greater", "GreaterEqual", "Equal", 
			"TwoEqual", "NotEqual", "Exclamation", "DataEquationOp", "StringAssignOp", 
			"TwoDots", "OpenBracket", "CloseBracket", "OpenSquareBracket", "CloseSquareBracket", 
			"RightArrow", "TwoSidesArrow", "Comma", "Semicolon", "VerticalBar", "Dolar", 
			"At", "Ignore", "Except", "Id", "FloatingConstNumber", "FractionalConstant", 
			"ExponentPart", "DigitSeq", "StringLiteral", "StringConst", "Whitespace", 
			"Backslash", "INFO_UNIT", "OtherCaracter", "SketchesDelimiter", "Condition", 
			"Implies", "Test_input", "Macro", "EndMacro", "And", "Or", "Delayp", 
			"Tabbed_array", "Graph", "Title", "Xaxis", "Xlabel", "Xdiv", "Yaxis", 
			"Ylabel", "Ydiv", "Xmin", "Xmax", "No_legend", "Scale", "Var", "Ymin", 
			"Ymax", "Line_width", "Metada_separator", "ViewDelimier", "Sketch_phrase", 
			"Sketch_version", "Keyword"
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


	public Tokens(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Tokens.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2L\u0329\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\3\2\6\2\u00a1\n\2\r\2\16\2\u00a2"+
		"\3\2\3\2\3\3\3\3\7\3\u00a9\n\3\f\3\16\3\u00ac\13\3\3\3\3\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\7\4\u00ec\n\4\f\4\16\4\u00ef\13\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3"+
		"\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3"+
		"\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3"+
		"!\3!\3!\3!\3!\3\"\3\"\7\"\u014a\n\"\f\"\16\"\u014d\13\"\3\"\3\"\3\"\7"+
		"\"\u0152\n\"\f\"\16\"\u0155\13\"\3\"\3\"\3\"\5\"\u015a\n\"\3#\3#\3$\3"+
		"$\3%\3%\3&\3&\3\'\3\'\5\'\u0166\n\'\3\'\3\'\3\'\5\'\u016b\n\'\3(\5(\u016e"+
		"\n(\3(\3(\3(\3(\3(\5(\u0175\n(\3)\3)\5)\u0179\n)\3)\3)\3)\5)\u017e\n)"+
		"\3)\5)\u0181\n)\3*\6*\u0184\n*\r*\16*\u0185\3+\3+\3+\3+\7+\u018c\n+\f"+
		"+\16+\u018f\13+\3+\3+\3,\3,\3,\3,\7,\u0197\n,\f,\16,\u019a\13,\3,\3,\3"+
		"-\6-\u019f\n-\r-\16-\u01a0\3-\3-\3.\3.\3.\3.\3/\3/\7/\u01ab\n/\f/\16/"+
		"\u01ae\13/\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\39\39\39"+
		"\39\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;"+
		"\3;\3;\3;\3<\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>"+
		"\3>\3>\3>\3>\3?\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3@\3A\3A\3A\3A"+
		"\3A\3A\3A\3A\3A\3B\3B\3B\3B\3B\3B\3B\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\3D"+
		"\3D\3D\3D\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3F\3F\3F\3F\3F\3F\3F\3G\3G"+
		"\3G\3G\3G\3H\3H\3H\3H\3H\3H\3H\3I\3I\3I\3I\3I\3I\3I\3J\3J\3J\3J\3J\3J"+
		"\3J\3J\3J\3J\3J\3J\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3L\3L\3L\3L\3L\3L\3L"+
		"\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M"+
		"\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M"+
		"\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3O\3O\7O\u0323\nO\fO\16O\u0326\13O\3O\3O\6\u00aa"+
		"\u00ed\u018d\u0198\2P\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E\2G\2I\2K\2M$O%Q&S\'U(W)Y*[+],_-a.c/e\60"+
		"g\61i\62k\63m\64o\65q\66s\67u8w9y:{;}<\177=\u0081>\u0083?\u0085@\u0087"+
		"A\u0089B\u008bC\u008dD\u008fE\u0091F\u0093G\u0095H\u0097I\u0099J\u009b"+
		"K\u009dL\3\2\20\4\2\f\f\17\17\t\2&)\62;C\\aac|\u00a3\u0251\u1e04\u1ef5"+
		"\5\2C\\aac|\3\2\62;\3\2\63;\4\2--//\3\2$$\4\2$$^^\3\2^^\3\2))\4\2))^^"+
		"\4\2\13\13\"\"\4\2~~\u0080\u0080\5\2\"\"C\\c|\2\u033b\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33"+
		"\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2"+
		"\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2"+
		"\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2"+
		"\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2"+
		"\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y"+
		"\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3"+
		"\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2"+
		"\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095"+
		"\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2"+
		"\2\3\u00a0\3\2\2\2\5\u00a6\3\2\2\2\7\u00b1\3\2\2\2\t\u00f4\3\2\2\2\13"+
		"\u00f6\3\2\2\2\r\u00f8\3\2\2\2\17\u00fa\3\2\2\2\21\u00fc\3\2\2\2\23\u00fe"+
		"\3\2\2\2\25\u0100\3\2\2\2\27\u0103\3\2\2\2\31\u0105\3\2\2\2\33\u0108\3"+
		"\2\2\2\35\u010a\3\2\2\2\37\u010d\3\2\2\2!\u0110\3\2\2\2#\u0112\3\2\2\2"+
		"%\u0115\3\2\2\2\'\u011a\3\2\2\2)\u011c\3\2\2\2+\u011e\3\2\2\2-\u0120\3"+
		"\2\2\2/\u0122\3\2\2\2\61\u0124\3\2\2\2\63\u0127\3\2\2\2\65\u012b\3\2\2"+
		"\2\67\u012d\3\2\2\29\u012f\3\2\2\2;\u0131\3\2\2\2=\u0133\3\2\2\2?\u0135"+
		"\3\2\2\2A\u013e\3\2\2\2C\u0159\3\2\2\2E\u015b\3\2\2\2G\u015d\3\2\2\2I"+
		"\u015f\3\2\2\2K\u0161\3\2\2\2M\u016a\3\2\2\2O\u0174\3\2\2\2Q\u0180\3\2"+
		"\2\2S\u0183\3\2\2\2U\u0187\3\2\2\2W\u0192\3\2\2\2Y\u019e\3\2\2\2[\u01a4"+
		"\3\2\2\2]\u01a8\3\2\2\2_\u01af\3\2\2\2a\u01b1\3\2\2\2c\u01b8\3\2\2\2e"+
		"\u01c8\3\2\2\2g\u01d2\3\2\2\2i\u01df\3\2\2\2k\u01e7\3\2\2\2m\u01f6\3\2"+
		"\2\2o\u01fc\3\2\2\2q\u0201\3\2\2\2s\u0209\3\2\2\2u\u0217\3\2\2\2w\u021e"+
		"\3\2\2\2y\u0225\3\2\2\2{\u022d\3\2\2\2}\u0236\3\2\2\2\177\u023d\3\2\2"+
		"\2\u0081\u0245\3\2\2\2\u0083\u024e\3\2\2\2\u0085\u0255\3\2\2\2\u0087\u025c"+
		"\3\2\2\2\u0089\u0263\3\2\2\2\u008b\u026e\3\2\2\2\u008d\u0275\3\2\2\2\u008f"+
		"\u027a\3\2\2\2\u0091\u0281\3\2\2\2\u0093\u0288\3\2\2\2\u0095\u0294\3\2"+
		"\2\2\u0097\u029e\3\2\2\2\u0099\u02a5\3\2\2\2\u009b\u02de\3\2\2\2\u009d"+
		"\u0320\3\2\2\2\u009f\u00a1\t\2\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\u00a5\b\2\2\2\u00a5\4\3\2\2\2\u00a6\u00aa\7}\2\2\u00a7\u00a9\13\2\2\2"+
		"\u00a8\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00ab\3\2\2\2\u00aa\u00a8"+
		"\3\2\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7\177\2\2"+
		"\u00ae\u00af\3\2\2\2\u00af\u00b0\b\3\3\2\u00b0\6\3\2\2\2\u00b1\u00b2\7"+
		",\2\2\u00b2\u00b3\7,\2\2\u00b3\u00b4\7,\2\2\u00b4\u00b5\7,\2\2\u00b5\u00b6"+
		"\7,\2\2\u00b6\u00b7\7,\2\2\u00b7\u00b8\7,\2\2\u00b8\u00b9\7,\2\2\u00b9"+
		"\u00ba\7,\2\2\u00ba\u00bb\7,\2\2\u00bb\u00bc\7,\2\2\u00bc\u00bd\7,\2\2"+
		"\u00bd\u00be\7,\2\2\u00be\u00bf\7,\2\2\u00bf\u00c0\7,\2\2\u00c0\u00c1"+
		"\7,\2\2\u00c1\u00c2\7,\2\2\u00c2\u00c3\7,\2\2\u00c3\u00c4\7,\2\2\u00c4"+
		"\u00c5\7,\2\2\u00c5\u00c6\7,\2\2\u00c6\u00c7\7,\2\2\u00c7\u00c8\7,\2\2"+
		"\u00c8\u00c9\7,\2\2\u00c9\u00ca\7,\2\2\u00ca\u00cb\7,\2\2\u00cb\u00cc"+
		"\7,\2\2\u00cc\u00cd\7,\2\2\u00cd\u00ce\7,\2\2\u00ce\u00cf\7,\2\2\u00cf"+
		"\u00d0\7,\2\2\u00d0\u00d1\7,\2\2\u00d1\u00d2\7,\2\2\u00d2\u00d3\7,\2\2"+
		"\u00d3\u00d4\7,\2\2\u00d4\u00d5\7,\2\2\u00d5\u00d6\7,\2\2\u00d6\u00d7"+
		"\7,\2\2\u00d7\u00d8\7,\2\2\u00d8\u00d9\7,\2\2\u00d9\u00da\7,\2\2\u00da"+
		"\u00db\7,\2\2\u00db\u00dc\7,\2\2\u00dc\u00dd\7,\2\2\u00dd\u00de\7,\2\2"+
		"\u00de\u00df\7,\2\2\u00df\u00e0\7,\2\2\u00e0\u00e1\7,\2\2\u00e1\u00e2"+
		"\7,\2\2\u00e2\u00e3\7,\2\2\u00e3\u00e4\7,\2\2\u00e4\u00e5\7,\2\2\u00e5"+
		"\u00e6\7,\2\2\u00e6\u00e7\7,\2\2\u00e7\u00e8\7,\2\2\u00e8\u00e9\7,\2\2"+
		"\u00e9\u00ed\3\2\2\2\u00ea\u00ec\13\2\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ef"+
		"\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f0\3\2\2\2\u00ef"+
		"\u00ed\3\2\2\2\u00f0\u00f1\7~\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\b\4"+
		"\3\2\u00f3\b\3\2\2\2\u00f4\u00f5\7,\2\2\u00f5\n\3\2\2\2\u00f6\u00f7\7"+
		"\61\2\2\u00f7\f\3\2\2\2\u00f8\u00f9\7`\2\2\u00f9\16\3\2\2\2\u00fa\u00fb"+
		"\7/\2\2\u00fb\20\3\2\2\2\u00fc\u00fd\7-\2\2\u00fd\22\3\2\2\2\u00fe\u00ff"+
		"\7>\2\2\u00ff\24\3\2\2\2\u0100\u0101\7>\2\2\u0101\u0102\7?\2\2\u0102\26"+
		"\3\2\2\2\u0103\u0104\7@\2\2\u0104\30\3\2\2\2\u0105\u0106\7@\2\2\u0106"+
		"\u0107\7?\2\2\u0107\32\3\2\2\2\u0108\u0109\7?\2\2\u0109\34\3\2\2\2\u010a"+
		"\u010b\7?\2\2\u010b\u010c\7?\2\2\u010c\36\3\2\2\2\u010d\u010e\7>\2\2\u010e"+
		"\u010f\7@\2\2\u010f \3\2\2\2\u0110\u0111\7#\2\2\u0111\"\3\2\2\2\u0112"+
		"\u0113\7<\2\2\u0113\u0114\7?\2\2\u0114$\3\2\2\2\u0115\u0116\7<\2\2\u0116"+
		"\u0117\7K\2\2\u0117\u0118\7U\2\2\u0118\u0119\7<\2\2\u0119&\3\2\2\2\u011a"+
		"\u011b\7<\2\2\u011b(\3\2\2\2\u011c\u011d\7*\2\2\u011d*\3\2\2\2\u011e\u011f"+
		"\7+\2\2\u011f,\3\2\2\2\u0120\u0121\7]\2\2\u0121.\3\2\2\2\u0122\u0123\7"+
		"_\2\2\u0123\60\3\2\2\2\u0124\u0125\7/\2\2\u0125\u0126\7@\2\2\u0126\62"+
		"\3\2\2\2\u0127\u0128\7>\2\2\u0128\u0129\7/\2\2\u0129\u012a\7@\2\2\u012a"+
		"\64\3\2\2\2\u012b\u012c\7.\2\2\u012c\66\3\2\2\2\u012d\u012e\7=\2\2\u012e"+
		"8\3\2\2\2\u012f\u0130\7~\2\2\u0130:\3\2\2\2\u0131\u0132\7&\2\2\u0132<"+
		"\3\2\2\2\u0133\u0134\7B\2\2\u0134>\3\2\2\2\u0135\u0136\7<\2\2\u0136\u0137"+
		"\7K\2\2\u0137\u0138\7I\2\2\u0138\u0139\7P\2\2\u0139\u013a\7Q\2\2\u013a"+
		"\u013b\7T\2\2\u013b\u013c\7G\2\2\u013c\u013d\7<\2\2\u013d@\3\2\2\2\u013e"+
		"\u013f\7<\2\2\u013f\u0140\7G\2\2\u0140\u0141\7Z\2\2\u0141\u0142\7E\2\2"+
		"\u0142\u0143\7G\2\2\u0143\u0144\7R\2\2\u0144\u0145\7V\2\2\u0145\u0146"+
		"\7<\2\2\u0146B\3\2\2\2\u0147\u014b\5G$\2\u0148\u014a\5E#\2\u0149\u0148"+
		"\3\2\2\2\u014a\u014d\3\2\2\2\u014b\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c"+
		"\u015a\3\2\2\2\u014d\u014b\3\2\2\2\u014e\u0153\5G$\2\u014f\u0152\5E#\2"+
		"\u0150\u0152\7\"\2\2\u0151\u014f\3\2\2\2\u0151\u0150\3\2\2\2\u0152\u0155"+
		"\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0156\3\2\2\2\u0155"+
		"\u0153\3\2\2\2\u0156\u0157\5E#\2\u0157\u015a\3\2\2\2\u0158\u015a\5U+\2"+
		"\u0159\u0147\3\2\2\2\u0159\u014e\3\2\2\2\u0159\u0158\3\2\2\2\u015aD\3"+
		"\2\2\2\u015b\u015c\t\3\2\2\u015cF\3\2\2\2\u015d\u015e\t\4\2\2\u015eH\3"+
		"\2\2\2\u015f\u0160\t\5\2\2\u0160J\3\2\2\2\u0161\u0162\t\6\2\2\u0162L\3"+
		"\2\2\2\u0163\u0165\5O(\2\u0164\u0166\5Q)\2\u0165\u0164\3\2\2\2\u0165\u0166"+
		"\3\2\2\2\u0166\u016b\3\2\2\2\u0167\u0168\5S*\2\u0168\u0169\5Q)\2\u0169"+
		"\u016b\3\2\2\2\u016a\u0163\3\2\2\2\u016a\u0167\3\2\2\2\u016bN\3\2\2\2"+
		"\u016c\u016e\5S*\2\u016d\u016c\3\2\2\2\u016d\u016e\3\2\2\2\u016e\u016f"+
		"\3\2\2\2\u016f\u0170\7\60\2\2\u0170\u0175\5S*\2\u0171\u0172\5S*\2\u0172"+
		"\u0173\7\60\2\2\u0173\u0175\3\2\2\2\u0174\u016d\3\2\2\2\u0174\u0171\3"+
		"\2\2\2\u0175P\3\2\2\2\u0176\u0178\7g\2\2\u0177\u0179\t\7\2\2\u0178\u0177"+
		"\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u0181\5S*\2\u017b"+
		"\u017d\7G\2\2\u017c\u017e\t\7\2\2\u017d\u017c\3\2\2\2\u017d\u017e\3\2"+
		"\2\2\u017e\u017f\3\2\2\2\u017f\u0181\5S*\2\u0180\u0176\3\2\2\2\u0180\u017b"+
		"\3\2\2\2\u0181R\3\2\2\2\u0182\u0184\5I%\2\u0183\u0182\3\2\2\2\u0184\u0185"+
		"\3\2\2\2\u0185\u0183\3\2\2\2\u0185\u0186\3\2\2\2\u0186T\3\2\2\2\u0187"+
		"\u018d\t\b\2\2\u0188\u018c\n\t\2\2\u0189\u018a\t\n\2\2\u018a\u018c\13"+
		"\2\2\2\u018b\u0188\3\2\2\2\u018b\u0189\3\2\2\2\u018c\u018f\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018d\u018b\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u018d\3\2"+
		"\2\2\u0190\u0191\t\b\2\2\u0191V\3\2\2\2\u0192\u0198\t\13\2\2\u0193\u0197"+
		"\n\f\2\2\u0194\u0195\t\n\2\2\u0195\u0197\13\2\2\2\u0196\u0193\3\2\2\2"+
		"\u0196\u0194\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0199\3\2\2\2\u0198\u0196"+
		"\3\2\2\2\u0199\u019b\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u019c\t\13\2\2"+
		"\u019cX\3\2\2\2\u019d\u019f\t\r\2\2\u019e\u019d\3\2\2\2\u019f\u01a0\3"+
		"\2\2\2\u01a0\u019e\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2"+
		"\u01a3\b-\3\2\u01a3Z\3\2\2\2\u01a4\u01a5\t\n\2\2\u01a5\u01a6\3\2\2\2\u01a6"+
		"\u01a7\b.\3\2\u01a7\\\3\2\2\2\u01a8\u01ac\7\u0080\2\2\u01a9\u01ab\n\16"+
		"\2\2\u01aa\u01a9\3\2\2\2\u01ab\u01ae\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ac"+
		"\u01ad\3\2\2\2\u01ad^\3\2\2\2\u01ae\u01ac\3\2\2\2\u01af\u01b0\13\2\2\2"+
		"\u01b0`\3\2\2\2\u01b1\u01b2\7\61\2\2\u01b2\u01b3\7\61\2\2\u01b3\u01b4"+
		"\7\61\2\2\u01b4\u01b5\7/\2\2\u01b5\u01b6\7/\2\2\u01b6\u01b7\7/\2\2\u01b7"+
		"b\3\2\2\2\u01b8\u01b9\7<\2\2\u01b9\u01ba\7V\2\2\u01ba\u01bb\7J\2\2\u01bb"+
		"\u01bc\7G\2\2\u01bc\u01bd\7\"\2\2\u01bd\u01be\7E\2\2\u01be\u01bf\7Q\2"+
		"\2\u01bf\u01c0\7P\2\2\u01c0\u01c1\7F\2\2\u01c1\u01c2\7K\2\2\u01c2\u01c3"+
		"\7V\2\2\u01c3\u01c4\7K\2\2\u01c4\u01c5\7Q\2\2\u01c5\u01c6\7P\2\2\u01c6"+
		"\u01c7\7<\2\2\u01c7d\3\2\2\2\u01c8\u01c9\7<\2\2\u01c9\u01ca\7K\2\2\u01ca"+
		"\u01cb\7O\2\2\u01cb\u01cc\7R\2\2\u01cc\u01cd\7N\2\2\u01cd\u01ce\7K\2\2"+
		"\u01ce\u01cf\7G\2\2\u01cf\u01d0\7U\2\2\u01d0\u01d1\7<\2\2\u01d1f\3\2\2"+
		"\2\u01d2\u01d3\7<\2\2\u01d3\u01d4\7V\2\2\u01d4\u01d5\7G\2\2\u01d5\u01d6"+
		"\7U\2\2\u01d6\u01d7\7V\2\2\u01d7\u01d8\7\"\2\2\u01d8\u01d9\7K\2\2\u01d9"+
		"\u01da\7P\2\2\u01da\u01db\7R\2\2\u01db\u01dc\7W\2\2\u01dc\u01dd\7V\2\2"+
		"\u01dd\u01de\7<\2\2\u01deh\3\2\2\2\u01df\u01e0\7<\2\2\u01e0\u01e1\7O\2"+
		"\2\u01e1\u01e2\7C\2\2\u01e2\u01e3\7E\2\2\u01e3\u01e4\7T\2\2\u01e4\u01e5"+
		"\7Q\2\2\u01e5\u01e6\7<\2\2\u01e6j\3\2\2\2\u01e7\u01e8\7<\2\2\u01e8\u01e9"+
		"\7G\2\2\u01e9\u01ea\7P\2\2\u01ea\u01eb\7F\2\2\u01eb\u01ec\7\"\2\2\u01ec"+
		"\u01ed\7Q\2\2\u01ed\u01ee\7H\2\2\u01ee\u01ef\7\"\2\2\u01ef\u01f0\7O\2"+
		"\2\u01f0\u01f1\7C\2\2\u01f1\u01f2\7E\2\2\u01f2\u01f3\7T\2\2\u01f3\u01f4"+
		"\7Q\2\2\u01f4\u01f5\7<\2\2\u01f5l\3\2\2\2\u01f6\u01f7\7<\2\2\u01f7\u01f8"+
		"\7C\2\2\u01f8\u01f9\7P\2\2\u01f9\u01fa\7F\2\2\u01fa\u01fb\7<\2\2\u01fb"+
		"n\3\2\2\2\u01fc\u01fd\7<\2\2\u01fd\u01fe\7Q\2\2\u01fe\u01ff\7T\2\2\u01ff"+
		"\u0200\7<\2\2\u0200p\3\2\2\2\u0201\u0202\7F\2\2\u0202\u0203\7G\2\2\u0203"+
		"\u0204\7N\2\2\u0204\u0205\7C\2\2\u0205\u0206\7[\2\2\u0206\u0207\7R\2\2"+
		"\u0207\u0208\7*\2\2\u0208r\3\2\2\2\u0209\u020a\7V\2\2\u020a\u020b\7C\2"+
		"\2\u020b\u020c\7D\2\2\u020c\u020d\7D\2\2\u020d\u020e\7G\2\2\u020e\u020f"+
		"\7F\2\2\u020f\u0210\7\"\2\2\u0210\u0211\7C\2\2\u0211\u0212\7T\2\2\u0212"+
		"\u0213\7T\2\2\u0213\u0214\7C\2\2\u0214\u0215\7[\2\2\u0215\u0216\7*\2\2"+
		"\u0216t\3\2\2\2\u0217\u0218\7<\2\2\u0218\u0219\7I\2\2\u0219\u021a\7T\2"+
		"\2\u021a\u021b\7C\2\2\u021b\u021c\7R\2\2\u021c\u021d\7J\2\2\u021dv\3\2"+
		"\2\2\u021e\u021f\7<\2\2\u021f\u0220\7V\2\2\u0220\u0221\7K\2\2\u0221\u0222"+
		"\7V\2\2\u0222\u0223\7N\2\2\u0223\u0224\7G\2\2\u0224x\3\2\2\2\u0225\u0226"+
		"\7<\2\2\u0226\u0227\7Z\2\2\u0227\u0228\7/\2\2\u0228\u0229\7C\2\2\u0229"+
		"\u022a\7Z\2\2\u022a\u022b\7K\2\2\u022b\u022c\7U\2\2\u022cz\3\2\2\2\u022d"+
		"\u022e\7<\2\2\u022e\u022f\7Z\2\2\u022f\u0230\7/\2\2\u0230\u0231\7N\2\2"+
		"\u0231\u0232\7C\2\2\u0232\u0233\7D\2\2\u0233\u0234\7G\2\2\u0234\u0235"+
		"\7N\2\2\u0235|\3\2\2\2\u0236\u0237\7<\2\2\u0237\u0238\7Z\2\2\u0238\u0239"+
		"\7/\2\2\u0239\u023a\7F\2\2\u023a\u023b\7K\2\2\u023b\u023c\7X\2\2\u023c"+
		"~\3\2\2\2\u023d\u023e\7<\2\2\u023e\u023f\7[\2\2\u023f\u0240\7/\2\2\u0240"+
		"\u0241\7C\2\2\u0241\u0242\7Z\2\2\u0242\u0243\7K\2\2\u0243\u0244\7U\2\2"+
		"\u0244\u0080\3\2\2\2\u0245\u0246\7<\2\2\u0246\u0247\7[\2\2\u0247\u0248"+
		"\7/\2\2\u0248\u0249\7N\2\2\u0249\u024a\7C\2\2\u024a\u024b\7D\2\2\u024b"+
		"\u024c\7G\2\2\u024c\u024d\7N\2\2\u024d\u0082\3\2\2\2\u024e\u024f\7<\2"+
		"\2\u024f\u0250\7[\2\2\u0250\u0251\7/\2\2\u0251\u0252\7F\2\2\u0252\u0253"+
		"\7K\2\2\u0253\u0254\7X\2\2\u0254\u0084\3\2\2\2\u0255\u0256\7<\2\2\u0256"+
		"\u0257\7Z\2\2\u0257\u0258\7/\2\2\u0258\u0259\7O\2\2\u0259\u025a\7K\2\2"+
		"\u025a\u025b\7P\2\2\u025b\u0086\3\2\2\2\u025c\u025d\7<\2\2\u025d\u025e"+
		"\7Z\2\2\u025e\u025f\7/\2\2\u025f\u0260\7O\2\2\u0260\u0261\7C\2\2\u0261"+
		"\u0262\7Z\2\2\u0262\u0088\3\2\2\2\u0263\u0264\7<\2\2\u0264\u0265\7P\2"+
		"\2\u0265\u0266\7Q\2\2\u0266\u0267\7/\2\2\u0267\u0268\7N\2\2\u0268\u0269"+
		"\7G\2\2\u0269\u026a\7I\2\2\u026a\u026b\7G\2\2\u026b\u026c\7P\2\2\u026c"+
		"\u026d\7F\2\2\u026d\u008a\3\2\2\2\u026e\u026f\7<\2\2\u026f\u0270\7U\2"+
		"\2\u0270\u0271\7E\2\2\u0271\u0272\7C\2\2\u0272\u0273\7N\2\2\u0273\u0274"+
		"\7G\2\2\u0274\u008c\3\2\2\2\u0275\u0276\7<\2\2\u0276\u0277\7X\2\2\u0277"+
		"\u0278\7C\2\2\u0278\u0279\7T\2\2\u0279\u008e\3\2\2\2\u027a\u027b\7<\2"+
		"\2\u027b\u027c\7[\2\2\u027c\u027d\7/\2\2\u027d\u027e\7O\2\2\u027e\u027f"+
		"\7K\2\2\u027f\u0280\7P\2\2\u0280\u0090\3\2\2\2\u0281\u0282\7<\2\2\u0282"+
		"\u0283\7[\2\2\u0283\u0284\7/\2\2\u0284\u0285\7O\2\2\u0285\u0286\7C\2\2"+
		"\u0286\u0287\7Z\2\2\u0287\u0092\3\2\2\2\u0288\u0289\7<\2\2\u0289\u028a"+
		"\7N\2\2\u028a\u028b\7K\2\2\u028b\u028c\7P\2\2\u028c\u028d\7G\2\2\u028d"+
		"\u028e\7/\2\2\u028e\u028f\7Y\2\2\u028f\u0290\7K\2\2\u0290\u0291\7F\2\2"+
		"\u0291\u0292\7V\2\2\u0292\u0293\7J\2\2\u0293\u0094\3\2\2\2\u0294\u0295"+
		"\7<\2\2\u0295\u0296\7N\2\2\u0296\u0297\7\u0081\2\2\u0297\u0298\7>\2\2"+
		"\u0298\u0299\7\'\2\2\u0299\u029a\7`\2\2\u029a\u029b\7G\2\2\u029b\u029c"+
		"\7#\2\2\u029c\u029d\7B\2\2\u029d\u0096\3\2\2\2\u029e\u029f\7/\2\2\u029f"+
		"\u02a0\7/\2\2\u02a0\u02a1\7/\2\2\u02a1\u02a2\7\61\2\2\u02a2\u02a3\7\61"+
		"\2\2\u02a3\u02a4\7\61\2\2\u02a4\u0098\3\2\2\2\u02a5\u02a6\7U\2\2\u02a6"+
		"\u02a7\7m\2\2\u02a7\u02a8\7g\2\2\u02a8\u02a9\7v\2\2\u02a9\u02aa\7e\2\2"+
		"\u02aa\u02ab\7j\2\2\u02ab\u02ac\7\"\2\2\u02ac\u02ad\7k\2\2\u02ad\u02ae"+
		"\7p\2\2\u02ae\u02af\7h\2\2\u02af\u02b0\7q\2\2\u02b0\u02b1\7t\2\2\u02b1"+
		"\u02b2\7o\2\2\u02b2\u02b3\7c\2\2\u02b3\u02b4\7v\2\2\u02b4\u02b5\7k\2\2"+
		"\u02b5\u02b6\7q\2\2\u02b6\u02b7\7p\2\2\u02b7\u02b8\7\"\2\2\u02b8\u02b9"+
		"\7/\2\2\u02b9\u02ba\7\"\2\2\u02ba\u02bb\7f\2\2\u02bb\u02bc\7q\2\2\u02bc"+
		"\u02bd\7\"\2\2\u02bd\u02be\7p\2\2\u02be\u02bf\7q\2\2\u02bf\u02c0\7v\2"+
		"\2\u02c0\u02c1\7\"\2\2\u02c1\u02c2\7o\2\2\u02c2\u02c3\7q\2\2\u02c3\u02c4"+
		"\7f\2\2\u02c4\u02c5\7k\2\2\u02c5\u02c6\7h\2\2\u02c6\u02c7\7{\2\2\u02c7"+
		"\u02c8\7\"\2\2\u02c8\u02c9\7c\2\2\u02c9\u02ca\7p\2\2\u02ca\u02cb\7{\2"+
		"\2\u02cb\u02cc\7v\2\2\u02cc\u02cd\7j\2\2\u02cd\u02ce\7k\2\2\u02ce\u02cf"+
		"\7p\2\2\u02cf\u02d0\7i\2\2\u02d0\u02d1\7\"\2\2\u02d1\u02d2\7g\2\2\u02d2"+
		"\u02d3\7z\2\2\u02d3\u02d4\7e\2\2\u02d4\u02d5\7g\2\2\u02d5\u02d6\7r\2\2"+
		"\u02d6\u02d7\7v\2\2\u02d7\u02d8\7\"\2\2\u02d8\u02d9\7p\2\2\u02d9\u02da"+
		"\7c\2\2\u02da\u02db\7o\2\2\u02db\u02dc\7g\2\2\u02dc\u02dd\7u\2\2\u02dd"+
		"\u009a\3\2\2\2\u02de\u02df\7X\2\2\u02df\u02e0\7\65\2\2\u02e0\u02e1\7\62"+
		"\2\2\u02e1\u02e2\7\62\2\2\u02e2\u02e3\7\"\2\2\u02e3\u02e4\7\"\2\2\u02e4"+
		"\u02e5\7F\2\2\u02e5\u02e6\7q\2\2\u02e6\u02e7\7\"\2\2\u02e7\u02e8\7p\2"+
		"\2\u02e8\u02e9\7q\2\2\u02e9\u02ea\7v\2\2\u02ea\u02eb\7\"\2\2\u02eb\u02ec"+
		"\7r\2\2\u02ec\u02ed\7w\2\2\u02ed\u02ee\7v\2\2\u02ee\u02ef\7\"\2\2\u02ef"+
		"\u02f0\7c\2\2\u02f0\u02f1\7p\2\2\u02f1\u02f2\7{\2\2\u02f2\u02f3\7v\2\2"+
		"\u02f3\u02f4\7j\2\2\u02f4\u02f5\7k\2\2\u02f5\u02f6\7p\2\2\u02f6\u02f7"+
		"\7i\2\2\u02f7\u02f8\7\"\2\2\u02f8\u02f9\7d\2\2\u02f9\u02fa\7g\2\2\u02fa"+
		"\u02fb\7n\2\2\u02fb\u02fc\7q\2\2\u02fc\u02fd\7y\2\2\u02fd\u02fe\7\"\2"+
		"\2\u02fe\u02ff\7v\2\2\u02ff\u0300\7j\2\2\u0300\u0301\7k\2\2\u0301\u0302"+
		"\7u\2\2\u0302\u0303\7\"\2\2\u0303\u0304\7u\2\2\u0304\u0305\7g\2\2\u0305"+
		"\u0306\7e\2\2\u0306\u0307\7v\2\2\u0307\u0308\7k\2\2\u0308\u0309\7q\2\2"+
		"\u0309\u030a\7p\2\2\u030a\u030b\7\"\2\2\u030b\u030c\7/\2\2\u030c\u030d"+
		"\7\"\2\2\u030d\u030e\7k\2\2\u030e\u030f\7v\2\2\u030f\u0310\7\"\2\2\u0310"+
		"\u0311\7y\2\2\u0311\u0312\7k\2\2\u0312\u0313\7n\2\2\u0313\u0314\7n\2\2"+
		"\u0314\u0315\7\"\2\2\u0315\u0316\7d\2\2\u0316\u0317\7g\2\2\u0317\u0318"+
		"\7\"\2\2\u0318\u0319\7k\2\2\u0319\u031a\7i\2\2\u031a\u031b\7p\2\2\u031b"+
		"\u031c\7q\2\2\u031c\u031d\7t\2\2\u031d\u031e\7g\2\2\u031e\u031f\7f\2\2"+
		"\u031f\u009c\3\2\2\2\u0320\u0324\7<\2\2\u0321\u0323\t\17\2\2\u0322\u0321"+
		"\3\2\2\2\u0323\u0326\3\2\2\2\u0324\u0322\3\2\2\2\u0324\u0325\3\2\2\2\u0325"+
		"\u0327\3\2\2\2\u0326\u0324\3\2\2\2\u0327\u0328\7<\2\2\u0328\u009e\3\2"+
		"\2\2\31\2\u00a2\u00aa\u00ed\u014b\u0151\u0153\u0159\u0165\u016a\u016d"+
		"\u0174\u0178\u017d\u0180\u0185\u018b\u018d\u0196\u0198\u01a0\u01ac\u0324"+
		"\4\2\4\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}