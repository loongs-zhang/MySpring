package com.example.design.template.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class DaoTemplate<T> {

    private final String url;

    private final String user;

    private final String password;

    private Connection connection;

    public DaoTemplate(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    private Connection connectDatabase() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private PreparedStatement getPreparedStatement(String sql)
            throws ClassNotFoundException, SQLException {
        this.loadDriver();
        connection = this.connectDatabase();
        return this.prepareStatement(sql);
    }

    public final void closeConnection() throws SQLException {
        this.connection.close();
    }

    public final List<T> executeQuery(String sql) throws Exception {
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = this.getPreparedStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapping(resultSet));
            }
        }
        return result;
    }

    public final int executeUpdate(String sql) throws Exception {
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            return statement.executeUpdate();
        }
    }

    private String higherUnderlineAfterFirstCase(String fieldName) {
        //下划线后首字母大写，原理ASCII码
        char[] chars = fieldName.toCharArray();
        int index = fieldName.indexOf("_");
        if (index == -1) {
            return fieldName;
        } else {
            chars[index + 1] -= 32;
        }
        return higherUnderlineAfterFirstCase(String.valueOf(chars).replaceFirst("_", ""));
    }

    protected T mapping(ResultSet resultSet) throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> targetClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        T instance = targetClass.newInstance();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String fieldName = metaData.getColumnName(i);
            String fieldType = metaData.getColumnClassName(i);
            try {
                Field field = targetClass.getDeclaredField(higherUnderlineAfterFirstCase(fieldName));
                field.setAccessible(true);
                if (fieldType.endsWith("Long")) {
                    field.set(instance, resultSet.getLong(fieldName));
                } else if (fieldType.endsWith("String")) {
                    field.set(instance, resultSet.getString(fieldName));
                } else if (fieldType.endsWith("Timestamp")) {
                    field.set(instance, resultSet.getTimestamp(fieldName));
                } else if (fieldType.endsWith("BigDecimal")) {
                    field.set(instance, resultSet.getBigDecimal(fieldName));
                } else if (fieldType.endsWith("Date")) {
                    field.set(instance, resultSet.getDate(fieldName));
                } else if (fieldType.endsWith("Byte")) {
                    field.set(instance, resultSet.getByte(fieldName));
                } else if (fieldType.endsWith("Short")) {
                    field.set(instance, resultSet.getShort(fieldName));
                } else if (fieldType.endsWith("Boolean")) {
                    field.set(instance, resultSet.getBoolean(fieldName));
                } else if (fieldType.endsWith("Time")) {
                    field.set(instance, resultSet.getTime(fieldName));
                } else if (fieldType.endsWith("Bytes")) {
                    field.set(instance, resultSet.getBytes(fieldName));
                } else if (fieldType.endsWith("Array")) {
                    field.set(instance, resultSet.getArray(fieldName));
                } else if (fieldType.endsWith("Float")) {
                    field.set(instance, resultSet.getFloat(fieldName));
                } else if (fieldType.endsWith("Double")) {
                    field.set(instance, resultSet.getDouble(fieldName));
                } else if (fieldType.endsWith("Blob")) {
                    field.set(instance, resultSet.getBlob(fieldName));
                } else if (fieldType.endsWith("Clob")) {
                    field.set(instance, resultSet.getClob(fieldName));
                } else if (fieldType.endsWith("Int")) {
                    field.set(instance, resultSet.getInt(fieldName));
                } else if (fieldType.endsWith("URL")) {
                    field.set(instance, resultSet.getURL(fieldName));
                } else {
                    field.set(instance, resultSet.getObject(fieldName));
                }
            } catch (NoSuchFieldException ignored) {
            }
        }
        return instance;
    }

}
