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
        return DaysPerMonth.length() != 0;
    }

    private void showToast(String message) {
        Toast.makeText(FourthQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }

}
