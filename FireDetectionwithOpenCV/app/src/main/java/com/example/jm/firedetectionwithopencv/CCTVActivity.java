package com.example.jm.firedetectionwithopencv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JM on 2017-09-19.
 */

class MyView extends View {
    public ArrayList<Bitmap> bitmapArrayList;
    public MediaMetadataRetriever MyViewretriever;
    public Bitmap bitmap= null;
    int millisecond;
    Handler mHandler = null;

    public MyView(Context context, MediaMetadataRetriever retriever,  MediaPlayer mediaPlayer){
        super(context);
        setBackgroundColor(Color.YELLOW);
        millisecond = mediaPlayer.getDuration();
        MyViewretriever = retriever;

        setHandler();
    }

    public void setHandler(){
        mHandler = new Handler(){
            public void handleMessage(Message msg){
                for(int i = 0; i < millisecond; i+=1000) {
                    // getFrameAtTime 함수는 i 라는 타임에 bitmap을 얻어와준다.(
                    // getFrameAtTime의 첫번째 인자의 unit 은 microsecond이다.
                    // 그래서 1000을 곱해주었다.

                    bitmap = MyViewretriever.getFrameAtTime(i * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                   // bitmapArrayList.add(MyViewretriever.getFrameAtTime(i * 1000, MediaMetadataRetriever.OPTION_CLOSEST));
                    invalidate(); // onDraw 함수 호출
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        };

        boolean bSend = mHandler.sendEmptyMessage(0);

    }

    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();

        canvas.drawBitmap(bitmap,0,0,null);

        //MyViewretriever.release();

    }
}

public class CCTVActivity extends Activity{
    public String state;
    private File    videoFile;
    private Uri     videoFileUri;
    public MediaMetadataRetriever retriever;
    private ArrayList<Bitmap> bitmapArrayList;
    private MediaPlayer mediaPlayer;
    private Bitmap bitmap;
    public Bitmap bitmap2;
    public Bitmap bitmap3;
    private Thread thread;
    private Thread thread2;

    public ImageView imageView;
    public LinearLayout layout;

    Integer j=0;

    MyView view2;
    public MySurfaceView view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String area;
        String area1;
        String area2;
        String area3;
        String area4;
        String uriPath;
        area1 = new String("area1");
        area2 = new String("area2");
        area3 = new String("area3");
        area4 = new String("area4");
        uriPath = new String();
        Uri uri;


       // setContentView(R.layout.activity_cctv);
       // imageView = (ImageView)findViewById(R.id.CCTVView);


        checkExternalStorage();
        // test.mp4 동영상파일을 불러온다.
        //this.setContentView(R.layout.activity_cctv);

       // VideoView videoView = (VideoView)this.findViewById(R.id.videoview);
      //  String folder = Environment.getExternalStorageDirectory().getAbsolutePath();
      //  videoView.setVideoPath(folder+"/test3.mp4");
      //  videoView.requestFocus();
      //  videoView.start();
        area = intent.getStringExtra("area");


        Log.i("area name", area);
        if(area1.compareTo(intent.getStringExtra("area")) == 0) {
            videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/area1.mp4");

        } else if(area2.compareTo(intent.getStringExtra("area")) == 0) {
            videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/area2.mp4");
        }



        videoFileUri    = Uri.parse(videoFile.toString());
        // instance 생성
        retriever       = new MediaMetadataRetriever();
        // 추출할 bitmap 을 담을 array 생성
        bitmapArrayList = new ArrayList<Bitmap>();
        // 사용할 data source의 경로를 설정해준다.
        Log.i("비디오이름", videoFile.toString());
        retriever.setDataSource(videoFile.toString());
        // video file의 총 재생시간을 얻어오기위함
        mediaPlayer = MediaPlayer.create(getBaseContext(), videoFileUri);



        // 나는 3초 짜리 동영상을 사용했다. millisecond = 3000


        int millisecond = mediaPlayer.getDuration();
        Integer test = millisecond;
        Log.i("millisecond", test.toString());

        // 1000씩 증가를 시킨 이유는 총 3초짜리 동영상 을 1초마다 bitmap을 얻고싶어서.
        /*
        for(int i = 0; i < millisecond; i+=400){
            // getFrameAtTime 함수는 i 라는 타임에 bitmap을 얻어와준다.(
            // getFrameAtTime의 첫번째 인자의 unit 은 microsecond이다.
            // 그래서 1000을 곱해주었다.
            bitmap = retriever.getFrameAtTime(i*400,MediaMetadataRetriever.OPTION_CLOSEST);
            bitmapArrayList.add(bitmap);
            Integer size = bitmapArrayList.size();
            Log.i("bitmapArrayList_Count", size.toString());

            //imageView.setImageBitmap(bitmap);
            //bitmap = null;
        }
        */

        /*
        for(Bitmap b : bitmapArrayList){
            imageView.setImageBitmap(b);
            b = null;
        }*/

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                for(Bitmap b : bitmapArrayList) {
                    thread2.sleep(1000);
                    if (b != null) {
                        imageView.setImageBitmap(b);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            }
        });*/

/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i <bitmapArrayList.size() ; i++){
                    try{
                        bitmap2=bitmapArrayList.get(i);
                        Thread.sleep(400);
                    } catch (InterruptedException e){}
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap2);
                            Log.i("current index", j.toString());
                        }
                    });
                    j++;
                }
            }
        }).start();
