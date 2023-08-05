package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.mlkit.vision.face.FaceLandmark;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {

//    ImageView originalimage, croopedface;
//    Button btndetectface;
//    static final int SCALING_FACTOR = 5;
    FaceDetector detector;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private ImageCapture imageCapture;
    private ImageAnalysis imageAnalysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        originalimage = findViewById(R.id.originalimage);
//        croopedface = findViewById(R.id.croppedimage);
//        btndetectface = findViewById(R.id.btndetectface);

        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());

        FaceDetectorOptions realTimeFdo =
                new FaceDetectorOptions.Builder()
//                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
//                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .build();

        // init FaceDetector obj
        detector = FaceDetection.getClient(realTimeFdo);
//
//        btndetectface.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cara2);
//
////                Uri imageUri = null;
////                try {
////                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
////                } catch (IOException e) {
////                    e.printStackTrace();
//////                    throw new RuntimeException(e);
////                }
////
////                BitmapDrawable bitmapDrawable = (BitmapDrawable) originalimage.getDrawable();
////                Bitmap bitmap2 = bitmapDrawable.getBitmap();
//
//                analyzePhoto(bitmap);
//            }
//        });
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        // Camera selector use case
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        
        // Preview use case
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        
        // Image capture use case
//        imageCapture = new ImageCapture.Builder()
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .build();

        // Image analysis use case
        imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)

                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis);
    }

    @NonNull
    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @Override
    @ExperimentalGetImage
    public void  analyze(@NonNull ImageProxy image) {
        // Proces de image
        Image cameraImage = image.getImage();
        Log.i("Analyze", "Inicio Analizando foto");
        if(cameraImage != null) {
            InputImage inputImage = InputImage.fromMediaImage(cameraImage, image.getImageInfo().getRotationDegrees());
            Log.i("Analyze", "Detectando rostro");
            Task<List<Face>> result = detector.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(List<Face> faces) {
                        Log.i("Analyze", "Deteccion correcta");
                        for (Face face: faces){
//                            Rect rect = face.getBoundingBox();
//                            rect.set(rect.left*SCALING_FACTOR,
//                                    rect.top*(SCALING_FACTOR-1),
//                                    rect.right*SCALING_FACTOR,
//                                    (rect.bottom*SCALING_FACTOR)+90
//                            );


//                            List<FaceLandmark> landmarks = face.getAllLandmarks();
//                            for(FaceLandmark landmark : landmarks) {
//                                Log.d("Landmaks", landmark.getPosition().toString());
//                            }
                            List<PointF> nose = face.getContour(FaceContour.NOSE_BRIDGE).getPoints();
                            for (PointF point : nose) {
                                Log.d("Nose point", point.toString());
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Dtection failed
                        Toast.makeText(MainActivity.this, "Detection failed due to "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<Face>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Face>> task) {
                        image.close();
                    }
                });

            List<Face> faces = result.getResult();
            for (Face face: faces){
                Rect rect = face.getBoundingBox();
                List<PointF> nose = face.getContour(FaceContour.NOSE_BRIDGE).getPoints();
                for (PointF point : nose) {
                    Log.d("Nose point", point.toString());
                }
            }
        }
//        image.close();
    }

//    private void analyzePhoto(Bitmap bitmap){
//        Bitmap smallerBitmap = Bitmap.createScaledBitmap(
//                bitmap,
//                bitmap.getWidth()/SCALING_FACTOR,
//                bitmap.getHeight()/SCALING_FACTOR,
//                false);
//
//        InputImage inputImage = InputImage.fromBitmap(smallerBitmap, 0);
//
//        dtector.process(inputImage)
//                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
//                    @Override
//                    public void onSuccess(List<Face> faces) {
//                        for (Face face: faces){
//                            Rect rect = face.getBoundingBox();
//                            rect.set(rect.left*SCALING_FACTOR,
//                                    rect.top*(SCALING_FACTOR-1),
//                                    rect.right*SCALING_FACTOR,
//                                    (rect.bottom*SCALING_FACTOR)+90
//                            );
//
//
////                            List<FaceLandmark> landmarks = face.getAllLandmarks();
////                            for(FaceLandmark landmark : landmarks) {
////                                Log.d("Landmaks", landmark.getPosition().toString());
////                            }
////                            List<PointF> nose = face.getContour(FaceContour.NOSE_BRIDGE).getPoints();
////                            for (PointF point : nose) {
////                                Log.d("Nose point", point.toString());
////                            }
//                        }
//
//                        croopDetectedFaces(bitmap, faces);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Dtection failed
//                        Toast.makeText(MainActivity.this, "Detection failed due to "+e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

//    private void croopDetectedFaces(Bitmap bitmap, List<Face> faces) {
//        Rect rect = faces.get(0).getBoundingBox();
//        List<PointF> nosePoints = faces.get(0).getContour(FaceContour.NOSE_BRIDGE).getPoints();
//
//        int x = Math.max(rect.left, 0);
//        int y = Math.max(rect.top, 0);
//        int width = rect.width();
//        int height = rect.height();
//
//        Bitmap croopedBitmap = Bitmap.createBitmap(
//                bitmap,
//                x,
//                y,
//                (x + width > bitmap.getWidth()) ? bitmap.getWidth() - x : width,
//                (y + height > bitmap.getHeight()) ? bitmap.getHeight() - y : height
//        );
//
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        paint.setTextSize(2);
//
//        Log.i("Resultado", "" + (width*SCALING_FACTOR));
//        Log.i("Resultado", "" + (height*SCALING_FACTOR));
//
//        Canvas canvas = new Canvas();
//        canvas.setBitmap(croopedBitmap);
//        canvas.drawLine(
//                (nosePoints.get(0).x)+(width/SCALING_FACTOR),
//                (nosePoints.get(0).y)+(height/SCALING_FACTOR),
//                (nosePoints.get(1).x)+(width/SCALING_FACTOR),
//                (nosePoints.get(1).y)+(height/SCALING_FACTOR),
//                paint
//        );
//
//        croopedface.setImageBitmap(croopedBitmap);
//    }
}