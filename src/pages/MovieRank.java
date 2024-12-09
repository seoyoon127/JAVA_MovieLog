package pages;

import javax.swing.*;

import DB.MovieData;
import DB.MovieDataAccess;
import Session.UserSession;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MovieRank extends JPanel{
	private JLabel title;
	private String userId;
	private JPanel rankPanel;
	private JPanel rankPanel1;
	private JPanel rankPanel2;
	public MovieRank(UserSession userSession){
		
		title = new JLabel();
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 30));
        
        setLayout(new BorderLayout()); 
		add(title, BorderLayout.NORTH); 
		
		rankPanel = new JPanel();
		
		//좌측 : 1위 패널
		rankPanel1 = new JPanel();
		//우측 : 2~5위 패널
		rankPanel2 = new JPanel();
		rankPanel2.setLayout(new GridLayout(2, 2, 20, 20));
		
		//패널 간 간격을 위한 nullPanel 
		JPanel nullPanel = new JPanel();
		nullPanel.setPreferredSize(new Dimension(50, 100));
		
		rankPanel.add(rankPanel1);
		rankPanel.add(nullPanel);
		rankPanel.add(rankPanel2);
		
		add(rankPanel);
		// userId 값이 변경될 때마다 UI 업데이트
        updateUI(userSession.getUserId());
		userSession.addListener(this::updateUI);
	}
	
	public void updateUI(String newUserId) {
		userId = newUserId;

        title.setText((userId != null) ? (userId.split("@")[0] + "님의 TOP 5") : "");
        
        rankPanel1.removeAll();
        rankPanel2.removeAll();

        // 영화 리스트 가져오기
        MovieDataAccess movie = new MovieDataAccess();
        ArrayList<MovieData> myList = movie.getMyMovieData(userId);

        //rate를 기준으로 내림차순 정렬
        myList.sort(Comparator.comparingDouble(MovieData::getRate).reversed());
        

        for(int i = 0; i < 5; i++) {
        	MovieData movieData = (i < myList.size()) ? myList.get(i) : null; 
        	String posterPath = movieData!=null ? movieData.getImgUrl() : "images/gray.png";
            ImageIcon posterIcon = new ImageIcon(posterPath);

            Image img;
            // 이미지 크기 조정
            if(i == 0) 
            	img = posterIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            else
            	img = posterIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            posterIcon = new ImageIcon(img);
            JLabel posterLabel = new JLabel(posterIcon);
            
            JPanel panel = new JPanel();
            panel.add(posterLabel);
            if(movieData!=null)
            	panel.add(new JLabel(i+1 + "위: " + movieData.getTitle() + movieData.getRate()));
            
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            if(i == 0)
            	rankPanel1.add(panel);
            else
            	rankPanel2.add(panel);
        }

        // UI 갱신
        revalidate();
        repaint();
    }
}
