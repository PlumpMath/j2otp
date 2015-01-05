package org.tao.j2otp;

import junit.framework.TestCase;

/**
 * Created by junjie on 12/22/14.
 */
public class Basic extends TestCase {

    protected Tlor _tlor = null;
    protected boolean _debug = true;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (_debug) {
            _tlor = new Tlor(new Options("tlor@192.168.4.11:tlor@192.168.21.168",
                    "abc",
                    "newspub@xwtec.im", "Welc0me"));
        }
    }

    public void test_info() throws Exception {
        if (!_debug) return;
        final String code = "Hell@";
        TlorResponse r = _tlor.info(code);
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    public void test_say_to() throws Exception {
        if (!_debug) return;
        final Options o = _tlor.options();
        TlorResponse r = _tlor.say_to(o.user(), o.password(),
                "beta@xwtec.im",
                String.format("%s at %s", "good news", System.currentTimeMillis()));
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

    public void test_publish() throws Exception {
        if (!_debug) return;
        final Options o = _tlor.options();
        TlorResponse r = _tlor.publish(o.user(), o.password(),
                "/home/xwtec.im/newspub/debug", "*NEWS*",
                String.format("%s at %s", "good news", System.currentTimeMillis()));
        assertNotNull("r is null", r);
        assertTrue(r.raw(), r.status());
    }

}
