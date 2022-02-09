package com.example.mypieces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepertoireListAdapter extends RecyclerView.Adapter<RepertoireListAdapter.ListViewHolder> {

    private ArrayList<PieceData> dataForList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }




    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }



    public class ListViewHolder extends RecyclerView.ViewHolder
    {
        TextView pieceName;
        TextView composerName;

        public ListViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            this.pieceName = itemView.findViewById(R.id.text_view_name);
            this.composerName = itemView.findViewById(R.id.text_view_composer);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener != null)
                    {
                        int index = getAdapterPosition();

                        if(index != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(index);
                        }
                    }
                }
            });




        }
    }
    public RepertoireListAdapter(ArrayList<PieceData> list)
    {
        this.dataForList = list;
    }

    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        ListViewHolder myViewHolder = new ListViewHolder(view, mListener);
        return myViewHolder;
    }


    public void onBindViewHolder(ListViewHolder holder, int listPosition)
    {
        holder.pieceName.setText(dataForList.get(listPosition).pieceName);
    }

    public int getItemCount()
    {
        return dataForList.size();
    }

}
