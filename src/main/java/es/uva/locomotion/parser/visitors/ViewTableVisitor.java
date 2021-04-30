package es.uva.locomotion.parser.visitors;

import es.uva.locomotion.model.View;
import es.uva.locomotion.model.ViewTable;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.parser.ModelParser;
import es.uva.locomotion.parser.ModelParserBaseVisitor;
import es.uva.locomotion.utilities.logs.VensimLogger;

import java.util.regex.Pattern;


public class ViewTableVisitor extends ModelParserBaseVisitor<Object> {
    private ViewTable table;
    private SymbolTable symbolTable;
    private View actualView;

    private String moduleSeparator;
    private String categorySeparator;

    protected static VensimLogger logger = VensimLogger.getInstance();

    private static final int SYMBOL_PRIVATE_ID = 10;

    private ViewTableVisitor() {
    }

    public static ViewTableVisitor createViewTableVisitor(String moduleSeparator, String categorySeparator) {
        ViewTableVisitor vtv = new ViewTableVisitor();
        vtv.moduleSeparator = moduleSeparator;
        vtv.categorySeparator = categorySeparator;
        return vtv;
    }

    public static ViewTableVisitor createViewTableVisitor(String moduleSeparator) {
        ViewTableVisitor vtv = new ViewTableVisitor();
        vtv.moduleSeparator = moduleSeparator;
        vtv.categorySeparator = null;
        return vtv;
    }

    public static ViewTableVisitor createViewTableVisitor() {
        ViewTableVisitor vtv = new ViewTableVisitor();
        vtv.moduleSeparator = null;
        vtv.categorySeparator = null;
        return vtv;
    }

    public ViewTable getViewTable(ModelParser.FileContext context) {
        table = new ViewTable();
        visit(context);
        return table;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    private boolean isEven(int num) {
        return num % 2 == 0;

    }

    @Override
    public Object visitViewName(ModelParser.ViewNameContext ctx) {
        String viewName = ctx.getText().trim().substring(1);
        String module = viewName;
        String category = null;
        String subcategory = null;

        int line = ctx.getStart().getLine();

        if (moduleSeparator != null) {
            String[] aux = viewName.split(Pattern.quote(moduleSeparator));

            if (aux.length == 1) {
                module = viewName;
                actualView = table.createOrSelectView(module);
            } else if(aux.length == 2 ) {
                module = aux[0];
                category = aux[1];
                String categoryAndSubcategory = aux[1];

                if (categorySeparator != null) { // Tiene categoría y subcategoría
                    aux = categoryAndSubcategory.split(Pattern.quote(categorySeparator));
                    if (aux.length == 2) {
                        category = aux[0];
                        subcategory = aux[1];
                        actualView = table.createOrSelectView(module, category, subcategory);
                    } else if (aux.length == 1) { //Solo tiene categoría, no subcategoría
                        category = categoryAndSubcategory;
                        actualView = table.createOrSelectView(module, category);
                    } else{ //No concuerda con la sintaxis
                        category = categoryAndSubcategory;
                        actualView = table.createOrSelectView(module, category, null);
                    }

                }else{
                    actualView = table.createOrSelectView(module, null);
                }
            }else{
                actualView = table.createOrSelectView(module, null);

            }
        }else{
            actualView = table.createOrSelectView(module);
        }


        actualView.addLine(line);
        return super.visitViewName(ctx);
    }

    @Override
    public Object visitViewVariable(ModelParser.ViewVariableContext ctx) {

        int internalId = Integer.parseInt(ctx.internalId.getText());
        if (internalId == SYMBOL_PRIVATE_ID) {
            int objectType = Integer.parseInt(ctx.bits.getText());
            String token = ctx.name.getText();
            String underScoreToken = token.replace("_", " ");
            String whitespaceScoreToken = token.replace(" ", "_");
            Symbol actualSymbol = symbolTable.getSymbol(underScoreToken);
            if(actualSymbol == null)
                actualSymbol = symbolTable.getSymbol(token);
            if(actualSymbol == null)
                actualSymbol = symbolTable.getSymbol(whitespaceScoreToken);
            
            if (actualSymbol != null) {
                if (isEven(objectType)) {
                    actualView.addShadow(actualSymbol);
                } else {
                    actualView.addPrimary(actualSymbol);
                }
            }
        }
        return super.visitViewVariable(ctx);
    }
}
