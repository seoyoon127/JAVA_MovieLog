package components;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;

public class PlaceHolder2 extends JTextArea {
    private String placeholder;
    private int maxLength = 400;
    private JLabel textLimit;
    public PlaceHolder2(String placeholder, int rows, int columns) {
    	super(rows, columns);
        this.placeholder = placeholder;

        //텍스트 영역의 테두리 설정
        this.setBorder(new LineBorder(Color.GRAY));  // 회색 테두리 설정

        // 줄 바꿈 설정
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        
        // 텍스트 길이 표시용 JLabel 초기화
        textLimit = new JLabel("0/" + maxLength);
        textLimit.setForeground(Color.BLUE);  // 파란색 텍스트
        textLimit.setFont(new Font("Monospaced", Font.PLAIN, 12));  // 폰트 설정
        this.setLayout(null); 
        this.add(textLimit);
        
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
        
        // 텍스트 길이 제한
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enforceMaxLength(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enforceMaxLength(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enforceMaxLength(e);
            }

         // 최대 길이를 넘지 않도록 처리
            private void enforceMaxLength(DocumentEvent e) {
                String text = getText(); // 현재 텍스트 가져오기
                if (text.length() > maxLength) {
                    // 최대 길이를 초과한 텍스트는 잘라냄
                    try {
                        getDocument().remove(maxLength, text.length() - maxLength);  // 초과된 부분만 제거
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                textLimit.setText(text.length() + "/" + maxLength);  // 텍스트 길이 갱신
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 플레이스홀더 표시 조건: 텍스트 비어 있고 포커스가 없을 때
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY); // 플레이스홀더 색상 설정
            Insets insets = getInsets(); // 여백 정보 가져오기
            int x = insets.left; // 시작 위치 X
            int y = g.getFontMetrics().getMaxAscent() + insets.top; // 시작 위치 Y
            g2.drawString(placeholder, x, y);        }
    }
    //글자 수 제한 하단에 표시
    public void doLayout() {
        super.doLayout();
        int labelWidth = 55;  // JLabel의 고정 너비
        int labelHeight = 20;  // JLabel의 고정 높이
        textLimit.setBounds(getWidth() - labelWidth -10, getHeight() - labelHeight - 10, labelWidth, labelHeight);
    }
}
