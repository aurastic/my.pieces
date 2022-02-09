package com.example.mypieces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RepertoireList extends AppCompatActivity {

    private RepertoireListAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;



    private ArrayList<PieceData> pieceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertoire_list);
        setButtons();
        loadData();
        startRecycler(pieceList);

    }


    private void startRecycler(ArrayList<PieceData> arrayList)
    {
        recyclerView = findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new RepertoireListAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RepertoireListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openPieceDetails(position);
            }
        });

    }

    private void setButtons()
    {
        Button addNewButton = findViewById(R.id.button_new_piece);
        Button retiredButton = findViewById(R.id.button_view_retired);

        retiredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchList();
            }
        });

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPieceInputPage.class);
                startActivity(intent);
            }
        });
    }

    private void switchList()
    {
        pieceList.clear();
        saveData();
    }


    private void openPieceDetails(int pos)
    {
        Intent intent = new Intent(this, DetailedPieceViewActivity.class);
        intent.putExtra("list index", pos);
        startActivity(intent);
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

        boolean added = intent.getBooleanExtra("piece added?", false);
        boolean deleted = intent.getBooleanExtra("piece deleted?", false);

        if(deleted)
        {
            int deletionIndex = intent.getIntExtra("index of deletion", 0);
            recyclerAdapter.notifyItemRemoved(deletionIndex);
        }

        if(added)
        {
            ArrayList<Integer> newIndices = intent.getIntegerArrayListExtra("indices");

            for (int index : newIndices)
            {
                recyclerAdapter.notifyItemInserted(index);
            }
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, PiecesMain.class);
        startActivity(intent);
    }

}
