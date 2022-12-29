package ir.alirezaalijani.lexical.core;

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
public class Pointer {
    private int line;
    private int blockLevel;
    private int index;
    private int maxIndex;

    public void moveForward(){
        index++;
    }

    public void moveBack(){
        if (index>0)index--;
    }

    public void nextLine(){
        line++;
    }

    public void inToBlock(){
        blockLevel++;
    }

    public void outFromBlock(){
        blockLevel--;
    }

    public boolean isIndexOut(){
        return index>=maxIndex;
    }
}
