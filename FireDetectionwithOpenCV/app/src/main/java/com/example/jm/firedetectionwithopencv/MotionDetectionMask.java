package com.example.jm.firedetectionwithopencv;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import static org.opencv.core.Core.absdiff;
import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_32FC;
import static org.opencv.core.CvType.CV_32FC4;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.COLOR_BGRA2GRAY;
import static org.opencv.imgproc.Imgproc.COLOR_RGBA2GRAY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.accumulate;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.initUndistortRectifyMap;
import static org.opencv.imgproc.Imgproc.threshold;

/**
 * Created by JM on 2017-09-26.
 */

public class MotionDetectionMask {
    public int nTh=20;
    public Size size;
    public Mat GrayImage;
    public Mat GrayImage2;
    public Mat Final_Mask;
    public Mat sumImage;
    public Mat frame;
    public Mat temp;


    public Bitmap bitframe;

    public MotionDetectionMask(int rows, int cols){
        size = new Size(rows, cols);
        frame = new Mat();
        GrayImage = new Mat();
        GrayImage2 = new Mat();
        sumImage = new Mat();
        temp = new Mat();
        //sumImage = new Mat(size,CV_32F, Scalar.all(0));

    }

    public Mat Make_bkgImage(MediaMetadataRetriever retriever , MediaPlayer mediaPlayer, String bkgImage){
        int frameNum = 0;
        int millisecond;
        millisecond = mediaPlayer.getDuration();

        for(int i = 0; i <millisecond; i+=1000){
            bitframe = retriever.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
            Utils.bitmapToMat(bitframe, frame);
            cvtColor(frame, GrayImage, COLOR_BGRA2GRAY);
            if(i == 0) {
                sumImage = new Mat(GrayImage.rows(),GrayImage.cols(),CV_32FC(GrayImage.channels()));
            }
            GrayImage.convertTo(GrayImage,CV_32FC(GrayImage.channels()));
            accumulate(GrayImage, sumImage);
            //addWeighted(GrayImage,1,sumImage,1,0,sumImage);

            frameNum++;
        }
        temp = GrayImage;
        temp.setTo(Scalar.all(0));
        Core.scaleAdd(sumImage,(1/frameNum),temp,sumImage);

        //imwrite(bkgImage, sumImage);
        return  sumImage;

    }


    public Mat Get_finalmask(Mat bkgImage, Mat frame){

        cvtColor(frame, GrayImage, COLOR_BGR2GRAY);


      //  Imgproc.resize(bkgImage,bkgImage,frame.size());
      //  cvtColor(bkgImage, GrayImage2, COLOR_BGRA2GRAY);

        Imgproc.GaussianBlur(GrayImage, GrayImage, new Size(5, 5), 0.5);

        GrayImage.convertTo(GrayImage,CV_8U);

     //   GrayImage2.convertTo(GrayImage2,CV_8UC1);


        Log.i("GrayImage size", String.valueOf(GrayImage.size()));
        Log.i("GrayImage channel", String.valueOf(GrayImage.channels()));

        Log.i("bkgImage size", String.valueOf(bkgImage.size()));
        Log.i("bkgImage channel", String.valueOf(bkgImage.channels()));


        Final_Mask = new Mat(GrayImage.rows(),GrayImage.cols(),CV_32FC(GrayImage.channels()));
        Final_Mask.convertTo(Final_Mask,CV_8U);


        Log.i("Final_Mask size", String.valueOf(Final_Mask.size()));
        Log.i("Final_Mask channel", String.valueOf(Final_Mask.channels()));


        absdiff(GrayImage,bkgImage, Final_Mask); //차영상 (움직임)
        threshold(Final_Mask, Final_Mask, nTh, 255, THRESH_BINARY);
        return Final_Mask;

    }

}
