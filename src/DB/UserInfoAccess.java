package DB;

import java.sql.*;
import java.util.ArrayList;

public class UserInfoAccess {
    private String url = "jdbc:mysql://localhost:3306/usersinfo?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "@10091004Sy";

    public ArrayList<User> getUserInfo() {
        // 1. DB 연결 및 SQL 실행
        ArrayList<User> list = new ArrayList<>(); // 결과 데이터를 담을 리스트
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 2. DB 연결
            conn = DriverManager.getConnection(url, user, password);
            
            // 3. SQL 실행
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 회원정보"; 
            rs = stmt.executeQuery(sql);

            // 4. SQL 결과 처리
            while (rs.next()) {
                String id = rs.getString("id");
                String pw = rs.getString("pw");

                User dto = new User(id, pw);
                list.add(dto); // 리스트에 User 객체 추가
            }
            }catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 5. 자원 해제
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return list;
    }
    
    public boolean setUserInfo(String newId, String newPw) {
    	// 1. DB 연결 및 SQL 실행
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO 회원정보 (id, pw) VALUES (?, ?)"; // id와 pw를 회원정보 테이블에 추가하는 SQL

        try {
            // 2. DB 연결
            conn = DriverManager.getConnection(url, user, password);

            // 3. PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);

            // 4. PreparedStatement에 데이터 바인딩
            pstmt.setString(1, newId);  // 첫 번째 ? 자리에 id 값 설정
            pstmt.setString(2, newPw);  // 두 번째 ? 자리에 pw 값 설정

            // 5. SQL 실행 (데이터 삽입)
            int result = pstmt.executeUpdate();  // executeUpdate는 INSERT, UPDATE, DELETE에 사용

            // 6. 삽입 성공 여부 확인
            return result > 0;  // 0보다 큰 값이 반환되면 성공
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리
            return false;
        } finally {
            // 7. 자원 해제
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}