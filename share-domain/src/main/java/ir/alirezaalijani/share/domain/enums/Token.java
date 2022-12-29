package ir.alirezaalijani.share.domain.enums;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/27/2022
 */
public enum Token {
    IDENTIFIER, // - Variable names
    STRING, // - Words between double quotes "";
    CHAR,
    INTEGER, // - Number with no dot ( . );
    FLOAT, // - Float point numbers;
    INVALID, //;
    PLUS, // - ( + )
    MINUS, // - ( - );
    TIMES, // - ( * ),
    DIVIDE, // - ( / );
    KEYWORD, // - for, while, do, if, else, print, switch, case, default and null;
    ASSIGN_OP, // - Assignment operator ( = );
    SEMICOLON, // - ( ; )
    LEFT_PARENTHESIS, // - '(';
    RIGHT_PARENTHESIS, // - ')';
    LEFT_BRACE, // - ( { );
    RIGHT_BRACE, // - ( } );

    LEFT_C_BRACE, // - ( [ );
    RIGHT_C_BRACE, // - ( ] );
    COMMA, // - ( , );
    DOT, // - ( . );
    DOTDOT, // - ( .. );
    COLON, // - ( : );
    EQUAL, // - ( == );
    LOWER_OR_EQUALS,// - ( <= );
    GREATER_OR_EQUALS, // - ( >= );
    NOT_EQUALS,// - ( <> );
    GREATER_THAN, // - ( > );
    LOWER_THAN,// - ( < );
    AT_SIGN // - ( @ ).
}
