package org.tao.j2otp;

import com.sun.javafx.UnmodifiableArrayList;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by junjie on 12/16/14.
 */
public final class Options {
    public Options(final String nodes, final String cookie, int retry, int timeout) {
        final String[] n = _split_nodes_string(nodes);
        _nodes = new UnmodifiableArrayList<String>(n, n.length);
        _cookie = cookie;
        _retry = retry;
        _timeout = timeout;
    }

    public final String client() {
        final String c = "j2otp@192.168.21.168";
        return (c);
    }

    public final List<String> nodes() {
        return (_nodes);
    }

    public final String node(final int n) {
//        final int i = 1 % _nodes.size();
//        return (_nodes.get(i));
        return (_nodes.get(n));
    }

    public final String cookie() {
        return (_cookie);
    }

    public final int retry() {
        return (_retry);
    }

    public final int timeout() {
        return (_timeout);
    }



    private static final String[] _split_nodes_string(final String nodes) {
        final String[] n = _SPLIT_NODES.split(nodes);
        return (n);
    }

    private final List<String> _nodes;
    private final String _cookie;
    private final int _retry;
    private final int _timeout;
    private static final Pattern _SPLIT_NODES = Pattern.compile("\\s*:\\s*");

    @Override
    public String toString() {
        return "options: {" +
                "\n\tnodes=" + _nodes +
                "\n\tcookie=" + _cookie +
                ",\n\tretry=" + _retry +
                ",\n\ttimeout=" + _timeout +
                "\n}";
    }
}
