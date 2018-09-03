package org.jd.image.find;

public class Position<E> implements Comparable<Position> {
    public int x, y;

    public Position(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }

    private CompareWith compareWith;
    public E extraData;

    public void setCompareWith(CompareWith c) {
        compareWith = c;
    }
    public void setCompareWith(E c) {
        compareWith = CompareWith.extraData;
        extraData = c;
    }

    public enum CompareWith {
        x, y, extraData
    }

    @Override
    public int compareTo(Position o) {
        switch (compareWith) {
            case x:
                return x - o.x;
            case y:
                return y = o.y;
            default:
                if (extraData instanceof Comparable && o.extraData instanceof Comparable)
                    return ((Comparable) extraData).compareTo(o.extraData);
                throw new RuntimeException(extraData.getClass().getName() + " is not comparable");
        }
    }
}
