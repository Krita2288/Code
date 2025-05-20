package com.example.samsung.ui.tracker;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.widget.EditText;
import com.example.samsung.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        // получаем доступ к контейнерам
        LinearLayout morningList = view.findViewById(R.id.morningList);
        LinearLayout afternoonList = view.findViewById(R.id.afternoonList);
        LinearLayout eveningList = view.findViewById(R.id.eveningList);

        // кнопки
        Button btnAddMorning = view.findViewById(R.id.btnAddMorning);
        Button btnAddAfternoon = view.findViewById(R.id.btnAddAfternoon);
        Button btnAddEvening = view.findViewById(R.id.btnAddEvening);

        btnAddMorning.setOnClickListener(v -> showAddTaskDialog(morningList));
        btnAddAfternoon.setOnClickListener(v -> showAddTaskDialog(afternoonList));
        btnAddEvening.setOnClickListener(v -> showAddTaskDialog(eveningList));


        return view;
    }
    private void showAddTaskDialog(LinearLayout container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Добавить задание");

        final EditText input = new EditText(getContext());
        input.setHint("Например: Выпить витамины");
        builder.setView(input);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String taskText = input.getText().toString().trim();
            if (!taskText.isEmpty()) {
                TextView task = createTaskTextView("• " + taskText);
                container.addView(task);
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private TextView createTaskTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(16);
        tv.setPadding(8, 8, 8, 8);
        return tv;
    }

}