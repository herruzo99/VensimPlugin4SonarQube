package es.uva.locomotion.rules;


import es.uva.locomotion.model.ExcelRef;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.model.symbol.SymbolType;
import es.uva.locomotion.parser.visitors.VensimVisitorContext;
import es.uva.locomotion.plugin.Issue;
import es.uva.locomotion.utilities.Constants;
import es.uva.locomotion.utilities.logs.VensimLogger;
import org.sonar.check.Rule;

import java.util.List;


@Rule(key = DictionarySymbolExcelRefCheck.CHECK_KEY, name = DictionarySymbolExcelRefCheck.NAME, description = DictionarySymbolExcelRefCheck.HTML_DESCRIPTION)
public class DictionarySymbolExcelRefCheck extends AbstractVensimCheck {
    protected static final VensimLogger LOG = VensimLogger.getInstance();

    public static final String CHECK_KEY = "dictionary-symbol-excel-missmatch";
    public static final String NAME = "DictionarySymbolExcelRefCheck";
    public static final String HTML_DESCRIPTION = "" + //TODO
            "<p>TODO</p>\n";


    @Override
    public void scan(VensimVisitorContext context) {
        SymbolTable parsedTable = context.getParsedSymbolTable();
        SymbolTable dbTable = context.getDbSymbolTable();

        if(dbTable!=null)
            checkSymbolsExcel(context, parsedTable, dbTable);
    }

    private void checkSymbolsExcel(VensimVisitorContext context, SymbolTable parsedTable, SymbolTable dbTable) {
        for(Symbol foundSymbol: parsedTable.getSymbols()){
            if(raisesIssue(foundSymbol,dbTable)){

                foundSymbol.setAsInvalid(this.getClass().getSimpleName());
                List<ExcelRef> expectedExcel = dbTable.getSymbol(foundSymbol.getToken()).getExcel();

                for(int line: foundSymbol.getLines()) {
                    Issue issue = new Issue(this, line,"The symbol '"+ foundSymbol.getToken() + "' has a excel references: '"+foundSymbol.getExcel() + "' but the dictionary has '"+ expectedExcel + "'." );
                    addIssue(context,issue,foundSymbol.isFiltered());

                }
            }
        }
    }

    private boolean raisesIssue(Symbol foundSymbol, SymbolTable dbTable) {
        if(symbolIsIgnored(foundSymbol))
            return false;

        if(!dbTable.hasSymbol(foundSymbol.getToken()))
            return false;

        List<ExcelRef> dbExcel = dbTable.getSymbol(foundSymbol.getToken()).getExcel();
        List<ExcelRef> fileExcel = foundSymbol.getExcel();
        return fileExcel != null  && !fileExcel.equals(dbExcel);

    }

    private boolean symbolIsIgnored(Symbol symbol){
        return symbol.getType()== SymbolType.FUNCTION || Constants.DEFAULT_VENSIM_SYMBOLS.contains(symbol.getToken().trim());
    }
}
