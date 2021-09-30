package com.puipuituipui.ontrack.reminders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.puipuituipui.ontrack.R;

import java.util.ArrayList;

public class RemindersListAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Reminder> reminders;

    public RemindersListAdapter(Context context, ArrayList<Reminder> reminders) {
        this.context = context;
        this.reminders = reminders;
    }
    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Reminder getItem(int i) {
        return reminders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_reminders, container, false);
        }

        Reminder reminder = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.reminder_list_name);
        name.setText(reminder.name);

        TextView time = (TextView) convertView.findViewById(R.id.reminder_list_time);
        time.setText(String.valueOf(reminder.time));

        return convertView;
    }
}
