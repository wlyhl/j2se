package topicModel;

import org.jd.util.lamda.Function2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by cuijiandong on 2018/5/10.
 */
class CharMap extends HashMap<Character, CharTree> {
    /**
     * 分词
     * @param cs
     */
     void analyze(CharSequence cs){
//         if(CharTree.needGc()){
//             forEach((k,v)->{
//                 v.wordForEach(w->{
//                     if(w.last.number==1){
//                        for(CharTree c:w.charArray){
//                            c.nextChars=null;
//                        }
//                     }
//                 });
//             });
//         }

        for (int i = 0; i < cs.length(); i++) {//找出所有2字的词
            char ch = cs.charAt(i);
            if (ch == Article.SP)
                continue;
            CharTree c = computeIfAbsent(ch, CharTree::new);
            c.number++;

            //取5个字进行统计，用来计算四字词的pureNum,
            for (int j = i + 1, jMax = Math.min(j + Article.WORLD_SIZE, cs.length()); j < jMax; j++) {
                char chj = cs.charAt(j);
                if (chj == Article.SP)
                    break;
                c = c.addNext(chj, 1);
                if (c.number > 1)
                    i++;
            }
        }
    }

}
