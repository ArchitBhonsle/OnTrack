package com.puipuituipui.ontrack.checkpoints;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.puipuituipui.ontrack.Utils;

import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.database.AppDatabase;

import java.util.Calendar;
import java.util.List;

public class CheckpointsListAdapter extends BaseAdapter{
    private final Context context;
    private List<Checkpoint> checkpoints;

    public CheckpointsListAdapter(Context context, List<Checkpoint> checkpoints) {
        this.context = context;
        this.checkpoints = checkpoints;
    }

    @Override
    public int getCount() {
        return checkpoints.size();
    }

    @Override
    public Checkpoint getItem(int i) {
        return checkpoints.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_checkpoints, container, false);
        }

        Checkpoint checkpoint = getItem(position);
        checkpoint.completed();

        TextView name = (TextView) convertView.findViewById(R.id.checkpoint_list_name);
        name.setText(checkpoint.name);

        ImageView state = (ImageView) convertView.findViewById(R.id.checkpoint_list_icon);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                checkpoint.state ? R.drawable.ic_baseline_flag_24: R.drawable.ic_baseline_outlined_flag_24
        );
        state.setImageDrawable(icon);

        TextView due = (TextView) convertView.findViewById(R.id.checkpoint_list_due);
        if (checkpoint.date != null) {
            due.setText(Utils.formatCalendarDateShort(checkpoint.date));
        }

        RelativeLayout markArea = convertView.findViewById(R.id.checkpoint_list_mark);
        markArea.setOnClickListener(view -> {
            Log.i("Checkpoint", "clicked");
            openEditDialog(context, checkpoint);
        });

        return convertView;
    }

    public void setData(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
        this.notifyDataSetChanged();
    }

    public void openEditDialog(Context ctx, Checkpoint checkpoint) {
        AppDatabase db = Room.databaseBuilder(
                ctx.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        BottomSheetDialog dialog = new BottomSheetDialog(ctx);
        dialog.setContentView(R.layout.dialog_edit_checkpoint);

        Calendar dueDate = checkpoint.date;

        ImageButton delete = dialog.findViewById(R.id.delete_checkpoint);
        ImageButton change = dialog.findViewById(R.id.change_checkpoint);
        EditText name = dialog.findViewById(R.id.name_checkpoint);
        EditText desc = dialog.findViewById(R.id.desc_checkpoint);
        TextView due = dialog.findViewById(R.id.due_checkpoint);
        name.setText(checkpoint.name);
        desc.setText(checkpoint.description);
        due.setText(Utils.formatCalendarDate(dueDate));

        delete.setOnClickListener(view -> {
            Log.i("Checkpoint","Delete");
            db.checkpointDao().delete(checkpoint);

            List<Checkpoint> newCheckpoints = db.checkpointDao().getAll();
            setData(newCheckpoints);
            dialog.cancel();
        });

        due.setOnClickListener(view -> setDate(ctx, due, dueDate));

        change.setOnClickListener(view -> {
            Log.i("Checkpoint","Update");
            checkpoint.name = name.getText().toString();
            checkpoint.description = desc.getText().toString();
            checkpoint.completed();

            db.checkpointDao().updateCheckpoints(checkpoint);

            List<Checkpoint> newCheckpoints = db.checkpointDao().getAll();
            setData(newCheckpoints);

            dialog.cancel();
        });

        dialog.show();
    }

    private void setDate(Context context, TextView dueTextView, Calendar dueDate) {
        new DatePickerDialog(context,
                R.style.CheckpointDialogTheme,
                (view, year, monthOfYear, dayOfMonth) -> {
                    dueDate.set(year, monthOfYear, dayOfMonth);
                    dueTextView.setText(Utils.formatCalendarDate(dueDate));
                },
                dueDate.get(Calendar.YEAR),
                dueDate.get(Calendar.MONTH),
                dueDate.get(Calendar.DATE)).show();
    }
}
