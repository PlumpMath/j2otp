package org.tao.j2otp;

import junit.framework.TestCase;

/**
 * Created by junjie on 1/4/15.
 */
public class Pubsub extends TestCase {
    protected Tlor _tlor = null;
    protected boolean _debug = false;
    protected static final String _NODE_NAME = "/home/xwtec.im/newspub/debug";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (_debug) {
            _tlor = new Tlor(new Options("tlor@192.168.4.11:tlor@192.168.21.168",
                    "abc",
                    "newspub@xwtec.im", "Welc0me"));
        }
    }

    public void test() throws Exception {
        if (!_debug) return;

        _delete_node();
        _create_node();
        _unsubscribe();
        _subscribe();
    }

    private void _delete_node() throws Exception {
        final Options o = _tlor.options();
        TlorResponse r = _tlor.delete_node(o.user(), o.password(), _NODE_NAME);
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    private void _create_node() throws Exception {
        final Options o = _tlor.options();
        TlorResponse r = _tlor.create_node(o.user(), o.password(), _NODE_NAME);
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    private void _unsubscribe() {
        TlorResponse r = _tlor.unsubscribe("beta@xwtec.im", "Welc0me");
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    private void _subscribe() {
        TlorResponse r = _tlor.subscribe("beta@xwtec.im", "Welc0me", _NODE_NAME);
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

}
