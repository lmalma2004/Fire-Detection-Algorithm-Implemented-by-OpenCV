package com.example.jm.firedetectionwithopencv;

import android.app.Activity;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * Created by JM on 2017-10-01.
 */

public class CprActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpr);

        ImageView imageview = (ImageView)findViewById(R.id.cprimage1);
        ImageView imageview2 = (ImageView)findViewById(R.id.cprimage2);

        imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageview.setAdjustViewBounds(true);
        imageview2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageview2.setAdjustViewBounds(true);

        VideoView videoView = (VideoView)findViewById(R.id.videoView);

        //String folder = Environment.getExternalStorageDirectory().getAbsolutePath();
        //videoView.setVideoPath(folder+"/cprvideo.mp4");

        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.cprvideo;
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);


        videoView.requestFocus();
        videoView.start();
    }
}
