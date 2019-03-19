package com.zy.demo.database.pool;

public interface DatabsePool {

    PooledConnection getPooledConnection();

    void createPooledConnection(int count);
}
