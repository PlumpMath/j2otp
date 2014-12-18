package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangString;

/**
 * Created by junjie on 12/16/14.
 */
public final class Tlor {

    public Tlor(final Options options) {
        this(options, new OtpConnectionPoolConfig());
    }

    public Tlor(final Options options, final OtpConnectionPoolConfig config) {
        _pool = new OtpConnectionPool(config, _options = options);
    }

    public final String info(final String code) {
        if (H.is_null_or_empty(code)) {
            return (null);
        }

        final OtpErlangObject[] i = new OtpErlangObject[] {
          new OtpErlangString(code)
        };

        final OtpErlangObject ret = _rpc_call(_pool, _options, _M_GANDALF, "info", i);
        return (ret.toString());
    }

    public final OtpErlangObject say_to(final String who, final String password, final String whom, final String what) {
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
        final OtpErlangObject ret = _rpc_call(_pool, _options, _M_GANDALF, "sayto", s);

        return (ret);
    }

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
            System.out.println("\n#resource:" + c.hashCode());
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
