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

public class FourthQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_question);
        findViewById(R.id.next_button4).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.daysPerMonth);
    }

    @Override
    public void onClick(View view) {
        if(checkInput()) {
            Intent intent = new Intent(FourthQuestionActivity.this, FifthQuestionActivity.class);
            startActivity(intent);
        }
        else showToast("Оберіть к-сть днів від 1 до 31");
    }

    private boolean checkInput() {
        String DaysPerMonth = mEditText.getText().toString().trim();
        if (DaysPerMonth.length() != 0) {
            int daysNumber = Integer.valueOf(DaysPerMonth);
            if (daysNumber <= 31 && daysNumber >= 1) {
                return true;
            } else return false;
        } else return false;
    }

    private void showToast(String message) {
        Toast.makeText(FourthQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
