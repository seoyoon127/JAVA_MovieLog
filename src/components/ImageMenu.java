package components;

import javax.swing.*;
import DB.MovieData;
import DB.MovieDataAccess;

import java.awt.*;
import java.awt.event.*;

import pages.AllMovieLists;
import pages.DetailPanel;
import pages.MovieRank;
import pages.MyMovieLists;
import pages.UpdatePanel;
import Session.UserSession;

public class ImageMenu {
	private JDialog dialog = new JDialog();
	
	//MyMovieList
    public ImageMenu(Component component, MovieData movieData, UserSession userSession, MyMovieLists myMovieLists, AllMovieLists allMovieLists, MovieRank movieRank) {

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.WHITE);
        
        UpdatePanel updatePanel = new UpdatePanel(userSession,movieData);
        
        // 수정하기
        JMenuItem menuItem1 = new JMenuItem("수정하기");
        menuItem1.addActionListener(e -> {
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(1200, 800); 
            dialog.setLocationRelativeTo(component);
            dialog.add(updatePanel); 
            dialog.setVisible(true); 
            updatePanel.setOnUpdateSuccessListener(() -> {
            	myMovieLists.updateUI(userSession.getUserId()); 
            	allMovieLists.updateUI(userSession.getUserId()); 
            	movieRank.updateUI(userSession.getUserId());
            	System.out.println("updated");
            	
                dialog.dispose(); // 수정 성공 후 다이얼로그 닫기
            });
        });
        
        //삭제하기
        JMenuItem menuItem2 = new JMenuItem("삭제하기"); 
        menuItem2.addActionListener(e->{
        	MovieDataAccess movieaccess = new MovieDataAccess();
        	movieaccess.deleteMovieData(movieData.getSerialNum());
        	myMovieLists.updateUI(userSession.getUserId()); 
        	allMovieLists.updateUI(userSession.getUserId()); 
        	movieRank.updateUI(userSession.getUserId());
        });
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        popupMenu.show(component, 150, 250); 
    }
    
    //AllMovieList
	public ImageMenu(Component component, MovieData movieData, UserSession userSession) {
			
	        JPopupMenu popupMenu = new JPopupMenu();
	        popupMenu.setBackground(Color.WHITE);
	        
	        DetailPanel detailPanel = new DetailPanel(userSession,movieData); //상세 페이지 패널(commentPanel)
	        		
	        // 상세보기
	        JMenuItem menuItem1 = new JMenuItem("상세보기");
	        menuItem1.addActionListener(e -> {
	        	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialog.setSize(1200, 800); 
	            dialog.setLocationRelativeTo(component);
	            dialog.add(detailPanel); 
	            dialog.setVisible(true); 
	        });
	        
	        popupMenu.add(menuItem1);
	
	        popupMenu.show(component, 150, 220); 
	    }
}
