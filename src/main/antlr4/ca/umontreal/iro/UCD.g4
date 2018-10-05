grammar UCD;

model : 'MODEL' ID declaration* ;

declaration : ( classDecl | association | aggregation | generalization ) ';' ;

classDecl : 'CLASS' ID 'ATTRIBUTES' (attribute (',' attribute)*)?
                       'OPERATIONS' (operation (',' operation)*)? ;

attribute : ID ':' type ;

operation : ID '(' (argument (',' argument)*)? ')' ':' type ;

argument : ID ':' type ;

type : ID ;

association : 'RELATION' ID 'ROLES' role ',' role ;

aggregation : 'AGGREGATION' 'CONTAINER' container=role
                            'PARTS' part (',' part)* ;

part : role ;

role : 'CLASS' ID multiplicity ;

multiplicity
    : 'ONE'
    | 'MANY'
    | 'ONE_OR_MANY'
    | 'OPTIONALLY_ONE'
    | 'UNDEFINED'
    ;

generalization : 'GENERALIZATION' ID 'SUBCLASSES' subclass (',' subclass)* ;

subclass : ID ;

ID : [a-zA-Z_]+ ;

WS : [ \r\t\n]+ -> skip ;
