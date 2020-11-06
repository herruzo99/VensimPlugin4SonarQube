// Generated from Model.g4 by ANTLR 4.7.2
package es.uva.locomotion.parser;
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
	 * Visit a parse tree produced by {@link ModelParser#sketchesGraphsAndMetadata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketchesGraphsAndMetadata(ModelParser.SketchesGraphsAndMetadataContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#symbolWithDoc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbolWithDoc(ModelParser.SymbolWithDocContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#symbolWithDocDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbolWithDocDefinition(ModelParser.SymbolWithDocDefinitionContext ctx);
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
	 * Visit a parse tree produced by the {@code CallExpr}
	 * labeled alternative in {@link ModelParser#exprAllowSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(ModelParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DelayPArg}
	 * labeled alternative in {@link ModelParser#exprAllowSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelayPArg(ModelParser.DelayPArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link ModelParser#exprAllowSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(ModelParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tabbedArray}
	 * labeled alternative in {@link ModelParser#exprAllowSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTabbedArray(ModelParser.TabbedArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link ModelParser#exprAllowSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(ModelParser.ParensContext ctx);
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
	 * Visit a parse tree produced by {@link ModelParser#subscriptValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptValueList(ModelParser.SubscriptValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#indexList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexList(ModelParser.IndexListContext ctx);
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
	 * Visit a parse tree produced by {@link ModelParser#constantLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantLine(ModelParser.ConstantLineContext ctx);
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
	 * Visit a parse tree produced by {@link ModelParser#unitsDoc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitsDoc(ModelParser.UnitsDocContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#graphsGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphsGroup(ModelParser.GraphsGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#graphs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphs(ModelParser.GraphsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#graph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraph(ModelParser.GraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#title}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTitle(ModelParser.TitleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#xaxis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXaxis(ModelParser.XaxisContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#xlabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXlabel(ModelParser.XlabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#xdiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXdiv(ModelParser.XdivContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#yaxis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYaxis(ModelParser.YaxisContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#ylabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYlabel(ModelParser.YlabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#ydiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYdiv(ModelParser.YdivContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#xmin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXmin(ModelParser.XminContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#xmax}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXmax(ModelParser.XmaxContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#nolegend}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNolegend(ModelParser.NolegendContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#scale}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScale(ModelParser.ScaleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#graphvar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphvar(ModelParser.GraphvarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#gvar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGvar(ModelParser.GvarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#ymin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYmin(ModelParser.YminContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#ymax}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYmax(ModelParser.YmaxContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#linewidthgraph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinewidthgraph(ModelParser.LinewidthgraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#metadataDivisor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMetadataDivisor(ModelParser.MetadataDivisorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#metadataLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMetadataLine(ModelParser.MetadataLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sketches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketches(ModelParser.SketchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sketchesDelimiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketchesDelimiter(ModelParser.SketchesDelimiterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#viewInfo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewInfo(ModelParser.ViewInfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#sketchInfo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSketchInfo(ModelParser.SketchInfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#versionCode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersionCode(ModelParser.VersionCodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#viewName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewName(ModelParser.ViewNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#viewSettings}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewSettings(ModelParser.ViewSettingsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#viewVariables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewVariables(ModelParser.ViewVariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#arrow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrow(ModelParser.ArrowContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#arrowCoordinates}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrowCoordinates(ModelParser.ArrowCoordinatesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#viewVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewVariable(ModelParser.ViewVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#visualInfo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVisualInfo(ModelParser.VisualInfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#typography}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypography(ModelParser.TypographyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#typographyName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypographyName(ModelParser.TypographyNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#color}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColor(ModelParser.ColorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#rgbColor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRgbColor(ModelParser.RgbColorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#singleColor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleColor(ModelParser.SingleColorContext ctx);
}