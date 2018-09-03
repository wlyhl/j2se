package topicModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by cuijiandong on 2018/5/10.
 */
class Article {
    static final String ex = "\0\r“” 。，　、（）％；！…：《》—？";
    static final Set<Character> exclusion = new HashSet<>(ex.length());
    static final char SP = ' ';
    int index;

    public Article(int index) throws Exception {
        this.index = index;
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\cuijiandong\\Downloads\\8000\\8000\\" + index + ".txt"));
        while (append(r.readLine())) ;
        r.close();
    }

    static {
        for (Character c : ex.toCharArray())
            exclusion.add(c);
    }

    StringBuilder content = new StringBuilder(" ");

    boolean append(String s) {
        if (s == null)
            return false;
        for (Character c : s.toCharArray()) {
            if (exclusion.contains(c) || (c < 255)) {
                if (content.charAt(content.length() - 1) != SP)
                    content.append(SP);
            } else content.append(c);
        }
        return true;
    }

    CharMap chars = new CharMap();
    static final CharMap allChars = new CharMap();//全局统计
    static final int WORLD_SIZE = 4;

    void init() {
        content.trimToSize();
        allChars.analyze(content);
//        chars.forEach((k, v) -> {
//            v.wordForEach(System.out::println);
//        });
//        chars.forEach((k, v) -> {
//            CharTree allC = allChars.computeIfAbsent(k, CharTree::new);//全局统计
//            allC.number += v.number;//全局统计,单字出现次数
//            allC.articleNum++;//全局统计,含有此字的文章数
////            allC.addArticle(this);
//
//            //全据统计词语
//            v.wordForEach(w -> {
//                if (w.charArray.length <= WORLD_SIZE && w.last.pureNum() > 0) {
//                    CharTree charTree = allC.addNextBy(w.charArray[1]).addArticle(this);
//                    for (int i = 2; i < w.charArray.length; i++) {
//                        charTree = charTree.addNextBy(w.charArray[i]).addArticle(this);
//                    }
//                }
//            });
//        });
//        allChars.forEach((k, v) -> {
//            v.wordForEach(System.out::println);
//        });
    }

    @Override
    public String toString() {
        return index + "";
    }


}
