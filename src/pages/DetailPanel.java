package pages;

import java.awt.*;
import javax.swing.*;

import DB.MovieComment;
import DB.MovieCommentAccess;
import DB.MovieData;
import Session.UserSession;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailPanel extends JPanel{
	private String Id;
	int cnt = 0;
	private UserSession userSession;
	private JButton seeCommentsBtn;
	private MovieCommentAccess cAccess = new MovieCommentAccess();
	public DetailPanel(UserSession userSession, MovieData movieData) {

		this.userSession = userSession;
		updateUserId(userSession.getUserId());
		userSession.addListener(this::updateUserId);
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		// 좌측: 이미지 패널
	    JPanel imagePanel = new JPanel();
	    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
	    imagePanel.setBackground(Color.WHITE);

	    // 이미지 설정
	    ImageIcon defaultImg = new ImageIcon(movieData.getImgUrl());
	    Image scaledImg = defaultImg.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH);
	    JLabel img = new JLabel(new ImageIcon(scaledImg));
		
		imagePanel.add(Box.createVerticalStrut(100)); //이미지 상단 여백
        imagePanel.add(img);
        imagePanel.add(Box.createVerticalStrut(20));
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
		
        //우측: 정보 패널
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
		textFieldPanel.setBackground(Color.WHITE);
		
		JLabel movieTitle = new JLabel(movieData.getTitle());
		movieTitle.setFont(new Font("Monospaced", Font.BOLD, 20));
		
		Date date = movieData.getReleaseDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        JLabel releaseDate = new JLabel(formattedDate);
        releaseDate.setFont(new Font("Monospaced", Font.BOLD, 15));
        
        JLabel rate = new JLabel("평점"+movieData.getRate());
		rate.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		JLabel cast = new JLabel(movieData.getCast());
		cast.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		JLabel id = new JLabel("작성자: "+movieData.getId().split("@")[0]);
		id.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		JLabel review = new JLabel(movieData.getReview());
		review.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		textFieldPanel.add(Box.createVerticalStrut(90)); //텍스트 상단 여백
		textFieldPanel.add(movieTitle);
		textFieldPanel.add(Box.createVerticalStrut(10)); //간격 조정
		textFieldPanel.add(releaseDate);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(rate);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(cast);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(id);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(review);

		
		//간격 생성을 위한 nullPanel
		JPanel nullPanel = new JPanel();
		nullPanel.setPreferredSize(new Dimension(50, 100));
		nullPanel.setBackground(Color.WHITE);
		
		JPanel allPanel = new JPanel();
		allPanel.setBackground(Color.WHITE);
		allPanel.add(imagePanel);
		allPanel.add(nullPanel);
		allPanel.add(textFieldPanel);
		
		ArrayList<MovieComment> commentList = cAccess.getAllMovieComment();
		
		//댓글 달기 버튼 생성
		for(MovieComment comment : commentList) {
			if(comment.getSerialNum() == movieData.getSerialNum())
				cnt++;
		}
		seeCommentsBtn = new JButton("댓글 " + cnt); 
		seeCommentsBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
        
        JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setBackground(Color.WHITE);
        btnPanel.add(seeCommentsBtn);
        btnPanel.add(Box.createVerticalStrut(100));
        
		//댓글창 열기
		seeCommentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 refreshComments(movieData);
        	};
        });
        
		//패널 배치
		add(allPanel, BorderLayout.CENTER);  
        add(btnPanel, BorderLayout.SOUTH);
	}
	//댓글 달면 화면 갱신
	private void refreshComments(MovieData movieData) {
	    ArrayList<MovieComment> updatedCommentList = cAccess.getAllMovieComment(); // 새 댓글 데이터 가져오기
	    
	    JPanel commentPanel = new JPanel();
	    commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

	    int newCnt = 0;
	    for (MovieComment comment : updatedCommentList) {
	        if (comment.getSerialNum() == movieData.getSerialNum()) {
	        	newCnt++;
	            JPanel cPanel = new JPanel();
	            JLabel commenter = new JLabel(comment.getId().equals(movieData.getId()) ? comment.getId().split("@")[0] + "(글쓴이): " : comment.getId().split("@")[0] + ": ");
	            commenter.setFont(new Font("Monospaced", Font.BOLD, 14));
	            JLabel c = new JLabel(comment.getComment());
	            cPanel.add(commenter);
	            cPanel.add(c);
	            commentPanel.add(cPanel);
	            commentPanel.add(Box.createVerticalStrut(10));
	        }
	    }

	    //댓글쓰기
        JPanel inputPanel = new JPanel(new BorderLayout());
    	JTextField commentField = new JTextField();
    	commentField.setPreferredSize(new Dimension(100, 30));
    	JButton setCommentBtn = new JButton("댓글달기");
    	inputPanel.add(commentField, BorderLayout.CENTER);
        inputPanel.add(setCommentBtn, BorderLayout.EAST);
        
        //댓글창 패널 생성
        JDialog dialog = new JDialog();
        dialog.setTitle("#"+movieData.getSerialNum()+" "+movieData.getTitle()+"댓글");
    	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(800, 500); 
        dialog.setLocationRelativeTo(commentPanel);
        dialog.setLayout(new BorderLayout());
        dialog.add(commentPanel, BorderLayout.NORTH);
        dialog.add(inputPanel, BorderLayout.SOUTH);
        dialog.setVisible(true); 
        
        //버튼 클릭 시 댓글 등록하기
    	setCommentBtn.addActionListener((new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String newComment = commentField.getText();
    			System.out.println(newComment);
            	MovieComment nc = new MovieComment(userSession.getUserId(), movieData.getSerialNum(), newComment);
            	if (cAccess.setMovieComment(nc)) { 
            		commentPanel.removeAll();
            		dialog.dispose(); // 이전 버전 다이알로그 삭제
            		refreshComments(movieData); //새 댓글 반영
            	}
            	else
            		JOptionPane.showMessageDialog(null, "댓글을 등록할 수 없습니다.", "Comment error", JOptionPane.ERROR_MESSAGE);
    		}
    	}));
    	
    	cnt = newCnt;  // 새 댓글 수로 갱신
        seeCommentsBtn.setText("댓글 " + cnt);
    	
	    // UI 업데이트
	    revalidate();
	    repaint();
	}

	public void updateUserId(String userId) {
        Id = userId; 
        revalidate();
        repaint();
    }
}