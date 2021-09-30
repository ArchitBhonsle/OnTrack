package com.puipuituipui.ontrack.todos;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.AppDatabase;
import com.puipuituipui.ontrack.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Todos extends Fragment {
    ListView todoList;
    TodosListAdapter adapter;
    FloatingActionButton fab;
    Calendar dueDate;

    public Todos() { /* Required empty public constructor */ }

    public static Todos newInstance() {
        Todos fragment = new Todos();
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
        View view = inflater.inflate(R.layout.fragment_todos, container, false);

        todoList = view.findViewById(R.id.todo_list);
        adapter = new TodosListAdapter(view.getContext(), refreshTodos());
        todoList.setAdapter(adapter);

        fab = view.findViewById(R.id.todos_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick(view.getContext());
            }
        });

        return view;
    }

    List<Todo> refreshTodos() {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()    // TODO Fix this later
                .build();
        return db.todoDao().getAll();
    }

    private void fabClick(Context ctx) {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()    // TODO Fix this later
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_todo);

        Button add = dialog.findViewById(R.id.add_todo);
        Button cancel = dialog.findViewById(R.id.cancel_todo);
        EditText name = dialog.findViewById(R.id.name_todo);
        EditText desc = dialog.findViewById(R.id.desc_todo);
        TextView due = dialog.findViewById(R.id.due_todo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo current = new Todo(
                        name.getText().toString(),
                        desc.getText().toString(),
                        new Date());
                db.todoDao().insertAll(current);

                List<Todo> newTodos = refreshTodos();
                adapter.setData(newTodos);

                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dueDate = Calendar.getInstance();
        dueDate.add(Calendar.DAY_OF_MONTH, 1);
        dueDate.set(Calendar.SECOND, 0);
        due.setText(formatCalendar(dueDate));

        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(ctx, due);
            }
        });

        dialog.show();
    }

    public void setDueDate(Context context, TextView dueTextView) {
        new DatePickerDialog(context, R.style.TodoDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, R.style.TodoDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueDate.set(Calendar.MINUTE, minute);
                        dueTextView.setText(formatCalendar(dueDate));
                    }
                }, dueDate.get(Calendar.HOUR_OF_DAY), dueDate.get(Calendar.MINUTE), false).show();
            }
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }

    private String formatCalendar(Calendar date) {
        SimpleDateFormat formatter = new SimpleDateFormat("h:m aa MMM d, y");
        return formatter.format(date.getTime());
    }
}
