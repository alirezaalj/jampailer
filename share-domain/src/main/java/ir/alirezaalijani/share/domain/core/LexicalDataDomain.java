package ir.alirezaalijani.share.domain.core;

import ir.alirezaalijani.share.domain.enums.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
public class LexicalDataDomain {
    private final static Map<String, Token> wordListTokenMap;
    static {
        wordListTokenMap=new HashMap<>();
        wordListTokenMap.put("public",Token.KEYWORD);
        wordListTokenMap.put("private",Token.KEYWORD);
        wordListTokenMap.put("static",Token.KEYWORD);
        wordListTokenMap.put("void",Token.KEYWORD);
        wordListTokenMap.put("for", Token.KEYWORD);
        wordListTokenMap.put("while", Token.KEYWORD);
        wordListTokenMap.put("do", Token.KEYWORD);
        wordListTokenMap.put("if", Token.KEYWORD);
        wordListTokenMap.put("else", Token.KEYWORD);
        wordListTokenMap.put("print", Token.KEYWORD);
        wordListTokenMap.put("switch", Token.KEYWORD);
        wordListTokenMap.put("case", Token.KEYWORD);
        wordListTokenMap.put("default", Token.KEYWORD);
        wordListTokenMap.put("null", Token.KEYWORD);
        wordListTokenMap.put("int", Token.KEYWORD);
        wordListTokenMap.put("long", Token.KEYWORD);
        wordListTokenMap.put("char", Token.KEYWORD);
        wordListTokenMap.put("float", Token.KEYWORD);
        wordListTokenMap.put("double", Token.KEYWORD);
        wordListTokenMap.put("string",Token.KEYWORD);
        wordListTokenMap.put("import",Token.KEYWORD);
        wordListTokenMap.put("break",Token.KEYWORD);
        wordListTokenMap.put("+", Token.PLUS);
        wordListTokenMap.put("-", Token.MINUS);
        wordListTokenMap.put("*", Token.TIMES);
        wordListTokenMap.put("/", Token.DIVIDE);
        wordListTokenMap.put("..", Token.DOTDOT);
        wordListTokenMap.put(".", Token.DOT);
        wordListTokenMap.put(",", Token.COMMA);
        wordListTokenMap.put("==", Token.EQUAL);
        wordListTokenMap.put(":", Token.COLON);
        wordListTokenMap.put(";", Token.SEMICOLON);
        wordListTokenMap.put("(", Token.LEFT_PARENTHESIS);
        wordListTokenMap.put(")", Token.RIGHT_PARENTHESIS);
        wordListTokenMap.put(">=", Token.GREATER_OR_EQUALS);
        wordListTokenMap.put("<=", Token.LOWER_OR_EQUALS);
        wordListTokenMap.put(">", Token.GREATER_THAN);
        wordListTokenMap.put("<", Token.LOWER_THAN);
        wordListTokenMap.put("<>", Token.NOT_EQUALS);
        wordListTokenMap.put("=", Token.ASSIGN_OP);
        wordListTokenMap.put("@", Token.AT_SIGN);
        wordListTokenMap.put("{", Token.LEFT_BRACE);
        wordListTokenMap.put("}", Token.RIGHT_BRACE);
        wordListTokenMap.put("[", Token.LEFT_C_BRACE);
        wordListTokenMap.put("]", Token.RIGHT_C_BRACE);
    }

    public static Token getByWord(String word){
        return wordListTokenMap.get(word);
    }
}
