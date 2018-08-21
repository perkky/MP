import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Prac3
{
	public static void main(String[] args)
	{
		System.loadLibrary("opencv_java330");
		
		Mat mat = readImage("prac03ex04img02.png", CvType.CV_8U);
		Mat n = edgeCannuy(mat);
		
		saveImage(n, "Canny edge7.png");
		
		//System.out.println( "mat = " + mat.dump() );
	}

	private static Mat edgeCannuy(Mat mat)
	{
		Mat edges = new Mat();
		
		Imgproc.Canny(mat, edges, 100, 200);
		
		return edges;
	}
	
	private static Mat shitomFilter(Mat mat)
	{
		Mat dest = mat.clone();
		MatOfPoint corners = new MatOfPoint();
		
		Imgproc.goodFeaturesToTrack(mat, corners, 16, 0.01, 10);
		
		Point[] arr = corners.toArray();
		for (int i = 0; i < corners.total(); i ++)
		{
			Imgproc.circle(dest, arr[i], 5 , new Scalar(150), 2 ,8 , 0);
		}
		
		return dest;
	}
	
	//parts were taken from https://stackoverflow.com/questions/41413509/opencv-java-harris-corner-detection
	private static Mat harrisFilter(Mat mat)
	{
		Mat dest = new Mat(), normal = new Mat(), scaled = new Mat();
		Mat fin = mat.clone();
		
		double k = 0.04; // different values work for different pics - 0.0005 kinda works for blured pics
		Imgproc.cornerHarris(mat, dest, 2, 3, k);
		Core.normalize(dest, normal, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
		//Core.convertScaleAbs(normal, scaled);
		
		int thresh = 100;
		for( int j = 0; j < normal.rows() ; j++)
		{
			for( int i = 0; i < normal.cols(); i++)
			{
				if ((int) normal.get(j,i)[0] > thresh)
				{
					Imgproc.circle(fin, new Point(i,j), 5 , new Scalar(150), 2 ,8 , 0);
					//System.out.println(normal.get(j,i)[0]);
				}
				
			}
		}
		
		return fin;
	}
	
	//holds the code used for linear filtering in question 2 prac 2
	private static void linearFiltering()
	{
		Mat mat = readImage("gray.jpg");
		
		//Kernel x
		double[] a = {	1.0, 4.0, 7.0, 4.0, 1.0,
						4.0, 16.0, 26.0, 16.0, 4.0,
						7.0, 26.0, 41.0, 26.0, 7.0,
						4.0, 16.0, 26.0, 16.0, 4.0,
						1.0, 4.0, 7.0, 4.0, 1.0 };	
		Mat kernelX = new Mat(5,5, CvType.CV_32F);
		kernelX.put(0,0, a);
		Core.multiply(kernelX, new Scalar(1/(double)(273.0)), kernelX);

		System.out.println(kernelX.dump());
		
		Mat dest1 = applyFilter2d(mat, kernelX);
		
		saveImage(dest1, "Guassian Kernel.jpg");
		
	}
	
	private static Mat applyFilter2d(Mat mat, Mat kernel)
	{
		Mat dest = new Mat();
		
		Imgproc.filter2D(mat, dest, -1, kernel);
		
		return dest;
	}
	
	private static Mat convertTo(Mat mat, int code)
	{
		Mat dest = new Mat();
		
		Imgproc.cvtColor(mat, dest, code);
		
		return dest;
	}
	
	private static Mat readImage(String location)
	{
		Mat img = Imgcodecs.imread(location);
		
		if (!img.empty())
			System.out.println("Successfully read image: " + location);
		else
			System.out.println("Error reading image: " + location);
		return img;
	}
	
	private static Mat readImage(String location, int type)
	{
		Mat img = Imgcodecs.imread(location, type);
		
		if (!img.empty())
			System.out.println("Successfully read image: " + location);
		else
			System.out.println("Error reading image: " + location);
		return img;
	}
	
	private static void saveImage(Mat mat, String location)
	{
		if (!mat.empty())
		{
			Imgcodecs.imwrite(location, mat);
			System.out.println("Successfully saved image: " + location);
		}
		else
			System.out.println("Error saving image: " + location);
		
	}
}