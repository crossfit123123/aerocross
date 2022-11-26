/*
 *  @(#)Myapplication.java   2022/11/23
 *   Camera Class
 *  *Gunneopyeon.eta
 *  *2022-11-23
 */

package com.example.myapplication;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

/*
Camera 는 카메라 권한을 얻어 사진을 촬영 후 텍스트화 하는 클래스
 */

public class Camera extends AppCompatActivity {

    private int ccalo;
    private int ccalbo;
    private int cprotein;
    private int cfat;
    private int cnutdata;
    private int ctextcost;
    private int ctotalcost;
    private int cnum;

    private MaterialButton inputImageBtn;
    private MaterialButton recognizeTextBtn;
    private ShapeableImageView imageIv;
    private EditText recognizedTextEt;

    private static final String TAG = "MAIN_TAG";
    private Uri imageUri = null;
    private static final int CAMERA_REQUEST_CODE = 100;

    private String[] cameraPermissions;

    private ProgressDialog progressDialog;
    private TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        inputImageBtn = findViewById(R.id.inputImagerBtn);
        recognizeTextBtn = findViewById(R.id.recognizeTextBtn);
        imageIv = findViewById(R.id.imageIv);
        recognizedTextEt = findViewById(R.id.recognizedTextEt);

//        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
        inputImageBtn.setOnClickListener(new View.OnClickListener() {
            //허가받은 카메라 사용을 위한 버튼
            @Override
            public void onClick(View v) {
                if (checkCameraPermissions()) {
                    pickImageCamera();//허가를 정상적으로 받았을 경우
                } else {
                    requestCameraPermissions(); // 그렇지 않을 경우 카메라 허가 요청
                }
                ;
            }
        });
        //Uri
        recognizeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    Toast.makeText(Camera.this, "Pick image first..", Toast.LENGTH_SHORT).show();
                } else {
                    recognizeTextFromImage(); /*이미지로부터 텍스트 인식 */
                }
            }
        });
    }

    /*텍스트화 성공여부 확인 및 결과도출*/
    private void recognizeTextFromImage() {
        Log.d(TAG, "recognizeTextFromImage");
        progressDialog.setMessage("Preparing image..");
        progressDialog.show();

        //이미지 불러오기
        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);
            progressDialog.setMessage("Recognizing text..");
            Task<Text> textTaskResult = textRecognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {

                        //텍스트화 성공
                        @Override
                        public void onSuccess(Text text) {
                            progressDialog.dismiss();
                            String recognizedText = text.getText();
                            Log.d(TAG, "onSuccess: recognizedText:" + recognizedText);
                            recognizedTextEt.setText(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        //텍스트화 실패
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.e(TAG, "Onfailure:", e);
                            Toast.makeText(Camera.this, "Failed recognizing text due to" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.e(TAG, "recognizedTextFromImage:", e);
            Toast.makeText(this, "Failed preparing image due to" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//  캡쳐된 이미지를 작업 수행을 위해 다른 곳으로 전달

    private void pickImageCamera() {
        Log.d(TAG, "pickImageCamera :");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    //결과를 위한 활동 실행
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),

            //Callback을 통해 onActivityResult 자동 호출됨
            new ActivityResultCallback<ActivityResult>() {
                @Override

                //넘어간 액티비티가 다시 돌아오게 만듬
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Log.d(TAG, "onAictivityResult :imageUri" + imageUri);
                        imageIv.setImageURI(imageUri);

                    } else {
                        Log.d(TAG, "onActivityResult:");
                        Toast.makeText(Camera.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    //카메라 퍼미션을 가지고 있는지 체크하는 method
    private boolean checkCameraPermissions() {
        boolean cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        return cameraResult;
    }

    //사용자에게 카메라 퍼미션을 요청하는 method
    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    //카메라 사용에 대한 허가 여부를 확인하는 method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (cameraAccepted) {
                pickImageCamera();
            } else {
                Toast.makeText(this, "Camera permissions are required", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

//    private String[] sortProductData(String recognizedText){
//          인식된 전체 문자열 데이터를 줄 단위로 나누어 배열공간으로 넣는 과정}

//    private int sortExpData(String recognizedText){
//           인식된 전체 문자열 데이터 중 숫자 데이터를 찾아 배열공간으로 넣는 과정}

//    private int findBestValue(int sortedText[]){
//               가장 큰 숫자를 반환하는 method 작성
//               ...
//               return ctotalcost; }


//    private String correctionData(String recognizedText[], String DatabaseText){
//        int cnum[];
//        for(int i=0 ;i<=n;++){
//        if (recognizedText[n] == DatabaseText){
//              cnum[i]=사용자로부터 받아옴
//              //  ccalo, ccalbo cprotein cfat는 데이터베이스(Product)로부터 받아옴
//              ccalbo[i]=(Integer.toString(제품명.getcalorie()))*cnum[n]
//              cprotein[i]=(Integer.toString(제품명.getcalorie()))*cnum[n]
//              cfat[i]=(Integer.toString(제품명.getcalorie()))*cnum[n]
//              ccalo[i]=(Integer.toString(제품명.getcalorie()))*cnum[n]
//        }
//    }
//        private int calcNutData(int ccalo[],int ccalbo[],int cprotein[],int cfat[]){
//            int ctcalo, ctcalbo, ctprotein, ctfat = 0;
//            for(int i=0;i<=n;++){
//                ctcalo = ccalo[i]+ctcalo;
//                ctcalbo = ccalbo[i]+ctcalbo;
//                ctprotein = cprotein[i]+ctprotein;
//                ctfat = cfat[i] + ctfat ;
//            }
//        }

//    }
//      private int sendNutriData(int ctcalo,int ctcalbo, int ctprotein, int ctfat){
//        //데이터베이스(Nutri) 로 보냄
//        }
//      private int sendExpData(int ctotalcost){
//        //expdata를 Exp 데이터베이스로 전달
//    }
}



