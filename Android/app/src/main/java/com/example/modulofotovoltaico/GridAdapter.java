package com.example.modulofotovoltaico;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.modulofotovoltaico.R.layout.adapter_sensor;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Sensor> dataSensores;
    private LayoutInflater mInflaterCatalogListItems;
    ViewHolder holder;


    public GridAdapter(Context context, ArrayList<Sensor> dataSensores){
        this.context = context;
        this.dataSensores = dataSensores;
        mInflaterCatalogListItems = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSensores.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSensores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_sensor, viewGroup, false);
            //convertView = mInflaterCatalogListItems.inflate(adapter_sensor,null);
            holder.setLblSensor((TextView) convertView.findViewById(R.id.Sensor));
            holder.setLblMedida((TextView) convertView.findViewById(R.id.Medida));
            holder.setLinearLayout((LinearLayout) convertView.findViewById(R.id.lyCelda));
            holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkBox));

            convertView.setTag(holder);
         } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(dataSensores.get(position) != null){
            holder.getLblSensor().setText(dataSensores.get(position).getSensorName());
            //holder.getLblSensor().setBackgroundColor(dataSensores.get(position).getColorTitle());
            holder.getLblMedida().setText(dataSensores.get(position).getMedida());
            holder.getLinearLayout().setBackgroundColor(dataSensores.get(position).getColor());
            holder.getCheckBox().setChecked(dataSensores.get(position).getEnfasis());
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView lblSensor;
        private TextView lblMedida;
        private CheckBox checkBox;
        private LinearLayout linearLayout;

        public TextView getLblSensor() {
            return lblSensor;
        }
        public void setLblSensor(TextView lblSensor) {
            this.lblSensor = lblSensor;
        }

        public CheckBox getCheckBox() {return checkBox;}
        public void  setCheckBox(CheckBox checkBox) {this.checkBox = checkBox;}



        public TextView getLblMedida() {
            return lblMedida;
        }
        public void setLblMedida(TextView lblMedida) {
            this.lblMedida = lblMedida;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

    }

    public void setMedida(int position,String medida){
        dataSensores.get(position).setMedida(medida);
        holder.getLblMedida().setText(medida);
        //holder.getCheckBox().setText(medida);
        holder.getCheckBox().setChecked(dataSensores.get(position).getEnfasis());
    }

    public void setEnfasis(int position, boolean estado){
        dataSensores.get(position).setEnfasis(estado);
        holder.getCheckBox().setChecked(estado);
        //holder.getLblSensor().setBackgroundColor(dataSensores.get(position).getColorTitle());
    }



}
