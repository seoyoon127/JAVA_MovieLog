package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import DB.User;
import DB.UserInfoAccess;

public class SignUp extends JPanel{
	public SignUp(CardLayout cardLayout, JPanel cardPanel) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		JLabel title = new JLabel("회원가입");
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

        inputPanel.add(new JLabel("PW Check"));
        JPasswordField PWCheck = new JPasswordField(15);
        inputPanel.add(PWCheck);
   
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        //제출 버튼
        JButton doneBtn = new JButton("회원가입 하기");
        doneBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
        doneBtn.setAlignmentX(CENTER_ALIGNMENT);
        doneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//아이디 유효성 검사
            	String id = ID.getText();
            	if(id.isEmpty()) {
            		JOptionPane.showMessageDialog(null, "아이디가 입력되지 않았습니다.", "ID alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if(!id.contains("@")) {
            		JOptionPane.showMessageDialog(null, "아이디는 이메일 형식으로 입력해주세요", "ID alert", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	if(isIdDuplicate(id)) {
            		JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", "ID duplicate alert", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	//비밀번호 유효성 검사 
            	String pw =  new String(PW.getPassword());
            	if(pw == "") {
            		JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다.", "ID alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if(pw.length()<8 || pw.length()>16) {
            		JOptionPane.showMessageDialog(null, "비밀번호는 8자리 이상 16자리 이하로 입력해야 합니다.", "PW alert", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	//비밀번호 확인 유효성 검사
            	String pwc =  new String(PWCheck.getPassword());
            	if(!pwc.equals(pw)) {
            		JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "PWCheck alert", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	UserInfoAccess access = new UserInfoAccess();
            	if(access.setUserInfo(id, pw)) {
            		cardLayout.show(cardPanel, "LogIn");
            	}
            }
        });

		add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(50));
        add(inputPanel);
        add(Box.createVerticalStrut(50));
        add(doneBtn);
	}
	//이미 가입된 아이디인지 확인
		private boolean isIdDuplicate(String id) {
			UserInfoAccess access = new UserInfoAccess();
	        ArrayList<User> userList = access.getUserInfo();
	        if (userList != null) {
	            for (User user : userList) {
	                if (user.getId().equals(id)) {
	                    return true; // ID와 비밀번호가 일치하면 로그인 성공
	                }
	            }
	        }
	        return false;
		}
}
