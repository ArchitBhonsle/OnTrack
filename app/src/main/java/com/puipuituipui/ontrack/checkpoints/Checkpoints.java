package com.puipuituipui.ontrack.checkpoints;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.puipuituipui.ontrack.R;

public class Checkpoints extends Fragment {
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
        return inflater.inflate(R.layout.fragment_checkpoints, container, false);
    }
}