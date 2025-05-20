package com.example.samsung.ui.exercise;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.samsung.R;

public class ExerciseDetailArmsFragment extends Fragment {

    private CountDownTimer timer;
    private boolean[] doneFlags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_detail_arms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnStart = view.findViewById(R.id.btnStart);
        Button btnStop = view.findViewById(R.id.btnStop);
        TextView timerText = view.findViewById(R.id.timerText);
        LinearLayout exerciseList = view.findViewById(R.id.exerciseList);
        ImageView imageView = view.findViewById(R.id.exerciseImage);

        // Можно указать другую картинку, если есть
        imageView.setImageResource(R.drawable.db78dac52229cd08444d6d04139a6060);

        String[] exercises = {
                "МЕДЛЕННЫЕ ПРИСЕДАНИЯ БЕЗ ВЕСА X10",
                "          ОТДЫХ 1 МИНУТА",
                "ЖИМ НОГАМИ ЛЕЖА НА ТРЕНАЖЕРЕ X10",
                "          ОТДЫХ 1 МИНУТА",
                "УПРАЖНЕНИЯ НА РАСТЯЖКУ И ВОССТАНОВЛЕНИЕ X10",
                "          ОТДЫХ 1 МИНУТА"
        };

        doneFlags = new boolean[exercises.length];
        CheckBox[] checkBoxes = new CheckBox[exercises.length];

        for (int i = 0; i < exercises.length; i++) {
            LinearLayout item = new LinearLayout(getContext());
            item.setOrientation(LinearLayout.HORIZONTAL);

            Button exButton = new Button(getContext());
            exButton.setText(exercises[i]);
            exButton.setAllCaps(false);

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setEnabled(false);
            checkBoxes[i] = checkBox;

            final int index = i;
            exButton.setOnClickListener(v -> {
                checkBox.setChecked(true);
                doneFlags[index] = true;

                boolean allDone = true;
                for (boolean b : doneFlags) {
                    if (!b) {
                        allDone = false;
                        break;
                    }
                }

                if (allDone) {
                    Toast.makeText(getContext(), "Поздравляем! Тренировка завершена 🎉", Toast.LENGTH_LONG).show();
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
            });

            item.addView(exButton);
            item.addView(checkBox);
            exerciseList.addView(item);
        }

        btnStart.setOnClickListener(v -> {
            timer = new CountDownTimer(10 * 60 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    timerText.setText(String.format("Time: %02d:%02d", minutes, seconds));
                }

                public void onFinish() {
                    timerText.setText("Готово!");
                }
            }.start();
        });

        btnStop.setOnClickListener(v -> {
            if (timer != null) {
                timer.cancel();
                timerText.setText("Time: 00:00");
            }
        });
    }
}
