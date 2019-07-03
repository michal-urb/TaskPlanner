package com.example.michau.taskplanner;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Task> implements View.OnClickListener {
    private ArrayList<Task> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
    }

    public ListViewAdapter(ArrayList<Task> data, Context context) {
        super(context, R.layout.item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

    }

    //private int lastPosition = -1;*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.secondLine);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(task.getName());
        viewHolder.txtDate.setText(task.getDate());

        return convertView;
    }
}
