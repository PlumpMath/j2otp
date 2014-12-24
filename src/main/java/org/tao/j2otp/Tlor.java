package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangString;

/**
 * Created by junjie on 12/16/14.
 */
public class Tlor {

    public static final String INS_INFO = "info";
    public static final String INS_SAY_TO = "say_to";
    public static final String INS_PUBLISH = "publish";
    public static final String INS_DELETE_NODE = "delete_node";
    public static final String INS_CREATE_NODE = "create_node";
    public static final String INS_DISCO_INFO = "disco_info";
    public static final String INS_REGISTER = "register";
    public static final String INS_SUBSCRIBE = "subscribe";
    public static final String INS_UNSUBSCRIBE = "unsubscribe";
    private static final String _M_GANDALF = "gandalf";
    private static final String _M_HOBBIT = "hobbit";
    private final OtpConnectionPool _pool;
    private final Options _options;

    public Tlor(final Options options) {
        this(options, new OtpConnectionPoolConfig());
    }

    public Tlor(final Options options, final OtpConnectionPoolConfig config) {
        _pool = new OtpConnectionPool(config, _options = options);
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
            return (r);
        } catch (final Exception e) {
            System.out.println(e);
        } finally {
            pool.returnResource(c);
        }

        return (null);
    }

    public final void close() {
        if (null != _pool) {
            _pool.destroy();
        }
    }

    public final Options options() {
        return (_options);
    }

    public final TlorResponse info(final String code) {
        if (H.is_null_or_empty(code)) {
            return (null);
        }

        final OtpErlangObject[] i = new OtpErlangObject[]{
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

        final OtpErlangObject[] s = new OtpErlangObject[]{
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
                                      final String subject) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node) || H.is_null_or_empty(type)
                || H.is_null_or_empty(type) || H.is_null_or_empty(subject)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
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

    public final TlorResponse delete_node(final String who,
                                          final String password,
                                          final String node) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(node)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_HOBBIT,
                INS_DELETE_NODE, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse create_node(final String who,
                                          final String password,
                                          final String node) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(node)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_HOBBIT,
                INS_CREATE_NODE, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse disco_info(final String who, final String password) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_HOBBIT,
                INS_DISCO_INFO, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse disco_info(final String who,
                                         final String password,
                                         final String node) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(node)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_HOBBIT,
                INS_DISCO_INFO, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse register(final String who, final String password) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password),
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_REGISTER, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse subscribe(final String who,
                                          final String password,
                                          final String node) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)
                || H.is_null_or_empty(node)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password),
                new OtpErlangString(node)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_SUBSCRIBE, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }

    public final TlorResponse unsubscribe(final String who,
                                        final String password) {
        if (H.is_null_or_empty(who) || H.is_null_or_empty(password)) {
            return (null);
        }

        final OtpErlangObject[] s = new OtpErlangObject[]{
                new OtpErlangString(who),
                new OtpErlangString(password)
        };

        final OtpErlangObject o = _rpc_call(_pool, _options, _M_GANDALF,
                INS_UNSUBSCRIBE, s);
        final TlorResponse r = TlorResponse.decode(o);

        return (r);
    }
}
