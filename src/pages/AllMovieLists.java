package pages;

import javax.swing.*;
import DB.MovieData;
import DB.MovieDataAccess;
import Session.UserSession;
import components.ImageMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AllMovieLists extends JPanel {
    private String userId;
    private JPanel posterPanel;  // 포스터를 담을 패널
    private JScrollPane scrollPane; //포스터 양이 많을 때를 대비한 스크롤
    private UserSession userSession;
    public AllMovieLists(UserSession userSession) {
    	this.userSession = userSession;
        userId = userSession.getUserId();
        // 제목 라벨 설정
        JLabel title = new JLabel("전체 영화 보기");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 30));

        // posterPanel 초기화
        posterPanel = new JPanel();
        posterPanel.setLayout(new GridLayout(0, 5, 10, 10));  // 영화 포스터가 여러 개 나올 수 있게 GridLayout 설정

        // UI 갱신
        updateUI(userId);

        // 스크롤 기능 추가
        scrollPane = new JScrollPane(posterPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH); 
        add(scrollPane, BorderLayout.CENTER);  
        
        userSession.addListener(this::updateUI);
    }

    // 영화 리스트를 갱신하는 메서드
    public void updateUI(String newUserId) {
    	userId = newUserId;
    	
    	
    	System.out.println("AllMovieList");
        // MovieDataAccess 객체 생성
        MovieDataAccess movie = new MovieDataAccess();
        ArrayList<MovieData> myList = movie.getAllMovieData();

        posterPanel.removeAll();  // 기존의 영화 포스터들 모두 제거

        // 영화 리스트에 있는 데이터를 posterPanel에 추가
        for (MovieData movieData : myList) {
            String posterPath = movieData.getImgUrl();
            ImageIcon posterIcon = new ImageIcon(posterPath);
            
            System.out.println("all:"+posterPath);

            // 이미지 크기 조정
            Image img = posterIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            posterIcon = new ImageIcon(img);
            JLabel posterLabel = new JLabel(posterIcon);
            posterPanel.add(posterLabel);

            // 이미지 클릭 시 이벤트 처리
            posterLabel.addMouseListener(new MouseAdapter() { 
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        new ImageMenu(e.getComponent(), movieData, userSession); 
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        new ImageMenu(e.getComponent(), movieData, userSession);
                    }
                }
            });
        }

        // UI 갱신
        revalidate();
        repaint();
    }
}
