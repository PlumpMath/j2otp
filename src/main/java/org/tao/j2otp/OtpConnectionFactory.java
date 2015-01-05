package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * author: Junjie Mars
 */
public final class OtpConnectionFactory implements PooledObjectFactory<OtpConnection> {

    private static final Logger _out = LogManager.getLogger(OtpConnectionFactory.class);
    private static final Random _random = new Random(System.currentTimeMillis());
    private static final Class<?> _clazz = OtpConnectionFactory.class;
    private final Options _options;

    public OtpConnectionFactory(final Options options) {
        _options = options;
    }

    private static final String _select(final Options options, final Random random) {
        final List<String> nodes = options.nodes();
        if (H.is_null_or_empty(nodes)) {
            return (null);
        }

        final int i = random.nextInt(16);
        final String n = nodes.get(i % nodes.size());
        return (n);
    }

    @Override
    public void activateObject(PooledObject<OtpConnection> pooled)
            throws Exception {
        final OtpConnection t = pooled.getObject();
        // do something for OtpConnection:
        _out.debug(H.format(_clazz, "activateObject"));
    }

    @Override
    public void destroyObject(PooledObject<OtpConnection> pooled) /*throws Exception*/ {
        final OtpConnection c = pooled.getObject();
        _out.debug("OtpConnectionFactory::destroyObject");

        if (null != c && c.isConnected()) {
            c.close();
            _out.debug(H.format(_clazz, "destroyObject closed"));
        }
    }

    @Override
    public PooledObject<OtpConnection> makeObject() /*throws Exception*/ {
        final String node = _select(_options, _random);
        if (H.is_null_or_empty(node)) {
            _out.warn(H.format(_clazz, "_select null/empty node"));
            return (null);
        }

        try {
            final OtpPeer remote = new OtpPeer(node);
            final OtpSelf client = new OtpSelf(_options.host(), _options.cookie());
            final OtpConnection c = client.connect(remote);
            return (new DefaultPooledObject<OtpConnection>(c));
        } catch (final Exception e) {
            _out.error(e);
        }

        return (new DefaultPooledObject<OtpConnection>(null));
    }

    @Override
    public void passivateObject(PooledObject<OtpConnection> pooled)
            /*throws Exception*/ {
        _out.debug(H.format(_clazz, "passivateObject"));
    }

    @Override
    public boolean validateObject(PooledObject<OtpConnection> pooled) {
        final OtpConnection c = pooled.getObject();
        final boolean v = c.isConnected() && c.isAlive() && (!c.isInterrupted());

        _out.debug(H.format(_clazz, "validateObject"));
        return (v);
    }
}
