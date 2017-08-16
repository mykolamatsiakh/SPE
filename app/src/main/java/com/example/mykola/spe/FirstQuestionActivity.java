package com.example.mykola.spe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by mykola on 22.06.17.
 */


public class FirstQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 0;
    EditText SuccesfullDays;
    EditText UnsuccesfulDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_question);
        SuccesfullDays = (EditText)findViewById(R.id.first_question_succesfull);
        UnsuccesfulDays = (EditText)findViewById(R.id.first_question_unsuccesfull);
        findViewById(R.id.first_next_button).setOnClickListener(this);

        // Here, thisActivity is the current activity
        requestPermission();
        if (!hasPermissions()) {
            return;
        }
    }

    private void requestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_CALENDAR};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
    }
    private boolean hasPermissions() {
        int res;
        String permission = Manifest.permission.WRITE_CALENDAR;
        res = checkCallingOrSelfPermission(permission);
        if (PackageManager.PERMISSION_GRANTED == res) {
            return true;
        } else
            return false;
    }

    private void showToast(String message) {
        Toast.makeText(FirstQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        String firstAnswer = SuccesfullDays.getText().toString().trim();
        String secondAnswer = UnsuccesfulDays.getText().toString().trim();
        if(firstAnswer.length() != 0 && secondAnswer.length() !=0){
            int FirstAnswer = Integer.parseInt(SuccesfullDays.getText().toString());
            int SecondAnswer = Integer.parseInt(UnsuccesfulDays.getText().toString());
            int Sum = FirstAnswer +SecondAnswer;
                if(Sum == 100) {
                    Intent intent = new Intent(FirstQuestionActivity.this, SecondQuestionActivity.class);
                    startActivity(intent);
                }
                else showToast("Ви не заповнили всі поля або пропорція неправильна");
        }
        else
            showToast("Ви не заповнили всі поля або пропорція неправильна");

    }

}


