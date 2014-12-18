package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * author: Junjie Mars
 */
public final class OtpConnectionFactory implements PooledObjectFactory<OtpConnection> {

    public OtpConnectionFactory(final Options options) {
        _options = options;
    }

    @Override
    public void activateObject(PooledObject<OtpConnection> pooled)
            throws Exception {
        final OtpConnection t = pooled.getObject();
//        if (t.getDB() != database) {
//            t.select(database);
//        }

    }

    @Override
    public void destroyObject(PooledObject<OtpConnection> pooled) /*throws Exception*/ {
        final OtpConnection c = pooled.getObject();
        if (c.isConnected()) {
            c.close();
        }
//        if (t.isConnected()) {
//            try {
//                try {
//                    t.quit();
//                } catch (Exception e) {
//                }
//                t.disconnect();
//            } catch (Exception e) {
//
//            }
//        }

    }

    @Override
    public PooledObject<OtpConnection> makeObject() /*throws Exception*/ {
        try {
            final OtpSelf client = new OtpSelf(_options.client(), _options.cookie());
            final OtpPeer remote = new OtpPeer(_options.node(0));
            final OtpConnection c = client.connect(remote);

            return (new DefaultPooledObject<OtpConnection>(c));
        } catch (final Exception e) {
            System.out.println(e);
        }




//        t.connect();
//        if (null != this.password) {
//            t.auth(this.password);
//        }
//        if (database != 0) {
//            t.select(database);
//        }
//        if (clientName != null) {
//            t.clientSetname(clientName);
//        }

        return (new DefaultPooledObject<OtpConnection>(null));
    }

    @Override
    public void passivateObject(PooledObject<OtpConnection> pooled)
            throws Exception {
        // !TODO: Not sure right now.
        throw new NotImplementedException();
    }

    @Override
    public boolean validateObject(PooledObject<OtpConnection> pooled) {
        final OtpConnection c = pooled.getObject();
        final boolean v = c.isConnected() && c.isAlive() && (!c.isInterrupted());
        return (v);

//        try {
//            return t.isConnected() && t.ping().equals("PONG");
//        } catch (final Exception e) {
//            return false;
//        }

    }

    private final Options _options;
}
