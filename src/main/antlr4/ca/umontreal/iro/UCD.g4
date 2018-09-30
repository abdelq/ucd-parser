grammar UCD;

model : 'MODEL' ID declaration* ;

declaration : ( classDecl | association | aggregation | generalization ) ';' ;

classDecl : 'CLASS' ID 'ATTRIBUTES' (dataItem (',' dataItem)*)?
                       'OPERATIONS' (operation (',' operation)*)? ;

operation : ID '(' (dataItem (',' dataItem)*)? ')' ':' type ;

dataItem : ID ':' type ;

type : ID ;

association : 'RELATION' ID 'ROLES' role ',' role ;

aggregation : 'AGGREGATION' 'CONTAINER' container=role 'PARTS' parts ;

parts : role (',' role)* ;

role : 'CLASS' ID multiplicity ;

multiplicity
    : 'ONE'
    | 'MANY'
    | 'ONE_OR_MANY'
    | 'OPTIONALLY_ONE'
    | 'UNDEFINED'
    ;

generalization : 'GENERALIZATION' ID 'SUBCLASSES' subclasses ;

subclasses : ID (',' ID)* ;

ID : [a-zA-Z_]+ ;

WS : [ \r\t\n]+ -> skip ;
