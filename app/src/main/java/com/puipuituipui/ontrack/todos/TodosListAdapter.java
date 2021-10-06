package com.puipuituipui.ontrack.todos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.puipuituipui.ontrack.AppDatabase;
import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.Utils;

import java.util.Calendar;
import java.util.List;

public class TodosListAdapter extends BaseAdapter {
    private final Context context;
    private List<Todo> todos;

    public TodosListAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Todo getItem(int i) {
        return todos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return todos.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_todos, container, false);
        }

        Todo todo = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.todo_list_name);
        name.setText(todo.name);

        ImageView state = (ImageView) convertView.findViewById(R.id.todo_list_icon);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                todo.state ? R.drawable.ic_baseline_check_box_24: R.drawable.ic_baseline_check_box_outline_blank_24
        );
        state.setImageDrawable(icon);

        TextView due = (TextView) convertView.findViewById(R.id.todo_list_due);
        due.setText(Utils.formatCalendarDateShort(todo.due));

        RelativeLayout markArea = convertView.findViewById(R.id.todo_list_mark);
        markArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (todo.state) {
                    todo.state = false;
                    todo.marked = null;
                } else {
                    todo.state = true;
                    todo.marked = Calendar.getInstance();
                }

                AppDatabase db = Room.databaseBuilder(
                        context.getApplicationContext(), AppDatabase.class, "db")
                        .allowMainThreadQueries()
                        .build();
                db.todoDao().updateTodos(todo);

                List<Todo> todos = db.todoDao().getAll();
                setData(todos);

                return true;
            }
        });
        markArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Todo", "clicked");
                // Open edit dialog here
            }
        });

        return convertView;
    }

    public void setData(List<Todo> todos) {
        this.todos = todos;
        this.notifyDataSetChanged();
    }
}
