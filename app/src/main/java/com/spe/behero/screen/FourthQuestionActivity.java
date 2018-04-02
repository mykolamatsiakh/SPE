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

public class FourthQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_question);
        findViewById(R.id.next_button4).setOnClickListener(this);
        mEditText =  findViewById(R.id.daysPerMonth);
        findViewById(R.id.back_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(checkInput()) {
            String DaysPerMonth = mEditText.getText().toString().trim();
            int mDays = Integer.valueOf(DaysPerMonth);
            Intent intent = new Intent(FourthQuestionActivity.this, FifthQuestionActivity.class);
            intent.putExtra("Days", mDays );
            startActivity(intent);
        }
        else {
        }
    }

    private boolean checkInput() {
        String DaysPerMonth = mEditText.getText().toString().trim();
        int Days = Integer.valueOf(DaysPerMonth);
        return Days <= 21 && Days > 0;
    }

    private void showToast(String message) {
        Toast.makeText(FourthQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
