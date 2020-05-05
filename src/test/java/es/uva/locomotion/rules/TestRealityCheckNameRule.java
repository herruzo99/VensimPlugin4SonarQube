package es.uva.locomotion.rules;

import es.uva.locomotion.parser.Symbol;
import es.uva.locomotion.parser.SymbolTable;
import es.uva.locomotion.parser.SymbolType;
import es.uva.locomotion.plugin.VensimScanner;
import es.uva.locomotion.plugin.VensimVisitorContext;
import org.junit.Test;

import static es.uva.locomotion.testutilities.RuleTestUtilities.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRealityCheckNameRule {

    private final String REALITYCHECKSUFFIX = ":THE CONDITION: firstVariable[subscript]>100 :IMPLIES: secondVariable<100 ~~|";
    @Test
    public void testCorrectName() {

        String program = "a_reality_check_test" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);

        assertDoesntHaveIssueOfType(visitorContext,RealityCheckNameRule.class);
    }

    @Test
    public void testNameCanContainAnyNumber(){
        String program = "numbers0_1_2_3_4_5_6_7_8_9_test" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);

        assertDoesntHaveIssueOfType(visitorContext,RealityCheckNameRule.class);

    }

    @Test
    public void testNameWithoutSuffix(){
        String program = "reality_check" +REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testUppercaseAnyWord(){
        String program = "a_REALITY_check_test" +REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testUppercaseLastWord(){
        String program = "a_reality_CHECK_test" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testUppercaseSuffix(){
        String program = "\na_reality_check_TEST" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,2);
    }



    @Test
    public void testSeveralUnderscore(){
        String program = "a__reality_check_test" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }



    @Test
    public void testSeveralUnderscoreBeforeSuffix(){
        String program = "reality_check__test" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testNameBeginningWithUnderscore(){
        String program = "_reality_check_test"+ REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testNameEndingWithUnderscore(){
        String program = "reality_check_test_" +REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testWeirdCharacters(){
        String program = "reálity_check_test"+REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testNameBeginsWithNumber(){
        String program = "\"2020reality_check_test\""+REALITYCHECKSUFFIX+ "\n"+
                "\"2020_reality_check_test\"" + REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1,2);

    }

    @Test
    public void testNameIsOnlySuffix(){
        String program = "_test"+REALITYCHECKSUFFIX;

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssueInLines(visitorContext,RealityCheckNameRule.class,1);
    }

    @Test
    public void testFailingRuleMakesSymbolInvalid(){
        RealityCheckNameRule check = new RealityCheckNameRule();

        SymbolTable table = new SymbolTable();
        Symbol invalid = new Symbol("invalid", SymbolType.Reality_Check);
        invalid.addDefinitionLine(1);
        Symbol valid = new Symbol("valid_test", SymbolType.Reality_Check);
        valid.addDefinitionLine(2);
        table.addSymbol(invalid);
        table.addSymbol(valid);

        VensimVisitorContext context = new VensimVisitorContext(null,table,null);
        check.scan(context);

        assertTrue(valid.isValid());
        assertFalse(invalid.isValid());
    }

}
