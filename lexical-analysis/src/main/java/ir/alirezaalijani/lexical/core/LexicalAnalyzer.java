package ir.alirezaalijani.lexical.core;

import ir.alirezaalijani.share.domain.core.Lexical;

import java.util.List;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
public interface LexicalAnalyzer extends Cloneable{
    List<Lexical> analyze();
}
