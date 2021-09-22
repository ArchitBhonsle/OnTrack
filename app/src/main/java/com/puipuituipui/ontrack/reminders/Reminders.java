package com.puipuituipui.ontrack.reminders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import com.puipuituipui.ontrack.R;

public class Reminders extends Fragment {
    ListView remindersList;
    Reminder[] reminders = {
            new Reminder("Water plants", 15),
            new Reminder("Charge power Bank", 0),
            new Reminder("Send in the mail", 5),
            new Reminder("Check marks", 30),
            new Reminder("Wash dishes", 23),
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