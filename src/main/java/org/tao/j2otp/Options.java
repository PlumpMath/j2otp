package org.tao.j2otp;


import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by junjie on 12/16/14.
 */
public final class Options {
    private static final Pattern _SPLIT_NODES = Pattern.compile("\\s*:\\s*");
    private static final Type _type = new TypeToken<Options>() {
    }.getType();
    private final List<String> _nodes;
    private final String _cookie;
    private final String _host;
    private final int _retry;
    private final int _timeout;
    private final String _user;
    private final String _password;
    private final String _ins;
    private final List<String> _arguments;

    public Options(final String nodes, final String cookie,
                   final String user, final String password) {
        this(nodes, cookie, null, 3, 500, user, password, null, null);
    }

    public Options(final String nodes, final String cookie, final String host,
                   int retry, int timeout,
                   final String user, final String password, final String ins,
                   final String arguments) {
        final String[] n = _split_colon_string(nodes);
        _nodes = Collections.unmodifiableList(Arrays.asList(n));
        _cookie = cookie;
        _host = (H.is_null_or_empty(host)
                ? String.format("j2otp@%s", H.host_name()):host);
        _retry = retry;
        _timeout = timeout;
        _user = user;
        _password = password;
        _ins = ins;
        final String[] a = _split_colon_string(arguments);
        _arguments = Collections.unmodifiableList(Arrays.asList(a));
    }

    public static final Options read(final String conf) {
        final String j = H.read_file(conf);
        if (H.is_null_or_empty(j)) {
            return (null);
        }

        final Options c = H.from_json(j, _type);
        return (c);
    }


    public static final void save(final Options options, final String conf) {
        final String j = H.to_json(options, _type);
        H.write_file(j, conf);
    }

    private static final String[] _split_colon_string(final String s) {
        if (null == s) {
            return (H.EMPTY_STR_ARRAY);
        }
        final String[] n = _SPLIT_NODES.split(s);
        return (n);
    }

    public final String host() {
        return (_host);
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
        return (A.LOG4J_CONFIG_PROPERTY);
    }

    @Override
    public String toString() {
        final String s = H.to_json(this, _type);
        save(this, A.OPTIONS_CONFIG_FILE);
        return (s);
    }
}
