package pages;

import javax.swing.*;
import java.awt.*; 
import Session.UserSession;

public class App extends JFrame{
	private CardLayout cardLayout;
    private JPanel cardPanel; //페이지 패널들을 담음
    private UserSession userSession;
	public App() {
		setTitle("JAVA응용 기말 프로젝트 - 무비록 Movielog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        userSession = new UserSession();
        
        //페이지 추가
        cardPanel.add(new Main(cardLayout, cardPanel), "Main"); 
        cardPanel.add(new SignUp(cardLayout, cardPanel), "SignUp"); 
        cardPanel.add(new LogIn(cardLayout, cardPanel, userSession), "LogIn"); 
        cardPanel.add(new MovieLog(cardLayout, cardPanel, userSession),"MovieLog");
        
        add(cardPanel);
        setVisible(true);
	}
	public static void main(String[] args) {	
		new App();
    }
}