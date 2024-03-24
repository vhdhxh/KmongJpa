package com.talentmarket.KmongJpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BulkInsert {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/KmongJpa";
        String user = "root";
        String password = "u1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO users (email, name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            for (int i = 10001; i <= 5000000; i++) {
                String email = "test" + i + "@test.com";
                String name = "test" + i;

                statement.setString(1, email);
                statement.setString(2, name);
                statement.addBatch();

                // 매 10,000개 데이터마다 executeBatch() 실행
                if (i % 10000 == 0) {
                    statement.executeBatch();
                    conn.commit();
                }
            }

            // 나머지 데이터 insert
            statement.executeBatch();
            conn.commit();

            System.out.println("Bulk insert completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
