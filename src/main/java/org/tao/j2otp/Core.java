package org.tao.j2otp;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junjie on 12/16/14.
 */
public final class Core {
    private static final Logger _out = LogManager.getLogger(Core.class);

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

                {
                    put(Tlor.INS_SUBSCRIBE, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.subscribe(o.user(), o.password(),
                                    o.argument(0)/* node */);
                            return (r);
                        }
                    });
                }

                {
                    put(Tlor.INS_UNSUBSCRIBE, new TlorRunnable() {
                        @Override
                        TlorResponse call(Tlor t) {
                            final Options o = t.options();
                            final TlorResponse r = t.unsubscribe(o.user(), o.password());
                            return (r);
                        }
                    });
                }

            };

    public static void main(String[] args) {
        if (args.length < 1) {
            _help();
            return;
        }

        final LongOpt[] opts = new LongOpt[]{
                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),
                new LongOpt("config", LongOpt.OPTIONAL_ARGUMENT, null, 'f'),
                new LongOpt("nodes", LongOpt.REQUIRED_ARGUMENT, null, 'n'),
                new LongOpt("host", LongOpt.REQUIRED_ARGUMENT, null, 's'),
                new LongOpt("cookie", LongOpt.REQUIRED_ARGUMENT, null, 'c'),
                new LongOpt("retry", LongOpt.OPTIONAL_ARGUMENT, null, 'r'),
                new LongOpt("timeout", LongOpt.REQUIRED_ARGUMENT, null, 't'),
                new LongOpt("user", LongOpt.REQUIRED_ARGUMENT, null, 'u'),
                new LongOpt("password", LongOpt.REQUIRED_ARGUMENT, null, 'p'),
                new LongOpt("args", LongOpt.REQUIRED_ARGUMENT, null, 'a'),
                new LongOpt("ins", LongOpt.REQUIRED_ARGUMENT, null, 'i')
        };

        final Getopt g = new Getopt("j2otp", args, "hf:n:c:s:r:t:u:p:i:a:;", opts);
        g.setOpterr(true);
        int c;

        String config = null;
        String nodes = null;
        String host = null;
        String cookie = null;
        int retry = 3;
        int timeout = 500;
        String user = null;
        String password = null;
        String ins = null;
        String arguments = null;

        while ((c = g.getopt()) != -1)
            switch (c) {
                case 'f':
                    config = g.getOptarg();
                    break;
                case 'n':
                    nodes = g.getOptarg();
                    break;
                case 'c':
                    cookie = g.getOptarg();
                    break;
                case 's':
                    host = g.getOptarg();
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

        final Options options = (!H.is_null_or_empty(config) ?
                Options.read(config) :
                new Options(nodes, cookie, host, retry, timeout,
                        user, password, ins, arguments));
        _out.info(H.pad_right("OPTIONS:", 40, "="));
        _out.info(options);
        _out.info(H.pad_right("REQUEST:", 40, "="));
        _out.info(String.format("%s:%s", options.ins(), options.arguments()));
        _out.info(String.format("%s", H.pad_right("RESPONSE:", 40, "=")));
        final Tlor tlor = new Tlor(options);
        _out.info(String.format("%s", _call(tlor)));
        tlor.close();
    }

    private static final void _help() {
        _help(null);
    }

    private static final void _help(final String m) {
        if (null != m) {
            _out.info(m);
        }

        _out.info("j2otp [-f|--config]");
        _out.info("j2otp [-n|--nodes] [-r|--retry] [-t|--timeout] [-i|--ins] [-a|--args]");
        _out.info("\t--config: specify configuration file");
        _out.info("\t--nodes: otp nodes, split via ':'");
        _out.info("\t--cookie: otp cookie");
        _out.info("\t--retry: otp retry times, default is 3");
        _out.info("\t--timeout: otp timeout, default 500ms");
        _out.info("\t--user: user name, username@domain.im");
        _out.info("\t--password: password");
        _out.info("\t--ins: instruction's name");
        _out.info("\t--args: instruction's arguments, split via ':'");
        System.exit(0);
    }

    private static int _parse_int(final String s, final int d) {
        try {
            final int i = Integer.parseInt(s);
            return (i);
        } catch (final NumberFormatException e) {
            _out.error(e);
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
