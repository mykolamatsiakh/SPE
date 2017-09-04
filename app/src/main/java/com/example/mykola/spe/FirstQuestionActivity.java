package com.example.mykola.spe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by mykola on 22.06.17.
 */


public class FirstQuestionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_CALENDAR, Manifest.permission.GET_ACCOUNTS};

    private static final int RC_PERMISSIONS = 0;
    EditText SuccesfullDays;
    EditText UnsuccesfulDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_question);
        SuccesfullDays = (EditText)findViewById(R.id.first_question_succesfull);
        UnsuccesfulDays = (EditText)findViewById(R.id.first_question_unsuccesfull);
        findViewById(R.id.first_next_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
if (lacksPermissions()) {
    requestPermission();
}
    }

    private boolean lacksPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        for (String permission : PERMISSIONS) {
            if (lacksPermission(permission)) return true;
        }
        return false;
    }

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(FirstQuestionActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(FirstQuestionActivity.this, PERMISSIONS, RC_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // ignored
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    private void showToast(String message) {
        Toast.makeText(FirstQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }
}


