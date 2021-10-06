package com.puipuituipui.ontrack.checkpoints;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.AppDatabase;
import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.Utils;

import java.util.Calendar;
import java.util.List;

public class Checkpoints extends Fragment {
    ListView checkpointsList;
    CheckpointsListAdapter adapter;
    FloatingActionButton fab;
    Calendar dueDate;

    List<Checkpoint> checkpoints;

    public Checkpoints() { /* Required empty public constructor */ }

    public static Checkpoints newInstance() {
        Checkpoints fragment = new Checkpoints();
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
        View view = inflater.inflate(R.layout.fragment_checkpoints, container, false);
        checkpointsList = view.findViewById(R.id.checkpoints_list);
        adapter = new CheckpointsListAdapter(view.getContext(), refreshCheckpoints());
        checkpointsList.setAdapter(adapter);

        fab = view.findViewById(R.id.checkpoints_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick(view.getContext());
            }
        });
        return view;
    }

    List<Checkpoint> refreshCheckpoints() {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        return db.checkpointDao().getAll();
    }

    private void fabClick(Context ctx) {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_checkpoint);

        Button add = dialog.findViewById(R.id.add_checkpoint);
        Button cancel = dialog.findViewById(R.id.cancel_checkpoint);
        EditText name = dialog.findViewById(R.id.name_checkpoint);
        EditText desc = dialog.findViewById(R.id.desc_checkpoint);
        TextView due = dialog.findViewById(R.id.due_checkpoint);

        due.setText("Set Date");
        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(ctx, due);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkpoint current = new Checkpoint(
                        name.getText().toString(),
                        desc.getText().toString(),
                        dueDate);
                db.checkpointDao().insertAll(current);

                List<Checkpoint> newCheckpoints = refreshCheckpoints();
                adapter.setData(newCheckpoints);

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

    private void setDueDate(Context context, TextView dueTextView) {
        dueDate = Calendar.getInstance();
        dueDate.set(Calendar.HOUR_OF_DAY, 23);

        new DatePickerDialog(context, R.style.CheckpointDialogTheme, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                dueDate.set(year, month, day);
                dueTextView.setText(Utils.formatCalendarLong(dueDate));
            }
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }
}
