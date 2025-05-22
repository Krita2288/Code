package com.example.samsung.ui.exercise;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samsung.R;
import java.util.List;
import android.widget.ImageView;
import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private final List<String> exerciseList;

    public ExerciseAdapter(List<String> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        String title = exerciseList.get(position);
        holder.exerciseTitle.setText(title);


        switch (title) {
            case "Тренировка на руки":
                holder.exerciseImage.setImageResource(R.drawable.handu);
                break;
            case "Зарядка для сердца":
                holder.exerciseImage.setImageResource(R.drawable.herrt);
                break;
            case "Упражнения после инсульта":
                holder.exerciseImage.setImageResource(R.drawable.jumnujumpu);
                break;
            default:
                holder.exerciseImage.setImageResource(R.drawable.jumnujumpu);
                break;
        }
        holder.selectButton.setOnClickListener(v -> {
            // Сохраняем дату и название тренировки
            Context context = v.getContext();
            SharedPreferences prefs = context.getSharedPreferences("calendar_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            String today = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
            editor.putBoolean("exercise_" + today, true);  // Можно сделать уникальный ключ под дату
            editor.apply();

            // Переход
            NavController navController = Navigation.findNavController(v);
            switch (title) {
                case "Тренировка на руки":
                    navController.navigate(R.id.exerciseDetailArmsFragment);
                    break;
                case "Зарядка для сердца":
                    navController.navigate(R.id.exerciseDetailHeartFragment);
                    break;
                case "Упражнения после инсульта":
                    navController.navigate(R.id.exerciseDetailRehabFragment);
                    break;
            }
        });

    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitle;
        ImageView exerciseImage;

        Button selectButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.exerciseTitle);
            selectButton = itemView.findViewById(R.id.selectButton);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);


        }
    }
}
