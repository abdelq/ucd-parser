grammar UCD;

model : 'MODEL' ID declaration* ;

declaration : ( classDecl | association | aggregation | generalization ) ';' ;

classDecl : 'CLASS' ID 'ATTRIBUTES' attributes? 'OPERATIONS' operations? ;

attributes : attribute=dataItem (',' attribute=dataItem)* ;

operations : operation (',' operation)* ;

operation : ID '(' arguments? ')' ':' type ;

arguments : argument=dataItem (',' argument=dataItem)* ;

dataItem : ID ':' type ;

type : ID ;

association : 'RELATION' ID 'ROLES' role ',' role ;

aggregation : 'AGGREGATION' 'CONTAINER' container=role 'PARTS' parts ;

parts : part=role (',' part=role)* ;

role : 'CLASS' ID multiplicity ;

multiplicity
    : 'ONE'
    | 'MANY'
    | 'ONE_OR_MANY'
    | 'OPTIONALLY_ONE'
    | 'UNDEFINED'
    ;

generalization : 'GENERALIZATION' ID 'SUBCLASSES' subclasses ;

subclasses : subclass=ID (',' subclass=ID)* ;

ID : [a-zA-Z0-9_]+ ;

WS : [ \r\t\n]+ -> skip ;
