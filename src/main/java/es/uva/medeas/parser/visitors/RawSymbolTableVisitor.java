package es.uva.medeas.parser.visitors;

import es.uva.medeas.parser.*;

import org.antlr.v4.runtime.ParserRuleContext;



import java.util.*;


public class RawSymbolTableVisitor extends ModelBaseVisitor {
    //TODO add javadocs for RawSymbolTableVisitor y SymbolTableGenerator

    private SymbolTable table;

    public SymbolTable getSymbolTable(ModelParser.FileContext context){
        table = new SymbolTable();
        visit(context);

        return table;
    }


    private int getStartLine(ParserRuleContext context){
        return context.start.getLine();
    }

    private Symbol getSymbolOrCreate(SymbolTable table,String token){
        if(table.hasSymbol(token))
            return table.getSymbol(token);

        else{
            return table.addSymbol(new Symbol(token));
        }
    }

    @Override
    public Symbol visitSubscriptRange(ModelParser.SubscriptRangeContext ctx) {
        Symbol symbol = getSymbolOrCreate(table,ctx.Id().getSymbol().getText());
        symbol.addDefinitionLine(getStartLine(ctx));
        symbol.setType(SymbolType.SUBSCRIPT_NAME);


        if(ctx.subscriptIdList()!=null) {
            for(ModelParser.SubscriptIdContext value:ctx.subscriptIdList().subscriptId()){
                Symbol subscriptValue = visitSubscriptId(value);
                subscriptValue.setType(SymbolType.SUBSCRIPT_VALUE);
                subscriptValue.addDefinitionLine(getStartLine(value));

            }
            for(ModelParser.SubscriptSequenceContext sequence:ctx.subscriptIdList().subscriptSequence())
                visitSubscriptSequence(sequence);
        }
        if(ctx.call()!=null)
            symbol.addDependencies(visitCall(ctx.call()));

        return symbol;
    }


    @Override
    public Symbol visitEquation(ModelParser.EquationContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.addDefinitionLine(getStartLine(ctx));


        if(ctx.expr()!=null)
            symbol.addDependencies( (List<Symbol>) visit(ctx.expr()));

        return symbol;
    }


    @Override
    public Symbol visitConstraint(ModelParser.ConstraintContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.setType(SymbolType.REALITY_CHECK);
        symbol.addDefinitionLine(getStartLine(ctx));

        return symbol;
    }

    @Override
    public Object visitMacroDefinition(ModelParser.MacroDefinitionContext ctx) {
        Symbol symbol = getSymbolOrCreate(table,ctx.macroHeader().Id().getSymbol().getText());
        symbol.setType(SymbolType.FUNCTION);
        symbol.addDefinitionLine(getStartLine(ctx));

        return super.visitMacroDefinition(ctx);
    }

    @Override
    public Symbol visitUnchangeableConstant(ModelParser.UnchangeableConstantContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.setType(SymbolType.CONSTANT);
        symbol.addDefinitionLine(getStartLine(ctx));
        return symbol;
    }

    @Override
    public Symbol visitDataEquation(ModelParser.DataEquationContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.addDefinitionLine(getStartLine(ctx));

        if(ctx.expr()!=null)
            symbol.addDependencies( (List<Symbol>) visit(ctx.expr()));

        return symbol;
    }

    @Override
    public Symbol visitLookupDefinition(ModelParser.LookupDefinitionContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.addDefinitionLine(getStartLine(ctx));
        symbol.setType(SymbolType.LOOKUP);

        if (ctx.lookup()!=null)
            symbol.addDependencies(  visitLookup(ctx.lookup()));
        else if (ctx.call()!=null)
            symbol.addDependencies(visitCall(ctx.call()));


        return symbol;
    }

    @Override
    public Symbol visitStringAssign(ModelParser.StringAssignContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.setType(SymbolType.CONSTANT);
        symbol.addDefinitionLine(getStartLine(ctx));

        return symbol;
    }


