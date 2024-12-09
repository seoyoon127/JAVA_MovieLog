package components;

import javax.swing.*;
import java.awt.*;

public class PlaceHolder extends JTextField {
    private String placeholder;

    public PlaceHolder(String placeholder) {
        this.placeholder = placeholder;

        // 포커스 및 텍스트 상태 변경 시 UI 갱신
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                repaint();
            }
        });
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 텍스트가 비어 있고 포커스를 가지지 않았을 때만 플레이스홀더 표시
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY); // 플레이스홀더 색상 설정
            g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top); //insets:여백
        }
    }
}
