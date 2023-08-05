package com.example.opencvapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private int opcion = 0;
    Mat mat1,mat2;
    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    private CascadeClassifier faceDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"Open CV OK",Toast.LENGTH_LONG).show();
            inicializarDependencias();//dependencias para el rostro
        }else{
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }

        cameraBridgeViewBase = (CameraBridgeViewBase) findViewById(R.id.mycameraview);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        baseLoaderCallback = new BaseLoaderCallback(this) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuoriginal:
                opcion = 0;
                break;
            case R.id.menugris:
                opcion = 1;
                break;
            case R.id.menuinverso:
                opcion = 2;
                break;
            case R.id.menuinversocolor:
                opcion = 3;
                break;
            case R.id.menuresaltar:
                opcion = 4;
                break;
            case R.id.menurostro:
                opcion = 5;
                break;
        }
        return true;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1 = new Mat(width,height, CvType.CV_8UC4);
        mat2 = new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mat1.release();
        mat2.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
        }else{
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
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        switch (opcion){
            case 0:
                mat1 = inputFrame.rgba();
                break;
            case 1:
                mat1 = inputFrame.gray();
                break;
            case 2:
                mat1 = inputFrame.gray();
                Core.bitwise_not(mat1,mat1);
                break;
            case 3:
                mat1 = inputFrame.rgba();
                Core.bitwise_not(mat1,mat1);
                break;
            case 4:
                mat1 = inputFrame.rgba();
                mat2 = mat1.clone();
                Mat mascara = new Mat();
                Imgproc.GaussianBlur(mat1,mat2,new Size(5,5),0);
                Imgproc.cvtColor(mat2,mat2,Imgproc.COLOR_BGR2HSV);
                Core.inRange(mat2,new Scalar(110,50,50),new Scalar(130,255,255),mascara);
                Core.bitwise_and(mat2,mat2,mat1,mascara);
                break;
            case 5:
                MatOfRect rostros = new MatOfRect();
                Rect[] facesArray;
                mat1 = inputFrame.rgba();
                mat2 = inputFrame.gray();
                Imgproc.equalizeHist(mat2,mat2);
                faceDetector.detectMultiScale(mat2,rostros,1.3,2,0|2,new Size(30,30),new Size(mat1.width(),mat1.height()));
                facesArray = rostros.toArray();
                for( int i=0;i<facesArray.length;i++){
                    int x = facesArray[i].x;
                    int y = facesArray[i].y;
                    int width = facesArray[i].width;
                    int height = facesArray[i].height;
                    Point center = new Point((x+width*0.5),(y+height*0.5));
                    Imgproc.rectangle(mat1,
                            new Point(x,y),
                            new Point(x+width,y+height),
                            new Scalar(123,213,23,220)
                    );
                    Imgproc.putText(mat1,"Rostro "+(i+1),new Point(x,y-20),1,1,new Scalar(255,255,255));
                }

                break;
        }
        mat2 = mat1.clone();
        Core.flip(mat1.t(),mat2,1);
        Imgproc.resize(mat2,mat2,mat1.size());
        mat1 = mat2.clone();
        Imgproc.putText(mat1,"UNI",new Point(0,mat1.height()-20),Imgproc.FONT_HERSHEY_SIMPLEX,1,new Scalar(255,0,0),2);
        return mat1;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void inicializarDependencias(){
        try {
            InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir,"haarcascade_frontalface_alt2.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead=is.read(buffer))!=-1){
                os.write(buffer,0,bytesRead);
            }
            is.close();
            os.close();
            faceDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error Haar",Toast.LENGTH_LONG).show();
        }
    }
}