package com.github.shoothzj.distribute.impl.common.db;

import com.github.shoothzj.javatool.util.EnvUtil;

/**
 * @author shoothzj
 */
public class SqlConnConfig {

    public static final String DB = EnvUtil.getStringVar("db", "DB", "mysql");

    public static final String DATABASE = EnvUtil.getStringVar("database", "DATABASE", "ttbb");

    public static final String USER = EnvUtil.getStringVar("dbUser", "DB_USER", "hzj");

    public static final String PASSWORD = EnvUtil.getStringVar("password", "PASSWORD", "Mysql@123");

    public static final String HOST = EnvUtil.getStringVar("host", "HOST", "localhost");

    public static final String JDBC_URL = String.format("jdbc:%s://%s/%s?user=%s&password=%s", DB, HOST, DATABASE, USER, PASSWORD);

}