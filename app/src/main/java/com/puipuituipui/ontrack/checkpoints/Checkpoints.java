package com.puipuituipui.ontrack.checkpoints;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import com.puipuituipui.ontrack.R;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Checkpoints extends Fragment {
    ListView checkpointsList;
    SimpleDateFormat dateFormat;
    Date date1, date2;

    {
        dateFormat = new SimpleDateFormat("dd/MM");
        try {
            date1 = dateFormat.parse("29/09/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            date2 = dateFormat.parse("01/10/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Checkpoint[] checkpoints = {
            new Checkpoint("Water plants", dateFormat.format(date1)),
            new Checkpoint("Charge power Bank", dateFormat.format(date1)),
            new Checkpoint("Send in the mail", dateFormat.format(date1)),
            new Checkpoint("Check marks", dateFormat.format(date2)),
            new Checkpoint("Wash dishes", dateFormat.format(date2)),
    };

    public Checkpoints() { /* Required empty public constructor */ }

    public static Checkpoints newInstance() {
        Checkpoints fragment = new Checkpoints();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkpoints, container, false);
        checkpointsList = view.findViewById(R.id.checkpoints_list);
        CheckpointsListAdapter adapter = new CheckpointsListAdapter(
                view.getContext(), new ArrayList<>(Arrays.asList(checkpoints)));
        checkpointsList.setAdapter(adapter);
        return view;
    }
}