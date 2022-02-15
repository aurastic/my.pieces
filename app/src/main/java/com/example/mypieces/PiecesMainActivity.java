package com.example.mypieces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

//import java.util.ArrayList;


public class PiecesMain extends AppCompatActivity {

    //private ArrayList<PieceData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pieces_main);
        initializePage();
    }

    public void initializePage()
    {

        Button b1 = findViewById(R.id.new_piece_button);
        Button b2 = findViewById(R.id.current_rep_button);

        linkButtonToIntent(b1, NewPieceInputPage.class);
        linkButtonToIntent(b2, RepertoireList.class);

    }
    public void linkButtonToIntent(Button button, final Class c)
    {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), c);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
