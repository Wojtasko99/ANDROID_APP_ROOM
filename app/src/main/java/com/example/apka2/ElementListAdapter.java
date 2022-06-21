package com.example.apka2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {
    LayoutInflater mLayoutInflater;
    Context context;
    private List<Element> mElementList = new ArrayList<>();
    private OnItemClickListener listener;


    public ElementListAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mElementList = null;
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mLayoutInflater.inflate(R.layout.wiersz, parent, false);
        return new ElementViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        Element current_element = mElementList.get(position);
        holder.text1.setText(current_element.getProducent());
        holder.text2.setText(current_element.getModel());

    }

    @Override
    public int getItemCount() {
        if (mElementList != null) {
            return mElementList.size();
        }
        return 0;
    }

    public void setElementList(List<Element> elementList) {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder {
        public TextView text1, text2;

        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.textView);
            text2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mElementList.get(position));
                    }
                }
            });
        }
    }

    public Element getElementAt(int position) {
        return mElementList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Element element);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
