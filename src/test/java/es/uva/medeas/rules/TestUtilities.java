package es.uva.medeas.rules;


import es.uva.medeas.VensimVisitorContext;
import es.uva.medeas.parser.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestUtilities {

    public static final Set<Symbol> NO_DEPENDENCIES = new HashSet<>();


    public static String loadFile(String file_path) throws IOException{
        File file = new File(
                TestUtilities.class.getClassLoader().getResource(file_path).getFile()
        );



        FileInputStream fileInputStream = new FileInputStream(file.getPath());
        byte[] value = new byte[(int) file.length()];
        fileInputStream.read(value);
        fileInputStream.close();


        return new String(value, StandardCharsets.UTF_8);

    }

    public static ParseTree getParseTree(String file_path) throws IOException {


        return getParseTreeFromString(loadFile(file_path));
    }

    public static ModelParser.FileContext getParseTreeFromString(String fileContent) {
        ModelLexer lexer = new ModelLexer(CharStreams.fromString(fileContent));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ModelParser parser = new ModelParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new VensimErrorListener());

        return parser.file();

    }

    public static SymbolTable getSymbolTableFromString(String content){

        SymbolTable table = getRAWSymbolTableFromString(content);
        SymbolTableGenerator.resolveSymbolTable(table);

        return table;
    }

    public static SymbolTable getRAWSymbolTableFromString(String content){


        ParseTree p = getParseTreeFromString(content);


        RawSymbolTableVisitor visitor = new RawSymbolTableVisitor();
        VensimVisitorContext context = new VensimVisitorContext(p);
        return visitor.getSymbolTable(context);
    }



    public static SymbolTable getRAWSymbolTable(String file_path) throws IOException {
        ParseTree tree = getParseTree(file_path);

        RawSymbolTableVisitor visitor = new RawSymbolTableVisitor();
        VensimVisitorContext context = new VensimVisitorContext(tree);
        return visitor.getSymbolTable(context);
    }

    public static SymbolTable getSymbolTable(String file_path) throws IOException {
        ParseTree tree = getParseTree(file_path);

        VensimVisitorContext context = new VensimVisitorContext(tree);
        return SymbolTableGenerator.getSymbolTable(context);
    }

    public static Set<Symbol> createSet(Symbol... symbols){

        return new HashSet<>(Arrays.asList(symbols));

    }


    public static void assertSymbol(Symbol symbol, SymbolType expectedType, int expectedLine, Set<Symbol> expectedDependencies){
        assertNotNull("The symbol is null",symbol);
       assertSymbolType(symbol,expectedType);
        assertSymbolDefinedOnlyIn(expectedLine,symbol);
        assertEquals(expectedDependencies,symbol.getDependencies());
        assertFalse("The symbol depends on null",expectedDependencies.contains(null));
    }




    public static void assertSymbolType(Symbol symbol, SymbolType expectedType){
        assertEquals("Expected type: '" + expectedType + "' Actual: '" + symbol.getType() +"' for symbol " + symbol.getToken(),
                expectedType,symbol.getType());
    }

    public static void assertSymbolDefinedOnlyIn(int expectedLine, Symbol symbol ){
        assertEquals("The symbol is defined in several lines" + symbol.getDefinitionLines() ,1,symbol.getDefinitionLines().size());

        int line =  symbol.getDefinitionLines().iterator().next();
        assertEquals("Symbol '" + symbol.getToken() +"' expected at line " + expectedLine + " found at: " + line,
                expectedLine,line);

    }

    public static void assertNoDependencies(Symbol symbol){
        assertEquals("Error: Expected 0 dependencies in symbol " + symbol.getToken() + " found " + symbol.getDependencies().size() + ".",
                NO_DEPENDENCIES,symbol.getDependencies());
    }


    public static Set<Symbol> getSymbols(SymbolTable table,String... symbols ){
        Set<Symbol> symbolSet = new HashSet<>();

        for(String symbolStr: symbols){
            Symbol symbolObject =   table.getSymbol(symbolStr);
            assertNotNull("The table of symbols doesn't have any symbol called: " + symbolStr + "." ,symbolObject);
            symbolSet.add(symbolObject);
        }
        return  symbolSet;
    }
}
