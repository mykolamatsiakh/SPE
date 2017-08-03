package com.example.mykola.spe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mykola on 22.06.17.
 */


public class FirstQuestionActivity extends AppCompatActivity {
    private Button mNextButton;
    private EditText SuccesfullDays;
    private EditText UnsuccesfulDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_question);
        SuccesfullDays = (EditText)findViewById(R.id.first_question_succesfull);
        UnsuccesfulDays = (EditText)findViewById(R.id.first_question_unsuccesfull);
        findViewById(R.id.first_next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstQuestionActivity.this, SecondQuestionActivity.class);
                startActivity(intent);
            }
        });

    }

}
