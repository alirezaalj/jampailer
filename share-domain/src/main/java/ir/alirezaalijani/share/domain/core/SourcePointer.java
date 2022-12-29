package ir.alirezaalijani.share.domain.core;

import lombok.*;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourcePointer {
    private int line;
    private int blockLevel;
    private int index;
    private int maxIndex;
}
