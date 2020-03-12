package es.uva.medeas.rules;

import es.uva.medeas.parser.Symbol;
import es.uva.medeas.parser.SymbolTable;
import es.uva.medeas.parser.SymbolType;
import es.uva.medeas.plugin.VensimVisitorContext;
import org.junit.Before;
import org.junit.Test;

import static es.uva.medeas.testutilities.RuleTestUtilities.assertHasIssueInLines;
import static es.uva.medeas.testutilities.TestUtilities.addSymbolInLines;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class TestSymbolWithoutCommentCheck {

    private SymbolWithoutCommentCheck check;

    @Before
    public void setUp(){
        check = new SymbolWithoutCommentCheck();
    }

    @Test
    public void testConstantCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"CONST", SymbolType.Constant,1,2,3);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,1,2,3);
    }

    @Test
    public void testVariablesCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"var", SymbolType.Variable,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,9);
    }

    @Test
    public void testRealityCheckCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"reality_check", SymbolType.Reality_Check,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,9);

    }

    @Test
    public void testSubscriptNameCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"MATERIALS_I", SymbolType.Subscript,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,9);

    }

    @Test
    public void testLookupCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"my_lookup_lt", SymbolType.Lookup_Table,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,9);
    }

    @Test
    public void testFunctionDoesntCreateIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"my_macro", SymbolType.Function,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertTrue(context.getIssues().isEmpty());
    }

    @Test
    public void testSubscriptValueDoesntCreateIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"SCENARIO1", SymbolType.Subscript_Value,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertTrue(context.getIssues().isEmpty());
    }

    @Test
    public void testCaseWithoutIssue(){
        SymbolTable table = new SymbolTable();
        Symbol symbol = addSymbolInLines(table,"CONST", SymbolType.Constant,1,2,3,4,5);
        symbol.setComment(" comment ");

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        assertTrue(context.getIssues().isEmpty());

    }

    @Test
    public void testSymbolWithoutDefinitionLine(){
        SymbolTable table = new SymbolTable();

        Symbol symbol = new Symbol("CONST");
        symbol.setType(SymbolType.Constant);
        table.addSymbol(symbol);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        assertTrue(context.getIssues().isEmpty());
    }


    @Test
    public void testEmptyCommentWithSpaces(){
        SymbolTable table = new SymbolTable();
        Symbol symbol = addSymbolInLines(table,"CONST", SymbolType.Constant,7);
        symbol.setComment("                                                                                 ");

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutCommentCheck.class,7);
    }
}
