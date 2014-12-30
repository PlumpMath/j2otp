package org.tao.j2otp;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by junjie on 12/16/14.
 */
public final class Options {
    private static final Pattern _SPLIT_NODES = Pattern.compile("\\s*:\\s*");
    private final List<String> _nodes;
    private final String _cookie;
    private final int _retry;
    private final int _timeout;
    private final String _user;
    private final String _password;
    private final String _ins;
    private final List<String> _arguments;

    public Options(final String nodes, final String cookie) {
        this(nodes, cookie, 3, 500, null, null, null, null);
    }

    public Options(final String nodes, final String cookie, int retry, int timeout,
                   final String user, final String password, final String ins,
                   final String arguments) {
        final String[] n = _split_colon_string(nodes);
        _nodes = Collections.unmodifiableList(Arrays.asList(n));
        _cookie = cookie;
        _retry = retry;
        _timeout = timeout;
        _user = user;
        _password = password;
        _ins = ins;
        final String[] a = _split_colon_string(arguments);
        _arguments = Collections.unmodifiableList(Arrays.asList(a));
    }

    private static final String[] _split_colon_string(final String s) {
        if (null == s) {
            return (H.EMPTY_STR_ARRAY);
        }
        final String[] n = _SPLIT_NODES.split(s);
        return (n);
    }

    public final String client() {
        final String c = String.format("j2otp@%s", H.host_name());
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

    public final String user() {
        return (_user);
    }

    public final String password() {
        return (_password);
    }

    public final String ins() {
        return (_ins);
    }

    public final List<String> arguments() {
        return (_arguments);
    }

    public final String argument(int i) {
        if (0 > i || i >= _arguments.size()) {
            return (null);
        }

        return (_arguments.get(i));
    }

    public final String logger_config() {
        return (System.getProperty("log4j.configurationFile"));
    }

    @Override
    public String toString() {
        final String s = String.format("Options@%d:{" +
                        "\n\tnodes:%s," + "\n\tcookie:%s," + "\n\tclient:%s" +
                        "\n\tretry:%s,\n\ttimeout:%s,\n\tuser:%s,\n\tpassword:%s," +
                        "\n\tins:%s,\n\targs:%s,\n\tlogger:%s\n}",
                this.hashCode(), _nodes, _cookie, client(),
                _retry, _timeout, _user, _password,
                _ins, _arguments, logger_config());
        return (s);
    }
}
