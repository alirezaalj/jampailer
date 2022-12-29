package ir.alirezaalijani.lexical.gui;

import ir.alirezaalijani.share.domain.core.Lexical;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/29/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LexicalView {
    private String flag;
    private String token;
    private Integer line;
    private Integer block;
    private String source;

    public LexicalView(Lexical lexical) {
        if (lexical != null) {
            this.flag = lexical.getFlag().name();
            this.token = lexical.getToken().name();
            if (lexical.getSourcePointer() != null) {
                this.line = lexical.getSourcePointer().getLine();
                this.block = lexical.getSourcePointer().getBlockLevel();
            }
            this.source = lexical.getSource();
        }
    }
}
