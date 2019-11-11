package com.example.jm.firedetectionwithopencv;

import org.opencv.core.Mat;

import static org.opencv.core.Core.bitwise_and;

/**
 * Created by JM on 2017-09-26.
 */

public class FireDetectionMask extends FireDetectionRules {
    public FireDetectionMask(ImageChannel Image){
        Mask1 = Rule_1(Image.R, Image.G, Image.B);
        Mask2 = Rule_2(Image.R, Image.G, Image.B);
        Mask3 = Rule_3(Image.Y, Image.Cr);
        Mask4 = Rule_4(Image.Cr, Image.Cb);
        FinalMask = new Mat();

       // bitwise_and(Mask1, Mask3, FinalMask);
        bitwise_and(Mask1, Mask2, FinalMask);
      bitwise_and(FinalMask, Mask3, FinalMask);
        bitwise_and(FinalMask, Mask4, FinalMask);

    }
    public FireDetectionMask(){

    }

    public void progress(ImageChannel Image){
        Mask1 = Rule_1(Image.R, Image.G, Image.B);
        Mask2 = Rule_2(Image.R, Image.G, Image.B);
        Mask3 = Rule_3(Image.Y, Image.Cr);
        Mask4 = Rule_4(Image.Cr, Image.Cb);
        FinalMask = new Mat();

        // bitwise_and(Mask1, Mask3, FinalMask);
        bitwise_and(Mask1, Mask2, FinalMask);
        bitwise_and(FinalMask, Mask3, FinalMask);
        bitwise_and(FinalMask, Mask4, FinalMask);

        Mask1.release();
        Mask2.release();
        Mask3.release();
        Mask4.release();
    }

    public Mat Mask1, Mask2, Mask3, Mask4;
    public Mat FinalMask;
}
