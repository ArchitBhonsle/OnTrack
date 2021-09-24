package com.puipuituipui.ontrack.reminders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.puipuituipui.ontrack.R;


public class Reminders extends Fragment {
    ListView remindersList;
    SimpleDateFormat timeFormat;
    Date time1, time2;

    {
        timeFormat = new SimpleDateFormat("HH:mm");
        try {
            time1 = timeFormat.parse("11:30:12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    {
        try {
            time2 = timeFormat.parse("1:30:54");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    Reminder[] reminders = {
            new Reminder("Water plants", timeFormat.format(time1)),
            new Reminder("Charge power Bank", timeFormat.format(time1)),
            new Reminder("Send in the mail", timeFormat.format(time2)),
            new Reminder("Check marks", timeFormat.format(time2)),
            new Reminder("Wash dishes", timeFormat.format(time2)),
    };

    public Reminders() { /* Required empty public constructor */ }

    public static Reminders newInstance() {
        Reminders fragment = new Reminders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        remindersList = view.findViewById(R.id.reminders_list);
        RemindersListAdapter adapter = new RemindersListAdapter(
                view.getContext(), new ArrayList<>(Arrays.asList(reminders)));
        remindersList.setAdapter(adapter);
        return view;
    }
}