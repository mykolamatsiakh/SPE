package com.spe.behero.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mykola on 04.07.17.
 */

public class ThirdQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText WillingLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_question);
        findViewById(R.id.next_button3).setOnClickListener(this);
        WillingLevel = (EditText) findViewById(R.id.willingLevel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(checkInput()) {
            Intent intent = new Intent(ThirdQuestionActivity.this, FourthQuestionActivity.class);
            startActivity(intent);
        }
        else showToast("Введіть число від 1 до 100");
    }

    private boolean checkInput() {
        String willingLevel = WillingLevel.getText().toString().trim();
        Intent intent = getIntent();
        int current_level= intent.getIntExtra("level", 0);
        if (willingLevel.length() != 0) {
            int RelationsLevel = Integer.valueOf(willingLevel);
            if (RelationsLevel <= 100 && RelationsLevel >= 1 && RelationsLevel > current_level) {
                return true;
            } else
                showToast("Ваш бажаний рівень менший за поточний");
                return false;
        } else return false;
    }

    private void showToast(String message) {
        Toast.makeText(ThirdQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
