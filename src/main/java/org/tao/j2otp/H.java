package org.tao.j2otp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by junjie on 12/17/14.
 */
public final class H {
    public static final String[] EMPTY_STR_ARRAY = new String[]{};

    private H() {
    }

    public static final String pad_right(final String s, final int length, final String c) {
        if (is_null_or_empty(s) || is_non_negative(s.length() - length) || is_null_or_empty(c)) {
            return (s);
        }

        final int d = length - s.length();
        if (d < c.length()) {
            return (s);
        }

        final String p = repeat(c, _floor_div(d, c.length()));
        final String t = s.concat(p);

        return (t);
    }

    public static final boolean is_null_or_empty(final String s) {
        return (null == s || s.length() == 0);
    }

    public static final <T> boolean is_null_or_empty(final T[] array) {
        if (null == array || array.length == 0) {
            return (true);
        }

        return (false);
    }

    public static final <T> boolean is_null_or_empty(final List<T> list) {
        if (null == list || list.size() == 0) {
            return (true);
        }
        return (false);
    }

    public static final String host_name() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            final String n = ip.getHostName();
            return (n);
        } catch (final UnknownHostException e) {
            _out.error(e);
        }

        return (null);
    }

    public static final <T> String format(Class<T> clazz, final String arg) {
        final String s = String.format("%s::%s", clazz.getSimpleName(), arg);
        return (s);
    }

//    private static final boolean is_positive(final int n) {
//        return (n > 0);
//    }

    private static final boolean is_non_negative(final int n) {
        return (n >= 0);
    }

    private static final String repeat(final String s, final int c) {
        if (is_null_or_empty(s) || c < 1) {
            return (s);
        }

        final StringBuilder b = new StringBuilder(s.length() * c);
        for (int i = 0; i < c; i++) {
            b.append(s);
        }

        return (b.toString());
    }

    private static final int _floor_div(int x, int y) {
        int r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return (r);
    }

    private static final Logger _out = LogManager.getLogger(H.class);
}
