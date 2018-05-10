package com.inception.lapi.visionplus;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private Camerapreview mPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of CameraActivity
        mCamera = getCameraInstance();
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        Camera.Parameters param= mCamera.getParameters();
        mCamera.setParameters(param);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new Camerapreview(this, mCamera);

        preview.addView(mPreview);


    }

    /** A safe way to get an instance of the CameraActivity object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a CameraActivity instance
        }
        catch (Exception e){
            // CameraActivity is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
