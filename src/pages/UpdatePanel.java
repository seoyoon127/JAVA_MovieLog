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

public class UpdatePanel extends JPanel{
	private String imgPath; 
	private double rateValue;
	private String Id;
	private Runnable onUpdateSuccessListener;
	public UpdatePanel(UserSession userSession, MovieData movieData) {
		
		updateUserId(userSession.getUserId());
		userSession.addListener(this::updateUserId);
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		// 좌측: 이미지 패널
	    JPanel imagePanel = new JPanel();
	    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
	    imagePanel.setBackground(Color.WHITE);

	    // 기본 이미지 설정
	    imgPath = movieData.getImgUrl();
	    ImageIcon defaultImg = new ImageIcon(movieData.getImgUrl());
	    Image scaledImg = defaultImg.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH);
	    JLabel img = new JLabel(new ImageIcon(scaledImg));

	    // 이미지 클릭 시 파일 선택 및 로드
	    img.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            imgPath = ImageLoader.loadImageWithFileChooser(img);
	            if (imgPath != null) {  // 사용자가 이미지를 선택한 경우
	                // 새 이미지로 갱신
	                ImageIcon newImgIcon = new ImageIcon(imgPath);
	                Image newImg = newImgIcon.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH);
	                img.setIcon(new ImageIcon(newImg));  // 이미지를 JLabel에 설정

	                imagePanel.revalidate();  // UI 갱신
	                imagePanel.repaint();     // UI 갱신
	            }
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
		movieTitle.setText(movieData.getTitle());
		movieTitle.setFont(new Font("Monospaced", Font.BOLD, 20));
		
		SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner releaseDate = new JSpinner(dateModel);
        releaseDate.setValue((Date)movieData.getReleaseDate());
        releaseDate.setFont(new Font("Monospaced", Font.BOLD, 15));
        releaseDate.setEditor(new JSpinner.DateEditor(releaseDate, "yyyy-MM-dd"));
        
        JPanel ratePanel = new JPanel();
        double movieRate = movieData.getRate();
        int sliderValue = (int) (movieRate * 20);
        JSlider setRate =  new JSlider(JSlider.HORIZONTAL, 0, 100, sliderValue);  // 0-100 범위 설정
        JLabel rate = new JLabel("평점"+movieRate);
        ratePanel.add(rate);
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
		cast.setText(movieData.getCast());
		cast.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		PlaceHolder2 review = new PlaceHolder2("줄거리/감상평",10,50);
		review.setText(movieData.getReview());
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
		
		//영화정보 수정 버튼 생성
        JButton button = new JButton("수정하기");
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
            	//개봉일 날짜 추출
            	Date date = (Date) releaseDate.getValue();
            	MovieData save = new MovieData(Id, imgPath, movieTitle.getText(), date, rateValue, cast.getText(), review.getText());
            	MovieDataAccess movie = new MovieDataAccess();
            	if (movie.updateMovieData(save, movieData.getSerialNum())&& Id!=null) {
            		JOptionPane.showMessageDialog(null, "수정사항이 반영되었습니다.", "Movie updated", JOptionPane.INFORMATION_MESSAGE);
            		if (onUpdateSuccessListener != null) {
                        onUpdateSuccessListener.run(); // 수정 성공 후 다이얼로그 닫기
                    }     		
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
	//수정 성공 시 다이얼로그 닫기
	public void setOnUpdateSuccessListener(Runnable listener) {
        this.onUpdateSuccessListener = listener;
    }

    public boolean isUpdatedSuccessfully() {
        return true;
    }
}