package com.example.opencv;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.opencv.android.CameraActivity;
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
import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;
    private int opcion = 0;
    private Mat mOut, mTemp;
    private CascadeClassifier faceDetector,ojoDetector,narizDetector,smileDetector ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);

        if(OpenCVLoader.initDebug()) {
            Log.d("OPENCV", "Load success");

            cameraBridgeViewBase.enableView();
            inicializarDependencias();
        } else {
            Log.d("OPENCV", "Load error");
        }

        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                mOut = new Mat(width,height, CvType.CV_8UC4);
                mTemp = new Mat(width,height, CvType.CV_8UC4);
            }

            @Override
            public void onCameraViewStopped() {
                mOut.release();
                mTemp.release();
            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                MatOfRect rostros = new MatOfRect();
                Rect[] facesArray;
                mOut = inputFrame.rgba();
                mTemp = inputFrame.gray();
                Imgproc.equalizeHist(mTemp,mTemp);
                faceDetector.detectMultiScale(mTemp,rostros,1.3,2,0|2,new Size(30,30),new Size(mOut.width(),mOut.height()));
                facesArray = rostros.toArray();
                for( int i=0;i<facesArray.length;i++){
                    int x = facesArray[i].x;
                    int y = facesArray[i].y;
                    int width = facesArray[i].width;
                    int height = facesArray[i].height;
                    Imgproc.rectangle(mOut,
                            new Point(x,y),
                            new Point(x+width,y+height),
                            new Scalar(123,213,23,220)
                    );
                    Imgproc.putText(mOut,"Rostro "+(i+1),new Point(x,y-20),1,1,new Scalar(255,255,255));
                }
                mTemp = mOut.clone();
                Core.flip(mOut.t(), mTemp, 1);
                Imgproc.resize(mTemp,mTemp,mOut.size());
                mOut = mTemp.clone();
                return mOut;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
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
                opcion = 0;
                break;
            case R.id.mnugris:
                opcion = 1;
                break;
            case R.id.mnurostro:
                opcion = 2;
                break;
            default:
                opcion = 0;
                break;
        }

        return true;
    }

    private void getPermission() {
        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            getPermission();
        }
    }
}