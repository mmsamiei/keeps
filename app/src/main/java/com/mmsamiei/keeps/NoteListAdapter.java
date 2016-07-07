package com.mmsamiei.keeps;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Win2 on 5/25/2016.
 */
public class NoteListAdapter extends ArrayAdapter {
    ArrayList<NoteTable> data = new ArrayList<NoteTable>();
    public NoteListAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void setNewItem(int id,String title,String desc,int col){
        NoteTable item  = new NoteTable();
        item.title=title;
        item.description=desc;
        item.color=col;
        item.id=id;
        data.add(item);

    }
    public void clean(){
        data.clear();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View root=inflater.inflate(R.layout.note_list_item,null,false);
        root.setBackgroundColor(data.get(position).color);
        TextView desc = (TextView) root.findViewById(R.id.description);
        TextView title = (TextView) root.findViewById(R.id.title_txt);

        title.setText(data.get(position).title);
        desc.setText(data.get(position).description);
        return root;
    }
}
