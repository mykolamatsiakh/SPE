package com.example.mykola.spe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

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

import static android.R.attr.description;
import static android.view.View.VISIBLE;


/**
 * Created by mykola on 04.07.17.
 */
//bla bla

public class FifthQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEditText;
    GoogleAccountCredential mCredentials;
    ProgressBar mProgressbar;
    ProgressDialog nDialog;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    final List<Event> mEvents = new ArrayList<>();
    List<String> description = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] array = getResources().getStringArray(R.array.events_string);
        description = Arrays.asList(array);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.monthBudget);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_loader);
                mCredentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(AppAccount.getInstance(FifthQuestionActivity.this).getName());
    }


    @Override
    public void onClick(View view) {
        String MoneyPerMonth = mEditText.getText().toString().trim();
        int mMonthBudget = Integer.valueOf(MoneyPerMonth);
        Intent mIntent = getIntent();
        int mDays = mIntent.getIntExtra("Days", 0);
        if (checkInput()) {
            mEvents.clear();
            mProgressbar.setVisibility(View.VISIBLE);
            if (mMonthBudget <= 10) {
                for(int i = 1; i < mDays; i++) {
                    // mEvents.add(createEvent(R.string.wash_dishes, 1, 20));
                    //mEvents.add(createEvent(R.string.ask_about_passed_day, 3, 22));
                    mEvents.add(createEvent(description.get(i), i, 18));
                }
            } else if (mMonthBudget > 10 && mMonthBudget <= 50) {
                for(int i = 1; i < mDays; i++) {
                    mEvents.add(createEvent(description.get(i), i, 18));
                }

            } else if(mMonthBudget > 50 && mMonthBudget <= 100) {
                for(int i = 1; i < mDays; i++) {
                    mEvents.add(createEvent(description.get(i), i, 18));
                }
            }
            } else if (mMonthBudget > 100 && mMonthBudget <= 200) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else if (mMonthBudget > 200 && mMonthBudget <= 400) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else if (mMonthBudget > 400 && mMonthBudget <= 600) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else if (mMonthBudget > 600 && mMonthBudget <= 1000) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else if (mMonthBudget > 1000 && mMonthBudget <= 2000) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else if (mMonthBudget > 2000 && mMonthBudget <= 4000) {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            } else {
            for(int i = 1; i < mDays; i++) {
                mEvents.add(createEvent(description.get(i), i, 18));
            }
            }
            saveEvents(mEvents);
        }


    private boolean checkInput() {
        String MonthBudget = mEditText.getText().toString().trim();
        return MonthBudget.length() != 0;
    }

    Event createEvent( String description, int day, int hour) {
        Date startDate = new Date(pickDate(day, hour));
        Date endDate = new Date(pickDate(day, hour + 1));
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("Europe/Kiev"));
        Event event = new Event()
                .setSummary("Be a hero:"+description)
                .setDescription(description);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            saveEvents(mEvents);
        }
    }

    private void saveEvents(List<Event> events) {
        new SaveEventTask(events, mCredentials).execute();
    }

    class SaveEventTask extends AsyncTask<Void, Void, List<Event>> {

        private final List<Event> mEvents;
        private final com.google.api.services.calendar.Calendar mService;

        SaveEventTask(List<Event> events,
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

    public void startNextActivity() {
        Intent intent = new Intent(FifthQuestionActivity.this, ThanksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }

}

