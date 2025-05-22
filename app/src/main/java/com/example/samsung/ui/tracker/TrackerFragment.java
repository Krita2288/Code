package com.example.samsung.ui.tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.samsung.R;
import com.example.samsung.ui.calendar.CalendarFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TrackerFragment extends Fragment {

    private LinearLayout morningList, afternoonList, eveningList;
    private List<Task> morningTasks, afternoonTasks, eveningTasks;
    private SharedPreferences prefs;
    private Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        prefs = requireContext().getSharedPreferences("tracker_prefs", Context.MODE_PRIVATE);
        gson = new Gson();

        morningList = view.findViewById(R.id.morningList);
        afternoonList = view.findViewById(R.id.afternoonList);
        eveningList = view.findViewById(R.id.eveningList);

        Button btnAddMorning = view.findViewById(R.id.btnAddMorning);
        Button btnAddAfternoon = view.findViewById(R.id.btnAddAfternoon);
        Button btnAddEvening = view.findViewById(R.id.btnAddEvening);

        morningTasks = loadTasks("morning_tasks");
        afternoonTasks = loadTasks("afternoon_tasks");
        eveningTasks = loadTasks("evening_tasks");

        showTasks(morningList, morningTasks, "morning_tasks");
        showTasks(afternoonList, afternoonTasks, "afternoon_tasks");
        showTasks(eveningList, eveningTasks, "evening_tasks");

        btnAddMorning.setOnClickListener(v -> showAddTaskDialog(morningList, morningTasks, "morning_tasks"));
        btnAddAfternoon.setOnClickListener(v -> showAddTaskDialog(afternoonList, afternoonTasks, "afternoon_tasks"));
        btnAddEvening.setOnClickListener(v -> showAddTaskDialog(eveningList, eveningTasks, "evening_tasks"));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tracker_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_new_day) {
            morningList.removeAllViews();
            afternoonList.removeAllViews();
            eveningList.removeAllViews();
            prefs.edit().clear().apply();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddTaskDialog(LinearLayout container, List<Task> taskList, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Добавить задание");

        final EditText input = new EditText(getContext());
        input.setHint("Например: Выпить витамины");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String taskText = input.getText().toString().trim();
            if (!taskText.isEmpty()) {
                Task newTask = new Task(taskText, false);
                taskList.add(newTask);
                addTaskView(container, newTask, taskList, key);
                saveTasks(taskList, key);
                CalendarFragment.markTodayDone(requireContext());
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void addTaskView(LinearLayout container, Task task, List<Task> taskList, String key) {
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(task.getText());
        checkBox.setChecked(task.isDone());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
            saveTasks(taskList, key);
            CalendarFragment.markTodayDone(requireContext());
        });
        container.addView(checkBox);
    }

    private void showTasks(LinearLayout container, List<Task> tasks, String key) {
        container.removeAllViews();
        for (Task task : tasks) {
            addTaskView(container, task, tasks, key);
        }
    }

    private void saveTasks(List<Task> tasks, String key) {
        String json = gson.toJson(tasks);
        prefs.edit().putString(key, json).apply();
    }

    private List<Task> loadTasks(String key) {
        String json = prefs.getString(key, null);
        if (json != null) {
            Type type = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    public static class Task {
        private String text;
        private boolean done;

        public Task(String text, boolean done) {
            this.text = text;
            this.done = done;
        }

        public String getText() {
            return text;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
