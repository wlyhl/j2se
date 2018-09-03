package topicModel;

/**
 * Created by cuijiandong on 2018/5/10.
 */
public class Word implements Comparable<Word> {
    CharTree[] charArray;
    CharTree last;

    Word(CharTree[] charArray) {
        this.charArray = charArray;
        last = charArray[charArray.length - 1];
    }

    @Override
    public int compareTo(Word o) {
        return o.last.pureNum() - last.pureNum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CharTree c : charArray)
            sb.append(c.value);
        sb.setLength(sb.length() - 1);
        sb.append(last);
        return sb.toString();
    }

    boolean eq(String s) {
        if (s.length() != charArray.length)
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != charArray[i].value)
                return false;
        }
        return true;
    }

}
