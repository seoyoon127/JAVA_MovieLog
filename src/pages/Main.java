package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import components.CustomButton;

public class Main extends JPanel{
	public Main(CardLayout cardLayout, JPanel cardPanel) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		
		//제목 설정
        JLabel title = new JLabel("무비록");
        title.setFont(new Font("Monospaced", Font.BOLD, 40));
        title.setForeground(Color.MAGENTA);
        title.setAlignmentX(CENTER_ALIGNMENT);
        
        //부제목 설정
        JLabel subTitle = new JLabel(":영화를 기록하다");
        subTitle.setFont(new Font("Monospaced", Font.BOLD, 30));
        subTitle.setAlignmentX(CENTER_ALIGNMENT);
        //title.setHorizontalAlignment(SwingConstants.CENTER);
        
        //회원가입 버튼
        JButton SignUpBtn = new JButton ("회원가입");
        SignUpBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
        SignUpBtn.setAlignmentX(CENTER_ALIGNMENT);
        SignUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignUp");
            }
        });

        //로그인 버튼
        JButton LogInBtn = new JButton ("로그인");
        LogInBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
        LogInBtn.setAlignmentX(CENTER_ALIGNMENT);
        LogInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "LogIn");
            }
        });
        
        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(10));
        add(subTitle);
        add(Box.createVerticalStrut(100));
        add(SignUpBtn);
        add(Box.createVerticalStrut(20));
        add(LogInBtn);
	}
}
