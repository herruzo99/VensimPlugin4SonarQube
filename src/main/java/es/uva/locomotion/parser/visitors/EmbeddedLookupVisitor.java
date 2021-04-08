package es.uva.locomotion.parser.visitors;

import com.ibm.icu.impl.Pair;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.parser.ModelParser;
import es.uva.locomotion.parser.ModelParserBaseVisitor;
import es.uva.locomotion.utilities.logs.LoggingLevel;
import es.uva.locomotion.utilities.logs.VensimLogger;

import java.util.ArrayList;
import java.util.List;

public class EmbeddedLookupVisitor extends ModelParserBaseVisitor<Void> {

    protected static  VensimLogger logger = VensimLogger.getInstance();

    private List<Pair<Symbol, Integer>> lookupsTable;

    private SymbolTable symbols;
    private boolean isSymbolFiltered;


    public EmbeddedLookupVisitor() {
        lookupsTable = new ArrayList<>();
        isSymbolFiltered = false;
    }

    public void setSymbols(SymbolTable symbols) {
        this.symbols = symbols;
    }

    @Override
    public Void visitLhs(ModelParser.LhsContext ctx) {

        if (symbols == null) {
            logger.unique("Symbol table unassigned in EmbeddedLookupVisitor", LoggingLevel.INFO);

        }
        else if (!symbols.hasSymbol(ctx.Id().getText())) {
            logger.error("Found symbol \"" + ctx.Id().getText() + "\" that is not in the symbol table");
        }
        else {
            Symbol symbol = symbols.getSymbol(ctx.Id().getText());
            isSymbolFiltered = symbol.isFiltered();
        }
        return null;

    }

    public List<Pair<Symbol, Integer>> getSymbolTable(ModelParser.FileContext context) {
        lookupsTable = new ArrayList<>();
        visit(context);
        return lookupsTable;
    }


    @Override
    public Void visitLookupPointList(ModelParser.LookupPointListContext ctx) {
        int size = ctx.lookupPoint().size();
        Symbol lookup = new Symbol("not used");
        lookup.addLine(ctx.start.getLine());
        lookup.setFiltered(isSymbolFiltered);
        lookupsTable.add(Pair.of(lookup, size));

        return null;
    }

    @Override
    public Void visitNumberList(ModelParser.NumberListContext ctx) {

        int size = ctx.integerConst().size() + ctx.floatingConst().size();
        Symbol lookup = new Symbol("not used");
        lookup.addLine(ctx.start.getLine());
        lookup.setFiltered(isSymbolFiltered);
        lookupsTable.add(Pair.of(lookup, size));

        return null;

    }

}
