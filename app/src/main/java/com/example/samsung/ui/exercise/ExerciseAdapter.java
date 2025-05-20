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
        holder.selectButton.setOnClickListener(v -> {
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
        Button selectButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.exerciseTitle);
            selectButton = itemView.findViewById(R.id.selectButton);

        }
    }
}
