package com.puipuituipui.ontrack.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
        reminder.completed();

        TextView name = (TextView) convertView.findViewById(R.id.reminder_list_name);
        name.setText(reminder.name);

        ImageView state = (ImageView) convertView.findViewById(R.id.reminder_list_icon);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                reminder.state ? R.drawable.ic_baseline_alarm_on_24: R.drawable.ic_baseline_access_alarm_24
        );
        state.setImageDrawable(icon);

        TextView due = (TextView) convertView.findViewById(R.id.reminder_list_time);
        if (reminder.time != null) {
            due.setText(Utils.formatCalenderTime(reminder.time));
        }

        RelativeLayout markArea = convertView.findViewById((R.id.reminder_list_mark));
        markArea.setOnClickListener(view -> {
            Log.i("Reminder", "clicked");
            openEditDialog(context, reminder);
        });

        return convertView;
    }

    public void setData(List<Reminder> reminders) {
        this.reminders = reminders;
        this.notifyDataSetChanged();
    }

    public void openEditDialog(Context ctx, Reminder reminder) {
        AppDatabase db = Room.databaseBuilder(
                ctx.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_edit_reminder);

        Calendar dueDate = reminder.time;

        ImageButton delete = dialog.findViewById(R.id.delete_reminder);
        ImageButton change = dialog.findViewById(R.id.change_reminder);
        EditText name = dialog.findViewById(R.id.name_reminder);
        EditText desc = dialog.findViewById(R.id.desc_reminder);
        TextView due = dialog.findViewById(R.id.due_reminder);
        name.setText(reminder.name);
        desc.setText(reminder.description);
        due.setText(Utils.formatCalendarLong(dueDate));

        delete.setOnClickListener(view -> {
            Log.i("Reminder","Delete");
            db.reminderDao().delete(reminder);

            List<Reminder> newReminders = db.reminderDao().getAll();
            setData(newReminders);
            dialog.cancel();
        });

        due.setOnClickListener(view -> setDueTime(ctx, due, dueDate));

        change.setOnClickListener(view -> {
            Log.i("Reminder","Update");
            reminder.name = name.getText().toString();
            reminder.description = desc.getText().toString();

            db.reminderDao().updateReminders(reminder);

            List<Reminder> newReminders = db.reminderDao().getAll();
            setData(newReminders);

            dialog.cancel();
        });

        dialog.show();
    }

    private void setDueTime(Context context, TextView dueTextview, Calendar dueDate) {
        new DatePickerDialog(context, R.style.ReminderDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            dueDate.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(context,
                    R.style.ReminderDialogTheme,
                    (view1, hourOfDay, minute) -> {
                        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueDate.set(Calendar.MINUTE, minute);
                        dueTextview.setText(Utils.formatCalendarLong(dueDate));
                    },
                    dueDate.get(Calendar.HOUR_OF_DAY),
                    dueDate.get(Calendar.MINUTE),
                    false).show();
        }, dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DATE)).show();
    }
}
