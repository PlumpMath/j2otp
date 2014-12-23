package org.tao.j2otp;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by junjie on 12/16/14.
 */
public final class Core {
    private static final Map<String, TlorRunnable> _ins =
            new HashMap<String, TlorRunnable>() {
                {
                    put(Tlor.INS_INFO, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final TlorResponse r = t.info(t.options().argument(0)/*code*/);
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_SAY_TO, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.say_to(o.user(), o.password(),
                                    o.argument(0), /* who */
                                    o.argument(1)); /* what */
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_PUBLISH, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.publish(o.user(), o.password(),
                                    o.argument(0), /* node */
                                    o.argument(1), /* type */
                                    o.argument(2)); /* subject */
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_DELETE_NODE, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.delete_node(o.user(), o.password(),
                                    o.argument(0));
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_CREATE_NODE, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.create_node(o.user(), o.password(),
                                    o.argument(0));
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_DISCO_INFO, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.disco_info(o.user(), o.password());
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_DISCO_INFO, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.disco_info(o.user(), o.password(),
                                    o.argument(0) /* node */);
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_REGISTER, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.register(o.user(), o.password());
                            return (r);
                        }
                    });
                }
            };

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
                new LongOpt("user", LongOpt.REQUIRED_ARGUMENT, null, 'u'),
                new LongOpt("password", LongOpt.REQUIRED_ARGUMENT, null, 'p'),
                new LongOpt("args", LongOpt.REQUIRED_ARGUMENT, null, 'a'),
                new LongOpt("ins", LongOpt.REQUIRED_ARGUMENT, null, 'i')
        };

        final Getopt g = new Getopt("j2otp", args, "hn:c:r:t:u:p:i:a:;", opts);
        g.setOpterr(true);
        int c;

        String nodes = null;
        String cookie = null;
        int retry = 3;
        int timeout = 500;
        String user = null;
        String password = null;
        String ins = null;
        String arguments = null;

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
                case 'u':
                    user = g.getOptarg();
                    break;
                case 'p':
                    password = g.getOptarg();
                    break;
                case 'i':
                    ins = g.getOptarg();
                    break;
                case 'a':
                    arguments = g.getOptarg();
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

        final Options options = new Options(nodes, cookie, retry, timeout,
                user, password, ins, arguments);
        out.println(H.pad_right("OPTIONS:", 40, "="));
        out.println(options);
        out.print(H.pad_right("REQUEST:", 40, "="));
        out.format("\n%s:%s", options.ins(), options.arguments());
        out.format("\n%s", H.pad_right("RESPONSE:", 40, "="));
        final Tlor tlor = new Tlor(options);
        out.format("\n%s", _call(tlor));
    }

    private static final void _help() {
        _help(null);
    }

    private static final void _help(final String m) {
        if (null != m) {
            out.println(m);
        }
        out.println("j2otp [-n|--nodes] [-r|--retry] [-t|--timeout]");
        out.println("\t--nodes: otp nodes, split via ':'");
        out.println("\t--cookie: otp cookie");
        out.println("\t--retry: otp retry times, default is 3");
        out.println("\t--timeout: otp timeout, default 500ms");
        out.println("\t--user: user name, username@domain.im");
        out.println("\t--password: password");
        out.println("\t--ins: instruction's name");
        out.println("\t--args: instruction's arguments");
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

    private static final TlorResponse _call(final Tlor tlor) {
        final String ins = tlor.options().ins();
        if (!_ins.containsKey(ins)) {
            return (null);
        }

        final TlorResponse r = _ins.get(ins).call(tlor);
        return (r);
    }
}
