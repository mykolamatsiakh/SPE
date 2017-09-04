package com.example.mykola.spe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mykola on 22.06.17.
 */

public class SecondQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText relationsLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_question);
        relationsLevel = (EditText) findViewById(R.id.relationsLevel);
        findViewById(R.id.next_button_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(checkInput()) {
            Intent intent = new Intent(SecondQuestionActivity.this, ThirdQuestionActivity.class);
            startActivity(intent);
        }
        else showToast("Введіть число від 1 до 100");
    }

    private  boolean checkInput() {
        String LevelOfRelations = relationsLevel.getText().toString().trim();
        if(LevelOfRelations.length() != 0){
            int RelationsLevel = Integer.valueOf(LevelOfRelations);
            if(RelationsLevel <=100 && RelationsLevel >= 1){
                return true;
            }
            else return false;
        }
        else return false;
    }

    private void showToast(String message) {
        Toast.makeText(SecondQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }

}
