package com.example.mykola.spe;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.Date;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by mykola on 04.07.17.
 */

public class FifthQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mNextButton;
    Context context;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.monthBudget);

    }

    private void addNewReminder(Calendar cal, int description, int dayOfweek) {
        Calendar calendar = Calendar.getInstance();
        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, "SPE");
        cv.put(CalendarContract.Events.DESCRIPTION, getString(description));
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
        cv.put(CalendarContract.Events.HAS_ALARM, true);
        cv.put(CalendarContract.Events.DTSTART, calendar.get(dayOfweek));



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);

    }


    @Override
    public void onClick(View view) {
        if(checkInput()){
        Intent intent = new Intent(FifthQuestionActivity.this, ThanksActivity.class);
        startActivity(intent);
        //   addNewReminder( ,R.string.ask_about_dreams);
            //
        }else return;
    }

    private boolean checkInput() {
        String MonthBudget = mEditText.getText().toString().trim();
        if (MonthBudget.length() != 0) {
            return true;
        } else return false;
    }

}


