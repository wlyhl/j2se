package topicModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class CharTree implements Comparable<CharTree> {
    static volatile int num = 0;
    char value;
    int number;
    private int pureNum = -1;//净次数，number - nextChars所有次数总和
    int articleNum;//含有此字的文章数
    CharMap nextChars;

    List<Article> articles;

    CharTree(char value) {
        this.value = value;
        num++;
    }

    @Override
    protected void finalize() throws Throwable {
        num--;
    }
    synchronized static boolean needGc(){
        return 1024*1024<num;
    }
    /**
     * @param c      char
     * @param number 数量
     * @return next CharTree
     */
    CharTree addNext(Character c, int number) {
        pureNum = -1;
        if (nextChars == null)
            nextChars = new CharMap();
        return nextChars.computeIfAbsent(c, CharTree::new).addNumber(number);
    }

    CharTree addNextBy(CharTree ct) {
        return addNext(ct.value, ct.number);
    }

    CharTree addNumber(int n) {
        number += n;
        return this;
    }

    CharTree addArticle(Article c) {
        if (articles == null)
            articles = new ArrayList<>();
        if (articles.size() == 0 || articles.size() > 0 && c != articles.get(articles.size() - 1)) {
            articles.add(c);
            articleNum++;
        }
        return this;
    }

    @Override
    public int compareTo(CharTree o) {
        return Integer.compare(o.number, number);
    }

    @Override
    public String toString() {
        return value + " 总次数 " + number + " 净次数 " + pureNum() + " 文章数 " + articleNum;
    }

    public boolean nextContains(Character c) {
        return nextChars != null && nextChars.containsKey(c);
    }

    //遍历词语
    public void wordForEach(Consumer<Word> f) {
        if (nextChars != null && !nextChars.isEmpty()) {
            nextChars.forEach((k, v) -> {
                ArrayList<CharTree> l = new ArrayList<>(Article.WORLD_SIZE);
                l.add(this);
                forEach(v, l, f);
            });
        }
    }

    //递归遍历
    private void forEach(CharTree ct, List<CharTree> l, Consumer<Word> f) {
        ArrayList<CharTree> l2 = new ArrayList<>(Article.WORLD_SIZE);
        l2.addAll(l);
        l2.add(ct);
        f.accept(new Word(l2.toArray(new CharTree[l2.size()])));

        if (ct.nextChars == null || ct.nextChars.isEmpty()) {
            l2.clear();
            return;
        }
        ct.nextChars.forEach((k, v) -> forEach(v, l2, f));
    }

    public int pureNum() {
        if (pureNum == -1) {
            pureNum = number;
            if (nextChars != null && !nextChars.isEmpty())
                nextChars.forEach((k, v) -> pureNum -= v.number);
        }
        return pureNum;
    }
}
