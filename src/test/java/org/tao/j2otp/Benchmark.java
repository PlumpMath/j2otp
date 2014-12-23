package org.tao.j2otp;

import junit.framework.TestCase;

/**
 * Created by junjie on 12/23/14.
 */
public class Benchmark extends TestCase {
    protected Tlor _tlor = null;
    protected boolean _debug = false;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        _tlor = new Tlor(new Options("tlor@192.168.4.11", "abc", 3, 500,
                "newspub@xwtec.im", "Welc0me", "", "a:b:c"));
    }

    public void test_publish_64() {
        if (!_debug) return;

        final Options o = _tlor.options();
        final String s = _workload_512().toString();
        for (int i = 0; i < 32; i++) {
            TlorResponse r = _tlor.publish(o.user(), o.password(),
                    "/home/xwtec.im/newspub/news", "*BENCHMARK*", s);
            System.out.println(r.raw());
            assertTrue(r.raw(), r.status());
        }
    }

    private static final StringBuffer _workload_512() {
        final String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345";
        final StringBuffer buffer = new StringBuffer(512);
        for (int i = 0; i < 512/s.length(); i++) {
            buffer.append(s);
        }
        return (buffer);
    }
}
