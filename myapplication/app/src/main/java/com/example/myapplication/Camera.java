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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.util.ArrayList;


public class Camera extends AppCompatActivity {
    //UI views
    private MaterialButton inputImageBtn;
    private MaterialButton recognizeTextBtn;
    private ShapeableImageView imageIv;

    //TAG
    private static final String TAG = "MAIN_TAG";
    private Uri imageUri = null;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private ProgressDialog progressDialog;
    private TextRecognizer textRecognizer;



    private String[] productname = {"동아)박카스F", "샌)이건가요샌드위치", "큐브티라미수N", "해성)미니사과300g", "빙그레)메로나"};
    private int[] productcalorie = {1, 10, 100, 1000, 10000};
    private int[] productcalbo = {1, 10, 100, 1000, 10000};
    private int[] productprotein = {1, 10, 100, 1000, 10000};
    private int[] productfat = {1, 10, 100, 1000, 10000};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        //init UI views

        inputImageBtn = findViewById(R.id.inputImagerBtn);
        recognizeTextBtn = findViewById(R.id.recognizeTextBtn);
        imageIv = findViewById(R.id.imageIv);
        //init arrays of permissions required for camera, gallery
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
        inputImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputImageDialog();
            }
        });

        recognizeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    Toast.makeText(Camera.this, "Pick image first..", Toast.LENGTH_SHORT).show();
                } else {
                    recognizeTextFromImage();

                }
            }
        });


    }

    private void recognizeTextFromImage() {
        Log.d(TAG, "recognizeTextFromImage");
        progressDialog.setMessage("Preparing image..");
        progressDialog.show();


        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);
            progressDialog.setMessage("Recognizing text..");
            Task<Text> textTaskResult = textRecognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            progressDialog.dismiss();
                            String recognizedText = text.getText();
                            Log.d(TAG, "onSuccess: recognizedText:" + recognizedText);
                            sortExpData(recognizedText); //지출량메소드 구동
                            correctionData(recognizedText);  //주석 해제하면 nutri 관련 메소드 구동

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
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

    private void showInputImageDialog() {
        PopupMenu popupMenu = new PopupMenu(this, inputImageBtn);
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Camera");
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Gallery");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == 1) {
                    Log.d(TAG, "onMenuItemClick : Camera Clicked..");

                    if (checkCameraPermissions()) {
                        pickImageCamera();
                    } else {
                        requestCameraPermissions();
                    }
                } else if (id == 2) {

                    Log.d(TAG, "onMenuItemClick : Gallery Clicked..");
                    if (checkStoragePermission()) {
                        pickImageGallery();

                    } else {
                        requestStoragePermission();
                    }
                }
                return true;
            }
        });

    }

    private void pickImageGallery() {
        Log.d(TAG, "pickImageGallery : ");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);

    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult : imageUri" + imageUri);
                        imageIv.setImageURI(imageUri);
//                        recognizeTextFromImage();
                    } else {
                        Log.d(TAG, "onActivityResult :");
                        Toast.makeText(Camera.this, "Canceled..", Toast.LENGTH_SHORT).show();

                    }
                }
            }
    );

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

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Log.d(TAG, "onActivityResult :imageUri" + imageUri);
                        imageIv.setImageURI(imageUri);
//                        recognizeTextFromImage();

                    } else {
                        Log.d(TAG, "onActivityResult:");
                        Toast.makeText(Camera.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);

    }

    private boolean checkCameraPermissions() {
        boolean cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return cameraResult && storageResult;
    }

    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        pickImageCamera();
                    } else {
                        Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickImageGallery();
                    } else {
                        Toast.makeText(this, "Storage permissions is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }


    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String[] sortProductData(String transfer) {
//          인식된 전체 문자열 데이터를 줄 단위로 나누어 배열공간으로 넣는 과정}
        String[] spliited = transfer.split("\\n");
        return spliited;
    }

    //
    private void sortExpData(String transfer) {
//          인식된 전체 문자열 데이터 중 숫자 데이터를 찾아 배열공간으로 넣는 과정}
        String tmp = transfer.replaceAll(",", "");
        String[] spliited = tmp.split("\\n");
        int max = 0;
        int[] sorted = new int[spliited.length];

        for (int i = 0; i < spliited.length; i++) {
            if (isInteger(spliited[i]))
                sorted[i] = Integer.parseInt(spliited[i]);
        }

        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] > max)
                max = sorted[i];
        }
        System.out.println(max);
        sendExpData(max);

    }

    private void correctionData(String transfer) {
        String[] splitted = sortProductData(transfer);
        ListView listview;
        ListViewAdapter adapter;
        // Adapter 생성
        adapter = new ListViewAdapter();
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        ArrayList<Integer> buycalorie = new ArrayList<Integer>();
        ArrayList<Integer> buycalbo = new ArrayList<Integer>();
        ArrayList<Integer> buyprotein = new ArrayList<Integer>();
        ArrayList<Integer> buyfat = new ArrayList<Integer>();
        for (int i = 0; i < splitted.length; i++) {
            for (int j = 0; j < productname.length; j++) {
                if (splitted[i].equals(productname[j])) {
                    System.out.println("***********");
                    System.out.println(splitted[i]);
                    System.out.println("***********");
                    buycalorie.add(productcalorie[j]);
                    buycalbo.add(productcalbo[j]);
                    buyprotein.add(productprotein[j]);
                    buyfat.add(productfat[j]);
                    adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bonobono),
                            productname[j], productcalorie[j], productcalbo[j], productprotein[j], productfat[j]);
                }
            }
        }
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem itemex= (ListViewItem)listview.getItemAtPosition(position);
                itemex.plusItemcount();
                adapter.notifyDataSetChanged();

                int calorie= itemex.getcalorie();
                int calbo= itemex.getcalbo();
                int protein= itemex.getprotein();
                int fat= itemex.getfat();
                calculateTotaldata(calorie,calbo,protein,fat);


            }
        });
    }


    private void calculateTotaldata(int totalcalorie,int totalcalbo,int totalprotein, int totalfat){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("user").child("nutrition").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);
//
                myRef.child("user").child("nutrition").child("eatcalorie").setValue(group.getEatcalorie()+totalcalorie);
                myRef.child("user").child("nutrition").child("eatcalbo").setValue(group.getEatcalbo()+totalcalbo);
                myRef.child("user").child("nutrition").child("eatprotein").setValue(group.getEatprotein()+totalprotein);
                myRef.child("user").child("nutrition").child("eatfat").setValue(group.getEatfat()+totalfat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        private void sendExpData ( int ctotalcost){
            //expdata를 Exp 데이터베이스로 전달
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child("user").child("expenditure").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Data_total group = snapshot.getValue(Data_total.class);
                    int tmp = group.getTotalcost();
                    int sum = tmp + ctotalcost;
                    myRef.child("user").child("expenditure").child("totalcost").setValue(sum);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }
