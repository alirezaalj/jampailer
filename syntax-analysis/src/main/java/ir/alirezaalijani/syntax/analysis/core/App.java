package ir.alirezaalijani.syntax.analysis.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {

    private  static Map<String,String> grammar=new HashMap<>();
    static {
        grammar.put("S.", "keyword keyword A");
        grammar.put("A", "keyword id ( B | id ( B");
        grammar.put("B", "keyword id F | )");
        grammar.put("F", ", keyword id F | )");
    }

    public static void main( String[] args )
    {
        String text="keyword keyword id ( keyword id , keyword id ) $";


        Stack<String> stack=new Stack<>();
        stack.push("S.");
        while (!stack.isEmpty()){
            System.out.println("stack :"+stack);
            String s = stack.peek();
            if (s.equals("S.")&& text.equals("$")){
                System.out.println("Accept");
                stack.pop();
                break;
            }
            if(s.endsWith(".") || s.length()==1 && Character.isUpperCase(s.charAt(0))){
                String findGrammar = findGrammar(s,text);
                if (!s.endsWith(".")){
                    stack.pop();
                }
                if (findGrammar!=null){
                    String[] spited =findGrammar.split(" ");
                    var list = Arrays.stream(spited).collect(Collectors.toList());
                    Collections.reverse(list);
                    list.forEach(stack::push);
                }else {
                    System.out.println("error");
                    break;
                }
            }else {
                if (matchTerminal(s,text)){
                    text= text.substring(s.length()).trim();
                    stack.pop();
                }else {
                    System.out.println("error");
                    break;
                }
            }
        }
    }

    private static boolean matchTerminal(String terminal,String input){
        return input.startsWith(terminal);
    }
    private static String findGrammar(String key,String value){
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

    private static int countMatch(String s1,String s2){
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
