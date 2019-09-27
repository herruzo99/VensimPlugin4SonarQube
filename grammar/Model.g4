grammar Model;

import Expr;

// A Vensim model is a sequence of equations and subscript ranges.
model: ( subscriptRange | equation |
      lookupCallEquation|constraint | macroDefinition | unchangeableConstant |
       dataEquation| lookupDefinition | stringAssign )+ sketchInfo+ EOF ;

// A subscript range definition names subscripts in a dimension.
subscriptRange : Id ':' ( subscriptIdList | subscriptSequence ) subscriptMappingList? unitsDoc;
subscriptSequence : '(' Id '-' Id ')' ;
subscriptMappingList : '->' subscriptMapping ( ',' subscriptMapping )* ;
subscriptMapping : Id | '(' Id ':' subscriptIdList ')' ;

// An equation has a left-hand side and a right-hand side.
// The RHS is a formula expression, a constant list, or a Vensim lookup.
// The RHS is empty for data equations.
equation : lhs  Equal (expr|constList)  (':IGNORE:' exprList)? unitsDoc;
lhs : Id ( subscript )? Keyword? ( ':EXCEPT:' subscript ( ',' subscript )* )? ;


unchangeableConstant: lhs TwoEqual ( expr | constList ) unitsDoc;
dataEquation: lhs ( EquationOp ( expr | constList ) )? (':IGNORE:' exprList)? unitsDoc;
lookupDefinition: lhs lookup unitsDoc;
constraint: Id ':THE CONDITION:' expr? ':IMPLIES:' expr unitsDoc;
stringAssign: lhs StringAssignOp StringConst  (':IGNORE:' exprList)? unitsDoc;
macroDefinition: ':MACRO:' macroHeader equation+ ':END OF MACRO:' unitsDoc;
lookupCallEquation: lookupCall unitsDoc;



// The lexer strips some tokens we are not interested in.
// The character encoding is given at the start of a Vensim file.
// The units and documentation sections and group markings are skipped for now.
// Line continuation characters and the sketch must be stripped by a preprocessor.
Encoding : '{' [A-Za-z0-9-]+ '}' -> skip ;

Group : '****' .*? '|' -> skip ;


// Backslash tokens are ignored in Expr.g4, so this rule doesn't take them into account.
sketchInfo: '---///' 'Sketch information - do not modify anything except names' .* ;
