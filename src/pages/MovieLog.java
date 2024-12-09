package pages;

import javax.swing.*;
import java.awt.*;

import Session.UserSession;

public class MovieLog extends JPanel{
	private JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
	public MovieLog(CardLayout cardLayout, JPanel cardPanel, UserSession userSession){
		setLayout(new BorderLayout());
		
		AllMovieLists allMovieLists = new AllMovieLists(userSession);
		MovieRank movieRank = new MovieRank(userSession);
		MyMovieLists myMovieLists = new MyMovieLists(userSession,allMovieLists, movieRank);
		
		pane.addTab("모든 영화",allMovieLists );
		pane.addTab("영화정보 등록",new AddMovieInfo(userSession, allMovieLists, myMovieLists, movieRank));
		pane.addTab("나의 영화", myMovieLists);
		pane.addTab("명예의 전당", movieRank);
		pane.addTab("무비록",null);
		
		pane.addChangeListener(e -> {
            int index = pane.getSelectedIndex();
            //무비록 탭이 클릭되었을 때 메인 페이지로 이동
            if (index == 4) {
            	//로그인 정보 삭제(자동 로그아웃)
            	userSession.setUserId(null);
            	cardLayout.show(cardPanel, "Main");
            }
        });
		
		add(pane, BorderLayout.CENTER);
	}
}