package org.david.rain.monitor.lang;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-10-24
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
public class Tuple<L, R> {
    public final L l;
    public final R r;

    public Tuple(L l, R r) {
        this.l = l;
        this.r = r;
    }
}
