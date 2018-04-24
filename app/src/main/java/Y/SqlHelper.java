package Y;
        import android.util.Log;

        import java.sql.*;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.lang.String;
public class SqlHelper {

    public static Connection openConnection() {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            Log.d("sss", "加载成功 ");
            conn = DriverManager.getConnection("jdbc:mysql:"+"//192.168.42.233:3306/tushuguan?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","root");
            Log.d("sss", "连接成功 ");
        } catch (ClassNotFoundException e) {
            conn = null;
            Log.d("sss", "连接失败1 ");
        } catch (SQLException e) {
            conn = null;
            Log.d("sss", "连接失败2 ");
        }

        return conn;
    }

    public static void query(Connection conn, String sql) {

        if (conn == null) {
            return;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                System.out.println("id\t\t" + "name");
                while (!result.isAfterLast()) {
                    System.out.print(result.getString(idColumnIndex) + "\t\t");
                    System.out.println(result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
    }
    public static ResultSet Rs (Connection conn,String sql){
        ResultSet resultSet=null;
        if (conn == null) {
            return resultSet;
        }
        Statement statement = null;
        try{
            statement = conn.createStatement();
            if(statement!=null)
            {
                resultSet =statement.executeQuery(sql);
            }
        }catch (SQLException e){
            resultSet = null;
        }
        return resultSet;
    }
    public static boolean execSQL(Connection conn, String sql) {

        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }

        return execResult;
    }

}

