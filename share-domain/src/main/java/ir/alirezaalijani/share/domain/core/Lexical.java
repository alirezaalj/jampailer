package ir.alirezaalijani.share.domain.core;

import ir.alirezaalijani.share.domain.enums.Flag;
import ir.alirezaalijani.share.domain.enums.Token;
import lombok.*;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lexical {
    private Flag flag;
    private Token token;
    private SourcePointer sourcePointer;
    private String source;
}
