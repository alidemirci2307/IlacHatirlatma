package com.ilac.ilachatirlatma;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.pojos.Disease;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

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
        View v = inflater.inflate(R.layout.list_item_disease,parent,false);
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
            textViewDiseaseName = itemView.findViewById(R.id.textViewDrugName);
            textViewDiseaseValue = itemView.findViewById(R.id.textViewDiseaseValue);
            textViewDiseaseDate = itemView.findViewById(R.id.textViewDiseaseDate);
            imageViewUpdate = itemView.findViewById(R.id.imageViewUpdate);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void setData(final Disease data, final int position) {
            this.clickedItemPosition = position;
            this.textViewDiseaseName.setText(data.getDiseaseName());
            this.textViewDiseaseValue.setText(data.getDiseaseValue());
            this.textViewDiseaseDate.setText(data.getCreatedDate());
            this.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInputDialog(itemView.getContext(),clickedItemPosition,data);
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

    private void showInputDialog(final Context context, int selectedIndex, final Disease data) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.input_dialog_disease);
        final TextInputLayout textInputLayoutDiseaseName = dialog.findViewById(R.id.layoutTextInputDrugName);
        final TextInputLayout textInputLayoutDiseaseResultValue = dialog.findViewById(R.id.layoutTextInputDrugDose);
        final EditText editTextDiseaseName = dialog.findViewById(R.id.editTextDiseaseName);
        final EditText editTextDiseaseResultValue = dialog.findViewById(R.id.editTextDiseaseResultValue);
        Button buttonRun = dialog.findViewById(R.id.buttonRun);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonRun.setText("Kaydet");
        final int selected = selectedIndex;
        editTextDiseaseName.setText(data.getDiseaseName());
        editTextDiseaseResultValue.setText(data.getDiseaseValue());
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateText(editTextDiseaseName.getText().toString(),textInputLayoutDiseaseName) && validateText(editTextDiseaseResultValue.getText().toString(),textInputLayoutDiseaseResultValue)){
                    data.setDiseaseName(editTextDiseaseName.getText().toString().trim());
                    data.setDiseaseValue(editTextDiseaseResultValue.getText().toString().trim());
                    long id = diseaseDAO.updateDisease(data);
                    Toasty.success(context, "Başarıyla güncellenmiştir. Id : " + id, Toast.LENGTH_SHORT, true).show();
                    //diseaseArrayList.clear();
                    //diseaseArrayList.addAll(diseaseDAO.getAllDiseasesById(user.getUserId()));
                    //DiseaseAdapter diseaseAdapter = new DiseaseAdapter(context, diseaseArrayList);
                    diseaseArrayList.set(selected,data);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    //recyclerView.refreshDrawableState();
                }

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public boolean validateText(String text, TextInputLayout textInputLayout){
        if(text != null){
            if(text.trim().length() <  4){
                textInputLayout.setError("En az 4 karakter girmelisiniz");
                return false;
            }else{
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        }else{
            textInputLayout.setError("Bu alan boş olamaz");
            return false;
        }
    }
}
