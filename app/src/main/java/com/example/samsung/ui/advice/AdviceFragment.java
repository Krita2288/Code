package com.example.samsung.ui.advice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.samsung.R;

public class AdviceFragment extends Fragment {

    public AdviceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advice, container, false);

        Button btnHeart = view.findViewById(R.id.btnHeart);
        Button btnMobility = view.findViewById(R.id.btnMobility);
        Button btnVision = view.findViewById(R.id.btnVision);
        TextView adviceText = view.findViewById(R.id.adviceText);

        btnHeart.setOnClickListener(v -> {
            adviceText.setText("СОВЕТЫ ДЛЯ СЕРДЦА\n\n" +
                    "1. Следите за питанием\n" +
                    "Включайте в рацион больше овощей, фруктов, ягод, морской и речной рыбы, орехов и цельнозерновых продуктов. " +
                    "Питайтесь 4-5 раз в день маленькими порциями, избегайте переедания, жирной и жареной пищи, а также избыточного потребления соли и сахара.\n\n" +

                    "2. Регулярно занимайтесь физической активностью\n" +
                    "Для укрепления сердца полезны ежедневные прогулки, плавание, бег или домашние упражнения с умеренной нагрузкой. " +
                    "Начинайте с легкой активности и постепенно увеличивайте нагрузку.\n\n" +

                    "3. Откажитесь от вредных привычек\n" +
                    "Курение и алкоголь повышают риск заболеваний сердца.\n\n" +

                    "4. Контролируйте стресс и высыпайтесь\n" +
                    "Стресс и недосып вредны для сердца. Отдыхайте, дышите глубоко, спите 6–8 часов.\n\n" +

                    "5. Проходите обследования\n" +
                    "Следите за давлением, сахаром, холестерином. Посещайте кардиолога.");
            adviceText.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_light));
        });

        btnMobility.setOnClickListener(v -> {
            adviceText.setText("СОВЕТЫ ПРИ ПРОБЛЕМАХ С ХОДЬБОЙ\n\n" +
                    "1. Поддерживайте физическую активность\n" +
                    "Регулярные прогулки и упражнения на укрепление ног и координацию.\n\n" +

                    "2. Используйте опору\n" +
                    "Трости, ходунки, ортопедические приспособления помогут снизить нагрузку и повысить безопасность.\n\n" +

                    "3. Следите за весом\n" +
                    "Лишний вес усиливает нагрузку на суставы.\n\n" +

                    "4. Удобная обувь\n" +
                    "Обувь должна быть с амортизацией и нескользкой подошвой.\n\n" +

                    "5. Консультируйтесь с врачом\n" +
                    "Проходите реабилитацию и делайте лечебную физкультуру.");
            adviceText.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_light));
        });

        btnVision.setOnClickListener(v -> {
            adviceText.setText("СОВЕТЫ ПРИ ПРОБЛЕМАХ СО ЗРЕНИЕМ\n\n" +
                    "1. Защищайте глаза от УФ-лучей\n" +
                    "Используйте качественные солнцезащитные очки с фильтрами UVA/UVB.\n\n" +

                    "2. Гимнастика для глаз\n" +
                    "Каждые 20 минут — смотреть вдаль на 20 секунд. Это снижает напряжение.\n\n" +

                    "3. Освещение и эргономика\n" +
                    "Правильно ставьте монитор, избегайте бликов, держите осанку.\n\n" +

                    "4. Питание\n" +
                    "Морковь, шпинат, цитрусовые, орехи и рыба полезны для глаз.\n\n" +

                    "5. Регулярные осмотры\n" +
                    "Посещайте офтальмолога для профилактики и контроля.");
            adviceText.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light));
        });

        return view;
    }
}
