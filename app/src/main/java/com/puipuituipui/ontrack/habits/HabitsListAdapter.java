package com.puipuituipui.ontrack.habits;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.room.Room;

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
            }
        });

        return convertView;
    }

    public void setData(List<Habit> habits) {
        this.habits = habits;
        this.notifyDataSetChanged();
    }
}
