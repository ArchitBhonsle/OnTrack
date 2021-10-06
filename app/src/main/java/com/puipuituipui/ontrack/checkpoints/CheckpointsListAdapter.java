package com.puipuituipui.ontrack.checkpoints;

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
import com.puipuituipui.ontrack.Utils;

import com.puipuituipui.ontrack.R;
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
        return checkpoints.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_checkpoints, container, false);
        }

        Checkpoint checkpoint = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.checkpoint_list_name);
        name.setText(checkpoint.name);

        ImageView state = (ImageView) convertView.findViewById(R.id.checkpoint_list_icon);
        Drawable icon = ContextCompat.getDrawable(
                convertView.getContext(),
                checkpoint.state ? R.drawable.ic_baseline_flag_24: R.drawable.ic_baseline_outlined_flag_24
        );
        state.setImageDrawable(icon);

        TextView due = (TextView) convertView.findViewById(R.id.checkpoint_list_due);
        if (checkpoint.due != null) {
            due.setText(Utils.formatCalendarDateShort(checkpoint.due));
        }

        RelativeLayout markArea = convertView.findViewById(R.id.checkpoint_list_mark);
        markArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (checkpoint.state) {
                    checkpoint.state = false;
                    checkpoint.marked = null;
                } else {
                    checkpoint.state = true;
                    checkpoint.marked = Calendar.getInstance();
                }

                AppDatabase db = Room.databaseBuilder(
                        context.getApplicationContext(), AppDatabase.class, "db")
                        .allowMainThreadQueries()
                        .build();
                db.checkpointDao().updateCheckpoints(checkpoint);
                notifyDataSetChanged();

                return true;
            }
        });
        markArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Checkpoint", "clicked");
                // Open edit dialog here
            }
        });

        return convertView;
    }

    public void setData(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
        this.notifyDataSetChanged();
    }
}
