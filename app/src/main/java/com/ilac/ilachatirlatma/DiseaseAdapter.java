package com.ilac.ilachatirlatma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.pojos.Disease;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {


    ArrayList<Disease> diseaseArrayList;
    LayoutInflater inflater;
    DiseaseDAO diseaseDAO;
    DrugDatabase drugDatabase;

    public DiseaseAdapter(Context context, ArrayList diseaseAllArrayList) {
        inflater = LayoutInflater.from(context);
        this.diseaseArrayList = diseaseAllArrayList;
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
        Disease disease = diseaseArrayList.get(position);
        holder.setData(disease, position);
    }

    @Override
    public int getItemCount() {
        return diseaseArrayList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewDiseaseName, textViewDiseaseValue, textViewDiseaseDate;
        ImageView imageViewUpdate, imageViewDelete;
        private int clickedItemPosition = 0;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDiseaseName = itemView.findViewById(R.id.textViewDiseaseName);
            textViewDiseaseValue = itemView.findViewById(R.id.textViewDiseaseValue);
            textViewDiseaseDate = itemView.findViewById(R.id.textViewDiseaseDate);
            imageViewUpdate = itemView.findViewById(R.id.imageViewUpdate);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void setData(Disease data, final int position) {
            this.clickedItemPosition = position;
            this.textViewDiseaseName.setText(data.getDiseaseName());
            this.textViewDiseaseValue.setText(data.getDiseaseValue());
            this.textViewDiseaseDate.setText(data.getCreatedDate());
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

            diseaseDAO.deleteDisease(diseaseArrayList.get(position).getDiseaseId());
            notifyItemRemoved(position);
            diseaseArrayList.remove(position);
            notifyItemRangeChanged(position, diseaseArrayList.size());
        }
    }
}
