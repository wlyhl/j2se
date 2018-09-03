package topicModel;


import org.jd.util.Jmath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by cuijiandong on 2018/5/10.
 */
public class Main {
    public static void main(String[] aStrings) throws Exception {
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Article article = new Article(i);
            articles.add(article);
            article.init();
        }

        List<Word> words = new ArrayList<>();
        Article.allChars.forEach((k, v) ->
                v.wordForEach(w -> {
                    if (Jmath.isAscending(1, w.last.pureNum(), 10000))
                        words.add(w);
                })
        );
        Collections.sort(words);
        words.forEach((w) -> {
            System.out.println(w);
        });
//        List<WorldGroup> worldGroups = new ArrayList<>();
//        for (int i = 0; i < 4 && i < words.size(); i++) {
//            Word w = words.get(i);
//            for (int j = i; j < words.size(); j++) {
//                if (j != i) {
//                    WorldGroup wg = new WorldGroup(w, words.get(j));
//                    if (wg.relevance > 0.1)
//                        worldGroups.add(wg);
//                }
//            }
//        }
//        Collections.sort(worldGroups);
//        worldGroups.forEach(System.out::println);
    }
}
