package com.puipuituipui.ontrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager2 pager;
    TextView title;
    ImageButton settings;

    private int chooseTabColor(int position) {
            switch (position) {
                case 0:
                return ContextCompat.getColor(getBaseContext(), R.color.todo);
                case 1:
                    return ContextCompat.getColor(getBaseContext(), R.color.habits);
                case 2:
                    return ContextCompat.getColor(getBaseContext(), R.color.reminders);
                case 3:
                    return ContextCompat.getColor(getBaseContext(), R.color.checkpoints);
                default:
                    return ContextCompat.getColor(getBaseContext(), R.color.gray);
        }
    }

    private int chooseTabIcon(int position) {
            switch (position) {
                case 0:
                    return R.drawable.ic_baseline_check_circle_outline_24;
                case 1:
                    return R.drawable.ic_baseline_loop_24;
                case 2:
                    return R.drawable.ic_baseline_access_alarm_24;
                case 3:
                    return R.drawable.ic_baseline_outlined_flag_24;
                default:
                    return R.drawable.ic_baseline_check_circle_outline_24;

            }
        }

    private void setupPagerAndTabs(TextView title, TabLayout tabs, ViewPager2 pager) {
        pager.setAdapter(new CollectionAdapter(this));

        // setup all the tabs
        new TabLayoutMediator(tabs, pager, (tab, position) -> {
            ImageView icon = new ImageView(getBaseContext());
            icon.setImageResource(chooseTabIcon(position));
            int pixels = (int) (30 * getResources().getDisplayMetrics().scaledDensity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels, pixels);
            icon.setLayoutParams(params);

            tab.setCustomView(icon);
        }).attach();

        // highlight the currently selected tab
        int currentPos = tabs.getSelectedTabPosition();
        TabLayout.Tab currentTab = tabs.getTabAt(currentPos);
        ImageView currentView = (ImageView) currentTab.getCustomView();
        currentView.setColorFilter(chooseTabColor(currentPos));
        title.setTextColor(chooseTabColor(currentPos));

        // changing colors based on the current tab
        tabs.addOnTabSelectedListener(
            new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabColor = chooseTabColor(tab.getPosition());
                    ImageView currentView = (ImageView) tab.getCustomView();
                    currentView.setColorFilter(tabColor, PorterDuff.Mode.SRC_IN);
                    title.setTextColor(tabColor);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int tabColor = ContextCompat.getColor(getBaseContext(), R.color.gray);
                    ImageView currentView = (ImageView) tab.getCustomView();
                    currentView.setColorFilter(tabColor, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            }
        );
    }

    private void setupSettings(ImageButton settings) {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Settings.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title_name);
        pager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        settings = findViewById(R.id.settings);

        setupPagerAndTabs(title, tabs, pager);
        setupSettings(settings);
    }
}
