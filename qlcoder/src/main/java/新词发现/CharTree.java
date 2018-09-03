package 新词发现;

import java.util.Map;
import java.util.TreeMap;

class CharTree {
    char value;
    int number;
    Map<Character, CharTree> nextChars;

    CharTree(char value) {
        this.value = value;
    }

    void addNext(Character c) {
        if (nextChars == null)
            nextChars = new TreeMap<>();
        nextChars.computeIfAbsent(c, CharTree::new).number++;
    }
}
