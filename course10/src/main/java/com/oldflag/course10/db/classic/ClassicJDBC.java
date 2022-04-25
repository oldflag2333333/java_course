package com.oldflag.course10.db.classic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author oldFlag
 * @since 2022/4/3
 */
@Component
public class ClassicJDBC {

    @Autowired
    @Qualifier("classicConnManager")
    private ClassicConnManager connManager;

    public int select(String sql, List<String> keySet) throws SQLException {
        Connection conn = connManager.getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                for (String s : keySet) {
                    System.out.println(rs.getObject(s));
                }
            }

            return rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connManager.releaseConnection(conn);
        }
        return 0;
    }

    public Statement getStatement() throws SQLException {
        Connection conn = connManager.getConnection();
        try {
           return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void release(Statement statement) throws SQLException {
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
    }

    public boolean save(Statement statement, String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            return false;
        }
        boolean result = false;
        try {
            result = statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public PreparedStatement prepare(String template) throws SQLException {
        Connection conn = connManager.getConnection();
        conn.setAutoCommit(false);
        return conn.prepareStatement(template);
    }

    public boolean preparedSave(PreparedStatement statement) throws SQLException {
        boolean result = false;
        Connection conn = statement.getConnection();
        try {
            statement.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
//            connManager.releaseConnection(conn);
        }

        return result;
    }

    public boolean preparedSaveBatch(PreparedStatement statement) throws SQLException {
        boolean result = false;
        Connection conn = statement.getConnection();
        try {
            System.out.println("开始执行");
            statement.executeBatch();
            System.out.println("准备提交");
            conn.commit();
            System.out.println("执行结束");
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            connManager.releaseConnection(conn);
        }

        return result;
    }

}
