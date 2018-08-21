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

public class OpenCV
{
	public static void main(String[] args)
	{
		System.loadLibrary("opencv_java330");
		
		Mat mat1 = readImage("Sobel Kernel x.jpg", CvType.CV_8U);
		Mat mat2 = readImage("Sobel Kernel y.jpg", CvType.CV_8U);
		Mat n = add(mat1, mat2);
		
		saveImage(n, "Sobel kernel.jpg");
		
		//System.out.println( "mat = " + mat.dump() );
	}
	
	private static Mat add(Mat m1, Mat m2)
	{
		Mat dest = m1.clone();
		int width = (int)m1.size().width, height = (int)m1.size().height;
		
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				double[] data = { (dest.get(j,i)[0] + m2.get(j,i)[0] )};
				dest.put(j, i, data);
			}
			
		}
		
		return dest;
	}
	
	//affines the pixels of a gray scale image - can be modified for rgb
	//used for prac 2 question 4
	private static Mat affinePixel(Mat mat, double contrast, double brightness)
	{
		Mat dest = new Mat(mat.size(), CvType.CV_8U);
		
		int width = (int)mat.size().width;
		int height = (int)mat.size().height;
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{

				if (mat.get(j,i) != null)
				{
					double[] data = { contrast*mat.get(j,i)[0] + brightness};
					dest.put(j,i, data);
				}
				else
					System.out.println(i + " " + j);
			}
		}
		
		return dest;
	}
	
	//returns the mat of a median filter
	//size refers to the size of the filter eg size=3 is a 3x3
	//used in question 3 of prac 2
	private static Mat medianFilter(Mat mat, int size)
	{
		Mat dest = new Mat();
		
		Imgproc.medianBlur(mat, dest, size);
		
		return dest;
	}
	
	//returns the mat of a gaussian blur
	//used in question 2 of prac 2
	private static Mat gaussianBlur(Mat mat, double sigmaX)
	{
		Mat dest = new Mat();
		
		Imgproc.GaussianBlur(mat, dest, new Size(5,5), sigmaX);
		
		return dest;
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