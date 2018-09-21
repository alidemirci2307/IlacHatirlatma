package com.ilac.ilachatirlatma;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ilac.ilachatirlatma.controller.DrugDAO;
import com.ilac.ilachatirlatma.pojos.Drug;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder> {


    ArrayList<Drug> drugArrayList;
    LayoutInflater inflater;
    DrugDAO drugDAO;
    DrugDatabase drugDatabase;

    public DrugAdapter(Context context, ArrayList drugArrayList) {
        inflater = LayoutInflater.from(context);
        this.drugArrayList = drugArrayList;
        drugDatabase = new DrugDatabase(context);
        drugDAO = new DrugDAO(drugDatabase);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_item_drug,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drug drug= drugArrayList.get(position);
        holder.setData(drug, position);
    }

    @Override
    public int getItemCount() {
        return drugArrayList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewDrugName, textViewDrugDose, textViewDrugDate,textViewDrugFrequence;
        Spinner spinnerDrugDoseType;
        ImageView imageViewUpdate, imageViewDelete;
        private int clickedItemPosition = 0;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDrugName = itemView.findViewById(R.id.textViewDrugName);
            textViewDrugDose = itemView.findViewById(R.id.textViewDrugDose);
            textViewDrugDate = itemView.findViewById(R.id.textViewDrugDate);
            textViewDrugFrequence = itemView.findViewById(R.id.textViewDrugFrequence);
            spinnerDrugDoseType = itemView.findViewById(R.id.spinnerDrugDoseType);
            imageViewUpdate = itemView.findViewById(R.id.imageViewUpdate);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void setData(final Drug data, final int position) {
            this.clickedItemPosition = position;
            this.textViewDrugName.setText(data.getDrugName());
            this.textViewDrugDose.setText(data.getDrugDose());
            this.textViewDrugDate.setText(data.getCreatedDate());
            this.textViewDrugFrequence.setText(getFrequence(data.getDrugFrequence()));
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

        private String getFrequence(String drugFrequence) {
            if(drugFrequence.length() == 3){
                String frequence = "";
                if(drugFrequence.charAt(0)== '1'){
                    frequence += "07:30 ";
                }
                if(drugFrequence.charAt(1)== '1'){
                    frequence += "15:30 ";
                }
                if(drugFrequence.charAt(2)== '1'){
                    frequence += "23:30 ";
                }
                return frequence;
            }else{
                String split[] = drugFrequence.split(" ");

                return getFrequenceSetTime(split);
            }

        }

        private String getFrequenceSetTime(String[] split) {
            String time[] = split[1].split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            String times = "";
            do {
                times += getTimeFormat(hour,minute)+" ";
                hour += Integer.parseInt(split[0]);
            } while(hour < 24);
            return times;

        }

        private void showInputDialog(final Context context, final int selectedIndex, final Drug data) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.input_dialog_drug);
            final TextInputLayout layoutTextInputDrugName = dialog.findViewById(R.id.layoutTextInputDrugName);
            final TextInputLayout layoutTextInputDrugDose = dialog.findViewById(R.id.layoutTextInputDrugDose);
            final EditText editTextDrugName = dialog.findViewById(R.id.editTextDrugName);
            final EditText editTextDrugDose = dialog.findViewById(R.id.editTextDrugDose);
            final EditText editTextDrugFrequence = dialog.findViewById(R.id.editTextDrugFrequence);
            final EditText editTextDrugTime = dialog.findViewById(R.id.editTextDrugTime);
            final CheckBox checkbox1 = dialog.findViewById(R.id.checkbox1);
            final CheckBox checkbox2 = dialog.findViewById(R.id.checkbox2);
            final CheckBox checkbox3 = dialog.findViewById(R.id.checkbox3);
            spinnerDrugDoseType = dialog.findViewById(R.id.spinnerDrugDoseType);


            if(data.getDrugFrequence().length() == 3){
                if(data.getDrugFrequence().charAt(0) == '1'){
                    checkbox1.setChecked(true);
                }
                if(data.getDrugFrequence().charAt(1) == '1'){
                    checkbox2.setChecked(true);
                }
                if(data.getDrugFrequence().charAt(2) == '1'){
                    checkbox3.setChecked(true);
                }
            }else{
                String split[] =  data.getDrugFrequence().split(" ");
                editTextDrugFrequence.setText(split[0]);
                editTextDrugTime.setText(split[1]);
            }
            editTextDrugName.setText(data.getDrugName());
            editTextDrugDose.setText(data.getDrugDose());

            checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        editTextDrugFrequence.setText("");
                        editTextDrugTime.setText("");
                    }
                }
            });

            checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        editTextDrugFrequence.setText("");
                        editTextDrugTime.setText("");
                    }
                }
            });

            checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        editTextDrugFrequence.setText("");
                        editTextDrugTime.setText("");
                    }
                }
            });

            editTextDrugFrequence.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if(charSequence.toString().trim().length() > 0){
                        checkbox1.setChecked(false);
                        checkbox2.setChecked(false);
                        checkbox3.setChecked(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editTextDrugTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if(charSequence.toString().trim().length() > 0){
                        checkbox1.setChecked(false);
                        checkbox2.setChecked(false);
                        checkbox3.setChecked(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            Button buttonRun = dialog.findViewById(R.id.buttonRun);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
            buttonRun.setText("Kaydet");

            buttonRun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(validateText(editTextDrugName.getText().toString(),layoutTextInputDrugName,4) && validateText(editTextDrugDose.getText().toString(),layoutTextInputDrugDose,1)){

                        data.setDrugDose(editTextDrugDose.getText().toString().trim());
                        data.setDrugName(editTextDrugName.getText().toString().trim());
                        if(checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked()){
                            String frequence = "";
                            if(checkbox1.isChecked()){
                                frequence = "1";
                            }else{
                                frequence = "0";
                            }

                            if(checkbox2.isChecked()){
                                frequence += "1";
                            }else{
                                frequence += "0";
                            }

                            if(checkbox3.isChecked()){
                                frequence += "1";
                            }else{
                                frequence += "0";
                            }
                            data.setDrugFrequence(frequence);
                            long id = drugDAO.updateDrug(data);
                            Toasty.success(context, "Başarıyla güncellenmiştir.", Toast.LENGTH_SHORT, true).show();
                        }else if(editTextDrugFrequence.getText().toString().length() > 0 && editTextDrugTime.getText().toString().length() > 3){
                            String frequence = editTextDrugFrequence.getText().toString().trim() + " " + editTextDrugTime.getText().toString().trim();
                            data.setDrugFrequence(frequence);
                            long id = drugDAO.updateDrug(data);
                            Toasty.success(context, "Başarıyla güncellenmiştir.", Toast.LENGTH_SHORT, true).show();
                        }else{
                            long id = drugDAO.updateDrug(data);
                            Toasty.success(context, "Başarıyla güncellenmiştir."+checkbox1.isSelected() +" " + checkbox2.isSelected() + " " + checkbox3.isSelected(), Toast.LENGTH_SHORT, true).show();
                        }




                        //Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir. ", Toast.LENGTH_SHORT, true).show();
                        drugArrayList.set(selectedIndex,data);
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

        private String getTimeFormat(int hour, int minute) {
            if (hour == 0) {
                return "00:" + minute;
            } else if (hour < 10) {
                return "0" + hour + ":" + minute;
            } else {
                return hour + ":" + minute;
            }
        }

        public boolean validateText(String text, TextInputLayout textInputLayout,int length){
            if(text != null){
                if(text.trim().length() <  length){
                    textInputLayout.setError("En az "+length+" karakter girmelisiniz");
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


        private void deleteItem(int position){

            drugDAO.deleteDrug(drugArrayList.get(position).getDrugId());
            notifyItemRemoved(position);
            drugArrayList.remove(position);
            notifyItemRangeChanged(position, drugArrayList.size());
        }
    }
}
