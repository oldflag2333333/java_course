package com.oldflag.course10.db.classic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

    public boolean save(String sql) throws SQLException {
        Connection conn = connManager.getConnection();
        Statement statement;
        boolean result = false;
        try {
            statement = conn.createStatement();
            result = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connManager.releaseConnection(conn);
        }

        return result;
    }

    public boolean prepareSave(String sql) throws SQLException {
        Connection conn = connManager.getConnection();
        PreparedStatement statement;
        boolean result = false;
        try {

            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            statement.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            connManager.releaseConnection(conn);
        }

        return result;
    }


}
