grammar UCD;

model : 'MODEL' Identifier list_dec ;

list_dec : declaration* ;

declaration : (class_dec | association | generalization | aggregation) ';' ;

class_dec : 'CLASS' Identifier class_content ;

class_content : 'ATTRIBUTES' attribute_list 'OPERATIONS' operation_list ;

attribute_list : (data_item (',' data_item)*)? ;

data_item : Identifier ':' type ;

operation_list : (operation (',' operation)*)? ;

operation : Identifier arg_declaration ':' type ;

arg_declaration : '(' arg_list ')' ;

arg_list : (data_item (',' data_item)*)? ;

type : Identifier ;

association : 'RELATION' Identifier 'ROLES' two_roles ;

two_roles : role ',' role ;

role : 'CLASS' Identifier multiplicity ;

multiplicity
    : 'ONE'
    | 'MANY'
    | 'ONE_OR_MANY'
    | 'OPTIONALLY_ONE'
    | 'UNDEFINED'
    ;

aggregation : 'AGGREGATION' 'CONTAINER' role 'PARTS' roles ;

roles : role (',' role)* ;

generalization : 'GENERALIZATION' Identifier 'SUBCLASSES' sub_class_names ;

sub_class_names : Identifier (',' Identifier)* ;

Identifier : [a-zA-Z_]+ ;

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;
