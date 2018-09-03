package org.jd.j2se.test0.apng;

import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/4/2.
 */
public class PlayableList<E> extends ArrayList<E> {
    private Player[] players;

    public PlayableList(int playerNum) {
        players = new Player[playerNum];
        for (int i = 0; i < players.length; i++) {
            players[i]=new Player();
        }
    }

    /**
     * 开始/继续
     */
    public void play(int playerIndex, int step) {
        players[playerIndex].step = step;
    }

    /**
     * 全部开始/继续
     *
     * @param step
     */
    public void play(int step) {
        for (Player p : players)
            p.step = step;
    }

    /**
     * 暂停
     *
     * @param playerIndex
     */
    public void pPause(int playerIndex) {
        players[playerIndex].step = 0;
    }

    /**
     * 停止/重置
     *
     * @param playerIndex
     */
    public void pReset(int playerIndex) {
        players[playerIndex].step = 0;
        players[playerIndex].index = 0;
    }

    /**
     * 下一个
     *
     * @param playerIndex
     * @return
     */
    public E pNext(int playerIndex) {
        Player p = players[playerIndex];
        int i = p.index;
        p.setIndex(i + p.step, 0, size() - 1);
        return get(p.index);
    }

    /**
     * 判断是否播放结束
     */
    boolean pEnd(int playerIndex) {
        Player p = players[playerIndex];
        return p.step > 0 ? p.index == size() - 1 : p.step < 0 && p.index == 0;
    }

    public boolean pEnd() {
        for (int i = 0; i < players.length; i++) {
            if (!pEnd(i))
                return false;
        }
        return true;
    }

    private static class Player {
        int index;
        int step;

        void setIndex(int i, int min, int max) {
            index = i < min ? min : i > max ? max : i;
        }
    }
}
