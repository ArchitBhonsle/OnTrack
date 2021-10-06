package com.puipuituipui.ontrack.habits;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.puipuituipui.ontrack.AppDatabase;
import com.puipuituipui.ontrack.R;

import java.util.List;

public class HabitsListAdapter extends BaseAdapter {
    private final Context context;
    private List<Habit> habits;

    public HabitsListAdapter(Context context, List<Habit> habits) {
        this.context = context;
        this.habits = habits;
    }

    @Override
    public int getCount() {
        return habits.size();
    }

    @Override
    public Habit getItem(int i) {
        return habits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_habits, container, false);
        }

        Habit habit = getItem(position);
        habit.ping();

        TextView name = (TextView) convertView.findViewById(R.id.habits_list_name);
        name.setText(habit.name);

        TextView streak = (TextView) convertView.findViewById(R.id.habits_list_streak);
        streak.setText(String.valueOf(habit.streak));

        RelativeLayout item = convertView.findViewById(R.id.habits_list_mark);
        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                habit.mark();
                AppDatabase db = Room.databaseBuilder(
                        context.getApplicationContext(), AppDatabase.class, "db")
                        .allowMainThreadQueries()
                        .build();
                db.habitDao().updateHabits(habit);
                notifyDataSetChanged();

                return true;
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Habits", "Clicked");
                Log.i("Habits", habit.toString());
                openEditDialog(context, habit);
            }
        });

        return convertView;
    }

    public void setData(List<Habit> habits) {
        this.habits = habits;
        this.notifyDataSetChanged();
    }

    private void openEditDialog(Context ctx, Habit habit) {
        AppDatabase db = Room.databaseBuilder(
                ctx.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_edit_habit);

        ImageButton delete = dialog.findViewById(R.id.delete_habit);
        ImageButton mark = dialog.findViewById(R.id.mark_habit);
        ImageButton change = dialog.findViewById(R.id.change_habit);
        EditText name = dialog.findViewById(R.id.name_habit);
        name.setText(habit.name);
        EditText desc = dialog.findViewById(R.id.desc_habit);
        desc.setText(habit.description);

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habit.mark();
                db.habitDao().updateHabits(habit);

                List<Habit> newHabits = db.habitDao().getAll();
                setData(newHabits);

                dialog.cancel();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.habitDao().delete(habit);

                List<Habit> newHabits = db.habitDao().getAll();
                setData(newHabits);

                dialog.cancel();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habit.name = name.getText().toString();
                habit.description = desc.getText().toString();

                db.habitDao().updateHabits(habit);

                List<Habit> newHabits = db.habitDao().getAll();
                setData(newHabits);

                dialog.cancel();
            }
        });

        dialog.show();
    }
}
