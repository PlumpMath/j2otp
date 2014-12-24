package org.tao.j2otp;

import junit.framework.TestCase;

/**
 * Created by junjie on 12/22/14.
 */
public class SingleTlorNode extends TestCase {

    protected Tlor _tlor = null;
    protected boolean _debug = true;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (_debug) {
            _tlor = new Tlor(new Options("tlor@192.168.4.11:tlor@192.168.21.168",
                    "abc", 3, 500,
                    "newspub@xwtec.im", "Welc0me", "", "a:b:c"));
        }
    }

    public void test_info() throws Exception {
//        if (!_debug) return;
//        final String code = "Hell@";
//        TlorResponse r = _tlor.info(code);
//        assertNotNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }

    public void test_say_to() throws Exception {
//        if (!_debug) return;
//        final Options o = _tlor.options();
//        TlorResponse r = _tlor.say_to(o.user(), o.password(),
//                "beta@xwtec.im", "good news");
//        assertNotNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }

    public void test_publish() throws Exception {
        if (!_debug) return;
        final Options o = _tlor.options();
        TlorResponse r = _tlor.publish(o.user(), o.password(),
                "/home/xwtec.im/newspub/news", "*NEWS*", "good news");
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    public void test_delete_node() throws Exception {
//        if (!_debug) return;
//        final Options o = _tlor.options();
//        TlorResponse r = _tlor.delete_node(o.user(), o.password(),
//                "/home/xwtec.im/newspub/news");
//        assertNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }

    public void test_create_node() throws Exception {
//        if (!_debug) return;
//        final Options o = _tlor.options();
//        TlorResponse r = _tlor.create_node(o.user(), o.password(),
//                "/home/xwtec.im/newspub/news");
//        assertNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }

    public void test_unsubscribe() {
//        if (!_debug) return;
//        TlorResponse r = _tlor.unsubscribe("beta@xwtec.im", "Welc0me");
//        assertNotNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }

    public void test_subscribe() {
//        if (!_debug) return;
//        TlorResponse r = _tlor.subscribe("beta@xwtec.im", "Welc0me",
//                "/home/xwtec.im/newspub/news");
//        assertNotNull("r is null", r);
//        assertTrue(r.raw(), r.status());
    }
}
