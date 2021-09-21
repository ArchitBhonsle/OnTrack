package com.puipuituipui.ontrack.todos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.puipuituipui.ontrack.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Todos extends Fragment {
    ListView todoList;
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

        return view;
    }
}
