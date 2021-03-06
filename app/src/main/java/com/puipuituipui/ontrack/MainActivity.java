package com.puipuituipui.ontrack;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.puipuituipui.ontrack.notifs.AlarmScheduler;
import com.puipuituipui.ontrack.notifs.NotificationsHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager2 pager;
    TextView title;
    ImageButton settings;

    private int chooseTabColor(int position) {
        switch (position) {
            case 0:
                return ContextCompat.getColor(getBaseContext(), R.color.todos);
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
            default:  // handle this properly
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
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
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

    private void setupNotifs() {
        NotificationsHelper.createNotificationChannel(
                this,
                NotificationManager.IMPORTANCE_DEFAULT,
                false,
                NotificationsHelper.NOTIFICATION_CHANNEL_TODO,
                NotificationsHelper.NOTIFICATION_CHANNEL_DESC_TODO
        );
        NotificationsHelper.createNotificationChannel(
                this,
                NotificationManager.IMPORTANCE_DEFAULT,
                false,
                NotificationsHelper.NOTIFICATION_CHANNEL_HABIT,
                NotificationsHelper.NOTIFICATION_CHANNEL_DESC_HABIT
        );
        NotificationsHelper.createNotificationChannel(
                this,
                NotificationManager.IMPORTANCE_HIGH,
                false,
                NotificationsHelper.NOTIFICATION_CHANNEL_REMINDER,
                NotificationsHelper.NOTIFICATION_CHANNEL_DESC_REMINDER
        );
        NotificationsHelper.createNotificationChannel(
                this,
                NotificationManager.IMPORTANCE_DEFAULT,
                false,
                NotificationsHelper.NOTIFICATION_CHANNEL_CHECKPOINT,
                NotificationsHelper.NOTIFICATION_CHANNEL_DESC_CHECKPOINT
        );

        Calendar eightAM = Calendar.getInstance();
        eightAM.set(Calendar.HOUR_OF_DAY, 8);
        eightAM.set(Calendar.MINUTE, 0);
        eightAM.set(Calendar.SECOND, 0);
        eightAM.set(Calendar.MILLISECOND, 0);
        AlarmScheduler.setRepeatingAlarm(
                this,
                eightAM,
                AlarmScheduler.createPendingIntent(this, AlarmScheduler.ALARM_DAILY_TYPE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title_name);
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                NotificationsHelper.fireTodoNotifs(MainActivity.this);
                NotificationsHelper.fireHabitNotifs(MainActivity.this);
                NotificationsHelper.fireCheckpointNotifs(MainActivity.this);
                return false;
            }
        });

        pager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        settings = findViewById(R.id.settings);

        setupPagerAndTabs(title, tabs, pager);
        setupSettings(settings);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean firstTime = sharedPreferences.getBoolean("FIRST_TIME", true);
        if (firstTime) {
            Log.i("MainActivity", "firstTime");
            setupNotifs();
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean("FIRST_TIME", false);
            sharedPreferencesEditor.apply();
        }
    }
}