    @Override
    public Symbol visitSubscriptCopy(ModelParser.SubscriptCopyContext ctx) {

        Symbol symbol = getSymbolOrCreate(table,ctx.Id(0).getSymbol().getText());
        symbol.setType(SymbolType.SUBSCRIPT_NAME);
        symbol.addDefinitionLine(getStartLine(ctx));

        return symbol;
    }

    @Override
    public Symbol visitRealityCheck(ModelParser.RealityCheckContext ctx) {
        Symbol symbol = visitLhs(ctx.lhs());
        symbol.setType(SymbolType.REALITY_CHECK);
        symbol.addDefinitionLine(getStartLine(ctx));

        return symbol;

    }



    @Override
    public Symbol visitSubscriptId(ModelParser.SubscriptIdContext ctx) {
        return getSymbolOrCreate(table,ctx.Id().getSymbol().getText());
    }

    @Override
    public List<Symbol> visitSubscriptSequence(ModelParser.SubscriptSequenceContext ctx) {
        Symbol firstSymbol = getSymbolOrCreate(table,ctx.Id(0).getSymbol().getText());
        Symbol secondSymbol = getSymbolOrCreate(table,ctx.Id(1).getSymbol().getText());

        firstSymbol.setType(SymbolType.SUBSCRIPT_VALUE);
        firstSymbol.addDefinitionLine(getStartLine(ctx));
        secondSymbol.setType(SymbolType.SUBSCRIPT_VALUE);
        secondSymbol.addDefinitionLine(getStartLine(ctx));

        return Arrays.asList(firstSymbol,secondSymbol);

    }








    @Override
    public Object visitExprOperation(ModelParser.ExprOperationContext ctx) {
        List<Symbol> symbols = (List<Symbol>) visit(ctx.expr(0));
        symbols.addAll((List<Symbol>) visit(ctx.expr(1)));

        return  symbols;
    }



    @Override
    public Object visitVar(ModelParser.VarContext ctx) {
        Symbol id = getSymbolOrCreate(table,ctx.Id().getSymbol().getText());

        List<Symbol> symbols = new ArrayList<>();
        symbols.add(id);

        return symbols;
    }

    @Override
    public Object visitConst(ModelParser.ConstContext ctx) {
        return new ArrayList<Symbol>();
    }

    @Override
    public List<Symbol> visitKeyword(ModelParser.KeywordContext ctx) {
        if (ctx.expr()!=null)
            return (List<Symbol>) visit(ctx.expr());
        else
            return new ArrayList<>();
    }


