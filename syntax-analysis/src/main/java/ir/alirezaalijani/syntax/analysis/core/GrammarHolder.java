package ir.alirezaalijani.syntax.analysis.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 1/2/2023
 */
public class GrammarHolder {
    private  static final Map<String,String> grammarMethodDefine =new HashMap<>();
    static {
        grammarMethodDefine.put("S.", "keyword keyword A");
        grammarMethodDefine.put("A", "keyword id ( B | id ( B");
        grammarMethodDefine.put("B", "keyword id F | )");
        grammarMethodDefine.put("F", ", keyword id F | )");
    }
}
