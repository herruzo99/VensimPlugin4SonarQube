package es.uva.medeas.rules;


import es.uva.medeas.parser.Symbol;
import es.uva.medeas.parser.SymbolTable;
import es.uva.medeas.parser.SymbolType;
import es.uva.medeas.plugin.Issue;
import es.uva.medeas.plugin.VensimVisitorContext;
import org.sonar.check.Rule;

import java.util.Arrays;
import java.util.List;

@Rule(key= SymbolWithoutUnitsCheck.CHECK_KEY, name = SymbolWithoutUnitsCheck.NAME, description = SymbolWithoutUnitsCheck.HTML_DESCRIPTION)
public class SymbolWithoutUnitsCheck implements VensimCheck{

    public static final String CHECK_KEY = "symbol-without-units" ;
    public static final String HTML_DESCRIPTION = "" +
            "<p>This rule checks that all the symbols have units. The only exceptions are macros/functions and subscript values. " +
            "<h2>Noncompliant Code Examples</h2>\n" +
            "<pre>\n" +
            "materials to extract for EV batteries Mt =\n" +
            "materials required for EV batteries *(1-recycling rates minerals alt tech) ~ ~ Annual materials to be mined\n for the construction of EV batteries  |"+
            "</pre>\n" +
            "<h2>Compliant Solution</h2>\n" +
            "<pre>\n" +
            "materials to extract for EV batteries Mt =\n" +
            "materials required for EV batteries *(1-recycling rates minerals alt tech) "+
            "~ Mt/Year \n~ Annual materials to be mined for the construction of EV batteries |"+
            "</pre>\n";

    public static final String NAME = "SymbolWithoutUnits" ;

    private List<SymbolType> IGNORED_TYPES = Arrays.asList(SymbolType.FUNCTION,SymbolType.SUBSCRIPT_VALUE);

    @Override
    public void scan(VensimVisitorContext context) {
        SymbolTable table = context.getParsedSymbolTable();

        for(Symbol symbol: table.getSymbols()){
            if(!IGNORED_TYPES.contains(symbol.getType()) && symbol.getUnits().isBlank()){
                for(int line: symbol.getDefinitionLines()) {
                    Issue issue = new Issue(this,line,"The symbol '"+ symbol.getToken() + "' should have units.");
                    context.addIssue(issue);
                }
            }
        }
    }
}
