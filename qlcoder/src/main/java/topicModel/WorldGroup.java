package topicModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by cuijiandong on 2018/5/10.
 */
public class WorldGroup implements Comparable<WorldGroup> {
    Word w1, w2;
    List<Article> w12a = new ArrayList<>();//相同的文章
    List<Article> w1a = new ArrayList<>();
    List<Article> w2a = new ArrayList<>();
    float relevance = 0;

    public WorldGroup(Word w1, Word w2) {
        this.w1 = w1;
        this.w2 = w2;
        List<Article> a1s = w1.last.articles;
        List<Article> a2s = w2.last.articles;
        if (a1s.size() < 2 || a2s.size() < 2)
            return;
        //查找两组文章的交集
        for (int i1 = 0, i2 = 0; i1 < a1s.size() && i2 < a2s.size(); ) {
            Article a = a1s.get(i1), b = a2s.get(i2);
            if (a == b) {
                w12a.add(a);
                i1++;
                i2++;
            } else {
                if (a.index < b.index) {
                    w1a.add(a);
                    i1++;
                } else {
                    w2a.add(b);
                    i2++;
                }
            }
        }
        relevance = 2f * w12a.size() / Math.max(a1s.size(),a2s.size());
    }

    @Override
    public String toString() {
        return w1 + " " + w2 + " 相关度 "+relevance+ " 公共文章" + w12a;//+ "\n" + w1a + "\n" + w2a;
    }

    @Override
    public int compareTo(WorldGroup o) {
        return Float.compare(o.relevance, relevance);
    }
}
