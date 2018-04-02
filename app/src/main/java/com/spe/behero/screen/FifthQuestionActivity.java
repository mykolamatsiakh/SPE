package com.spe.behero.screen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.spe.behero.other.AppAccount;
import com.spe.behero.other.NotificationPublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;


/**
 * Created by mykola on 04.07.17.
 */


public class FifthQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEditText;
    GoogleAccountCredential mCredentials;
    ProgressBar mProgressbar;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    final List<Event> mEvents = new ArrayList<>();
    List<String> description = new ArrayList<>();
    List<String> second_description = new ArrayList<>();
    List<String> push_up_phrases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        findViewById(R.id.back_button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String[] phrases = getResources().getStringArray(R.array.push_up_phrases);
        String[] array = getResources().getStringArray(R.array.events_string);
        String[] second_array = getResources().getStringArray(R.array.events_string_2);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Your_Shared_Prefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        description = Arrays.asList(array);
        second_description = Arrays.asList(second_array);
        push_up_phrases = Arrays.asList(phrases);
        for(int i = 0; i<array.length; i++){
            Log.d("Порада: ", description.get(i));
        }
        Log.d("Розмір масиву", String.valueOf(array.length));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_question);
        findViewById(R.id.next_button5).setOnClickListener(this);
        mEditText = findViewById(R.id.monthBudget);
        mProgressbar = findViewById(R.id.progress_loader);
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
            int min = 9;
            int max = 22;
            int firstPhraseIndex = 0;
            int lastPhraseIndex = push_up_phrases.size();
            mProgressbar.setVisibility(View.VISIBLE);
                for (int i = 1; i <= mDays; i++) {
                    Random r = new Random();
                    Random t = new Random();
                    Random foo = new Random();
                    int randomNumber = foo.nextInt((54 + 1)) + 0;
                    Random foo2 = new Random();
                    int randomNumber2 = foo2.nextInt((54 + 1)) + 0;
                    Random phraseStart = new Random();
                    int anotherHour = t.nextInt(max - min + 1) + min;
                    int hour = r.nextInt(max - min + 1) + min;
                    int phraseIndex = phraseStart.nextInt(lastPhraseIndex -  firstPhraseIndex + 1)+firstPhraseIndex;
                    mEvents.add(createEvent(description.get(randomNumber), i, hour));
                    mEvents.add(createEvent(description.get(randomNumber2), i, anotherHour));
//                    scheduleNotification(makeNotification(push_up_phrases.get(phraseIndex)), i, hour);
                }
            saveEvents(mEvents);
            }
            else showToast("Ви не ввели бюджет, який готові виділити на цей період");
        }

    private void showToast(String message) {
        Toast.makeText(FifthQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void scheduleNotification(Notification notification, int day, int hour) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + pickDate(day, hour) ;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

    }

    private int randomize() {
        int min = 15;
        int max = 60;
        Random randomTime = new Random();
        return randomTime.nextInt(max - min + 1) + min;
    }

    private Notification makeNotification(String notificationText) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentTitle("Be hero for your woman")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(notificationText)
                .setAutoCancel(true);
        return mBuilder.build();

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

}

