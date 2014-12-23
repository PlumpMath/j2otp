package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangTuple;

/**
 * Created by junjie on 12/22/14.
 */
public final class TlorResponse {

    private TlorResponse() {
        this("!response-failed");
    }

    private TlorResponse(final String raw) {
        this(raw, "!bad");
    }

    private TlorResponse(final String raw, final String status) {
        this(raw, status, null);
    }

    private TlorResponse(final String raw, final String status, final String module) {
        this(raw, status, module, null);
    }

    private TlorResponse(final String raw, final String status, final String module, final String info) {
        _raw = raw;
        _status = _to_bool(status);
        _module = module;
        _info = info;
    }

    public static final TlorResponse decode(final OtpErlangObject response) {
        if (null == response) {
            return (null);
        }

        final OtpErlangTuple t = _from(response);
        if (null == t) {
            return (new TlorResponse(response.toString()));
        }
        if (t.arity() < 2) {
            return (new TlorResponse(t.toString()));
        }

        final OtpErlangTuple i = _from(t.elementAt(t.arity() - 1));
        if (null == i) {
            return (new TlorResponse(t.toString()));
        }
        final OtpErlangTuple d = i;
        if (d.arity() >= 3) {
            return (new TlorResponse(d.toString(),
                    _decode_status(d, 0), _to_str(d, 1), _to_str(d, 2)));
        } else if (d.arity() >= 2) {
            return (new TlorResponse(d.toString(), _decode_status(d, 0), _to_str(d, 1)));
        } else if (d.arity() >= 1) {
            return (new TlorResponse(d.toString(), _decode_status(d, 0)));
        }

        return (new TlorResponse(d.toString()));
    }

    public final String raw() {
        return (_raw);
    }

    public final boolean status() {
        return (_status);
    }

    public final String module() {
        return (_module);
    }

    public final String info() {
        return (_info);
    }

    @Override
    public String toString() {
        final String s = String.format("TlorResponse@%d<%s>",
                this.hashCode(), _raw);
        return (s);
    }

    private static final String _decode_status(final OtpErlangTuple t, final int i) {
        final OtpErlangObject first = t.elementAt(i);
        final OtpErlangTuple d = _from(first);
        if (null == d) {
            return (first.toString());
        }

        final OtpErlangObject status = d.elementAt(0);

        return (status.toString());
    }

    private static final OtpErlangTuple _from(final OtpErlangObject o) {
        if (null == o) {
            return (null);
        }

        if (!(o instanceof OtpErlangTuple)) {
            return (null);
        }

        final OtpErlangTuple t = (OtpErlangTuple)o;
        return (t);
    }

    private static final String _to_str(final OtpErlangTuple t, final int i) {
        if (null == t || i < 0 || i >= t.arity()) {
            return (null);
        }

        final OtpErlangObject o = t.elementAt(i);
        return (o.toString());
    }

    private static final boolean _to_bool(final String s) {
        if (H.is_null_or_empty(s)) {
            return (false);
        }

        return (s.equals("ok") ? true : false);
    }

    private final boolean _status;
    private final String _module;
    private final String _info;
    private final String _raw;

}
