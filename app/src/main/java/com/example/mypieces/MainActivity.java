package com.example.mypieces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();

    }

    private void initializeButtons()
    {
        Button b1 = findViewById(R.id.button1);
        Button b2 = findViewById(R.id.button2);

        linkButtonToIntent(b1, PiecesMain.class);
        linkButtonToIntent(b2, ScalesMain.class);
    }

    public void linkButtonToIntent(Button button, final Class c)
    {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), c);
                startActivity(intent);
            }
        });
    }



}

