package es.uva.locomotion.rules;

import es.uva.locomotion.parser.Symbol;
import es.uva.locomotion.parser.SymbolTable;
import es.uva.locomotion.parser.SymbolType;
import es.uva.locomotion.parser.visitors.VensimVisitorContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static es.uva.locomotion.testutilities.RuleTestUtilities.assertHasIssueInLines;
import static es.uva.locomotion.testutilities.GeneralTestUtilities.addSymbolInLines;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestSymbolWithoutUnitsCheck {

    private SymbolWithoutUnitsCheck check;

    @Before
    public void setUp(){
        check = new SymbolWithoutUnitsCheck();
    }

    @Test
    public void testConstantCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"CONST", SymbolType.Constant,1,2,3);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,1,2,3);

    }

    @Test
    public void testVariablesCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"var", SymbolType.Variable,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,9);
    }

    @Test
    public void testRealityCheckCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"reality_check", SymbolType.Reality_Check,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,9);

    }

    @Test
    public void testSubscriptNameCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"MATERIALS_I", SymbolType.Subscript,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,9);

    }

    @Test
    public void testLookupCreatesIssue(){
        SymbolTable table = new SymbolTable();
        addSymbolInLines(table,"my_lookup_lt", SymbolType.Lookup_Table,9);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);

        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,9);
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
        symbol.setUnits("    units ");

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
    public void testEmptyUnitsWithSpaces(){
        SymbolTable table = new SymbolTable();
        Symbol symbol = addSymbolInLines(table,"CONST", SymbolType.Constant,7);
        symbol.setUnits("                                                                                 ");

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        assertHasIssueInLines(context,SymbolWithoutUnitsCheck.class,7);
    }

    @Test
    public void testFailingRuleMakesSymbolInvalid(){
        SymbolWithoutUnitsCheck check = new SymbolWithoutUnitsCheck();

        SymbolTable table = new SymbolTable();
        Symbol invalid = new Symbol("invalid", SymbolType.Constant);
        invalid.addDefinitionLine(1);
        Symbol valid = new Symbol("VALID", SymbolType.Constant);
        valid.addDefinitionLine(2);
        valid.setUnits("units");
        table.addSymbol(invalid);
        table.addSymbol(valid);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        Assert.assertTrue(valid.isValid());
        assertFalse(invalid.isValid());
    }



}
