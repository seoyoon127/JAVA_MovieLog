package DB;

import java.sql.*;
import java.util.ArrayList;

public class MovieDataAccess {
    private String url = "jdbc:mysql://localhost:3306/allmovies?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "@10091004Sy";

    public ArrayList<MovieData> getAllMovieData() {
        // 1. DB 연결 및 SQL 실행
        ArrayList<MovieData> list = new ArrayList<>(); // 결과 데이터를 담을 리스트
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 2. DB 연결
            conn = DriverManager.getConnection(url, user, password);
            
            // 3. SQL 실행
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 영화목록"; 
            rs = stmt.executeQuery(sql);

            // 4. SQL 결과 처리
            while (rs.next()) {
                String id = rs.getString("id");
                String imgUrl = rs.getString("img_url");
                String title = rs.getString("title");
                Date releaseDate = rs.getDate("release_date");
                double rate = rs.getDouble("rate");
                String cast= rs.getString("cast");
                String review= rs.getString("review");
                int serialNum = rs.getInt("serial_num");
                
                MovieData dto = new MovieData(id, imgUrl, title, releaseDate, rate, cast, review, serialNum);
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
    public ArrayList<MovieData> getMyMovieData(String loginId) {
        ArrayList<MovieData> myList = new ArrayList<>(); 
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 영화목록"; 
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id");
                if(!id.equals(loginId))
                	continue;
                String imgUrl = rs.getString("img_url");
                String title = rs.getString("title");
                Date releaseDate = rs.getDate("release_date");
                double rate = rs.getDouble("rate");
                String cast= rs.getString("cast");
                String review= rs.getString("review");
                int serialNum = rs.getInt("serial_num");
                
                MovieData dto = new MovieData(id, imgUrl, title, releaseDate, rate, cast, review, serialNum);
                System.out.println(dto);
                myList.add(dto); 
            }
            }catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return myList;
    }
    public boolean setMovieData(MovieData newMovieData) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO 영화목록 (id,img_url, title, release_date, rate, cast, review ) VALUES (?, ?, ?, ?, ?, ?, ?)"; 

        try {
            conn = DriverManager.getConnection(url, user, password);

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newMovieData.getId());
            pstmt.setString(2, newMovieData.getImgUrl()); 
            pstmt.setString(3, newMovieData.getTitle()); 
            java.util.Date releaseDate = newMovieData.getReleaseDate();
            java.sql.Date sqlReleaseDate = new java.sql.Date(releaseDate.getTime());
            pstmt.setDate(4, sqlReleaseDate);
            pstmt.setDouble(5, newMovieData.getRate()); 
            pstmt.setString(6, newMovieData.getCast()); 
            pstmt.setString(7, newMovieData.getReview()); 

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
    public boolean updateMovieData(MovieData newMovieData, int serialNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE 영화목록 SET id = ?, img_url = ?, title = ?, release_date = ?, rate = ?, cast = ?, review = ? WHERE serial_num = ?"; 

        try {
            conn = DriverManager.getConnection(url, user, password);

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newMovieData.getId());
            pstmt.setString(2, newMovieData.getImgUrl()); 
            pstmt.setString(3, newMovieData.getTitle()); 
            java.util.Date releaseDate = newMovieData.getReleaseDate();
            java.sql.Date sqlReleaseDate = new java.sql.Date(releaseDate.getTime());
            pstmt.setDate(4, sqlReleaseDate);
            pstmt.setDouble(5, newMovieData.getRate()); 
            pstmt.setString(6, newMovieData.getCast()); 
            pstmt.setString(7, newMovieData.getReview()); 
            pstmt.setInt(8, serialNum);
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
    public boolean deleteMovieData(int serialNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM 영화목록 WHERE serial_num = ?"; 

        try {
        	conn = DriverManager.getConnection(url, user, password);
 
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, serialNum);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                conn.commit();  // 변경 사항을 커밋
                return true;
            } else {
                conn.rollback();  // 실패 시 롤백
                return false;
            }
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