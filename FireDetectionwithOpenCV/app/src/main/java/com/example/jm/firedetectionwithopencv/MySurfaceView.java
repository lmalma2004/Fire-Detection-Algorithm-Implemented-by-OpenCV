package com.example.jm.firedetectionwithopencv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;

import static org.opencv.core.Core.bitwise_and;
import static org.opencv.core.Core.countNonZero;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8SC4;
import static org.opencv.core.CvType.CV_8UC;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC4;
import static org.opencv.imgproc.Imgproc.COLOR_BGRA2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;


/**
 * Created by JM on 2017-09-20.
 */

class FrameBitmap{
    public Bitmap frame;
    public Bitmap frame2;
    public Mat matframe;
    public ArrayList<Bitmap> bitmapArrayList;
    public ArrayList<Mat> matArrayList;
    int cols, rows;
    int millisecond;

    FrameBitmap(){
        bitmapArrayList = new ArrayList<Bitmap>();
        matArrayList = new ArrayList<Mat>();
        matframe = new Mat();


    }

    public void paint(Canvas g, Bitmap bitmap, int nCount){
        //cols = (int) (Math.random() * 10 +100);
        //rows = (int) (Math.random() * 10 +100);
        Paint paint = new Paint();
       // g.drawBitmap(bitmap,800,640,paint);
        int width = g.getWidth();
        int height = g.getHeight();


        g.drawBitmap(Bitmap.createScaledBitmap(bitmap,width,height,true),0,0,paint);
        Check_fire(g,paint,width,nCount,3,50);
       // paint.setColor(Color.YELLOW);
      //  g.drawCircle(width-100,100,50,paint);


    }
    void Check_fire(Canvas g, Paint paint, int width, int nCount, int Green, int Yellow) {
        if (nCount < Green) {
            paint.setColor(Color.GREEN);
            g.drawCircle(width-100,100,50,paint);
        }
        else if (nCount < Yellow) {
            paint.setColor(Color.YELLOW);
            g.drawCircle(width-100,100,50,paint);
        }
        else {
            paint.setColor(Color.RED);
            g.drawCircle(width-100,100,50,paint);
        }
    }

}


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public File    videoFile;
    public Uri     videoFileUri;

    public FrameBitmap framebitmap;
    public MotionDetectionMask MotionMask;
    Bitmap bitbkgImage;
    public Mat bkgImage;
    public Mat graybkgImage;
    private MyThread thread;
    int millisecond;
    int nCount;
    int nCount2;
    int nCount3;
    int nCount4;
    int nCount5;
    int nCount6;
    int nCount7;

    public ImageChannel Three_Image ;
    public Bitmap bitMotionFinalMask;


    public MySurfaceView(Context context, MediaMetadataRetriever retriever , MediaPlayer mediaPlayer, String area){
        super(context);
        framebitmap = new FrameBitmap();
        Three_Image = new ImageChannel(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());
        bitMotionFinalMask = Bitmap.createBitmap(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight(), Bitmap.Config.ARGB_8888);
        bitbkgImage = Bitmap.createBitmap(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight(), Bitmap.Config.ALPHA_8);
        bkgImage = new Mat();
        graybkgImage = new Mat();

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        millisecond = mediaPlayer.getDuration();
        framebitmap.millisecond = millisecond;

        MotionMask = new MotionDetectionMask(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());

        if(area.compareTo("area1") == 0) {
            bitbkgImage = BitmapFactory.decodeResource(getResources(),R.drawable.area1);
        } else if(area.compareTo("area2") == 0) {
            bitbkgImage = BitmapFactory.decodeResource(getResources(),R.drawable.area2);
        }
        //bitbkgImage = BitmapFactory.decodeResource(getResources(),R.drawable.area1);
       // bitbkgImage = BitmapFactory.decodeResource(getResources(),R.drawable.area2);
        Utils.bitmapToMat(bitbkgImage,bkgImage);

        /*
        Imgproc.resize(bkgImage,bkgImage,new Size(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight()));
        cvtColor(bkgImage,graybkgImage, COLOR_BGRA2GRAY);
        graybkgImage.convertTo(graybkgImage,CV_8UC1);
        bkgImage = graybkgImage;
        */

      //  bkgImage = MotionMask.Make_bkgImage(retriever,mediaPlayer,"bkgImage");





        thread = new MyThread(holder, retriever);

        /*for(int i = 0; i < millisecond; i+=1000){
            // getFrameAtTime 함수는 i 라는 타임에 bitmap을 얻어와준다.(
            // getFrameAtTime의 첫번째 인자의 unit 은 microsecond이다.
            // 그래서 1000을 곱해주었다.
            framebitmap.frame = retriever.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
            thread.temp = framebitmap.frame;
            framebitmap.bitmapArrayList.add(framebitmap.frame);
        }*/

        //retriever.release();
    }
    public MySurfaceView(Context context) {
        super(context);
    }
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context);
    }


    public void progress(Context context, MediaMetadataRetriever retriever , MediaPlayer mediaPlayer){
        //new MySurfaceView(context);
        framebitmap = new FrameBitmap();
        Three_Image = new ImageChannel(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());
        bitMotionFinalMask = Bitmap.createBitmap(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight(), Bitmap.Config.ARGB_8888);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        millisecond = mediaPlayer.getDuration();
        framebitmap.millisecond = millisecond;

        MotionMask = new MotionDetectionMask(mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());


        bkgImage = MotionMask.Make_bkgImage(retriever,mediaPlayer,"bkgImage");





        thread = new MyThread(holder, retriever);

        /*for(int i = 0; i < millisecond; i+=1000){
            // getFrameAtTime 함수는 i 라는 타임에 bitmap을 얻어와준다.(
            // getFrameAtTime의 첫번째 인자의 unit 은 microsecond이다.
            // 그래서 1000을 곱해주었다.
            framebitmap.frame = retriever.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
            thread.temp = framebitmap.frame;
            framebitmap.bitmapArrayList.add(framebitmap.frame);
        }*/

        //retriever.release();
    }

    public Bitmap combineImage(Bitmap first, Bitmap second, boolean isVerticalMode){
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inDither = true;
        option.inPurgeable = true;

        Bitmap bitmap = null;
        if(isVerticalMode)
            bitmap = Bitmap.createScaledBitmap(first, first.getWidth(), first.getHeight()+second.getHeight(), true);
        else
            bitmap = Bitmap.createScaledBitmap(first, first.getWidth()+second.getWidth(), first.getHeight(), true);

        Paint p = new Paint();
        p.setDither(true);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);

        Canvas c = new Canvas(bitmap);
        c.drawBitmap(first, 0, 0, p);
        if(isVerticalMode)
            c.drawBitmap(second, 0, first.getHeight(), p);
        else
            c.drawBitmap(second, first.getWidth(), 0, p);

        //first.recycle();
        //second.recycle();

        return bitmap;
    }


    public MyThread getThread(){
        return thread;
    }
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        thread.addbitmap.recycle();
        thread.MotionFinalMask.release();
        bkgImage.release();
        bitbkgImage.recycle();

        while(retry){
            try{
                thread.join();
                retry = false;
            } catch (InterruptedException e){}
        }
    }




    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    public class MyThread extends Thread{

        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;
        MediaMetadataRetriever retriever2;
        public Mat MotionFinalMask;
        public Bitmap addbitmap;
        public int j = 0;

        public MyThread(SurfaceHolder surfaceHolder, MediaMetadataRetriever retriever ){
            retriever2 = retriever;
            mSurfaceHolder = surfaceHolder;


        }

        public void run(){
            FireDetectionMask FireMask2 = new FireDetectionMask();

/*
            for(int i = 0; i <millisecond; i+=1000){
                framebitmap.frame = retriever2.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
                bitMotionFinalMask = framebitmap.frame;

                Utils.bitmapToMat(framebitmap.frame, framebitmap.matframe );



                framebitmap.bitmapArrayList.add(framebitmap.frame);
                framebitmap.matArrayList.add(framebitmap.matframe);
            }*/


            for(int i = 0; i <millisecond; i+=1000){//

                Canvas c = null;
                framebitmap.frame = retriever2.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
                framebitmap.frame2 = retriever2.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);

                if(i ==0) {
                    Imgproc.resize(bkgImage, bkgImage, new Size(framebitmap.frame.getWidth(), framebitmap.frame.getHeight()));
                    cvtColor(bkgImage, graybkgImage, COLOR_BGRA2GRAY);
                    graybkgImage.convertTo(graybkgImage, CV_8UC1);
                    bkgImage = graybkgImage;
                }

                bitMotionFinalMask = framebitmap.frame;

                try{
                    c = mSurfaceHolder.lockCanvas(null);

                    if(c!=null) {
                        c.drawColor(Color.WHITE);

                        synchronized (mSurfaceHolder) {

                            Utils.bitmapToMat(framebitmap.frame, framebitmap.matframe );
                            Three_Image.Make_RGB(framebitmap.matframe);
                            Three_Image.Make_YCrCb(framebitmap.matframe);
                            FireMask2.progress(Three_Image);

                            MotionFinalMask = MotionMask.Get_finalmask(bkgImage, framebitmap.matframe);
                            nCount4 = countNonZero(FireMask2.Mask1);
                            nCount5 = countNonZero(FireMask2.Mask2);
                            nCount6 = countNonZero(FireMask2.Mask3);
                            nCount7 = countNonZero(FireMask2.Mask4);

                            FireMask2.FinalMask.convertTo(FireMask2.FinalMask, MotionFinalMask.type());

                            bitwise_and(MotionFinalMask, FireMask2.FinalMask, MotionFinalMask);


                            nCount = countNonZero(MotionFinalMask);
                            nCount2 = countNonZero(FireMask2.FinalMask);
                            nCount3 = countNonZero(bkgImage);


                            try {
                                sleep(250);
                            } catch (Exception e) {
                            }
                            //framebitmap.paint(c,framebitmap.bitmapArrayList.get(i));
                            Log.i("MotionFinalMask", String.valueOf(nCount));
                            Log.i("FireMask.FinalMask", String.valueOf(nCount2));
                            Log.i("bkgImage", String.valueOf(nCount3));
                            Log.i("Rule1", String.valueOf(nCount4));
                            Log.i("Rule2", String.valueOf(nCount5));
                            Log.i("Rule3", String.valueOf(nCount6));
                            Log.i("Rule4", String.valueOf(nCount7));
                            MotionFinalMask.convertTo(MotionFinalMask, CV_8UC4);


                            Utils.matToBitmap(MotionFinalMask, bitMotionFinalMask);
                            addbitmap = combineImage(framebitmap.frame2, bitMotionFinalMask, true);
                            framebitmap.paint(c, addbitmap, nCount);
                            //framebitmap.paint(c,framebitmap.bitmapArrayList.get(i));


                        }
                    }
                } finally{
                    if(c!=null){
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }

            }//

 /*           for(int i = 0; i < framebitmap.bitmapArrayList.size(); i++){
                Canvas c = null;
                try{
                    c = mSurfaceHolder.lockCanvas(null);

                    if(c!=null) {
                        c.drawColor(Color.WHITE);

                        synchronized (mSurfaceHolder) {

                            Utils.bitmapToMat(framebitmap.bitmapArrayList.get(i), framebitmap.matframe);
                            Three_Image.Make_RGB(framebitmap.matframe);
                            Three_Image.Make_YCrCb(framebitmap.matframe);

                            //Three_Image.Make_RGB(framebitmap.matArrayList.get(i));
                            //Three_Image.Make_YCrCb(framebitmap.matArrayList.get(i));

                            //FireDetectionMask FireMask = new FireDetectionMask(Three_Image);
                            FireMask2.progress(Three_Image);

                            MotionFinalMask = MotionMask.Get_finalmask(bkgImage, framebitmap.matframe);

                            //MotionFinalMask = MotionMask.Get_finalmask(bkgImage, framebitmap.matArrayList.get(i));

                            nCount4 = countNonZero(FireMask2.Mask1);
                            nCount5 = countNonZero(FireMask2.Mask2);
                            nCount6 = countNonZero(FireMask2.Mask3);
                            nCount7 = countNonZero(FireMask2.Mask4);

                            FireMask2.FinalMask.convertTo(FireMask2.FinalMask, MotionFinalMask.type());

                            bitwise_and(MotionFinalMask, FireMask2.FinalMask, MotionFinalMask);


                            nCount = countNonZero(MotionFinalMask);
                            nCount2 = countNonZero(FireMask2.FinalMask);
                            nCount3 = countNonZero(bkgImage);


                            try {
                                sleep(1000);
                            } catch (Exception e) {
                            }
                            //framebitmap.paint(c,framebitmap.bitmapArrayList.get(i));
                            Log.i("MotionFinalMask", String.valueOf(nCount));
                            Log.i("FireMask.FinalMask", String.valueOf(nCount2));
                            Log.i("bkgImage", String.valueOf(nCount3));
                            Log.i("Rule1", String.valueOf(nCount4));
                            Log.i("Rule2", String.valueOf(nCount5));
                            Log.i("Rule3", String.valueOf(nCount6));
                            Log.i("Rule4", String.valueOf(nCount7));
                            MotionFinalMask.convertTo(MotionFinalMask, CV_8UC4);


                            Utils.matToBitmap(MotionFinalMask, bitMotionFinalMask);
                            addbitmap = combineImage(framebitmap.bitmapArrayList.get(i), bitMotionFinalMask, true);
                            framebitmap.paint(c, addbitmap, nCount);
                            //framebitmap.paint(c,framebitmap.bitmapArrayList.get(i));


                        }
                    }
                    } finally{
                        if(c!=null){
                            mSurfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }*/
            }

        public void setRunning(boolean b){
            mRun = b;
        }
    }
}
