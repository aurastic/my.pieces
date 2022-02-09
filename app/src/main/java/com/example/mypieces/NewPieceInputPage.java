package com.example.mypieces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewPieceInputPage extends AppCompatActivity {

    private ArrayList<PieceData> pieceList;

    private TextInputLayout nameInput;
    private TextInputLayout composerInput;
    private TextInputLayout startDateInput;
    private TextInputLayout endDateInput;
    private TextInputLayout formInput;
    private TextInputLayout genreInput;
    private TextInputLayout moodInput;
    private TextInputLayout upperTime;
    private TextInputLayout lowerTime;
    private TextInputLayout idealBPM;
    private TextInputLayout tempoTextInput;
    private TextInputLayout keyLetterInput;
    private Spinner spinnerKeyTypeInput;
    private TextInputLayout whyInput;
    private TextInputLayout collectionInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piece_input_page);

        nameInput = findViewById(R.id.piece_title_field);
        composerInput = findViewById(R.id.composer_field);
        startDateInput = findViewById(R.id.input_start_date);
        endDateInput = findViewById(R.id.input_end_date);
        formInput = findViewById(R.id.form_field);
        genreInput = findViewById(R.id.genre_field);
        moodInput = findViewById(R.id.mood_field);
        upperTime = findViewById(R.id.upper_time_field);
        lowerTime = findViewById(R.id.lower_time_field);
        idealBPM = findViewById(R.id.input_field_bpm);
        tempoTextInput = findViewById(R.id.tempo_field);
        keyLetterInput = findViewById(R.id.key_field);
        spinnerKeyTypeInput = findViewById(R.id.spinner);
        whyInput = findViewById(R.id.input_why);
        collectionInput = findViewById(R.id.input_collection);

        clearInputs();
        loadData();
        makeSpinners();
        initializeButtons();
    }

    public void initializeButtons()
    {
        Button submitButton = findViewById(R.id.submit_button);
        Button resetButton = findViewById(R.id.button_reset);
        Button submitPlusButton = findViewById(R.id.submit_plus_button);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                logNewPiece(false);
            }
        });

        submitPlusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                logNewPiece(true);
                clearInputs();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                clearInputs();
            }
        });
    }

    public void logNewPiece(boolean isFirstOfMany)
    {
        PieceData entry = new PieceData();

        entry.pieceName = nameInput.getEditText().getText().toString();
        entry.composer = composerInput.getEditText().getText().toString();
        entry.form = formInput.getEditText().getText().toString();
        entry.genre = genreInput.getEditText().getText().toString();
        entry.mood = moodInput.getEditText().getText().toString();
        entry.key = keyLetterInput.getEditText().getText().toString();
        entry.keyType = (KeyType) spinnerKeyTypeInput.getSelectedItem();
        entry.tempoText = tempoTextInput.getEditText().getText().toString();
        entry.idealTempo = idealBPM.getEditText().getText().toString();
        entry.upperTimeSig = upperTime.getEditText().getText().toString();
        entry.lowerTimeSig = lowerTime.getEditText().getText().toString();
        entry.startMonth = startDateInput.getEditText().getText().toString();
        entry.why = whyInput.getEditText().getText().toString();
        entry.collection = collectionInput.getEditText().getText().toString();

        this.pieceList.add(entry);


        Toast.makeText(this,"piece added to current repertoire" + pieceList.size(),Toast.LENGTH_LONG).show();
        saveData();

        ArrayList<Integer> newIndices = new ArrayList();
        newIndices.add(pieceList.lastIndexOf(entry));

        if(!isFirstOfMany)
        {
            Intent intent = new Intent(this, RepertoireList.class);
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("indices", newIndices);
            bundle.putBoolean("was piece added", true);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }

    private void makeSpinners()
    {
        Spinner keyTypeSpinner = findViewById(R.id.spinner);
        Spinner gradeSpinner = findViewById(R.id.grade_spinner);
        Spinner compSpinner = findViewById(R.id.composition_spinner);
        Spinner typeSpinner = findViewById(R.id.list_type_spinner);
        Spinner stateSpinner = findViewById(R.id.state_spinner);


        ArrayList<KeyType> keyTypeArray = new ArrayList<>();
        ArrayList<GradingType> gradeArray = new ArrayList<>();
        ArrayList<CompositionType> compArray = new ArrayList<>();
        ArrayList<RepType> typeArray = new ArrayList<>();
        ArrayList<StateType> stateArray = new ArrayList<>();

        keyTypeArray.add(KeyType.Major);
        keyTypeArray.add(KeyType.Minor);

        gradeArray.add(GradingType.RCM);
        gradeArray.add(GradingType.ABRSM);
        gradeArray.add(GradingType.AMEB);
        gradeArray.add(GradingType.Trinity);

        compArray.add(CompositionType.Anh);
        compArray.add(CompositionType.BWV);
        compArray.add(CompositionType.Op);

        typeArray.add(RepType.Current);
        typeArray.add(RepType.Retired);

        stateArray.add(StateType.Development);
        stateArray.add(StateType.Memorization);
        stateArray.add(StateType.Finishing);

        ArrayAdapter<KeyType> keyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keyTypeArray);
        ArrayAdapter<GradingType> gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gradeArray);
        ArrayAdapter<CompositionType> compAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, compArray);
        ArrayAdapter<StateType> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stateArray);
        ArrayAdapter<RepType> repListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeArray);


        keyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        compAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        repListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        keyTypeSpinner.setAdapter(keyAdapter);
        gradeSpinner.setAdapter(gradeAdapter);
        compSpinner.setAdapter(compAdapter);
        typeSpinner.setAdapter(repListAdapter);
        stateSpinner.setAdapter(stateAdapter);

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
            pieceList = new ArrayList<>();
        }

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadData();
        clearInputs();
    }

    private void clearInputs()
    {
        nameInput.getEditText().getText().clear();
        composerInput.getEditText().getText().clear();
        formInput.getEditText().getText().clear();
        genreInput.getEditText().getText().clear();
        moodInput.getEditText().getText().clear();
        //keyLetterInput.getEditText().getText().charAt(0);
        //spinnerKeyTypeInput.getSelectedItem();
        tempoTextInput.getEditText().getText().clear();
        idealBPM.getEditText().getText().clear();
        upperTime.getEditText().getText().clear();
        lowerTime.getEditText().getText().clear();
        startDateInput.getEditText().getText().clear();

    }
}
