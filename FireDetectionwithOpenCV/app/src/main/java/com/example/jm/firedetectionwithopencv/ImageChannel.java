package com.example.jm.firedetectionwithopencv;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.util.Vector;

import static org.opencv.core.Core.split;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2HSV;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2YCrCb;
import static org.opencv.imgproc.Imgproc.cvtColor;

/**
 * Created by JM on 2017-09-26.
 */

public class ImageChannel {
    Mat HSV_image;
    Mat RGB_image;
    Mat YCrCb_image;

    Mat R;
    Mat G;
    Mat B;
    Mat H;
    Mat S;
    Mat V;
    Mat Y;
    Mat Cr;
    Mat Cb;


    Vector<Mat> HSVPlanes;
    Vector<Mat> RGBPlanes;
    Vector<Mat> YCrCbPlanes;

    public ImageChannel(double rows, double cols){
        RGBPlanes = new Vector<Mat>();
        YCrCbPlanes = new Vector<Mat>();
        HSVPlanes = new Vector<Mat>();

        RGB_image = new Mat(new Size(rows,cols), CV_8U, Scalar.all(0));
        HSV_image = new Mat(new Size(rows,cols), CV_8U, Scalar.all(0));
        YCrCb_image = new Mat(new Size(rows,cols), CV_8U, Scalar.all(0));
        Mat R = new Mat();
        Mat G = new Mat();
        Mat B = new Mat();
        Mat H = new Mat();
        Mat S = new Mat();
        Mat V = new Mat();
        Mat Y = new Mat();
        Mat Cr = new Mat();
        Mat Cb = new Mat();

    }
    public ImageChannel(){

    }
    public void Make_RGB(Mat Image){
        Image.convertTo(Image,CV_8UC3);
        Log.i("채널수", String.valueOf(Image.channels()));
        split(Image, RGBPlanes); //rgb채널 분리하여 vector<Mat> RGBplanes_1에 저장
        B = RGBPlanes.get(2); //블루
        G = RGBPlanes.get(1);; //그린
        R = RGBPlanes.get(0);; //레드
    }

    public void Make_HSV(Mat Image){
        cvtColor(Image, HSV_image, COLOR_BGR2HSV);
        split(Image, HSVPlanes); //rgb채널 분리하여 vector<Mat> RGBplanes_1에 저장
        H = HSVPlanes.get(2); //블루
        S = HSVPlanes.get(1);; //그린
        V = HSVPlanes.get(0);; //레드

    }
    public void Make_YCrCb(Mat Image){
        Image.convertTo(Image,CV_8UC3);
        cvtColor(Image, YCrCb_image, COLOR_BGR2YCrCb);
        split(Image,YCrCbPlanes); //rgb채널 분리하여 vector<Mat> RGBplanes_1에 저장
        Y = YCrCbPlanes.get(2); //블루
        Cr = YCrCbPlanes.get(1);; //그린
        Cb = YCrCbPlanes.get(0);; //레드
    }

}
