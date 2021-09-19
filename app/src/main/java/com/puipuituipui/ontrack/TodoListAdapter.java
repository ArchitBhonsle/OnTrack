package com.puipuituipui.ontrack;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class TodoListAdapter extends BaseAdapter {
    private final Context context;
    private final Todo[] todos;

    public TodoListAdapter(Context context, Todo[] todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.length;
    }

    @Override
    public Todo getItem(int i) {
        return todos[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.todo_list_item, container, false);
        }

        Todo todo = getItem(position);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                todo.state ? R.drawable.ic_baseline_check_box_24: R.drawable.ic_baseline_check_box_outline_blank_24
                );
        ((TextView) convertView.findViewById(R.id.todo_list_name))
                .setText(todo.name);
        ((ImageView) convertView.findViewById(R.id.todo_list_icon))
                .setImageDrawable(icon);
        return convertView;
    }

}
