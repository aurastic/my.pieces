package com.example.mypieces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewSheetInputActivity extends AppCompatActivity {

    private ArrayList<SheetMusicData> sheetList;
    private TextInputLayout titleInput;
    private TextInputLayout publisherInput;
    private static final int PICK_IMAGE_FILE = 2;

    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sheet_input);
        initializeButtons();
        loadData();
        imageView = findViewById(R.id.imageView);
        titleInput = findViewById(R.id.input_sheet_title);
        publisherInput = findViewById(R.id.input_publisher);

    }

    private void initializeButtons()
    {
        Button submitButton = findViewById(R.id.button_submit);
        Button openImageButton = findViewById(R.id.button_add_image);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logSheet();

            }
        });

        openImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFile();
            }
        });
    }

    private void logSheet()
    {
        SheetMusicData entry = new SheetMusicData();
        entry.title = titleInput.getEditText().getText().toString();
        entry.publisher = publisherInput.getEditText().getText().toString();
        entry.imageUri = imageUri;
        sheetList.add(entry);
        saveData();
        Intent i = new Intent(this, SheetsMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_FILE) {

                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {

                    imageView.setImageURI(selectedImageUri);
                    imageUri = selectedImageUri;
                }
            }
        }
    }



    private void openFile()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_FILE);
    }



    private void saveData()
    {
        SharedPreferences sharedPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sheetList);
        editor.putString("sheet list", json);
        editor.apply();

    }

    private void loadData()
    {
        SharedPreferences sharedPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("sheet list", null);
        Type type = new TypeToken<ArrayList<SheetMusicData>>(){}.getType();
        sheetList = gson.fromJson(json, type);

        if(sheetList == null)
        {
            sheetList = new ArrayList<>();
            Toast.makeText(this, "New Sheet List Created", Toast.LENGTH_SHORT).show();
        }

    }
}
