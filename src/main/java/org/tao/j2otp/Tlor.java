package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangString;

/**
 * Created by junjie on 12/16/14.
 */
public class Tlor {

    public Tlor(final Options options) {
        this(options, new OtpConnectionPoolConfig());
    }

    public Tlor(final Options options, final OtpConnectionPoolConfig config) {
        _pool = new OtpConnectionPool(config, _options = options);
    }

    public final Options options() {
        return (_options);
    }

    public final TlorResponse info(final String code) {
        if (H.is_null_or_empty(code)) {
            return (null);
        }

        final OtpErlangObject[] i = new OtpErlangObject[] {
          new OtpErlangString(code)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_INFO, i);
        final TlorResponse r = TlorResponse.decode(o);
        return (r);
    }

    public final TlorResponse say_to(final String who, final String password, final String whom, final String what) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(who) || H.is_null_or_empty(what)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[] {
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(whom),
                new OtpErlangString(what)
        };
        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_SAY_TO, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse publish(final String who,
                                      final String password,
                                      final String node,
                                      final String type,
                                      final String subject ) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node) || H.is_null_or_empty(type)
                || H.is_null_or_empty(type) || H.is_null_or_empty(subject)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[] {
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(node),
                new OtpErlangString(type),
                new OtpErlangString(subject)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_PUBLISH, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public static final String INS_INFO = "info";
    public static final String INS_SAY_TO = "say_to";
    public static final String INS_PUBLISH = "publish";


    private static final OtpErlangObject _rpc_call(
            final OtpConnectionPool pool,
            final Options options,
            final String mod,
            final String fn,
            final OtpErlangObject[] args) {
        if (null == pool || H.is_null_or_empty(mod) || H.is_null_or_empty(fn)) {
            return (null);
        }

        final OtpConnection c = pool.getResource();
        if (null == c) {
            return (null);
        }

        try {
            c.sendRPC(mod, fn, args);
            final OtpErlangObject r = c.receive(options.timeout());
            return (r);
        } catch (final Exception e) {
            System.out.println(e);
        } finally {
            pool.returnResource(c);
        }

        return (null);
    }

    private final OtpConnectionPool _pool;
    private final Options _options;
    private static final String _M_GANDALF = "gandalf";
    private static final String _M_HOBBIT = "hobbit";
}
