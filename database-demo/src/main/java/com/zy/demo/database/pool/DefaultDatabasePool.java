package com.zy.demo.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class DefaultDatabasePool implements DatabsePool {

    private Vector<PooledConnection> pooledConnectionVector = new Vector<>();

    private String jdbcDriver;

    private String jdbcUsername;

    private String jdbcPassword;

    private String jdbcUrl;

    private int maxCount;

    private int step;

    @Override
    public PooledConnection getPooledConnection() {
        if (pooledConnectionVector.size() < 1){
            throw new RuntimeException("");
        }

        PooledConnection pooledConnection = null;

        try {
            pooledConnection = getReadPooledConnectionFromPool();
            while (pooledConnection == null){
                createPooledConnection(step);
                pooledConnection = getReadPooledConnectionFromPool();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pooledConnection;
    }

    @Override
    public void createPooledConnection(int count) {
        if (pooledConnectionVector.size() >= maxCount
                || pooledConnectionVector.size() + count >= maxCount){
            throw new RuntimeException("");
        }
        for (int i = 0; i < count; i++) {
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                PooledConnection pooledConnection = new PooledConnection(connection, false);
                pooledConnectionVector.add(pooledConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO:
    private PooledConnection getReadPooledConnectionFromPool() throws SQLException {
        for (PooledConnection pooledConnection : pooledConnectionVector){
            if (!pooledConnection.isBusy()){
                if (pooledConnection.getConnection().isValid(3000)){
                    pooledConnection.setBusy(true);
                    return pooledConnection;
                } else {
                    Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                    PooledConnection newPooledConnecton = new PooledConnection(connection, true);
                    return newPooledConnecton;
                }
            }
        }

        return null;
    }

}
