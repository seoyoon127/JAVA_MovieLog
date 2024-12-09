package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MovieCommentAccess {
	private String url = "jdbc:mysql://localhost:3306/moviecomment?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "@10091004Sy";

    public ArrayList<MovieComment> getAllMovieComment() {
        // 1. DB 연결 및 SQL 실행
        ArrayList<MovieComment> list = new ArrayList<>(); // 결과 데이터를 담을 리스트
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 2. DB 연결
            conn = DriverManager.getConnection(url, user, password);
            
            // 3. SQL 실행
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 댓글목록"; 
            rs = stmt.executeQuery(sql);

            // 4. SQL 결과 처리
            while (rs.next()) {
                String id = rs.getString("id");
                int serialNum = rs.getInt("serial_num");
                String comment= rs.getString("comment");
                
                MovieComment dto = new MovieComment(id, serialNum, comment);
                list.add(dto);
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
    public boolean setMovieComment(MovieComment newMovieComment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO 댓글목록 (id, serial_num, comment) VALUES (?, ?, ?)"; 

        try {
            conn = DriverManager.getConnection(url, user, password);

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newMovieComment.getId());
            pstmt.setInt(2, newMovieComment.getSerialNum()); 
            pstmt.setString(3, newMovieComment.getComment()); 

            int result = pstmt.executeUpdate();

            return result > 0;  // 0보다 큰 값이 반환되면 성공
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } 
}
