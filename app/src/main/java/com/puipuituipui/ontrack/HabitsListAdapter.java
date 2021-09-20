package com.puipuituipui.ontrack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class HabitsListAdapter extends BaseAdapter {
    private final Context context;
    private final Habit[] habits;

    public HabitsListAdapter(Context context, Habit[] habits) {
        this.context = context;
        this.habits = habits;
    }

    @Override
    public int getCount() {
        return habits.length;
    }

    @Override
    public Habit getItem(int i) {
        return habits[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.habits_list_item, container, false);
        }

        Habit habit = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.habits_list_name);
        name.setText(habit.name);

        TextView streak = (TextView) convertView.findViewById(R.id.habits_list_streak);
        streak.setText(String.valueOf(habit.streak));

        return convertView;
    }

}
