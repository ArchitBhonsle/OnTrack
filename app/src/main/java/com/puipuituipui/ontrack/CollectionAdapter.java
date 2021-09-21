package com.puipuituipui.ontrack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.puipuituipui.ontrack.checkpoints.Checkpoints;
import com.puipuituipui.ontrack.habits.Habits;
import com.puipuituipui.ontrack.reminders.Reminders;
import com.puipuituipui.ontrack.todos.Todos;

public class CollectionAdapter extends FragmentStateAdapter {
    public CollectionAdapter(MainActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Todos();
            case 1:
                return new Habits();
            case 2:
                return new Reminders();
            case 3:
                return new Checkpoints();
            default:
                return new Todos(); // don't know what the right way to do this is
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
