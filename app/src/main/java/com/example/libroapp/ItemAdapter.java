package com.example.libroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String [] Libros;
    String[] Descripcion;
    String []Autor;
    public ItemAdapter(){

    }
    public ItemAdapter(Context c, String[] L, String[] D, String[ ]A)
    {
        Libros=L;
        Descripcion=D;
        Autor=A;
        mInflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return Libros.length;
    }

    @Override
    public Object getItem(int position) {
        return Libros[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=mInflater.inflate(R.layout.my_listview_detail,null);
        TextView Librostext=(TextView) v.findViewById(R.id.nombre_libro);
        TextView Autortext=(TextView) v.findViewById(R.id.autor_libro);
        TextView Descripciontext=(TextView) v.findViewById(R.id.descripcion_libro);

        String name=Libros[position];
        String autor=Autor[position];
        String desc=Descripcion[position];

        Librostext.setText(name);
        Autortext.setText(autor);
        Descripciontext.setText(desc);

        return v;
    }
}
