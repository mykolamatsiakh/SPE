package com.example.mykola.spe;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

        mCredentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccount(AppAccount.getInstance().account);
    }

    private void addNewEvent(int description, int day, int hour) {
        new SaveDateTask(description, day, hour, mCredentials).execute();
    }

    class SaveDateTask extends AsyncTask<Void, Void, Event> {

        private int mDescription;
        private int mDay;
        private int mHour;
        private com.google.api.services.calendar.Calendar mService;

        SaveDateTask(int description,
                     int day, int hour,
                     GoogleAccountCredential credential) {
            mDescription = description;
            mDay = day;
            mHour = hour;
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("SPE")
                    .build();
        }

        @Override
        protected Event doInBackground(Void... voids) {
            try {
                Event createdEvent = mService.events().insert("primary", createEvent()).execute();
                Log.d("tag", createdEvent.toString());
            } catch (IOException e) {
                Log.e("tag", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute(event);
            startNextActivity();

        }
    }

    @Override
    public void onClick(View view) {
        String MoneyPerMonth = mEditText.getText().toString().trim();
        int mMonthBudget = Integer.valueOf(MoneyPerMonth);
        if(checkInput()){
             if(mMonthBudget <= 10) {
                 addNewEvent(R.string.wash_dishes, 1, 20);
                 addNewEvent(R.string.ask_about_passed_day, 3, 22);
                 addNewEvent(R.string.get_from_the_job, 5, 18);
             }
             else if(mMonthBudget > 10 && mMonthBudget <= 50){
                 addNewEvent(R.string.clean_window, 1, 19);
                 addNewEvent(R.string.ask_about_passed_day, 3, 21);
                 addNewEvent(R.string.thanks_for_her, 5, 22);
             }
             else if(mMonthBudget > 50 && mMonthBudget <= 100){
                 addNewEvent(R.string.buy_book_or_journal, 1, 18);
                 addNewEvent(R.string.ask_about_dreams, 3, 22);
                 addNewEvent(R.string.clean_up_room, 5, 9);
             }
             else if (mMonthBudget > 100 && mMonthBudget <= 200) {
                 addNewEvent(R.string.buy_trinket, 1, 18);
                 addNewEvent(R.string.clean_up_room, 3, 9);
                 addNewEvent(R.string.make_compliment_about_appearence, 5, 22);
             }
             else if (mMonthBudget > 200 && mMonthBudget <= 400) {
                 addNewEvent(R.string.go_to_restourant, 1, 20);
                 addNewEvent(R.string.buy_and_give_decoration, 3, 9);
                 addNewEvent(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 400 && mMonthBudget <= 600) {
                 addNewEvent(R.string.buy_ticket_for_the_concert, 1, 9);
                 addNewEvent(R.string.clean_washbasin, 3, 20);
                 addNewEvent(R.string.make_compliment_about_taste, 5, 22);
             }
             else if(mMonthBudget > 600 && mMonthBudget <= 1000) {
                 addNewEvent(R.string.buy_procedure_in_beauty_salon, 1, 9);
                 addNewEvent(R.string.invite_to_theatre, 3, 14);
                 addNewEvent(R.string.thank_for_passed_dinner, 5, 22);
             }
             else if(mMonthBudget > 1000 && mMonthBudget <= 2000) {
                 addNewEvent(R.string.buy_subscription, 1, 20);
                 addNewEvent(R.string.go_shopping, 3, 18);
                 addNewEvent(R.string.make_compliment_about_nice_solution, 5, 18);
             }
             else if(mMonthBudget > 2000 && mMonthBudget <= 4000) {
                 addNewEvent(R.string.give_a_trip, 1, 10);
                 addNewEvent(R.string.clean_toilet, 3, 19);
                 addNewEvent(R.string.ask_about_help_needed, 5, 20);
             }
             else if (mMonthBudget > 4000) {
                addNewEvent(R.string.buy_chocolate, 1, 13);
                 addNewEvent(R.string.clean_mirror, 3, 20);
                 addNewEvent(R.string.sorry_about_inattention, 5, 22);
            }
        }
    }

    Event createEvent() {
        Date startDate = new Date(pickDate(mDay, mHour));
        Date endDate = new Date(pickDate(mDay, mHour + 1));
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("Europe/Kiev"));
        Event event = new Event()
                .setSummary("SPE")
                .setDescription(String.valueOf(mDescription));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("Europe/Kiev"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    long pickDate(int day, int hour) {
        Calendar rightNow = Calendar.getInstance();
        int timeShift = rightNow.get(Calendar.ZONE_OFFSET);
        return Date.UTC(rightNow.get(Calendar.YEAR) - 1900, rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH) + day, hour - (timeShift / 3600000)-1, 0, 0);
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


