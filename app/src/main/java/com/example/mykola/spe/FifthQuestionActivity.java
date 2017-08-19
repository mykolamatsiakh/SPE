package com.example.mykola.spe;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
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
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.monthBudget);
    }

    private void addNewReminder(int description, int day, int hour) {
        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, "SPE");
        cv.put(CalendarContract.Events.DESCRIPTION, getString(description));
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
        cv.put(CalendarContract.Events.HAS_ALARM, true);
        cv.put(CalendarContract.Events.DTSTART, pickDate(day,hour));
        cv.put(CalendarContract.Events.DTEND, pickDate(day,hour+1));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[]    grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);

    }

    public static long pickDate(int day, int hour)
    {
        Calendar rightNow = Calendar.getInstance();
        int timeShift = rightNow.get(Calendar.ZONE_OFFSET);
        return Date.UTC(rightNow.get(Calendar.YEAR) - 1900, rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH) + day, hour - (timeShift / 3600000)-1, 0, 0);
    }

    @Override
    public void onClick(View view) {
        String MoneyPerMonth = mEditText.getText().toString().trim();
        int mMonthBudget = Integer.valueOf(MoneyPerMonth);
        if(checkInput()){
             if(mMonthBudget <= 10) {
                 startNextActivity();
                 addNewReminder(R.string.wash_dishes, 1, 20);
                 addNewReminder(R.string.ask_about_passed_day, 3, 22);
                 addNewReminder(R.string.get_from_the_job, 5, 18);
             }
             else if(mMonthBudget > 10 && mMonthBudget <= 50){
                 startNextActivity();
                 addNewReminder(R.string.clean_window, 1, 19);
                 addNewReminder(R.string.ask_about_passed_day, 3, 21);
                 addNewReminder(R.string.thanks_for_her, 5, 22);
             }
             else if(mMonthBudget > 50 && mMonthBudget <= 100){
                 startNextActivity();
                 addNewReminder(R.string.buy_book_or_journal, 1, 18);
                 addNewReminder(R.string.ask_about_dreams, 3, 22);
                 addNewReminder(R.string.clean_up_room, 5, 9);
             }
             else if (mMonthBudget > 100 && mMonthBudget <= 200) {
                 startNextActivity();
                 addNewReminder(R.string.buy_trinket, 1, 18);
                 addNewReminder(R.string.clean_up_room, 3, 9);
                 addNewReminder(R.string.make_compliment_about_appearence, 5, 22);
             }
             else if (mMonthBudget > 200 && mMonthBudget <= 400) {
                 startNextActivity();
                 addNewReminder(R.string.go_to_restourant, 1, 20);
                 addNewReminder(R.string.buy_and_give_decoration, 3, 9);
                 addNewReminder(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 400 && mMonthBudget <= 600) {
                 startNextActivity();
                 addNewReminder(R.string.buy_ticket_for_the_concert, 1, 9);
                 addNewReminder(R.string.clean_washbasin, 3, 20);
                 addNewReminder(R.string.make_compliment_about_taste, 5, 22);
             }
             else if(mMonthBudget > 600 && mMonthBudget <= 1000) {
                 startNextActivity();
                 addNewReminder(R.string.buy_procedure_in_beauty_salon, 1, 9);
                 addNewReminder(R.string.invite_to_theatre, 3, 14);
                 addNewReminder(R.string.thank_for_passed_dinner, 5, 22);
             }
             else if(mMonthBudget > 1000 && mMonthBudget <= 2000) {
                 startNextActivity();
                 addNewReminder(R.string.buy_subscription, 1, 20);
                 addNewReminder(R.string.go_shopping, 3, 18);
                 addNewReminder(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 2000 && mMonthBudget <= 4000) {
                 startNextActivity();
                 addNewReminder(R.string.give_a_trip, 1, 10);
                 addNewReminder(R.string.clean_toilet, 3, 19);
                 addNewReminder(R.string.ask_about_help_needed, 5, 20);
             }
             else if (mMonthBudget > 4000) {
                startNextActivity();
                addNewReminder(R.string.buy_chocolate, 1, 13);
                 addNewReminder(R.string.clean_mirror, 3, 20);
                 addNewReminder(R.string.sorry_about_inattention, 5, 22);
            }


        }else return;
    }

    private boolean checkInput() {
        String MonthBudget = mEditText.getText().toString().trim();
        if (MonthBudget.length() != 0) {
            return true;
        } else return false;
    }

    public void startNextActivity() {
        Intent intent = new Intent(FifthQuestionActivity.this, ThanksActivity.class);
        startActivity(intent);
    }

}


