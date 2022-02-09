package com.example.mypieces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailedPieceViewActivity extends AppCompatActivity {

    private ArrayList<PieceData> pieceList;
    private int currentIndex;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_piece_view);
        loadData();
        Intent intent = getIntent();
        currentIndex = intent.getIntExtra("list index", 0);
        displayPieceData(currentIndex);

        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePiece();

            }
        });

    }

    private void deletePiece()
    {
        Toast.makeText(this,"deleted " + name, Toast.LENGTH_LONG).show();
        pieceList.remove(currentIndex);
        saveData();
        Intent intent = new Intent(this, RepertoireList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("piece deleted?", true);
        bundle.putInt("index of deletion", currentIndex);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void displayPieceData(int index)
    {
        name = pieceList.get(currentIndex).pieceName;

        TextView name = findViewById(R.id.text_view_name);
        TextView composer = findViewById(R.id.text_view_composer);
        TextView key = findViewById(R.id.text_view_key);
        TextView keyType = findViewById(R.id.text_view_key_type);
        TextView listType = findViewById(R.id.text_view_list_type);

        PieceData data = pieceList.get(index);

        name.setText(data.pieceName);
        composer.setText(data.composer);
        key.setText(data.key);
        if(data.keyType != null)
        {
            keyType.setText(data.keyType.toString());
        }

        if(data.listType != null)
        {
            listType.setText(data.listType.toString());
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pieceList);
        editor.putString("pieces list", json);
        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("pieces list", null);
        Type type = new TypeToken<ArrayList<PieceData>>(){}.getType();
        pieceList = gson.fromJson(json, type);

        if(pieceList == null)
        {
            Toast.makeText(this,"no list to load",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadData();
        Intent intent = getIntent();
        currentIndex = intent.getIntExtra("list index", 0);
        displayPieceData(currentIndex);
    }
}
