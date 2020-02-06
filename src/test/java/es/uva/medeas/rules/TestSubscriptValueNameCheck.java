package es.uva.medeas.rules;


import es.uva.medeas.plugin.Issue;
import es.uva.medeas.plugin.VensimScanner;
import es.uva.medeas.plugin.VensimVisitorContext;
import es.uva.medeas.testutilities.TestUtilities;
import org.junit.Test;

import java.util.List;

import static es.uva.medeas.testutilities.RuleTestUtilities.*;
import static org.junit.Assert.assertTrue;

public class TestSubscriptValueNameCheck {

    @Test
    public void testCorrectName() {

        String program = "MY_COUNTRIES_I: FIRST_COUNTRY, COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);
        List<Issue> issues =TestUtilities.getIssuesFromType(visitorContext,SubscriptValueNameCheck.class);
        assertTrue(issues.isEmpty());

    }

    @Test
    public void testNameCanContainAnyNumber() {
        String program = "NUMBERS_INSIDE_SUBSCRIPTS_I:\n COUNTRY_0_1_2_3_4_5_6_7_8_9, COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();

        scanner.checkIssues(visitorContext);

        List<Issue> issues =TestUtilities.getIssuesFromType(visitorContext,SubscriptValueNameCheck.class);
        assertTrue(issues.isEmpty());
    }


    @Test
    public void testLowerCaseAnyWord() {

        String program = "COUNTRIES_I: COUNTRY,\n my_COUNTRY~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext, SubscriptValueNameCheck.class, 2);

    }

    @Test
    public void testLowerCaseLastWord() {

        String program = "COUNTRIES_I: COUNTRY1,\n country2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,2);

    }

    @Test
    public void testSeveralUnderscore(){
        String program = "MY_COUNTRIES_I: ONE__COUNTRY,\n COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,1);
    }


    @Test
    public void testNameBeginningWithUnderscore(){
        String program = "MY_COUNTRIES_I:\n _COUNTRY1\n, COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,2);
    }

    @Test
    public void testNameEndingWithUnderscore(){
        String program = "MY_COUNTRIES_I_:\n COUNTRY1_, COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,2);
    }


    @Test
    public void testWeirdCharacters(){
        String program = "COUNTRIES_I:\n WEIRD_È, COUNTRY2~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,2);
    }

    @Test
    public void testBeginsWithNumber(){
        String program = "COUNTRIES_I: \"1_COUNTRY\", COUNTRY2~|\n" +
                         "ANOTHER_I: \"1COUNTRY\"~|";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,1);
        assertHasIssue(visitorContext,SubscriptValueNameCheck.class,2);

    }

    @Test
    public void testMultipleDefinitionCreateDifferentIssues() {

        String program = "MY_COUNTRIES_I: a_country, COUNTRY2~|\n"+
                "OTHER_COUNTRIES_I: a_country, COUNTRY2~|\n";

        VensimVisitorContext visitorContext = getVisitorContextFromString(program);
        VensimScanner scanner = getScanner();


        scanner.checkIssues(visitorContext);
        assertHasIssue(visitorContext, SubscriptValueNameCheck.class, 1);
        assertHasIssue(visitorContext, SubscriptValueNameCheck.class, 2);
    }

}
