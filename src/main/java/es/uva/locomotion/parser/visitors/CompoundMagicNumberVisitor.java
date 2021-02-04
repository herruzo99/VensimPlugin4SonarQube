package es.uva.locomotion.parser.visitors;

import es.uva.locomotion.parser.ModelParser;
import es.uva.locomotion.parser.ModelParserBaseVisitor;
import es.uva.locomotion.utilities.Constants;

/**
 * From the outside you should only call 'callIsACompoundNumber'. The other methods can't be protected/private
 * due to inheritance.
 */
public class CompoundMagicNumberVisitor extends ModelParserBaseVisitor<Object> {


    /**
     * Returns True if the call is a compound number
     *
     * To be considered a compound number, the call must meet the following conditions:
     *        - The function called must be included in Constants.FUNCTIONS_THAT_FORM_COMPOUND_MAGIC_NUMBERS
     *        - It doesn't contain identifiers (neither constants nor variables).
     *        - If there are nested calls, every function called must be included in Constants.FUNCTIONS_THAT_FORM_COMPOUND_MAGIC_NUMBERS
     *        - It doesn't contain wildcards
     */
    public boolean callIsACompoundNumber(ModelParser.CallContext call){
        return visitCall(call);
    }


    @Override
    public Boolean visitExprList(ModelParser.ExprListContext ctx) {
        for(ModelParser.ExprContext expr: ctx.expr()){
            if(! (boolean) this.visit(expr))
                return false;
        }

        return true;
    }


    @Override
    public Boolean visitExprOperation(ModelParser.ExprOperationContext ctx) {
        return (boolean) visit(ctx.expr(0)) && (boolean) visit(ctx.expr(1));
    }

    @Override
    public Object visitConstVensim(ModelParser.ConstVensimContext ctx) {
        return ctx.StringConst()==null;
    }

    @Override
    public Object visitKeyword(ModelParser.KeywordContext ctx) {
        if(ctx.expr()==null)
            return true;

        return visit(ctx.expr());
    }

    @Override
    public Object visitLookup(ModelParser.LookupContext ctx) {
        return false;
    }



    @Override
    public Boolean visitCall(ModelParser.CallContext ctx) {
        String funcName = ctx.Id().getText();
        return Constants.FUNCTIONS_THAT_FORM_COMPOUND_MAGIC_NUMBERS.contains(funcName) && visitExprList(ctx.exprList());
    }



    @Override
    public Boolean visitWildCard(ModelParser.WildCardContext ctx) {
        return false;
    }

    @Override
    public Object visitSignExpr(ModelParser.SignExprContext ctx) {
        if(ctx.exprAllowSign() instanceof ModelParser.ParensContext) {
            ModelParser.ParensContext parenth = (ModelParser.ParensContext) ctx.exprAllowSign();
            return visit(parenth.expr());

        }else if(ctx.exprAllowSign() instanceof ModelParser.CallExprContext)
            return visit(ctx.exprAllowSign());

        return false;
    }

}
