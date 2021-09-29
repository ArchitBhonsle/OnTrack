package com.puipuituipui.ontrack.todos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puipuituipui.ontrack.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Todos extends Fragment {
    ListView todoList;
    FloatingActionButton fab;
    Todo[] todos = {
            new Todo(false, "AAD Literature Review"),
            new Todo(true, "AAD Text Fields and Toast"),
            new Todo(false, "FYP First Evaluation"),
            new Todo(false, "AAD Intents"),
    };

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
        TodosListAdapter adapter = new TodosListAdapter(
                view.getContext(), new ArrayList<>(Arrays.asList(todos)));
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

    private void fabClick(Context ctx) {
        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_add_todo);

        Button add = dialog.findViewById(R.id.add_todo), cancel = dialog.findViewById(R.id.cancel_todo);

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
