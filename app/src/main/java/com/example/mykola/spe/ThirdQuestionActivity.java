package com.example.mykola.spe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mykola on 04.07.17.
 */

public class ThirdQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mNextButton;
    private EditText WillingLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_question);
        findViewById(R.id.next_button3).setOnClickListener(this);
        WillingLevel = (EditText) findViewById(R.id.willingLevel);
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
        if (willingLevel.length() != 0) {
            int RelationsLevel = Integer.valueOf(willingLevel);
            if (RelationsLevel <= 100 && RelationsLevel >= 1) {
                return true;
            } else return false;
        } else return false;
    }

    private void showToast(String message) {
        Toast.makeText(ThirdQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
