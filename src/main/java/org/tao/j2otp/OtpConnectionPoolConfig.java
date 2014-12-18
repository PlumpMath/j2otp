package org.tao.j2otp;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * author: Junjie Mars
 */
public final class OtpConnectionPoolConfig extends GenericObjectPoolConfig {
    public OtpConnectionPoolConfig() {
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }
}
