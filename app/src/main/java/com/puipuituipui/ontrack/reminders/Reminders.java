package com.puipuituipui.ontrack.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.AppDatabase;
import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.Utils;

import java.util.Calendar;
import java.util.List;


public class Reminders extends Fragment {
    ListView remindersList;
    RemindersListAdapter adapter;
    FloatingActionButton fab;
    Calendar dueDate;

    List<Reminder> reminders;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        remindersList = view.findViewById(R.id.reminders_list);
        adapter = new RemindersListAdapter(view.getContext(), refreshReminders());
        remindersList.setAdapter(adapter);

        fab = view.findViewById(R.id.reminder_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick(view.getContext());
            }
        });

        return view;
    }

    List<Reminder> refreshReminders() {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        return db.reminderDao().getAll();
    }

    private void fabClick(Context ctx) {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();


        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_reminder);

        ImageButton add = dialog.findViewById(R.id.add_reminder);
        ImageButton cancel = dialog.findViewById(R.id.cancel_reminder);
        EditText name = dialog.findViewById(R.id.name_reminder);
        EditText desc = dialog.findViewById(R.id.desc_reminder);
        TextView dueTime = dialog.findViewById(R.id.time_reminder);

        dueTime.setText("Set due date (optional)");
        dueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(ctx, dueTime);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reminder current = new Reminder(
                        name.getText().toString(),
                        desc.getText().toString(),
                        dueDate);
                db.reminderDao().insertAll(current);
                List<Reminder> newReminders = refreshReminders();
                adapter.setData(newReminders);

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

    private void setTime(Context context, TextView timeTextView) {
        dueDate = Calendar.getInstance();
        dueDate.set(Calendar.HOUR_OF_DAY, 23);
        dueDate.set(Calendar.MINUTE, 59);

        new DatePickerDialog(context, R.style.ReminderDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, R.style.ReminderDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueDate.set(Calendar.MINUTE, minute);
                        timeTextView.setText(Utils.formatCalendarLong(dueDate));
                    }
                }, 23, 59, false).show();
            }
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }
}