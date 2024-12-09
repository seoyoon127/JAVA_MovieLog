package pages;

import javax.swing.*;

import DB.User;
import DB.UserInfoAccess;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Session.UserSession;

public class LogIn extends JPanel{
	private final UserSession userSession;
	public LogIn(CardLayout cardLayout, JPanel cardPanel, UserSession Session) {
		userSession = Session;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("로그인");
		title.setFont(new Font("Monospaced", Font.BOLD, 40));
		title.setAlignmentX(CENTER_ALIGNMENT);
		
		//입력창 패널 생성
		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); 
        inputPanel.setMaximumSize(new Dimension(400, 150)); 

        inputPanel.add(new JLabel("ID"));
        JTextField ID = new JTextField(15);
        inputPanel.add(ID); 

        inputPanel.add(new JLabel("PW"));
        JPasswordField PW = new JPasswordField(15);
        inputPanel.add(PW); 
		
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        //제출 버튼
        JButton doneBtn = new JButton("로그인 하기");
        doneBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
        doneBtn.setAlignmentX(CENTER_ALIGNMENT);
        doneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(! validateLogin(ID.getText(),new String(PW.getPassword()))) { // ID나 PW가 일치하지 않을 시
            		JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 일치하지 않습니다.", "Login validate alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}else {
            		String userId = ID.getText();
            		// 사용자가 로그인하거나 정보를 업데이트할 때
            		userSession.setUserId(userId);
            		cardLayout.show(cardPanel, "MovieLog");
            	}
        	};
        });
        
		add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(50));
        add(inputPanel);
        add(Box.createVerticalStrut(50));
        add(doneBtn);
	}
	//등록된 로그인 정보인지 확인
	private boolean validateLogin(String id, String pw) {
		UserInfoAccess access = new UserInfoAccess();
        ArrayList<User> userList = access.getUserInfo();
        if (userList != null) {
            for (User user : userList) {
                if (user.getId().equals(id) && user.getPw().equals(pw)) {
                    return true; // ID와 비밀번호가 일치하면 로그인 성공
                }
            }
        }
        return false;
	}
}
