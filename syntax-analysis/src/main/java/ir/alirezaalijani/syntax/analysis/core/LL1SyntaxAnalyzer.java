package ir.alirezaalijani.syntax.analysis.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 1/2/2023
 */
@Slf4j
public class LL1SyntaxAnalyzer implements SyntaxAnalyzer {
    private final Map<String,String> grammar;

    public LL1SyntaxAnalyzer(Map<String, String> grammar) {
        this.grammar = grammar;
    }

    @Override
    public void analyze(String tokenString) {
        Stack<String> stack = new Stack<>();
        stack.push("S.");
        while (!stack.isEmpty()) {
            System.out.println("stack :" + stack);
            String s = stack.peek();
            if (s.equals("S.") && tokenString.equals("$")) {
                System.out.println("Accept");
                stack.pop();
                break;
            }
            if (s.endsWith(".") || s.length() == 1 && Character.isUpperCase(s.charAt(0))) {
                String findGrammar = findGrammar(s, tokenString);
                if (!s.endsWith(".")) {
                    stack.pop();
                }
                if (findGrammar != null) {
                    String[] spited = findGrammar.split(" ");
                    var list = Arrays.stream(spited).collect(Collectors.toList());
                    Collections.reverse(list);
                    list.forEach(stack::push);
                } else {
                    System.out.println("error");
                    break;
                }
            } else {
                if (matchTerminal(s, tokenString)) {
                    tokenString = tokenString.substring(s.length()).trim();
                    stack.pop();
                } else {
                    System.out.println("error");
                    break;
                }
            }
        }
    }
    private boolean matchTerminal(String terminal,String input){
        return input.startsWith(terminal);
    }
    private String findGrammar(String key,String value){
        String grammarValue = grammar.get(key);
        if (grammarValue.contains("|")){
            String[] splitGrammars= grammarValue.split("\\|");
            int maxMatch=0;
            int maxMatchIndex=-1;
            for (int i=0;i<splitGrammars.length;i++){
                int countMath = countMatch(splitGrammars[i],value);
                if (countMath>maxMatch){
                    maxMatchIndex=i;
                    maxMatch=countMath;
                }
            }
            if (maxMatchIndex!=-1){
                return splitGrammars[maxMatchIndex].trim();
            }
            return null;
        }
        int countMatch = countMatch(grammarValue,value);
        if (countMatch >=1){
            return grammarValue.trim();
        }
        return null;
    }

    private int countMatch(String s1,String s2){
        var splitS=s1.trim().split(" ");
        var valSplit=s2.trim().split(" ");
        int min = Math.min(splitS.length,valSplit.length);
        int countMath =0;
        for (int i =0; i< min ; i++){
            if (splitS[i].trim().equals(valSplit[i].trim())){
                countMath++;
            }
        }
        return countMath;
    }
}
