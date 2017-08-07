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


/**
 * Created by mykola on 22.06.17.
 */


public class FirstQuestionActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 0;
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
        // Here, thisActivity is the current activity

        requestPermission();
        if (!hasPermissions()) {
            return;
        }
    }

    private boolean checkInput() {
            if ( Integer.parseInt(SuccesfullDays.getText().toString().trim())+ Integer.parseInt(UnsuccesfulDays.getText().toString().trim())== 100) {
                return true;
            }
            else return false;

        }

    private boolean isEmpty() {
        if (SuccesfullDays.getText().toString().trim().length() == 0 || UnsuccesfulDays.getText().toString().trim().length() == 0) {
          return false;
        }
        else return true;
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


    }


