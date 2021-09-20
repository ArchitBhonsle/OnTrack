package com.puipuituipui.ontrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class Habits extends Fragment {
    ListView habitsList;
    Habit[] habits = {
            new Habit("Exercise", 15),
            new Habit("Study", 0),
            new Habit("Meditation", 5),
            new Habit("Walk", 30),
            new Habit("Duolingo", 23),
    };

    public Habits() { /* Required empty public constructor */ }

    public static Habits newInstance() {
        Habits fragment = new Habits();
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
        View view = inflater.inflate(R.layout.fragment_habits, container, false);

        habitsList = view.findViewById(R.id.habits_list);
        HabitsListAdapter adapter = new HabitsListAdapter(view.getContext(), habits);
        habitsList.setAdapter(adapter);

        return view;
    }
}
