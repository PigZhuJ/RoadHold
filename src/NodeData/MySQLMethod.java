package NodeData;

import java.sql.*;

import static NodeData.MySQLConstant.*;

/**
 * 数据库相关操作
 */
public class MySQLMethod {
    /**
     * get the MySQL connect
     *
     * @return retturn the connect about MySQL
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getMySQLConnected() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        Connection connection = DriverManager.getConnection(url,user,passWord);
        return connection;
    }

    /**
     * close the Connection
     * @param conn
     * @param statement
     * @throws SQLException
     */
    public void closeMySQLConnection(Connection conn, Statement statement) throws SQLException {
        statement.close();
        conn.close();
    }

    /**
     * close the Connection
     * @param conn
     * @param ps
     * @throws SQLException
     */
    public void closeMySQLConnection(Connection conn, PreparedStatement ps) throws SQLException {
        ps.close();
        conn.close();
    }

    /**
     * close the connection
     * @param conn
     * @param ps
     * @param rs
     * @throws SQLException
     */
    public void closeMySQLConnection(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException {
        conn.close();
        ps.close();
        rs.close();
    }
}
