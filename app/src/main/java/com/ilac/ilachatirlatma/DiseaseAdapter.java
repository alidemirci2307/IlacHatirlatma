package com.ilac.ilachatirlatma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.model.DiseaseAll;
import com.ilac.ilachatirlatma.pojos.Disease;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {


    ArrayList<DiseaseAll> diseaseAllArrayList;
    LayoutInflater inflater;
    DiseaseDAO diseaseDAO;
    DrugDatabase drugDatabase;

    public DiseaseAdapter(Context context, ArrayList diseaseAllArrayList) {
        inflater = LayoutInflater.from(context);
        this.diseaseAllArrayList = diseaseAllArrayList;
        drugDatabase = new DrugDatabase(context);
        diseaseDAO = new DiseaseDAO(drugDatabase);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DiseaseAll diseaseAll = diseaseAllArrayList.get(position);
        holder.setData(diseaseAll, position);
    }

    @Override
    public int getItemCount() {
        return diseaseAllArrayList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewDiseaseName;
        ImageView imageViewUpdate, imageViewDelete;
        private int clickedItemPosition = 0;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDiseaseName = itemView.findViewById(R.id.textViewDiseaseName);
            imageViewUpdate = itemView.findViewById(R.id.imageViewUpdate);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void setData(DiseaseAll data, final int position) {
            this.clickedItemPosition = position;
            this.textViewDiseaseName.setText(data.toString());
            this.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }

            });
            this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(clickedItemPosition);
                }
            });
        }

        private void deleteItem(int position){

            diseaseDAO.deleteDisease(diseaseAllArrayList.get(position).getDisease().getId());
            notifyItemRemoved(position);
            diseaseAllArrayList.remove(position);
            notifyItemRangeChanged(position, diseaseAllArrayList.size());
        }
    }
}
