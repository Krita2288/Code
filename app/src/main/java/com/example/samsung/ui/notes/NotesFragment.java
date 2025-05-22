package com.example.samsung.ui.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samsung.R;
import java.text.SimpleDateFormat;
import java.util.*;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private List<String> notes = new ArrayList<>();
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true); // для кнопки "+"
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prefs = requireContext().getSharedPreferences("notes_pref", Context.MODE_PRIVATE);
        notes = new ArrayList<>(prefs.getStringSet("notes", new HashSet<>()));

        recyclerView = view.findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_note) {
            showNoteDialog();
            return true;
        } else if (id == R.id.action_delete_last) {
            deleteLastNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void deleteLastNote() {
        if (!notes.isEmpty()) {
            notes.remove(0); // удаляем последнюю добавленную (самую верхнюю)
            adapter.notifyItemRemoved(0);
            saveNotes();
            Toast.makeText(getContext(), "Последняя запись удалена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Записей нет", Toast.LENGTH_SHORT).show();
        }
    }



    private void showNoteDialog() {
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        new AlertDialog.Builder(getContext())
                .setTitle("Новая запись")
                .setView(input)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    if (!text.isEmpty()) {
                        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
                        String entry = date + "\n" + text;
                        notes.add(0, entry);
                        adapter.notifyItemInserted(0);
                        saveNotes();
                    } else {
                        Toast.makeText(getContext(), "Пустая запись", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void saveNotes() {
        prefs.edit().putStringSet("notes", new HashSet<>(notes)).apply();
    }
}
