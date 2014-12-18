package org.tao.j2otp;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import static java.lang.System.out;

/**
 * Created by junjie on 12/16/14.
 */
public final class Core {
    public static void main(String[] args) {
        if (args.length < 2) {
            _help();
            return;
        }

        final LongOpt[] opts = new LongOpt[]{
                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),
                new LongOpt("nodes", LongOpt.REQUIRED_ARGUMENT, null, 'n'),
                new LongOpt("cookie", LongOpt.REQUIRED_ARGUMENT, null, 'c'),
                new LongOpt("retry", LongOpt.OPTIONAL_ARGUMENT, null, 'r'),
                new LongOpt("timeout", LongOpt.REQUIRED_ARGUMENT, null, 't'),
        };

        final Getopt g = new Getopt("j2otp", args, "hn:c:r:t:;", opts);
        g.setOpterr(true);
        int c;

        String nodes = null;
        String cookie = null;
        int retry = 3;
        int timeout = 500;

        while ((c = g.getopt()) != -1)
            switch (c) {
                case 'n':
                    nodes = g.getOptarg();
                    break;
                case 'c':
                    cookie = g.getOptarg();
                    break;
                case 'r':
                    final int r = _parse_int(g.getOptarg(), retry);
                    retry = (r > 0 ? r : retry);
                    break;
                case 't':
                    final int t = _parse_int(g.getOptarg(), timeout);
                    timeout = (t > 0 ? t : timeout);
                    break;
                case 'h':
                    _help();
                    break;
                case ':':
                    _help(String.format("u need specify an argument for option:%s", g.getOptopt()));
                    break;
                case '?':
                    _help(String.format("the option:%s is invalid", g.getOptopt()));
                default:
                    _help(String.format("cli-parser return:%s", c));
                    break;
            }

        final Options options = new Options(nodes, cookie, retry, timeout);
        out.println(H.pad_right("OPTIONS:", 40, "="));
        out.println(options);
        out.println();
        out.print(H.pad_right("REQUEST:", 40, "="));
        out.format("\ninfo");
        out.format("\nsayto");
        out.format("\n%s", H.pad_right("RESPONSE:", 40, "="));
        final Tlor tlor = new Tlor(options);
        out.format("\n%s", tlor.info("Hell@"));
        out.format("\n%s", tlor.say_to("newspub@xwtec.im", "Welc0me", "beta@xwtec.im", "say hello"));
    }

    private static final void _help() {
        _help(null);
    }

    private static final void _help(final String m) {
        if (null != m) {
            out.println(m);
        }
        out.println("j2otp [-n|--nodes] [-r|--retry] [-t|--timeout]");
        out.println("\t--nodes: otp nodes, split via :");
        out.println("\t--retry: retry times, default is 3");
        out.println("\t--timeout: timeout, default 500ms");
    }

    private static int _parse_int(final String s, final int d) {
        try {
            final int i = Integer.parseInt(s);
            return (i);
        } catch (final NumberFormatException e) {
            out.println(e);
        }

        return (d);
    }
}
