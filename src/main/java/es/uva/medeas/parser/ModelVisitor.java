// Generated from Model.g4 by ANTLR 4.7.2
package es.uva.medeas.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ModelParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ModelVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ModelParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(ModelParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel(ModelParser.ModelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptRange(ModelParser.SubscriptRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptSequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptSequence(ModelParser.SubscriptSequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptMappingList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptMappingList(ModelParser.SubscriptMappingListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptMapping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptMapping(ModelParser.SubscriptMappingContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(ModelParser.EquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(ModelParser.LhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptCopy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptCopy(ModelParser.SubscriptCopyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#unchangeableConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnchangeableConstant(ModelParser.UnchangeableConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#dataEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataEquation(ModelParser.DataEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lookupDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupDefinition(ModelParser.LookupDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(ModelParser.ConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#realityCheck}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealityCheck(ModelParser.RealityCheckContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#stringAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringAssign(ModelParser.StringAssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#macroDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroDefinition(ModelParser.MacroDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sketchInfo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketchInfo(ModelParser.SketchInfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sketches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketches(ModelParser.SketchesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DelayPArg}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelayPArg(ModelParser.DelayPArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tabbedArray}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTabbedArray(ModelParser.TabbedArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code const}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConst(ModelParser.ConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Keyword}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword(ModelParser.KeywordContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprOperation}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOperation(ModelParser.ExprOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code signExpr}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignExpr(ModelParser.SignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(ModelParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(ModelParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallExpr}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(ModelParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WildCard}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildCard(ModelParser.WildCardContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LookupArg}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupArg(ModelParser.LookupArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link ModelParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(ModelParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(ModelParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#macroHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroHeader(ModelParser.MacroHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#macroArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroArguments(ModelParser.MacroArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(ModelParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptIdList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptIdList(ModelParser.SubscriptIdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscript}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript(ModelParser.SubscriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lookup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookup(ModelParser.LookupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lookupRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupRange(ModelParser.LookupRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lookupPointList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupPointList(ModelParser.LookupPointListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#referenceLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceLine(ModelParser.ReferenceLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#lookupPoint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupPoint(ModelParser.LookupPointContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#constList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstList(ModelParser.ConstListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#numberList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberList(ModelParser.NumberListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#subscriptId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptId(ModelParser.SubscriptIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#constVensim}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstVensim(ModelParser.ConstVensimContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#integerConst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerConst(ModelParser.IntegerConstContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#floatingConst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingConst(ModelParser.FloatingConstContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#fractionalConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFractionalConstant(ModelParser.FractionalConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#exponentPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExponentPart(ModelParser.ExponentPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSign(ModelParser.SignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#unitsDoc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitsDoc(ModelParser.UnitsDocContext ctx);
}