package com.zy.demo.database.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PooledConnection {

    private Connection connection;

    private boolean isBusy;

    public PooledConnection(Connection connection, boolean isBusy) {
        this.connection = connection;
        this.isBusy = isBusy;
    }

    public void close(){
        this.isBusy = false;
    }

    public ResultSet query(String sql){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }
}
