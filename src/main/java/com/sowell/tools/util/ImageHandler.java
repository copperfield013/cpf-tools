package com.sowell.tools.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;

public class ImageHandler {
	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public static void main(String[] args) throws IOException {
		String[] contents = { "榴莲披萨", "红咖喱鸡", "羊肉胡萝卜(加蚝油)" }; // 水印内容
		BufferedImage bufImg = renderImage(contents);

		String tarImgPath = "d:/src1.png"; // 待存储的地址
		// 输出图片
		FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
		ImageIO.write(bufImg, "png", outImgStream);
		outImgStream.flush();
		outImgStream.close();
		System.out.println("添加水印完成");

	}

	public static BufferedImage renderImage(String[] contents) {
		Font font = new Font("微软雅黑", Font.PLAIN, 30); // 水印字体
		Color markContentColor = new Color(200, 0, 0); // 水印图片色彩以及透明度
		
		try {
			// 读取原图片信息
			ClassPathResource srcImageFile = new ClassPathResource("eat-food.png");
			Image srcImg = ImageIO.read(srcImageFile.getInputStream());// 文件转化为图片
			int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
			int srcImgHeight = srcImg.getHeight(null) + contents.length * (font.getSize() + 10);// 获取图片的高
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

			Graphics2D gx = (Graphics2D) bufImg.getGraphics();
			gx.drawImage(srcImg, 0, 0, null);
			gx.setBackground(Color.WHITE);
			gx.clearRect(0, 0, srcImgWidth, srcImgHeight);
			gx.dispose();

			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				Graphics2D g = (Graphics2D) bufImg.getGraphics();
				g.setColor(markContentColor); // 根据图片的背景设置水印颜色
				g.setFont(font); // 设置字体

				// 设置水印的坐标
				int x = srcImgWidth - getWatermarkLength(content, g) - 30;
				int y = srcImgHeight - 10 - (contents.length - i - 1) * (font.getSize() + 10);
				g.drawString(content, x, y); // 画出水印
				g.dispose();
			}
			gx = (Graphics2D) bufImg.getGraphics();
			gx.drawImage(srcImg, 0, 0, null);

			gx.setPaint(Color.RED);
			gx.setStroke(new BasicStroke(4f));
			gx.drawLine(210, 260, 360, 260);

			gx.dispose();
			
			return bufImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
