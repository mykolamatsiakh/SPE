package com.example.mykola.spe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by mykola on 04.07.17.
 */

public class ThirdQuestionActivity extends AppCompatActivity{
    private Button mNextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_question);
        findViewById(R.id.next_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdQuestionActivity.this, FourthQuestionActivity.class);
                startActivity(intent);
            }
        });
    }
}
