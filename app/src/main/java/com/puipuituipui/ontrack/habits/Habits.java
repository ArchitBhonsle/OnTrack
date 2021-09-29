package com.puipuituipui.ontrack.habits;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Habits extends Fragment {
    ListView habitsList;
    FloatingActionButton fab;

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
        HabitsListAdapter adapter = new HabitsListAdapter(
                view.getContext(), new ArrayList<>(Arrays.asList(habits)));
        habitsList.setAdapter(adapter);

        fab = view.findViewById(R.id.habits_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick(view.getContext());
            }
        });

        return view;
    }

    private void fabClick(Context ctx) {
        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_habit);

        Button add = dialog.findViewById(R.id.add_habit), cancel = dialog.findViewById(R.id.cancel_habit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add logic
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
