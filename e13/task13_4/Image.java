//15824071 ZHANG JINGYANG
package task13_4;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class Image {
	
	// 画像をPNGファイルとして保存する
	void saveImage(BufferedImage img, String name) {
		try{
            File save = new File(name);
            ImageIO.write(img, "png", save);
            System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
	}
	// 左右反転する
	BufferedImage flipHorizontal(BufferedImage img) {
	    int width = img.getWidth();
	    int height = img.getHeight();

	    BufferedImage out = new BufferedImage(width, height, img.getType());

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int p = img.getRGB(x, y);
	            out.setRGB(width - 1 - x, y, p);
	        }
	    }
	    return out;
	}
	// 画像を上下反転する
	BufferedImage flipVertical(BufferedImage img) {
	    int width = img.getWidth();
	    int height = img.getHeight();

	    BufferedImage out = new BufferedImage(width, height, img.getType());

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int p = img.getRGB(x, y);
	            out.setRGB(x, height - 1 - y, p);
	        }
	    }
	    return out;
	}
	
	// 2枚の画像を横方向に連結
	BufferedImage Concatenate(BufferedImage img1, BufferedImage img2) {
		int w1 = img1.getWidth();
	    int h1 = img1.getHeight();
	    int w2 = img2.getWidth();
	    int h2 = img2.getHeight();

	    int width = w1 + w2;
	    int height = Math.max(h1, h2);
	    
	    BufferedImage out = new BufferedImage(width, height, img1.getType());

	 // 左側に1枚目の画像をコピー
	    for (int y = 0; y < h1; y++) {
	        for (int x = 0; x < w1; x++) {
	            out.setRGB(x, y, img1.getRGB(x, y));
	        }
	    }
	    
	 // 右側に2枚目の画像をコピー
	    for (int y = 0; y < h2; y++) {
	        for (int x = 0; x < w2; x++) {
	            out.setRGB(x + w1, y, img2.getRGB(x, y));
	        }
	    }

	    return out;
	}
	
	// 画像を表示する
	void showImage(BufferedImage img) {
        JFrame frame = new JFrame();
        frame.setSize(img.getWidth(), img.getHeight());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		if (args.length < 2) {
	        System.out.println("使い方: java Image <img1> <img2>");
	        System.out.println("例: java Image raze1.png raze2.png");
	        return;
	    }

	    try {
	        System.out.println("Working dir: " + new File(".").getAbsolutePath());

	        File f1 = new File(args[0]);
	        File f2 = new File(args[1]);

	        System.out.println("img1 path: " + f1.getAbsolutePath() + " exists=" + f1.exists());
	        System.out.println("img2 path: " + f2.getAbsolutePath() + " exists=" + f2.exists());

	        BufferedImage img1 = ImageIO.read(f1);
	        BufferedImage img2 = ImageIO.read(f2);

	        if (img1 == null) {
	            System.out.println("img1 を読み込めません（形式が非対応 or パスが違う）");
	            return;
	        }
	        if (img2 == null) {
	            System.out.println("img2 を読み込めません（形式が非対応 or パスが違う）");
	            return;
	        }

	        Image tool = new Image();

	        BufferedImage hflip = tool.flipHorizontal(img1);
	        tool.showImage(hflip);
	        tool.saveImage(hflip, "hflip.png");

	        BufferedImage vflip = tool.flipVertical(img1);
	        tool.saveImage(vflip, "vflip.png");

	        BufferedImage concat = tool.Concatenate(img1, img2);
	        tool.saveImage(concat, "concat.png");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

}
