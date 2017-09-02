package com.example.mykola.spe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by mykola on 04.07.17.
 */

public class FifthQuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;
    GoogleAccountCredential mCredentials;
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.monthBudget);
    }

    private void addNewEvent(int description, int day, int hour) {
        /*ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, "SPE");
        cv.put(CalendarContract.Events.DESCRIPTION, getString(description));
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
        cv.put(CalendarContract.Events.HAS_ALARM, true);
        cv.put(CalendarContract.Events.DTSTART, pickDate(day,hour));
        cv.put(CalendarContract.Events.DTEND, pickDate(day,hour+1));*/
       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[]    grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);*/
        mCredentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName("mmatsiakh@gmail.com");

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        Event event = new Event()
                .setSummary("SPE")
                .setDescription(String.valueOf(description));
        com.google.api.services.calendar.Calendar service
                = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, mCredentials)
                .setApplicationName("SPE")
                .build();
        Date startDate = new Date(pickDate(day, hour));
        Date endDate = new Date(pickDate(day, hour+1));
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("Europe/Kiev"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("Europe/Kiev"));
        event.setEnd(new EventDateTime().setDateTime(end));

        String calendarId = "primary";
        try {
            Event createdEvent = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                 addNewEvent(R.string.wash_dishes, 1, 20);
                 addNewEvent(R.string.ask_about_passed_day, 3, 22);
                 addNewEvent(R.string.get_from_the_job, 5, 18);
             }
             else if(mMonthBudget > 10 && mMonthBudget <= 50){
                 startNextActivity();
                 addNewEvent(R.string.clean_window, 1, 19);
                 addNewEvent(R.string.ask_about_passed_day, 3, 21);
                 addNewEvent(R.string.thanks_for_her, 5, 22);
             }
             else if(mMonthBudget > 50 && mMonthBudget <= 100){
                 startNextActivity();
                 addNewEvent(R.string.buy_book_or_journal, 1, 18);
                 addNewEvent(R.string.ask_about_dreams, 3, 22);
                 addNewEvent(R.string.clean_up_room, 5, 9);
             }
             else if (mMonthBudget > 100 && mMonthBudget <= 200) {
                 startNextActivity();
                 addNewEvent(R.string.buy_trinket, 1, 18);
                 addNewEvent(R.string.clean_up_room, 3, 9);
                 addNewEvent(R.string.make_compliment_about_appearence, 5, 22);
             }
             else if (mMonthBudget > 200 && mMonthBudget <= 400) {
                 startNextActivity();
                 addNewEvent(R.string.go_to_restourant, 1, 20);
                 addNewEvent(R.string.buy_and_give_decoration, 3, 9);
                 addNewEvent(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 400 && mMonthBudget <= 600) {
                 startNextActivity();
                 addNewEvent(R.string.buy_ticket_for_the_concert, 1, 9);
                 addNewEvent(R.string.clean_washbasin, 3, 20);
                 addNewEvent(R.string.make_compliment_about_taste, 5, 22);
             }
             else if(mMonthBudget > 600 && mMonthBudget <= 1000) {
                 startNextActivity();
                 addNewEvent(R.string.buy_procedure_in_beauty_salon, 1, 9);
                 addNewEvent(R.string.invite_to_theatre, 3, 14);
                 addNewEvent(R.string.thank_for_passed_dinner, 5, 22);
             }
             else if(mMonthBudget > 1000 && mMonthBudget <= 2000) {
                 startNextActivity();
                 addNewEvent(R.string.buy_subscription, 1, 20);
                 addNewEvent(R.string.go_shopping, 3, 18);
                 addNewEvent(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 2000 && mMonthBudget <= 4000) {
                 startNextActivity();
                 addNewEvent(R.string.give_a_trip, 1, 10);
                 addNewEvent(R.string.clean_toilet, 3, 19);
                 addNewEvent(R.string.ask_about_help_needed, 5, 20);
             }
             else if (mMonthBudget > 4000) {
                startNextActivity();
                addNewEvent(R.string.buy_chocolate, 1, 13);
                 addNewEvent(R.string.clean_mirror, 3, 20);
                 addNewEvent(R.string.sorry_about_inattention, 5, 22);
            }
        }
    }

    private boolean checkInput() {
        String MonthBudget = mEditText.getText().toString().trim();
        return MonthBudget.length() != 0;
    }

    public void startNextActivity() {
        Intent intent = new Intent(FifthQuestionActivity.this, ThanksActivity.class);
        startActivity(intent);
    }

}


