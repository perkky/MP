/*import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;*/

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class OpenCV
{
	public static void main(String[] args)
	{
		//System.loadLibrary("opencv_java330");
		//Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
		//System.out.println( "mat = " + mat.dump() );
		BufferedImage oldImage = readImage("prac01ex02img01.png");
		BufferedImage croppedImage = cropImage(oldImage, 10, 10, 400, 400);
		BufferedImage drawImage = drawOnImage(oldImage, 10, 10, 400, 400);

		saveImage(croppedImage, "croppedImage.jpg");
		saveImage(drawImage, "drawImage.jpg");
	}
	
	private static void saveImage(BufferedImage image, String location)
	{
		try{
			File outputfile = new File(location);
			ImageIO.write(image, "jpg", outputfile);
		}
		catch (IOException e) {}
	}
	private static BufferedImage drawOnImage(BufferedImage image, int x1, int y1, int x2, int y2)
	{
		int width = image.getWidth();
        int height = image.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		//copy image
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				Color c = new Color(image.getRGB(i, j));
				newImage.setRGB(i,j, c.getRGB());
			}
		}
		
		for (int i = x1; i <= x2; i++)
		{
			for (int j = y1; j <= y2; j++)
			{
				if (i == x1 || i == x2 || j == y1 || j == y2)
				{
					Color c = new Color(0,255,0);
					newImage.setRGB(i, j, c.getRGB());
				}
			}
		}
		
		return newImage;
	}
	
	private static BufferedImage cropImage(BufferedImage image, int x1, int y1, int x2, int y2)
	{
		int width = image.getWidth();
        int height = image.getHeight();
		
		BufferedImage newImage = new BufferedImage(x2-x1+1, y2-y1+1, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int i = x1; i <= x2; i++)
		{
			for (int j = y1; j <= y2; j++)
			{
				Color c = new Color(image.getRGB(i, j));
				newImage.setRGB(i-x1, j-y1, c.getRGB());
			}
		}
		
		return newImage;
	}
	
	private static BufferedImage readImage(String location)
	{
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		try {
         File input = new File(location);
         image = ImageIO.read(input);
         int width = image.getWidth();
         int height = image.getHeight();
         
         int count = 0;
       
         
		} catch (Exception e) {}
	  
	  return image;
	}
}