package org.tao.xmpp;

import com.sun.javafx.UnmodifiableArrayList;

import java.util.List;

/**
 * Created by junjie on 12/16/14.
 */
public final class Options {
    public Options(final String[] nodes, int retry, int timeout) {
        _nodes = new UnmodifiableArrayList<String>(nodes, nodes.length);
        _retry = retry;
        _timeout = timeout;
    }

    public final List<String> nodes() {
        return (_nodes);
    }

    public final int retry() {
        return (_retry);
    }

    public final int timeout() {
        return (_timeout);
    }

    private final List<String> _nodes;
    private final int _retry;
    private final int _timeout;
}
