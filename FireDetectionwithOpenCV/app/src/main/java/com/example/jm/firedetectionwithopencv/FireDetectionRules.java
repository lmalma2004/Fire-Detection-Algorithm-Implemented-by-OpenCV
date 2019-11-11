package com.example.jm.firedetectionwithopencv;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import static org.opencv.core.Core.bitwise_and;
import static org.opencv.core.Core.bitwise_not;
import static org.opencv.core.Core.subtract;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_32FC;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;
import static org.opencv.imgproc.Imgproc.threshold;

/**
 * Created by JM on 2017-09-26.
 */

public class FireDetectionRules {

    public Mat temp;
    public Mat temp2;
    public Mat temp3;
    public Mat temp4;
    public Mat temp5;
    public Mat temp6;
    public Mat temp7;



    public FireDetectionRules(Mat Image){
       // temp = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
       // temp = new Mat(Image.rows(),Image.cols(),CV_32FC(Image.channels()));
       // temp2 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
      //  temp3 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
      //  temp4 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
      //  temp5 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
     //   temp6 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));
       // temp7 = new Mat(new Size(Image.rows(),Image.cols()), CV_32F, Scalar.all(0));

    }
    public FireDetectionRules(){

    }

    public Mat Rule_1(Mat R, Mat G, Mat B){

        R.convertTo(R,CV_8UC1);
        G.convertTo(G,CV_8UC1);
        B.convertTo(B,CV_8UC1);

        temp = new Mat(R.rows(),R.cols(),CV_8UC(R.channels()));
        subtract(R, G, temp);
        threshold(temp, temp, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
        temp2 = new Mat(R.rows(),R.cols(),CV_8UC(R.channels()));
        subtract(G, B, temp2);
        threshold(temp2, temp2, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

        bitwise_and(temp, temp2, temp);
        //bitwise_not(temp, temp);
        //imshow("Rule_1", temp);
        return temp;

    }
    public Mat Rule_2(Mat R, Mat G, Mat B){
        R.convertTo(R,CV_8UC1);
        G.convertTo(G,CV_8UC1);
        B.convertTo(B,CV_8UC1);

        temp3 = new Mat(R.rows(),R.cols(),CV_8UC(R.channels()));
        temp4 = new Mat(R.rows(),R.cols(),CV_8UC(R.channels()));
        temp5 = new Mat(R.rows(),R.cols(),CV_8UC(R.channels()));

        R.convertTo(temp3, CV_8UC(R.channels()), 1.0, -190);
        threshold(temp3, temp3, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
        G.convertTo(temp4, CV_8UC(R.channels()), 1.0, -100);
        //add(G, Scalar(-100), temp4);
        threshold(temp4, temp4, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
        B.convertTo(temp5, CV_8UC(R.channels()), 1.0, -139);
        //add(B, Scalar(-139), temp5);
        threshold(temp5, temp5, 0, 255, THRESH_BINARY_INV); //nTh2 값이상의 픽셀은 전부 255

        bitwise_and(temp3, temp4, temp3);
        bitwise_and(temp3, temp5, temp3);
        //bitwise_not(temp3, temp3);

        //imshow("Rule_2", temp3);
        return temp3;
    }
    public Mat Rule_3(Mat Y, Mat Cb){
        Y.convertTo(Y,CV_8UC1);
        Cb.convertTo(Cb,CV_8UC1);

        temp6 = new Mat(Y.rows(),Y.cols(),CV_8UC(Y.channels()));
        subtract(Y, Cb, temp6);
        threshold(temp6, temp6, 0, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

        bitwise_not(temp6, temp6);


        //imshow("Rule_3", temp6);
        return temp6;
    }
    public Mat Rule_4(Mat Cr, Mat Cb){
        Cr.convertTo(Cr,CV_8UC1);
        Cb.convertTo(Cb,CV_8UC1);

        temp7 = new Mat(Cr.rows(),Cr.cols(),CV_8UC(Cr.channels()));
        subtract(Cr, Cb, temp7);
        threshold(temp7, temp7, 0, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

        bitwise_not(temp7, temp7);

        //imshow("Rule_4", temp);
        return temp7;
    }

}
