package com.example.samsung.ui.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.samsung.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private SharedPreferences prefs;
    private final String[] days = {"ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        prefs = requireContext().getSharedPreferences("calendar_prefs", Context.MODE_PRIVATE);

        LinearLayout boxContainer = view.findViewById(R.id.activityBox);
        TextView label = view.findViewById(R.id.activityLabel);
        label.setText("Ваша активность на неделе");

        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK); // Sunday = 1

        // Преобразуем в наш формат (ПН = 0)
        int dayIndex = (today + 5) % 7;

        for (int i = 0; i < days.length; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView dayText = new TextView(getContext());
            dayText.setText(days[i]);
            dayText.setPadding(16, 16, 16, 16);

            CheckBox checkBox = new CheckBox(getContext());

            boolean isChecked = prefs.getBoolean("day_" + i, false);

            // 🟢 Если это сегодняшний день и тренировка была — ставим галочку
            if (i == dayIndex) {
                String todayKey = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
                if (prefs.getBoolean("exercise_" + todayKey, false)) {
                    isChecked = true;
                    prefs.edit().putBoolean("day_" + i, true).apply();
                }
            }

            checkBox.setChecked(isChecked);

            int index = i;
            checkBox.setOnCheckedChangeListener((buttonView, isNowChecked) -> {
                prefs.edit().putBoolean("day_" + index, isNowChecked).apply();
            });

            row.addView(dayText);
            row.addView(checkBox);
            boxContainer.addView(row);
        }


        // Автоматически отмечаем сегодняшнюю активность, если была тренировка
        String todayKey = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        boolean didExerciseToday = prefs.getBoolean("exercise_" + todayKey, false);
        if (didExerciseToday) {
            prefs.edit().putBoolean("day_" + dayIndex, true).apply();
        }

        return view;
    }

    public static void markTodayDone(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("calendar_prefs", Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        int today = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        prefs.edit().putBoolean("day_" + today, true).apply();
    }
}
