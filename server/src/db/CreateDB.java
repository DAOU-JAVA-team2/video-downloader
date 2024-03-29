package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class CreateDB {
    public static void main(String[] args) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost:3306/java_pjt";
            String id = "root";
            String pass = "1111";

            Connection conn = DriverManager.getConnection(url, id, pass);
            Statement stmt = conn.createStatement();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("원하는 작업을 선택하세요:");
                System.out.println("1. 테이블 생성");
                System.out.println("2. 테이블 삭제");
                System.out.println("3. 종료");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createTables(stmt);
                        break;
                    case 2:
                        dropTables(stmt);
                        break;
                    case 3:
                        System.out.println("프로그램을 종료합니다.");
                        conn.close();
                        scanner.close();
                        return;
                    default:
                        System.out.println("올바른 선택지를 입력하세요.");
                }
            }
        } catch (Exception e) {
            System.out.println("DB Error");
        }
    }


    // functions
    // 테이블 생성
    public static void createTables(Statement stmt) {
        try {
            UserTableCreate(stmt);
            System.out.println("유저 테이블이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            System.out.println("유저 테이블 생성 중 오류가 발생했습니다.");
        }
        try {
            VideoTableCreate(stmt);
            System.out.println("비디오 테이블이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            System.out.println("비디오 테이블 생성 중 오류가 발생했습니다.");
        }
        try {
            FavoriteTableCreate(stmt);
            System.out.println("다운로드 테이블이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            System.out.println("다운로드 테이블 생성 중 오류가 발생했습니다.");
        }
    }

    // 테이블 삭제
    public static void dropTables(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS FAVORITE");
            System.out.println("다운로드 테이블이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("다운로드 테이블 삭제 중 오류가 발생했습니다.");
        }
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS VIDEO");
            System.out.println("비디오 테이블이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("비디오 테이블 삭제 중 오류가 발생했습니다.");
        }
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS USER");
            System.out.println("유저 테이블이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("유저 테이블 삭제 중 오류가 발생했습니다.");
        }
    }
    //// 2. UserTableCreate
    public static void UserTableCreate(Statement stmt) throws Exception {
        String sql = "CREATE TABLE USER(\n" +
                "\tUSER_ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\tNAME VARCHAR(255),\n" +
                "\tID VARCHAR(255) NOT NULL UNIQUE,\n" +
                "\tPASSWORD VARCHAR(255) NOT NULL,\n" +
                "\tACCESS VARCHAR(255)\n" +
                ");";
        stmt.executeUpdate(sql);
    }
    //// 3. VideoTableCreate
    public static void VideoTableCreate(Statement stmt) throws Exception {
        String sql = "CREATE TABLE VIDEO(\n" +
                "\tVIDEO_ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "\tTITLE VARCHAR(255) NOT NULL,\n" +
                "\tURL VARCHAR(255) NOT NULL UNIQUE,\n" +
                "\tVIEW_COUNT VARCHAR(255),\n" +
                "\tTHUMNAIL_URL VARCHAR(255)\n" +
                "\t)";
        stmt.executeUpdate(sql);
    }
    //// 4. FavoriteTableCreate
    public static void FavoriteTableCreate(Statement stmt) throws Exception {
        String sql = "CREATE TABLE FAVORITE(\n" +
                "    USER_ID INT,\n" +
                "    VIDEO_ID INT,\n" +
                "    PRIMARY KEY(USER_ID, VIDEO_ID),\n" +
                "    FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID),\n" +
                "    FOREIGN KEY(VIDEO_ID) REFERENCES VIDEO(VIDEO_ID)\n" +
                ")";
        stmt.executeUpdate(sql);
    }

}