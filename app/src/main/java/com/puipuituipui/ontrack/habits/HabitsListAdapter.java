package com.puipuituipui.ontrack.habits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

        TextView name = (TextView) convertView.findViewById(R.id.habits_list_name);
        name.setText(habit.name);

        TextView streak = (TextView) convertView.findViewById(R.id.habits_list_streak);
        streak.setText(String.valueOf(habit.streak));

        return convertView;
    }

    public void setData(List<Habit> habits) {
        this.habits = habits;
        this.notifyDataSetChanged();
    }
}
