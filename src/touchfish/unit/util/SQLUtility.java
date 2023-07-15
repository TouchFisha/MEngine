package touchfish.unit.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class SQLUtility {
    public static Connection Connection(String host, String database, String username, String password, String port) {
        try {
            Connection connection = DriverManager.getConnection ( "jdbc:mysql://"+host+":"+port+"/"+database, username, password );
            if (connection.isClosed()) {
                System.err.println("Connection Err");
            }
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static String objectToSqlKeys(Object object){
        StringBuilder res = new StringBuilder();
        res.append('(');
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object v = null;
            try {
                v = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (v != null) {
                res.append(field.getName()).append(',');
            }
        }
        res.deleteCharAt(res.length()-1);
        res.append(')');
        return res.toString();
    }
    public static String objectToSqlKeyValue(Object object){
        StringBuilder res = new StringBuilder();
        res.append(objectToSqlKeys(object)).append(" VALUES ").append(objectToSqlValues(object));
        return res.toString();
    }
    public static String objectToSqlValues(Object object){
        StringBuilder res = new StringBuilder();
        res.append('(');
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object v = null;
            try {
                v = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (v != null) {
                res.append("'").append(v).append("'").append(',');
            }
        }
        res.deleteCharAt(res.length()-1);
        res.append(')');
        return res.toString();
    }

    public static String mapToSqlKeys(Map<?,?> map){
        StringBuilder res = new StringBuilder();
        res.append('(');
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue()!=null) {
                res.append(entry.getKey()).append(',');
            }
        }
        res.deleteCharAt(res.length()-1);
        res.append(')');
        return res.toString();
    }
    public static String mapToSqlKeyValue(Map<?,?> object){
        StringBuilder res = new StringBuilder();
        res.append(mapToSqlKeys(object)).append(" VALUES ").append(mapToSqlValues(object));
        return res.toString();
    }
    public static String mapToSqlValues(Map<?,?> map){
        StringBuilder res = new StringBuilder();
        res.append('(');
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue()!=null) {
                res.append('\'').append(entry.getValue()).append('\'').append(',');
            }
        }
        res.deleteCharAt(res.length()-1);
        res.append(')');
        return res.toString();
    }
}
