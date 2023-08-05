package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class FiltrosActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private Mat mRBG,mRGBt,mGray;
    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    private int menu_option = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        if (OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"OK Opencv",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Error Opencv",Toast.LENGTH_SHORT).show();
        }

        cameraBridgeViewBase = (CameraBridgeViewBase) findViewById(R.id.mycameraview);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        baseLoaderCallback=new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status){
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRBG = new Mat(width,height, CvType.CV_8UC4);
        mGray = new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRBG.release();
        mGray.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        switch (menu_option){
            case 0:
                mRBG = inputFrame.rgba();
                break;
            case 1:
                mRBG = inputFrame.gray();
                break;
            case 2:
                mRBG = inputFrame.gray();
                Core.bitwise_not(mRBG,mRBG);
                break;
            case 3:
                mRBG = inputFrame.gray();
                Imgproc.blur(mRBG, mRBG, new Size(3,3));
                Imgproc.Canny(mRBG,mRBG,100,300,5,false);
                break;
        }
        mRGBt = mRBG.clone();
        Core.flip(mRBG.t(),mRGBt,1);
        Imgproc.resize(mRGBt,mRGBt,mRBG.size());
        mRBG = mRGBt.clone();

        Imgproc.putText(mRBG, "UNI", new Point(0,mRBG.height()-20), Core.FONT_HERSHEY_SIMPLEX, 1,  new Scalar(255, 0, 0), 2);

        return mRBG;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"Problema Opencv",Toast.LENGTH_SHORT).show();
        }else {
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufiltros,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuoriginal:
                menu_option = 0;
                break;
            case R.id.menugris:
                menu_option = 1;
                break;
            case R.id.menuinversogris:
                menu_option = 2;
                break;
            case R.id.menubordes:
                menu_option = 3;
                break;
            default:
                menu_option = 0;
                break;
        }
        return true;
    }
}