    @Override
    public Object visitParens(ModelParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitWildCard(ModelParser.WildCardContext ctx) {
        return new ArrayList<Symbol>();
    }


    @Override
    public Object visitDelayPArg(ModelParser.DelayPArgContext ctx) {
        List<Symbol> symbols = new ArrayList<>();
        Symbol delayP = getSymbolOrCreate(table,"DELAYP");
        List<Symbol> input = (List<Symbol>) visit(ctx.expr(0));
        List<Symbol> delayTime = (List<Symbol>) visit(ctx.expr(1));
        Symbol pipeline = getSymbolOrCreate(table,ctx.Id().getSymbol().getText());
        pipeline.setType(SymbolType.VARIABLE);
        pipeline.addDefinitionLine(ctx.Id().getSymbol().getLine());


        symbols.add(delayP);
        symbols.addAll(input);
        symbols.addAll(delayTime);

        pipeline.addDependencies(symbols);

        symbols.add(pipeline);
        return  symbols;
    }

    @Override
    public Object visitTabbedArray(ModelParser.TabbedArrayContext ctx) {
        List<Symbol> symbols = new ArrayList<>();
        Symbol tabbedFunc = getSymbolOrCreate(table,"TABBED ARRAY");

        symbols.add(tabbedFunc);
        return symbols;
    }

    @Override
    public Object visitSignExpr(ModelParser.SignExprContext ctx) {
        return visit(ctx.exprAllowSign());

    }


    @Override
    public List<Symbol> visitCall(ModelParser.CallContext ctx) {
        String token = ctx.Id().getSymbol().getText();
        Symbol call;

        if (table.hasSymbol(token))
            call = table.getSymbol(token);
        else{
            call = table.addSymbol(new Symbol(token));
            call.setType(SymbolType.UNDETERMINED_FUNCTION);  //TODO hacer un builder o un método que se encargue de crear los símbolos por si necesito cambiar el tipo de todos los simbolos de golpe
        }


        List<Symbol> symbols;
        if(ctx.exprList()!=null)
             symbols =  (List<Symbol>) visit(ctx.exprList());
        else
            symbols = new ArrayList<>();

        symbols.add(call);
        return symbols;
    }




    @Override
    public Object visitExprList(ModelParser.ExprListContext ctx) {
        List<Symbol> symbols = new ArrayList<>();
        for(ModelParser.ExprContext expr: ctx.expr()){
            symbols.addAll((List<Symbol>)visit(expr));
        }
        return symbols;
    }

    @Override
    public List<Symbol> visitSubscriptIdList(ModelParser.SubscriptIdListContext ctx) {
        List<Symbol> symbols = new ArrayList<>();


        if(!ctx.subscriptId().isEmpty()){
            for(ModelParser.SubscriptIdContext subscript: ctx.subscriptId()){
                symbols.add( visitSubscriptId(subscript));
            }
        }

        if(!ctx.subscriptSequence().isEmpty()){
            for(ModelParser.SubscriptSequenceContext sequence: ctx.subscriptSequence()){
                symbols.addAll(visitSubscriptSequence(sequence));
            }
        }
        return symbols;
    }


    @Override
    public List<Symbol> visitLookup(ModelParser.LookupContext ctx) {
        return new ArrayList<>();
    }


    @Override
    public List<Symbol> visitLookupPoint(ModelParser.LookupPointContext ctx) {

        return  new ArrayList<>();
    }

    @Override
    public List<Symbol> visitConstList(ModelParser.ConstListContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public List<Symbol> visitLookupPointList(ModelParser.LookupPointListContext ctx) {
        List<Symbol> symbols = new ArrayList<>();


        for(ModelParser.LookupPointContext point:ctx.lookupPoint()){
            symbols.addAll(visitLookupPoint(point));
        }



        return symbols;
    }

    @Override
    public List<Symbol> visitLookupRange(ModelParser.LookupRangeContext ctx) {
        List<Symbol> symbols = new ArrayList<>();

        symbols.addAll(visitLookupPoint(ctx.lookupPoint(0)));
        symbols.addAll(visitLookupPoint(ctx.lookupPoint(1)));

        if (ctx.referenceLine()!=null)
            symbols.addAll(visitReferenceLine(ctx.referenceLine()));

       return symbols;
    }


    @Override
    public List<Symbol> visitReferenceLine(ModelParser.ReferenceLineContext ctx) {
        return visitLookupPointList(ctx.lookupPointList());
    }

    @Override
    public List<Symbol> visitNumberList(ModelParser.NumberListContext ctx) {
        return new ArrayList<>();
    }


    @Override
    public Symbol visitSymbolWithDoc(ModelParser.SymbolWithDocContext ctx) {
        Object symbolObj = visitSymbolWithDocDefinition(ctx.symbolWithDocDefinition());
        if(!(symbolObj instanceof Symbol))
            throw new IllegalStateException("The visitor returned an object that isn't of type Symbol. Actual object: "+ symbolObj);

        Symbol symbol = (Symbol) symbolObj;

        String comment = ctx.unitsDoc().comment.getText().substring(1);
        String units = ctx.unitsDoc().units.getText().substring(1);

        if(!comment.isBlank())
            symbol.setComment(comment);

        if(!units.isBlank())
            symbol.setUnits(units);


        return symbol;
    }

    @Override
    public Symbol visitLhs(ModelParser.LhsContext ctx) {
       Symbol id =  getSymbolOrCreate(table,ctx.Id().getText());

       if(ctx.indexes!=null) {
           List<Symbol> indexes = visitSubscript(ctx.indexes);
           id.addIndexLine(indexes);
       }

        return id;
    }

    @Override
    public List<Symbol> visitSubscript(ModelParser.SubscriptContext ctx) {
        return  visitSubscriptIdList(ctx.subscriptIdList());
    }




}
