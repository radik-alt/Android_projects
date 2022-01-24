package com.example.whatsapp.ItemMenues;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.Home;
import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting extends AppCompatActivity {

    private CircleImageView circleImageView;
    private Button save;
    private EditText name, status;
    private String currentId;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser fUser;
    private DatabaseReference rootDB;
    private StorageReference storageReference;

    private static final int GALLERY_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

        // сохранение данных о пользователе и переход в метод UpdateInformation
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInformation();
            }
        });

        verificateInformantionUser();

        // открытие файлового менеджера
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                startActivityForResult(galleryIntent, GALLERY_PIC);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PIC && resultCode == RESULT_OK && data != null) {
            Uri uriImage = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                StorageReference filePath = storageReference.child(currentId + ".jpg");
                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUri = uri.toString();
                                rootDB.child("User").child(currentId).child("image").setValue(downloadUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            verificateInformantionUser();
                                            Toast.makeText(getApplicationContext(), "Фотография загружена!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Накладки с фотографией! Упс...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

            }
        }
    }


    // Здесь идет проверка существует ли статус и имя пользователя по id-ку,
    // потом их забираю из firebase и вставляю в editText-ы
    private void verificateInformantionUser() {
        rootDB.child("User").child(currentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.hasChild("name") && snapshot.hasChild("image")) {
                    String picImage = snapshot.child("image").getValue().toString();
                    String registUser = snapshot.child("name").getValue().toString();
                    String registStatus = snapshot.child("status").getValue().toString();

                    name.setText(registUser);
                    status.setText(registStatus);
                    Picasso.get().load(picImage).into(circleImageView);
                } else if (snapshot.exists() && snapshot.hasChild("name")) {
                    String registUser = snapshot.child("name").getValue().toString();
                    String registStatus = snapshot.child("status").getValue().toString();

                    name.setText(registUser);
                    status.setText(registStatus);
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // Обновление информации о пользователе! Здесь можно обновить статус и имя пользователя
    // После обновления переход в Home.class
    private void UpdateInformation() {
        String setName = name.getText().toString();
        String setStatus = status.getText().toString();

        if (TextUtils.isEmpty(setName)) {
            Toast.makeText(getApplicationContext(), "Введите имя!", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("uId", currentId);
            map.put("name", setName);
            map.put("status", setStatus);

            rootDB.child("User").child(currentId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Информация обновлена", Toast.LENGTH_SHORT).show();

                        Intent homeIntent = new Intent(Setting.this, Home.class);
                        startActivity(homeIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    // инициализация
    private void init () {
        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();
        rootDB = FirebaseDatabase.getInstance().getReference();
        circleImageView = findViewById(R.id.prof);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile image");
        save = findViewById(R.id.save);
        name = findViewById(R.id.name);
        status = findViewById(R.id.status);
    }
}