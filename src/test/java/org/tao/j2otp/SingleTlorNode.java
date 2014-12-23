package org.tao.j2otp;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by junjie on 12/22/14.
 */
public class SingleTlorNode extends TestCase {

    protected Tlor _tlor = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        _tlor = new Tlor(new Options("tlor@192.168.4.11", "abc", 3, 500,
                "newspub@xwtec.im", "Welc0me", "", "a:b:c"));
    }

    public void test_info() throws Exception {
        final String code = "Hell@";

        TlorResponse r = _tlor.info("Hell@");
        assertTrue(r.raw(), r.status());
    }

    public void test_say_to() throws Exception {
        final Options o = _tlor.options();
        TlorResponse r = _tlor.say_to(o.user(), o.password(),
                "beta@xwtec.im", "good news");
        System.out.println(r.raw());
        assertTrue(r.raw(), r.status());
    }

    public void test_publish() throws Exception {
        final Options o = _tlor.options();
        TlorResponse r = _tlor.publish(o.user(), o.password(),
                "/home/xwtec.im/newspub/news", "*NEWS*", "good news");
        System.out.println(r.raw());
        assertTrue(r.raw(), r.status());
    }

}
