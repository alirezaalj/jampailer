package ir.alirezaalijani.lexical.core;

import lombok.Data;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
@Data
public class CharReader implements Closeable {
    private final Pointer pointer;
    private final String text;

    public CharReader(String text) {
        this.text = text;
        this.pointer = Pointer.builder()
                .index(0)
                .blockLevel(0)
                .line(0)
                .maxIndex(text.length())
                .build();
    }

    public Character nextChar() {
        pointer.moveForward();
        if (!pointer.isIndexOut()) {
            char c = text.charAt(pointer.getIndex());
            if (c == '\n') {
                pointer.nextLine();
            }
            if (c == '{') {
                pointer.inToBlock();
            }
            if (c == '}') {
                pointer.outFromBlock();
            }
            return c;
        }
        return null;
    }

    public String nexNumber() {
        pointer.moveForward();
        if (!pointer.isIndexOut()) {
            char c = text.charAt(pointer.getIndex());
            boolean digitContinue = true;
            boolean ral= false;
            if (Character.isDigit(c)) {
                StringBuilder digit = new StringBuilder();
                pointer.moveBack();
                while (digitContinue){
                    pointer.moveForward();
                    char dc=text.charAt(pointer.getIndex());
                    if (!Character.isDigit(dc)){
                        if (dc != '.'){
                            digitContinue =false;
                        }else if(!ral) {
                            ral = true;
                            digit.append('.');
                        }else {
                            digitContinue =false;
                        }
                    } else {
                        digit.append(dc);
                    }
                }
                return digit.toString();
            }
        }

        return null;
    }

    public String nextWord() {
        pointer.moveForward();
        if (!pointer.isIndexOut()) {
            char c = text.charAt(pointer.getIndex());
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_') {
                boolean wordContinue = true;
                pointer.moveBack();
                StringBuilder word = new StringBuilder();
                while (wordContinue) {
                    pointer.moveForward();
                    char wc = text.charAt(pointer.getIndex());
                    if (wc < 'A' || wc > 'z') {
                        if (!Character.isDigit(wc)){
                            wordContinue = false;
                            pointer.moveBack();
                            continue;
                        }
                    }
                    word.append(wc);
                }
                return word.toString();
            }
            pointer.moveBack();
        }
        return null;
    }

    public String nextString() {
        pointer.moveForward();
        if (!pointer.isIndexOut()) {
//            char c = text.charAt(pointer.getIndex());
//
            boolean valueContinue =true;
            pointer.moveBack();
            StringBuilder value= new StringBuilder();
            while (valueContinue){
                pointer.moveForward();
                char s= text.charAt(pointer.getIndex());
                if (s == '\"' || s == '\'') {
                    if (text.charAt(pointer.getIndex()-1)!='\\'){
                        valueContinue=false;
                        continue;
                    }
                }
                value.append(s);
            }
            return value.toString();
        }
        return null;
    }

    public void pointerBack(String word) {
        for (int i = 0; i < word.length(); i++) {
            pointer.moveBack();
        }
    }

    public void pointerBack() {
        pointer.moveBack();
    }


    @Override
    public void close() throws IOException {

    }
}
