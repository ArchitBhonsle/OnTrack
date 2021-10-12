package com.puipuituipui.ontrack.todos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.puipuituipui.ontrack.database.AppDatabase;
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
                openEditDialog(context, todo);
            }
        });

        return convertView;
    }

    public void setData(List<Todo> todos) {
        this.todos = todos;
        this.notifyDataSetChanged();
    }

    private void openEditDialog(Context ctx, Todo todo) {
        AppDatabase db = Room.databaseBuilder(
                ctx.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_edit_todo);

        Calendar dueDate = todo.due;

        ImageButton delete = dialog.findViewById(R.id.delete_todo);
        ImageButton mark = dialog.findViewById(R.id.mark_todo);
        ImageButton change = dialog.findViewById(R.id.change_todo);
        EditText name = dialog.findViewById(R.id.name_todo);
        EditText desc = dialog.findViewById(R.id.desc_todo);
        TextView due = dialog.findViewById(R.id.due_todo);

        name.setText(todo.name);
        desc.setText(todo.description);
        due.setText(Utils.formatCalendarDate(dueDate));


        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(ctx, due, dueDate);
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo.state = !todo.state;
                db.todoDao().updateTodos(todo);

                List<Todo> todos = db.todoDao().getAll();
                setData(todos);

                dialog.cancel();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.todoDao().updateTodos(todo);

                List<Todo> todos = db.todoDao().getAll();
                setData(todos);

                dialog.cancel();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo.name = name.getText().toString();
                todo.description = desc.getText().toString();

                db.todoDao().updateTodos(todo);

                List<Todo> todos = db.todoDao().getAll();
                setData(todos);

                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void setDueDate(Context context, TextView dueTextView, Calendar dueDate) {
        new DatePickerDialog(context, R.style.TodoDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate.set(year, monthOfYear, dayOfMonth);
                dueTextView.setText(Utils.formatCalendarDate(dueDate));
            }
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }
}
