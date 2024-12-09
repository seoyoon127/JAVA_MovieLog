package pages;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Date;
import Session.UserSession;

public class AddMovieInfo extends JPanel {
    private JLabel title;
    private String userId;
    private AddPanel addPanel;
    public AddMovieInfo(UserSession userSession, AllMovieLists allMovieLists, MyMovieLists myMovieLists, MovieRank movieRank) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        title = new JLabel();
        title.setFont(new Font("Monospaced", Font.BOLD, 30));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);  
         
        // userId 값이 변경될 때마다 UI 업데이트
        updateUI(userSession.getUserId());
        
        addPanel = new AddPanel(userSession, allMovieLists, myMovieLists, movieRank);
        add(addPanel);
        
        // 리스너 등록
        userSession.addListener(this::updateUI);

    }

    // userId 값에 따라 UI 업데이트
    private void updateUI(String newUserId) {
        userId = newUserId;

        title.setText((userId != null) ? (userId.split("@")[0] + "님이 좋아하는 영화를 등록해보세요!") : "로그인 후 영화를 등록해보세요!");
        
        // UI 갱신
        revalidate();
        repaint();
    }
}
