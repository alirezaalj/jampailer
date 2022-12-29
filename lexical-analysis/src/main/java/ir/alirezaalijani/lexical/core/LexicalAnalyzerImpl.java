package ir.alirezaalijani.lexical.core;

import ir.alirezaalijani.share.domain.core.Lexical;
import ir.alirezaalijani.share.domain.core.LexicalDataDomain;
import ir.alirezaalijani.share.domain.core.SourcePointer;
import ir.alirezaalijani.share.domain.enums.Flag;
import ir.alirezaalijani.share.domain.enums.State;
import ir.alirezaalijani.share.domain.enums.Token;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
@Slf4j
public class LexicalAnalyzerImpl implements LexicalAnalyzer{
    private final CharReader charReader;
    private final List<Lexical> lexicalList;

    public LexicalAnalyzerImpl(String source) {
        if (source == null || source.isBlank()) {
            throw new RuntimeException("source must not be empty");
        }
        this.charReader = new CharReader(source);
        this.lexicalList = new ArrayList<>();
    }

    @Override
    public List<Lexical> analyze() {
        State state = State.read;
        boolean eof = false;
        while (!eof) {
            Character c = charReader.nextChar();
            if (c == null) {
                eof = true;
                continue;
            }

            if (commandSkip(c)) {
                c = charReader.nextChar();
            }

            if (c == '\t' || c == '\n' || c == ' ' || c == '\r') {
                continue;
            }

            var pointer = charReader.getPointer();
            log.debug("char: {} , pointer: {}", c, pointer);
            switch (state) {
                case read : {
                    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                        charReader.pointerBack();
                        // word read state
                        state = State.word;
                    } else if (Character.isDigit(c)) {
                        // char is digit
                        charReader.pointerBack();
                        state = State.digit;
                    } else if (c == '\"' || c == '\'') {
                        charReader.pointerBack();
                        state = State.string_char;
                    } else if (c == '=' || c == '<' || c == '>') {
                        charReader.pointerBack();
                        state = State.operation;
                    } else {
                        var token = LexicalDataDomain.getByWord(String.valueOf(c));
                        if (token != null) {
                            log.debug("char: {} , token: {}", c, token);
                            lexicalList.add(lexicalGenerate(Flag.character,token,activePointer(), String.valueOf(c)));
                        }
                    }
                    break;
                }
                case word : {
                    var word = charReader.nextWord();
                    if (word != null || Character.isLetter(c)) {
                        if (word == null) word = "";
                        word = c + word;
                        var token = LexicalDataDomain.getByWord(word);
                        if (token != null) {
                            // set token
                            log.debug("word: {} , token: {}", word, token);
                            lexicalList.add(lexicalGenerate(Flag.word,token,activePointer(), word));
                            state = State.read;
                        } else {
                            // change state to char analyser
                            log.debug("word: {} , token: {}", word, Token.IDENTIFIER);
                            lexicalList.add(lexicalGenerate(Flag.word,Token.IDENTIFIER,activePointer(), word));
                            state = State.read;
                        }
                    }
                    break;
                }
                case digit : {
                    if (Character.isDigit(c)) {
                        charReader.pointerBack();
                        String digit = charReader.nexNumber();
                        if (digit != null) {
                            if (digit.contains(".")) {
                                // float
                                log.debug("number: {} , token: {}", digit, Token.FLOAT);
                                lexicalList.add(lexicalGenerate(Flag.number,Token.FLOAT,activePointer(), digit));
                            } else {
                                // int
                                log.debug("number: {} , token: {}", digit, Token.INTEGER);
                                lexicalList.add(lexicalGenerate(Flag.number,Token.INTEGER,activePointer(), digit));
                            }
                            state = State.read;
                            charReader.pointerBack();
                        } else {
                            charReader.pointerBack();
                            state = State.read;
                        }
                    }
                    break;
                }
                case string_char : {
                    if (c == '\"' || c == '\'') {
                        String stringValue = charReader.nextString();
                        if (stringValue != null) {
                            int size = stringValue.length();
                            if (size == 1) {
                                log.debug("char-value: {} , token: {}", stringValue, Token.CHAR);
                                lexicalList.add(lexicalGenerate(Flag.char_value,Token.CHAR,activePointer(), stringValue));
                            } else {
                                log.debug("string-value: {} , token: {}", stringValue, Token.STRING);
                                lexicalList.add(lexicalGenerate(Flag.string_value,Token.STRING,activePointer(), stringValue));
                            }
                        }
                        state = State.read;
                    } else {
                        state = State.read;
                    }
                    break;
                }
                case operation : {
                    if (c == '=' || c == '<' || c == '>') {
                        Character next = charReader.nextChar();
                        if (next == '=' || next == '<' || next == '>') {
                            Token token = LexicalDataDomain.getByWord(c + String.valueOf(next));
                            if (token != null) {
                                log.debug("operation: {}{} , token: {}", c, next, token);
                                lexicalList.add(lexicalGenerate(Flag.operation,token,activePointer(), c+String.valueOf(next)));
                            }
                        } else {
                            Token token = LexicalDataDomain.getByWord(String.valueOf(c));
                            if (token != null) {
                                log.debug("operation: {} , token: {}", c, token);
                                lexicalList.add(lexicalGenerate(Flag.operation,token,activePointer(), String.valueOf(c)));
                            }
                            charReader.pointerBack();
                            state = State.read;
                        }
                    } else {
                        charReader.pointerBack();
                        state = State.read;
                    }
                    break;
                }
            }
        }
        return lexicalList;
    }


    private Pointer activePointer(){
        return charReader.getPointer();
    }
    private boolean commandSkip(Character c) {
        if (c == '/') {
            c = charReader.nextChar();
            if (c == '/') {
                readToNextLine();
                return true;
            } else if (c == '*') {
                readToCommentEnd();
            } else {
                charReader.pointerBack();
                return false;
            }
        }
        return false;
    }

    private void readToCommentEnd() {
        while (true) {
            Character c = charReader.nextChar();
            if (c == null) {
                break;
            }
            if (c == '*') {
                c = charReader.nextChar();
                if (c == '/') {
                    break;
                }
            }
        }
    }

    private void readToNextLine() {
        while (true) {
            Character c = charReader.nextChar();
            if (c == null || c == '\n') {
                break;
            }
        }

    }

    private Lexical lexicalGenerate(Flag flag, Token token, Pointer pointer, String source){
        return Lexical.builder()
                .flag(flag)
                .token(token)
                .source(source)
                .sourcePointer(SourcePointer.builder()
                        .index(pointer.getIndex())
                        .blockLevel(pointer.getBlockLevel())
                        .line(pointer.getLine())
                        .maxIndex(pointer.getMaxIndex())
                        .build())
                .build();
    }
}
