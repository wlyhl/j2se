package 新词发现;

import jdk.nashorn.internal.runtime.NumberToString;
import org.jd.util.JCollection;
import org.jd.util.Jmath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by cuijiandong on 2018/5/9.
 */
public class Word {
    static final String ex = "“” 。，…　";
    static final Set<Character> exclusion = new HashSet<>(ex.length());

    static {
        for (Character c : ex.toCharArray())
            exclusion.add(c);
    }

    public static void main(String[] a) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\cuijiandong\\Downloads\\santi2.txt"));
        Map<Character, CharTree> map = new TreeMap<>();
        CharTree prevChar = null;

        for (String s = r.readLine(); s != null; s = r.readLine()) {
            for (Character c : s.toCharArray()) {
                if (exclusion.contains(c)) {
                    prevChar = null;
                    continue;
                }
                CharTree charTree = map.computeIfAbsent(c, CharTree::new);
                charTree.number++;
                if (prevChar != null)
                    prevChar.addNext(c);
                prevChar = charTree;
            }
        }
        for (Map.Entry<Character, CharTree> e : map.entrySet()) {
            Map<Character, CharTree> nextChars = e.getValue().nextChars;
            if (nextChars != null) for (Map.Entry<Character, CharTree> ne : nextChars.entrySet()) {
                if (ne.getValue().number > 99)
                    System.out.println(e.getValue().value + " " + e.getValue().number + " " + ne.getValue().value + " " + ne.getValue().number + " " + ne.getValue().number * 194285 / e.getValue().number / map.get(ne.getKey()).number);
            }
        }
    }
}
