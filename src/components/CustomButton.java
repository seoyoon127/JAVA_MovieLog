package components;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton{
	public CustomButton(String s){
		super(s);
		setPreferredSize(new Dimension(500, 50)); 
//		setFocusPainted(false);          
//        setContentAreaFilled(false);  
//        setBorderPainted(false);  
	}
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.setColor(Color.GRAY);
//	}
}
