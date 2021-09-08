package com.puipuituipui.ontrack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CollectionAdapter extends FragmentStateAdapter {
    public CollectionAdapter(MainActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Habits();
            case 1:
                return new Todos();
            case 2:
                return new Reminders();
            case 3:
                return new Checkpoints();
            default:
                return new Habits(); // don't know what the right way to do this is
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
