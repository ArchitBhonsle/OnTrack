package com.puipuituipui.ontrack.todos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.database.AppDatabase;
import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.Utils;

import java.util.Calendar;
import java.util.List;

public class Todos extends Fragment {
    ListView todoList;
    TodosListAdapter adapter;
    FloatingActionButton fab;
    Calendar dueDate;

    List<Todo> todos;

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
                openAddDialog(view.getContext());
            }
        });

        return view;
    }

    List<Todo> refreshTodos() {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        return db.todoDao().getAll();
    }

    private void openAddDialog(Context ctx) {
        AppDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_todo);

        ImageButton add = dialog.findViewById(R.id.add_todo);
        ImageButton cancel = dialog.findViewById(R.id.cancel_todo);
        EditText name = dialog.findViewById(R.id.name_todo);
        EditText desc = dialog.findViewById(R.id.desc_todo);
        TextView due = dialog.findViewById(R.id.due_todo);

        dueDate = Utils.todayEnd();
        due.setText(Utils.formatCalendarDate(dueDate));
        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(ctx, due);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo current = new Todo(
                        name.getText().toString(),
                        desc.getText().toString(),
                        dueDate);
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


        dialog.show();
    }

    private void setDueDate(Context context, TextView dueTextView) {
        new DatePickerDialog(context, R.style.TodoDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate.set(year, monthOfYear, dayOfMonth);
                dueTextView.setText(Utils.formatCalendarDate(dueDate));
            }
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }
}
