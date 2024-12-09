package components;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageLoader {
    private static final int DEFAULT_WIDTH = 250;
    private static final int DEFAULT_HEIGHT = 360;

    // 파일 선택 및 이미지 로드
    public static String loadImageWithFileChooser(JLabel imgLabel) {
    	JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "gif"));
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            imgLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH)));
            return selectedFile.getAbsolutePath(); // 파일 경로 반환
        }
        return null; // 파일 선택 취소 시 null 반환
//        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF, PNG Images", "jpg", "gif", "png");
//        chooser.setFileFilter(filter);
//
//        int result = chooser.showOpenDialog(null);
//        if (result != JFileChooser.APPROVE_OPTION) {
//            JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        File selectedFile = chooser.getSelectedFile();
//        loadImage(selectedFile.getPath(), imgLabel);
    }

    // 비율을 유지하며 이미지를 로드
    public static void loadImage(String filePath, JLabel imgLabel) {
        ImageIcon originalIcon = new ImageIcon(filePath);
        Image originalImg = originalIcon.getImage();

        int originalWidth = originalImg.getWidth(null);
        int originalHeight = originalImg.getHeight(null);

        // 이미지 크기 비율 계산
        double widthRatio = (double) DEFAULT_WIDTH / originalWidth;
        double heightRatio = (double) DEFAULT_HEIGHT / originalHeight;
        double scaleRatio = Math.min(widthRatio, heightRatio);

        int scaledWidth = (int) (originalWidth * scaleRatio);
        int scaledHeight = (int) (originalHeight * scaleRatio);
        Image scaledImg = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // JLabel에 이미지 설정
        imgLabel.setIcon(new ImageIcon(scaledImg));
        imgLabel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        imgLabel.revalidate();
        imgLabel.repaint();
    }
}