*/




                thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            for(Bitmap b : bitmapArrayList) {
                                thread2.sleep(1000);
                                if (b != null) {
                                    imageView.setImageBitmap(b);
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });



               // view2=new MyView(this,retriever,mediaPlayer);
               // setContentView(view2);


                  setContentView(R.layout.activity_cctv);
                  layout = (LinearLayout)findViewById(R.id.layout);

                  if(area1.compareTo(intent.getStringExtra("area")) == 0) {
                      view = new MySurfaceView(this, retriever, mediaPlayer,area1);
                  } else if(area2.compareTo(intent.getStringExtra("area")) == 0) {
                      view = new MySurfaceView(this, retriever, mediaPlayer,area2);
                  }

                  layout.addView(view);



                 // view = (MySurfaceView)findViewById(R.id.mysurfaceview);
                  //view.progress(this,retriever,mediaPlayer);

                // retreiver를 다 사용했다면 release를 해주어야한다.
                //retriever.release();


                // Thread start
                thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            saveFrames(bitmapArrayList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //thread.start();
                //thread2.start();

            }

                public void saveFrames(ArrayList<Bitmap> saveBitmap) throws IOException{
                    String folder = Environment.getExternalStorageDirectory().toString();
                    File saveFolder = new File(folder + "/");
                    if(!saveFolder.exists()){
                        saveFolder.mkdirs();
                    }
                    int i = 1;
                    for (Bitmap b : saveBitmap){
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                        File f = new File(saveFolder,("filename"+i+".jpg"));

                        f.createNewFile();
                        FileOutputStream fo = new FileOutputStream(f);
                        fo.write(bytes.toByteArray());

                        fo.flush();
                        fo.close();
                        i++;
                    }
                    thread.interrupt();
                }
                boolean checkExternalStorage() {
                    state = Environment.getExternalStorageState();
                    // 외부메모리 상태
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        // 읽기 쓰기 모두 가능
                        Log.d("test", "외부메모리 읽기 쓰기 모두 가능");
                        return true;
                    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
                        //읽기전용
                        Log.d("test", "외부메모리 읽기만 가능");
                        return false;
                    } else {
                        // 읽기쓰기 모두 안됨
                        Log.d("test", "외부메모리 읽기쓰기 모두 안됨 : "+ state);
                        return false;
                    }
                }

                protected void onPause(){
                    super.onPause();
                    //view.getThread().setRunning(false);
                }
                @Override
                protected void onResume(){
                    super.onResume();
                    //view.getThread().setRunning(false);
                }
                protected void onStop(){
                    super.onStop();
                    //view.getThread().setRunning(false);
                }


                protected void onSaveInstanceState(Bundle outState){
                    super.onSaveInstanceState(outState);
                }

            }

