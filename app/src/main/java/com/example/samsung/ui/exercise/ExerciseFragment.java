package com.example.samsung.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.samsung.R;

import java.util.Arrays;
import java.util.List;

public class ExerciseFragment extends Fragment {

    public ExerciseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        List<String> exercises = Arrays.asList(
                "Тренировка на руки",
                "Упражнения после инсульта",
                "Зарядка для сердца"
        );

        ExerciseAdapter adapter = new ExerciseAdapter(exercises);
        viewPager.setAdapter(adapter);

        return view;
    }
}
