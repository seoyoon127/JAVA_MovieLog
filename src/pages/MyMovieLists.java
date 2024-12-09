package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import DB.MovieDataAccess;
import Session.UserSession;
import DB.MovieData;
import components.ImageMenu;

public class MyMovieLists extends JPanel {
    private String userId;
    private JLabel title;
    private JPanel posterPanel; 
    private UserSession userSession;
    private AllMovieLists allMovieLists;
    private MovieRank movieRank;
    public MyMovieLists(UserSession userSession, AllMovieLists allMovieLists, MovieRank movieRank) {
    	this.userSession = userSession;
    	this.allMovieLists = allMovieLists;
    	this.movieRank = movieRank;
        userId = userSession.getUserId();
        
        title = new JLabel();
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 30));

        posterPanel = new JPanel();
        posterPanel.setLayout(new GridLayout(0, 5, 10, 10));

        updateUI(userId);

        // 스크롤 기능
        JScrollPane scrollPane = new JScrollPane(posterPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout()); // 레이아웃 추가
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
       

        // userSession에서 listener 추가
        userSession.addListener(this::updateUI);
    }

    // updateUI를 수정하여 title과 posterPanel만 업데이트
    public void updateUI(String newUserId) {
        userId = newUserId;

        title.setText((userId != null && !userId.isEmpty()) ? ("나의 영화") : "로그인 후 영화를 등록해보세요!");

        // 영화 리스트 가져오기
        MovieDataAccess movie = new MovieDataAccess();
        ArrayList<MovieData> myList = movie.getMyMovieData(userId);

        // 기존 posterPanel 비우기
        posterPanel.removeAll();

        // myList에 있는 영화 데이터를 posterPanel에 추가
        for (MovieData movieData : myList) {
            String posterPath = movieData.getImgUrl();
            System.out.println("my:"+posterPath);
            ImageIcon posterIcon = new ImageIcon(posterPath);

            // 이미지 크기 조정
            Image img = posterIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            posterIcon = new ImageIcon(img);
            JLabel posterLabel = new JLabel(posterIcon);
            posterPanel.add(posterLabel);
 
            posterLabel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { 
                        new ImageMenu(e.getComponent(), movieData, userSession, MyMovieLists.this, allMovieLists, movieRank);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { 
                        new ImageMenu(e.getComponent(), movieData, userSession, MyMovieLists.this, allMovieLists, movieRank);
                    }
                }
            });
        }

        // UI 갱신
        revalidate();
        repaint();
    }
}
