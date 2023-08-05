package com.example.myapplicationcamerax;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageView faceView;
    PreviewView previewView;
    FaceDetector detector;
    Paint paintNose, paintRect, paintEyes, paintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewView = findViewById(R.id.previewView);
        faceView = findViewById(R.id.faceView);

        faceView.setVisibility(View.GONE);
        faceView.setVisibility(View.VISIBLE);

        paintNose = new Paint();
        paintNose.setColor(Color.BLUE);
        paintNose.setStyle(Paint.Style.STROKE);
        paintNose.setStrokeWidth(4);
        paintNose.setAntiAlias(true);

        paintRect = new Paint();
        paintRect.setColor(Color.YELLOW);
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setStrokeWidth(4);
        paintRect.setAntiAlias(true);

        paintEyes = new Paint();
        paintEyes.setColor(Color.RED);
        paintEyes.setStyle(Paint.Style.STROKE);
        paintEyes.setStrokeWidth(4);
        paintEyes.setAntiAlias(true);

        paintText = new Paint();
        paintText.setColor(Color.GREEN);
        paintText.setTextSize(40);

        FaceDetectorOptions realTimeFdo =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .build();
        detector = FaceDetection.getClient(realTimeFdo);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(getExecutor(), this);

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis);
    }

    @Override
    @OptIn(markerClass = ExperimentalGetImage.class)
    public void analyze(@NonNull ImageProxy imageProxy) {
//        Log.d("ANALYZE", "analyze: got the frame at: " + imageProxy.getImageInfo().getTimestamp());

        Image cameraImage = imageProxy.getImage();
        if(cameraImage == null) {
            imageProxy.close();
            return;
        }

        Bitmap previewViewBitmap = previewView.getBitmap();

        if (previewViewBitmap == null) {
            imageProxy.close();
            return;
        }

//        InputImage inputImage = InputImage.fromBitmap(previewViewBitmap, 0);
        InputImage inputImage = InputImage.fromMediaImage(cameraImage, imageProxy.getImageInfo().getRotationDegrees());

        Task<List<Face>> result = detector.process(inputImage);
        result
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
            @Override
            public void onSuccess(List<Face> faces) {
//                Log.d("ANALYZE", "analyze: got a face");

                for(Face face: faces) {
                    Rect rect = face.getBoundingBox();
                    List<PointF> nosePoints = face.getContour(FaceContour.NOSE_BRIDGE).getPoints();
                    List<PointF> eyeR = face.getContour(FaceContour.RIGHT_EYE).getPoints();
                    List<PointF> eyeL = face.getContour(FaceContour.LEFT_EYE).getPoints();

                    PointF eyeR4pointF = eyeR.get(4);
                    PointF eyeR12pointF = eyeR.get(12);
                    PointF eyeRCenter = new PointF((eyeR4pointF.x + eyeR12pointF.x)/2, (eyeR4pointF.y + eyeR12pointF.y)/2);

                    PointF eyeL4pointF = eyeL.get(4);
                    PointF eyeL12pointF = eyeL.get(12);
                    PointF eyeLCenter = new PointF((eyeL4pointF.x + eyeL12pointF.x)/2, (eyeL4pointF.y + eyeL12pointF.y)/2);

                    PointF nosePoint0 = nosePoints.get(0);
                    PointF nosePoint1 = nosePoints.get(1);

                    float m = (nosePoint1.y - nosePoint0.y)/(nosePoint1.x - nosePoint0.x);
                    float b = nosePoint0.y - (m * nosePoint0.x);

                    double d0 = Math.abs((m*eyeRCenter.x-eyeRCenter.y+b)/(Math.sqrt(m*m+1)));
                    double d1 = Math.abs((m*eyeLCenter.x-eyeLCenter.y+b)/(Math.sqrt(m*m+1)));
                    double ratio;
                    if(d1 == d0) ratio= 100;
                    else ratio = d1>d0 ? (d0/d1)*100 : (d1/d0)*100;

                    int width, height;
                    height = previewViewBitmap.getHeight();
                    width = previewViewBitmap.getWidth();

                    Bitmap faceViewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas faceCanvas = new Canvas(faceViewBitmap);
                    faceCanvas.drawRect(rect, paintRect);
                    faceCanvas.drawLine(nosePoints.get(0).x, nosePoints.get(0).y, nosePoints.get(1).x, nosePoints.get(1).y, paintNose);
                    faceCanvas.drawPoint(eyeRCenter.x, eyeRCenter.y, paintEyes);
                    faceCanvas.drawPoint(eyeLCenter.x, eyeLCenter.y, paintEyes);
                    faceCanvas.drawText("Simetria: "+ ratio, 20, 50, paintText);

                    faceView.setImageBitmap(faceViewBitmap);
                }

                imageProxy.close();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        imageProxy.close();
                    }
                });
    }
}