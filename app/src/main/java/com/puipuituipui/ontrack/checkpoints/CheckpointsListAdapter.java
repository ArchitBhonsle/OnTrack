package com.puipuituipui.ontrack.checkpoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.puipuituipui.ontrack.R;

import java.util.ArrayList;

public class CheckpointsListAdapter extends BaseAdapter{
    private final Context context;
    private final ArrayList<Checkpoint> checkpoints;

    public CheckpointsListAdapter(Context context, ArrayList<Checkpoint> checkpoints) {
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

        TextView name = (TextView) convertView.findViewById(R.id.checkpoint_list_name);
        name.setText(checkpoint.name);

        TextView time = (TextView) convertView.findViewById(R.id.checkpoint_list_date);
        time.setText(String.valueOf(checkpoint.date));

        return convertView;
    }
}
