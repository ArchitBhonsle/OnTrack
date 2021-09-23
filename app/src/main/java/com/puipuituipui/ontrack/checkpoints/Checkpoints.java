package com.puipuituipui.ontrack.checkpoints;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import com.puipuituipui.ontrack.R;

public class Checkpoints extends Fragment {
    ListView checkpointsList;
    Checkpoint[] checkpoints = {
            new Checkpoint("Water plants", 15),
            new Checkpoint("Charge power Bank", 0),
            new Checkpoint("Send in the mail", 5),
            new Checkpoint("Check marks", 30),
            new Checkpoint("Wash dishes", 23),
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