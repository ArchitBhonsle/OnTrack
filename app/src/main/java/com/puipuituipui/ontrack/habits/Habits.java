package com.puipuituipui.ontrack.habits;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.database.AppDatabase;
import com.puipuituipui.ontrack.R;

import java.util.List;

public class Habits extends Fragment {
    ListView habitsList;
    HabitsListAdapter adapter;
    FloatingActionButton fab;

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
        adapter = new HabitsListAdapter(
                view.getContext(),
                refreshHabits()
        );
        habitsList.setAdapter(adapter);

        fab = view.findViewById(R.id.habits_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog(view.getContext());
            }
        });

        return view;
    }

    List<Habit> refreshHabits() {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        return db.habitDao().getAll();
    }

    private void openAddDialog(Context ctx) {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_habit);

        ImageButton add = dialog.findViewById(R.id.add_habit);
        ImageButton cancel = dialog.findViewById(R.id.cancel_habit);
        EditText name = dialog.findViewById(R.id.name_habit);
        EditText desc = dialog.findViewById(R.id.desc_habit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Habit current = new Habit(name.getText().toString(), desc.getText().toString());
                db.habitDao().insertAll(current);

                List<Habit> newHabits = refreshHabits();
                adapter.setData(newHabits);

                dialog.cancel();
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
