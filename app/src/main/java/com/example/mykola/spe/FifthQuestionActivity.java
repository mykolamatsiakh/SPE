package com.example.mykola.spe;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
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
//bla bla

public class FifthQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText;
    GoogleAccountCredential mCredentials;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.monthBudget);

        mCredentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccount(getAccount());
    }

    private Account getAccount() {

            AccountManager am = AccountManager.get(this);
            Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        return accounts[0];
        }

    @Override
    public void onClick(View view) {
        String MoneyPerMonth = mEditText.getText().toString().trim();
        int mMonthBudget = Integer.valueOf(MoneyPerMonth);
        if (checkInput()) {
            List<Event> events = new ArrayList<>(3);
//            if (mMonthBudget <= 10) {
            events.add(createEvent(R.string.wash_dishes, 1, 20));
//            events.add(createEvent(R.string.ask_about_passed_day, 3, 22));
//            events.add(createEvent(R.string.get_from_the_job, 5, 18));
//            } else if (mMonthBudget > 10 && mMonthBudget <= 50) {
//                createEvent(R.string.clean_window, 1, 19);
//                createEvent(R.string.ask_about_passed_day, 3, 21);
//                createEvent(R.string.thanks_for_her, 5, 22);
//            } else if (mMonthBudget > 50 && mMonthBudget <= 100) {
//                createEvent(R.string.buy_book_or_journal, 1, 18);
//                createEvent(R.string.ask_about_dreams, 3, 22);
//                createEvent(R.string.clean_up_room, 5, 9);
//            } else if (mMonthBudget > 100 && mMonthBudget <= 200) {
//                createEvent(R.string.buy_trinket, 1, 18);
//                createEvent(R.string.clean_up_room, 3, 9);
//                createEvent(R.string.make_compliment_about_appearence, 5, 22);
//            } else if (mMonthBudget > 200 && mMonthBudget <= 400) {
//                createEvent(R.string.go_to_restourant, 1, 20);
//                createEvent(R.string.buy_and_give_decoration, 3, 9);
//                createEvent(R.string.make_compliment_about_nice_solution, 5, 18);
//            } else if (mMonthBudget > 400 && mMonthBudget <= 600) {
//                createEvent(R.string.buy_ticket_for_the_concert, 1, 9);
//                createEvent(R.string.clean_washbasin, 3, 20);
//                createEvent(R.string.make_compliment_about_taste, 5, 22);
//            } else if (mMonthBudget > 600 && mMonthBudget <= 1000) {
//                createEvent(R.string.buy_procedure_in_beauty_salon, 1, 9);
//                createEvent(R.string.invite_to_theatre, 3, 14);
//                createEvent(R.string.thank_for_passed_dinner, 5, 22);
//            } else if (mMonthBudget > 1000 && mMonthBudget <= 2000) {
//                createEvent(R.string.buy_subscription, 1, 20);
//                createEvent(R.string.go_shopping, 3, 18);
//                createEvent(R.string.make_compliment_about_nice_solution, 5, 18);
//            } else if (mMonthBudget > 2000 && mMonthBudget <= 4000) {
//                createEvent(R.string.give_a_trip, 1, 10);
//                createEvent(R.string.clean_toilet, 3, 19);
//                createEvent(R.string.ask_about_help_needed, 5, 20);
//            } else {
//                createEvent(R.string.buy_chocolate, 1, 13);
//                createEvent(R.string.clean_mirror, 3, 20);
//                createEvent(R.string.sorry_about_inattention, 5, 22);
//            }
            new SaveDateTask(events, mCredentials).execute();
        }
    }

    Event createEvent(@StringRes int description, int day, int hour) {
        Date startDate = new Date(pickDate(day, hour));
        Date endDate = new Date(pickDate(day, hour + 1));
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("Europe/Kiev"));
        Event event = new Event()
                .setSummary("SPE")
                .setDescription(getString(description));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("Europe/Kiev"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    long pickDate(int day, int hour) {
        Calendar rightNow = Calendar.getInstance();
        int timeShift = rightNow.get(Calendar.ZONE_OFFSET);
        return Date.UTC(rightNow.get(Calendar.YEAR) - 1900, rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH) + day, hour - (timeShift / 3600000) - 1, 0, 0);
    }

    private boolean checkInput() {
        String MonthBudget = mEditText.getText().toString().trim();
        return MonthBudget.length() != 0;
    }

    class SaveDateTask extends AsyncTask<Void, Void, List<Event>> {

        private final List<Event> mEvents;
        private final com.google.api.services.calendar.Calendar mService;
        private Exception mLastError = null;
        SaveDateTask(List<Event> events,
                     GoogleAccountCredential credential) {
            mEvents = events;
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("SPE")
                    .build();
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            List<Event> events = new ArrayList<>(mEvents.size());
            try {
                for (Event event : mEvents) {
                    Event createdEvent = mService.events().insert("primary", event).execute();
                    events.add(createdEvent);
                }

            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), 100);
            } catch (IOException e) {
                Log.e("tag", "", e);
            }
            return events;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            Log.d("tag", events.toString());
            startNextActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("log", data.toString());
    }

    public void startNextActivity() {
        Intent intent = new Intent(FifthQuestionActivity.this, ThanksActivity.class);
        startActivity(intent);
    }
}

