package org.tao.xmpp;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import static java.lang.System.out;

/**
 * Created by junjie on 12/16/14.
 */
public final class J2otp {
    public static void main(final String[] args) {
        out.println("try to implement otp-java connection pool");

        if (args.length < 2) {
            _help();
            return;
        }

        final LongOpt[] opts = new LongOpt[]{
                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),
                new LongOpt("nodes", LongOpt.OPTIONAL_ARGUMENT, null, 'n'),
                new LongOpt("retry", LongOpt.OPTIONAL_ARGUMENT, null, 'r'),
                new LongOpt("timeout", LongOpt.REQUIRED_ARGUMENT, null, 't'),
        };

        final Getopt g = new Getopt("j2otp", args, "hn:r:t:;", opts);
        g.setOpterr(true);
        int c;

        DeviceType device = DeviceType.ANDROID;
        CastType cast = CastType.UNICAST;
        String api = null;
        String secret = null;
        Long channel = null;
        String user = null;
        MessageType type = MessageType.MESSAGE;
        String message = null;
        long cooked = 0;
        String cookie = null;

        while ((c = g.getopt()) != -1)
            switch (c) {
                case 'd':
                    device = DeviceType.from_str(g.getOptarg(), DeviceType.ANDROID);
                    break;
                case 'c':
                    cast = CastType.from_str(g.getOptarg(), CastType.UNICAST);
                    break;
                case 'a':
                    api = g.getOptarg();
                    break;
                case 's':
                    secret = g.getOptarg();
                    break;
                case 'n':
                    channel = _from_str(g.getOptarg());
                    break;
                case 'u':
                    user = g.getOptarg();
                    break;
                case 't':
                    type = MessageType.from_str(g.getOptarg(), MessageType.MESSAGE);
                    break;
                case 'm':
                    message = g.getOptarg();
                    break;
                case 'k':
                    cooked = _from_str(g.getOptarg());
                    break;
                case 'i':
                    cookie = g.getOptarg();
                    break;
                case 'h':
                    _help();
                    break;
                case ':':
                    _help(String.format("u need specify an argument for option:%s",
                            g.getOptopt()));
                    break;
                case '?':
                    _help(String.format("the option:%s is invalid",
                            g.getOptopt()));
                default:
                    _help(String.format("cli-parser return:%s", c));
                    break;
            }

        final Options options = new Options(device, cast, api, secret,
                channel, user, type, message, cooked, cookie);
        out.print("REQUEST:\n=========================");
        out.println(options);
        out.println("COOK:\n=========================");
        final String body = _cook(options, message);
        out.println("RESPONSE:\n=========================");


        if (CastType.UNICAST == cast) {
            Pusher.push_unicast_message(
                    options.api(),
                    options.secret(),
                    options.device(),
                    options.channel(),
                    options.user(),
                    options.type(),
                    body,
                    _build_logger()
            );
        } else {
     p       Pusher.push_broadcast_message(
                    options.api(),
                    options.secret(),
                    options.device(),
                    options.type(),
                    body,
                    _build_logger()
            );
        }
    }

    private static final void _help() {
        _help(null);
    }

    private static final void _help(final String m) {
        if (null != m) {
            out.println(m);
        }
        out.println("bcp [-d|--device] [-c|cast] " +
                "\n\t[-a|--api] [-s|--secret] " +
                "\n\t[-c|--channel] [-m|--message]");
        out.println("\t--device: android/1, ios/4");
        out.println("\t--cast: unicast/0, broadcast/1");
        out.println("\t--api: api key");
        out.println("\t--secret: secret key");
        out.println("\t--channel: channel id");
        out.println("\t--user: user id");
        out.println("\t--message: message body");
        out.println("\t--cooked: not cooke(0), coded(1) zipped(2)");
        out.println("\t--cookie: coded key");
    }
}
