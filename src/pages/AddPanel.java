package pages;

import java.awt.*;
import javax.swing.*;
import DB.MovieData;
import DB.MovieDataAccess;
import Session.UserSession;
import components.ImageLoader;
import components.PlaceHolder;
import components.PlaceHolder2;

import java.awt.event.*;
import javax.swing.event.*;
import java.util.Date;

public class AddPanel extends JPanel{
	private String imgPath = null; 
	private double rateValue;
	private String Id;
	public AddPanel(UserSession userSession, AllMovieLists allMovieLists, MyMovieLists myMovieLists, MovieRank movieRank) {
		
		updateUserId(userSession.getUserId());
		userSession.addListener(this::updateUserId);
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		// 좌측: 이미지 패널
	    JPanel imagePanel = new JPanel();
	    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
	    imagePanel.setBackground(Color.WHITE);

	    // 기본 이미지 설정
	    ImageIcon defaultImg = new ImageIcon("images/gray.png");
	    Image scaledImg = defaultImg.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH);
	    JLabel img = new JLabel(new ImageIcon(scaledImg));

	    // 이미지 클릭 시 파일 선택 및 로드
	    img.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	imgPath = ImageLoader.loadImageWithFileChooser(img);
	        }
	    });
		
		imagePanel.add(Box.createVerticalStrut(100)); //이미지 상단 여백
        imagePanel.add(img);
        imagePanel.add(Box.createVerticalStrut(20));
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
		
        //우측: 입력 패널
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
		textFieldPanel.setBackground(Color.WHITE);
		PlaceHolder movieTitle = new PlaceHolder("제목");
		movieTitle.setFont(new Font("Monospaced", Font.BOLD, 20));
		SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner releaseDate = new JSpinner(dateModel);
        releaseDate.setFont(new Font("Monospaced", Font.BOLD, 15));
        releaseDate.setEditor(new JSpinner.DateEditor(releaseDate, "yyyy-MM-dd"));
        
        JPanel ratePanel = new JPanel();
        JLabel rate = new JLabel("평점");
        ratePanel.add(rate);
        JSlider setRate =  new JSlider(JSlider.HORIZONTAL, 0, 100, 50);  // 0-100 범위 설정
        setRate.setPaintTrack(true);
        setRate.setPaintLabels(true);
        setRate.setPaintTicks(true);
        setRate.setMajorTickSpacing(20);
        setRate.setMinorTickSpacing(5);
        setRate.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	rateValue = setRate.getValue() / 20.0;  // 0-100을 0.0-5.0 범위로 변환
                rate.setText("평점 "+rateValue);
            }
        });
        ratePanel.add(setRate);
        
		rate.setFont(new Font("Monospaced", Font.BOLD, 12));
		PlaceHolder cast = new PlaceHolder("감독/배우");
		cast.setFont(new Font("Monospaced", Font.BOLD, 12));
		PlaceHolder2 review = new PlaceHolder2("줄거리/감상평",10,50);
		review.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		textFieldPanel.add(Box.createVerticalStrut(90)); //텍스트 상단 여백
		textFieldPanel.add(movieTitle);
		textFieldPanel.add(Box.createVerticalStrut(10)); //간격 조정
		textFieldPanel.add(releaseDate);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(ratePanel);
		textFieldPanel.add(setRate);
		textFieldPanel.add(Box.createVerticalStrut(10)); 
		textFieldPanel.add(cast);
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
		
		//영화정보 저장 버튼 생성
        JButton button = new JButton("저장하기");
        button.setFont(new Font("Monospaced", Font.BOLD, 30));
        button.setPreferredSize(new Dimension(300, 80));
        
        JPanel btnPanel = new JPanel(); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setBackground(Color.WHITE);
        btnPanel.add(button);
        btnPanel.add(Box.createVerticalStrut(100));
        
        //버튼 클릭 시
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (imgPath == null) {
                    JOptionPane.showMessageDialog(null, "이미지를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            	//개봉일 날짜 추출
            	Date date = (Date) releaseDate.getValue();
            	
            	System.out.print(Id);
            	MovieData save = new MovieData(Id, imgPath, movieTitle.getText(), date, rateValue, cast.getText(), review.getText());
            	MovieDataAccess movie = new MovieDataAccess();
            	if (movie.setMovieData(save)) {
            		JOptionPane.showMessageDialog(null, "영화가 등록되었습니다.", "Movie added", JOptionPane.INFORMATION_MESSAGE);
            		resetFields(img, movieTitle, releaseDate, setRate, cast, review);      	
            		//영화 데이터를 사용하는 페이지 업데이트 (방금 등록한 데이터 반영)
            		allMovieLists.updateUI(userSession.getUserId());
            		myMovieLists.updateUI(userSession.getUserId());
            		movieRank.updateUI(userSession.getUserId());
            	}
        	};
        });
        
		//패널 배치
		add(allPanel, BorderLayout.CENTER);  
        add(btnPanel, BorderLayout.SOUTH);
	}
	public void updateUserId(String userId) {
        Id = userId; 
        revalidate();
        repaint();
    }
	private void resetFields(JLabel img, PlaceHolder movieTitle, JSpinner releaseDate, JSlider setRate, PlaceHolder cast, PlaceHolder2 review) {
	    // 이미지 초기화
	    ImageIcon defaultImg = new ImageIcon("images/gray.png");
	    Image scaledImg = defaultImg.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH);
	    img.setIcon(new ImageIcon(scaledImg));
	    imgPath = null;

	    // 텍스트 필드 초기화
	    movieTitle.setText("");
	    cast.setText("");
	    review.setText("");

	    // 개봉일 초기화
	    releaseDate.setValue(new Date()); // 오늘 날짜로 초기화

	    // 슬라이더 초기화
	    setRate.setValue(50);
	    rateValue = 2.5; // 기본 평점 값
	}

}
