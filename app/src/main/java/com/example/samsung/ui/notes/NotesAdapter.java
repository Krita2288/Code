package com.example.samsung.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samsung.R;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final List<String> notes;

    public NotesAdapter(List<String> notes) {
        this.notes = notes;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.noteText);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textView.setText(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
