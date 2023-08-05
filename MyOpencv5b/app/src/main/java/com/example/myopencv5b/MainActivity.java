package com.example.myopencv5b;

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
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements CameraBridgeViewBase.CvCameraViewListener2{
    private Mat mRBG,mRGBt,mGray;
    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    private int opcion = 0;
    private CascadeClassifier faceDetector,ojoDetector,narizDetector,smileDetector ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"OK Opencv",Toast.LENGTH_SHORT).show();
            //initializeOpenCVDependencies();
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
        //mRBG = inputFrame.rgba();
       // mGray = inputFrame.gray();

        switch (opcion){
            case 0:
                mRBG = inputFrame.rgba();

                break;
            case 1:
                mRBG = inputFrame.gray();
                /*mRGBt = mRBG.clone();
                Core.flip(mRBG.t(),mRGBt,1);
                Imgproc.resize(mRGBt,mRGBt,mRBG.size());
                mRBG = mRGBt.clone();*/
                break;
            case 2:
                mRBG = inputFrame.gray();
                Core.bitwise_not(mRBG,mRBG);
                break;
            case 3:
                mRBG = inputFrame.gray();
                Imgproc.threshold(mRBG,mRBG,120,255,Imgproc.THRESH_TRUNC);
                break;
            case 4:
                mRBG = inputFrame.rgba();
                Core.bitwise_not(mRBG,mRBG);
                break;
            case 5:
                mRBG = inputFrame.rgba();
                mRGBt=mRBG.clone();
                Mat maskR = new Mat();
                Imgproc.GaussianBlur(mRBG, mRGBt, new Size(5,5),0);
                Imgproc.cvtColor(mRGBt, mRGBt, Imgproc.COLOR_BGR2HSV);
                Core.inRange(mRGBt, new Scalar(110,50,50), new Scalar(130,255,255), maskR);
                Core.bitwise_and(mRGBt,mRGBt,mRBG,maskR);
                break;
            case 6:
                mRBG = inputFrame.rgba();
                mRGBt=mRBG.clone();
                Mat maskG = new Mat();
                Imgproc.GaussianBlur(mRBG, mRGBt, new Size(5,5),0);
                Imgproc.cvtColor(mRGBt, mRGBt, Imgproc.COLOR_BGR2HSV);
                Core.inRange(mRGBt, new Scalar(50,50,50), new Scalar(70,255,255), maskG);
                Core.bitwise_and(mRGBt,mRGBt,mRBG,maskG);
                break;
            case 7:
                mRBG = inputFrame.rgba();
                mRGBt=mRBG.clone();
                Mat maskB = new Mat();
                Imgproc.GaussianBlur(mRBG, mRGBt, new Size(5,5),0);
                Imgproc.cvtColor(mRGBt, mRGBt, Imgproc.COLOR_BGR2HSV);
                Core.inRange(mRGBt, new Scalar(0,50,50), new Scalar(10,255,255), maskB);
                Core.bitwise_and(mRGBt,mRGBt,mRBG,maskB);
                break;
            case 8:
                mRBG = inputFrame.rgba();
                mRGBt=mRBG.clone();
                Mat maskP = new Mat();
                Imgproc.GaussianBlur(mRBG, mRGBt, new Size(5,5),0);
                Imgproc.cvtColor(mRGBt, mRGBt, Imgproc.COLOR_BGR2HSV);
                Core.inRange(mRGBt, new Scalar(0,58,30), new Scalar(33,255,255), maskP);
                Core.bitwise_and(mRGBt,mRGBt,mRBG,maskP);
                break;
            case 9:
                mRBG = inputFrame.gray();
                Imgproc.blur(mRBG, mRBG, new Size(3,3));
                Imgproc.Canny(mRBG,mRBG,100,300,5,false);
                break;
            case 10:
                boolean done = false;
                mRGBt = inputFrame.gray();
                Mat tresh = new Mat();
                double thresh = Imgproc.threshold(mRGBt, tresh, 100, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3,3));
                Mat eroded = new Mat();
                Mat temp = new Mat();
                mRBG = new Mat (tresh.rows(), tresh.cols(), CvType.CV_8UC1, new Scalar (0));
                int size = mRBG.cols() * mRBG.rows();
                int zeros = 0;
                while(!done)
                {
                    Imgproc.erode(tresh, eroded, element);
                    Imgproc.dilate(eroded, temp, element);
                    Core.subtract(tresh, temp, temp);
                    Core.bitwise_or(mRBG, temp, mRBG);
                    eroded.copyTo(tresh);

                    zeros = size - Core.countNonZero(tresh);
                    if(zeros == size)
                        done = true;
                }
                break;
            case 11:
                mRBG = inputFrame.rgba();
                mRGBt=mRBG.clone();
                Mat maskO = new Mat();
                Imgproc.GaussianBlur(mRBG, mRGBt, new Size(5,5),0);
                Imgproc.cvtColor(mRGBt, mRGBt, Imgproc.COLOR_BGR2HSV);
                Core.inRange(mRGBt, new Scalar(110,50,50), new Scalar(130,255,255), maskO);
                Core.bitwise_and(mRGBt,mRGBt,mRBG,maskO);
                Imgproc.cvtColor(mRBG, mRGBt, Imgproc.COLOR_BGR2GRAY);
                Imgproc.threshold(mRGBt,mRGBt,120,255,Imgproc.THRESH_TRUNC);
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(mRGBt, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
                if (hierarchy.size().height > 0 && hierarchy.size().width > 0){
                    MatOfPoint maxContours = contours.get(0);
                    int idC = 0;
                    double MaxArea = 0;
                    for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
                        if(Imgproc.contourArea(contours.get(contourIdx))>MaxArea){
                            MaxArea =Imgproc.contourArea(contours.get(contourIdx));
                            maxContours = contours.get(contourIdx);
                            idC = contourIdx;
                        }
                    }
                    Imgproc.drawContours(mRBG, contours, idC, new Scalar(0, 0, 255), 3);
                }
                Imgproc.line(mRBG,new Point(mRBG.width()/3,0),new Point(mRBG.width()/3,mRBG.height()),new Scalar(0,0,255),1);
                Imgproc.line(mRBG,new Point(mRBG.width()*2/3,0),new Point(mRBG.width()*2/3,mRBG.height()),new Scalar(0,0,255),1);
                Imgproc.line(mRBG,new Point(0,mRBG.height()/3),new Point(mRBG.width(),mRBG.height()/3),new Scalar(0,0,255),1);
                Imgproc.line(mRBG,new Point(0,mRBG.height()*2/3),new Point(mRBG.width(),mRBG.height()*2/3),new Scalar(0,0,255),1);

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
        getMenuInflater().inflate(R.menu.mimenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnuoriginal:
                opcion=0;
                break;
            case R.id.mnugris:
                opcion=1;
                break;
            case R.id.mnuinversogris:
                opcion=2;
                break;
            case R.id.mnuradiografia:
                opcion=3;
                break;
            case R.id.mnuinversocolor:
                opcion=4;
                break;
            case R.id.mnucanalr:
                opcion=5;
                break;
            case R.id.mnucanalg:
                opcion=6;
                break;
            case R.id.mnucanalb:
                opcion=7;
                break;
            case R.id.mnupiel:
                opcion=8;
                break;
            case R.id.mnuborde:
                opcion=9;
                break;
            case R.id.mnurostro:
                opcion=10;
                break;
            case R.id.mnubelleza:
                opcion=11;
                break;
            case R.id.mnuesqueleto:
                opcion=12;
                break;
            case R.id.mnusiguerojo:
                opcion=13;
                break;
            case R.id.mnusiguemano:
                opcion=14;
                break;
            default:
                opcion=0;
                break;
        }

        return true;
    }
}