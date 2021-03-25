package es.uva.locomotion.rules;


import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.model.symbol.SymbolType;
import es.uva.locomotion.model.ViewTable;
import es.uva.locomotion.plugin.VensimScanner;
import es.uva.locomotion.parser.visitors.VensimVisitorContext;
import org.junit.Test;

import static es.uva.locomotion.testutilities.RuleTestUtilities.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLookupNameCheck {

    @Test
    public void testCorrectName() {

        String program = "historic3_demand_lt( GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' ))~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);

        assertDoesntHaveIssueOfType(visitorContext,LookupNameCheck.class);
    }

    @Test
    public void testNameCanContainAnyNumber() {
        String program = "numbers_1_2_3_4_5_6_7_8_9_lt( GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' ))~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);

        assertDoesntHaveIssueOfType(visitorContext,LookupNameCheck.class);
    }

    @Test
    public void testUpperCaseAnyWord() {

        String program = "\nHISTORICAL_extraction_lt(  GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,2);

    }

    @Test
    public void testUpperCaseLastWord() {

        String program = "\nhistorical_EXTRACTION_lt(  GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,2);

    }


    @Test
    public void testUppercaseSuffix(){

        String program = "\nhistorical_extraction_LT(  GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,2);
    }

    @Test
    public void testNameWithoutSuffix(){
        String program = "historical_extraction(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testOnlySuffix(){
        String program = "lt(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testUnderscoreSuffix(){
        String program = "_lt(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testSeveralUnderscore(){
        String program = "historical__extraction_lt(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testSeveralUnderscoreBeforeSuffix(){
        String program = "historical_extraction__lt(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testNameBeginningWithUnderscore(){
        String program = "_historical_extraction_lt(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testNameEndingWithUnderscore(){
        String program = "historical_extraction_lt_(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);
    }

    @Test
    public void testNameBeginsWithNumber(){
        String program = "\"3_historical_extraction_lt\"(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|\n" +
                "\"3historical_extraction_lt\"(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1,2);
    }

    @Test
    public void testNameEndsWithNumber(){
        String program = "historical_extraction_lt3(GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1);

    }

    @Test
    public void testMultipleDefinitionCreateDifferentIssues(){
        String program = "historical_extraction_lt3[firstSubscript](GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|\n" +
                "historical_extraction_lt3[firstSubscript](GET XLS LOOKUPS('inputs.xlsx', 'ssData' , 'a', 'b3' )) ~~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,LookupNameCheck.class,1,2);
    }

    @Test
    public void testFailingRuleMakesSymbolInvalid(){
        LookupNameCheck check = new LookupNameCheck();

        SymbolTable table = new SymbolTable();
        Symbol invalid = new Symbol("invalid", SymbolType.Lookup_Table);
        invalid.addLine(1);
        Symbol valid = new Symbol("valid_lt", SymbolType.Lookup_Table);
        valid.addLine(2);
        table.addSymbol(invalid);
        table.addSymbol(valid);

        VensimVisitorContext context = new VensimVisitorContext(null,table, new ViewTable(), null, null);
        check.scan(context);

        assertTrue(valid.isValid());
        assertFalse(invalid.isValid());
    }
}
