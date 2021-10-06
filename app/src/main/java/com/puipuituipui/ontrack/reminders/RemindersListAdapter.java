package com.puipuituipui.ontrack.reminders;

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

public class RemindersListAdapter extends BaseAdapter {
    private final Context context;
    private List<Reminder> reminders;

    public RemindersListAdapter(Context context, List<Reminder> reminders) {
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

        ImageView state = (ImageView) convertView.findViewById(R.id.reminder_list_icon);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                reminder.state ? R.drawable.ic_baseline_access_alarm_24: R.drawable.ic_baseline_check_box_outline_blank_24
        );

        TextView due = (TextView) convertView.findViewById(R.id.reminder_list_time);
        if (reminder.time != null) {
            due.setText(Utils.formatCalendarDate(reminder.time));
        }

        RelativeLayout markArea = convertView.findViewById((R.id.reminder_list_mark));
        markArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (reminder.state) {
                    reminder.state = false;
                    reminder.marked = null;
                } else {
                    reminder.state = true;
                    reminder.marked = Calendar.getInstance();
                }

                AppDatabase db = Room.databaseBuilder(
                        context.getApplicationContext(), AppDatabase.class, "db")
                        .allowMainThreadQueries()
                        .build();
                db.reminderDao().updateReminders(reminder);
                notifyDataSetChanged();

                return true;
            }
        });

        markArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Reminder", "clicked");
                // Open edit dialog here
            }
        });

        return convertView;
    }

    public void setData(List<Reminder> reminders) {
        this.reminders = reminders;
        this.notifyDataSetChanged();
    }
}
