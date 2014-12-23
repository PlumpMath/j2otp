package org.tao.j2otp;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by junjie on 12/17/14.
 */
public final class H {
    private H() {}

    public static final String[] EMPTY_STR_ARRAY = new String[]{};

    public static final String pad_right(final String s, final int length, final String c) {
        if (is_null_or_empty(s) || is_non_negative(s.length() - length) || is_null_or_empty(c)) {
            return (s);
        }

        final int d = length - s.length();
        if (d < c.length()) {
            return (s);
        }

        final String p = repeat(c, Math.floorDiv(d, c.length()));
        final String t = s.concat(p);

        return (t);
    }

    public static final boolean is_null_or_empty(final String s) {
        return (null == s || s.length() == 0);
    }

    public static final String host_name() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            final String n = ip.getHostName();
            return (n);
        } catch (final UnknownHostException e) {
            System.out.println(e);
        }

        return (null);
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

        final StringBuilder b = new StringBuilder(s.length()*c);
        for (int i = 0; i < c; i++) {
            b.append(s);
        }

        return (b.toString());
    }

}
