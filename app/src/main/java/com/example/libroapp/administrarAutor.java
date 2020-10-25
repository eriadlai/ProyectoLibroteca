package com.example.libroapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link administrarAutor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class administrarAutor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public administrarAutor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment administrarAutor.
     */
    // TODO: Rename and change types and number of parameters
    public static administrarAutor newInstance(String param1, String param2) {
        administrarAutor fragment = new administrarAutor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administrar_autor, container, false);


        final EditText editNombreBusqueda = (EditText) view.findViewById(R.id.IDAutorBusqueda_textview);
        final EditText editID = (EditText) view.findViewById(R.id.IDautor_editText);
        final EditText editNombre = (EditText) view.findViewById(R.id.nombreAutor_editText);

        Button botonEditar = (Button) view.findViewById(R.id.editarAutor_button);
        Button botonBorrar = (Button) view.findViewById(R.id.borrarAutor_button);
        Button botonCrear = (Button) view.findViewById(R.id.crearAutor_button);
        Button botonConsulta = (Button) view.findViewById(R.id.busquedaAutor_button);

        botonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombreBusqueda.getText().toString();
                String sql = "Select * from autor where Nombre like '%"+nombre+"%' limit 1;";
                try {
                    ConnectionSql con = new ConnectionSql(sql,1);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    ResultSet output = con.getOutput();
                    if (output.next() == false) {

                    } else {

                        editNombre.setText(output.getString(2));
                        editID.setText(output.getString(1));

                    }
                    con.desconectar();
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = editNombre.getText().toString();
                    String ID = editID.getText().toString();

                    String sql = "update autor set nombre='"+nombre+"' where IDAutor = "+ID+";";
                    ConnectionSql con = new ConnectionSql(sql,3);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    con.desconectar();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ID = editID.getText().toString();

                    String sql ="delete from autor_libro where IDAutor='"+ID+"' ;";
                    borrarQuery(sql);
                    sql = "delete from autor where IDAutor='"+ID+"' ;";
                    borrarQuery(sql);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = editNombre.getText().toString();

                    String sql = "INSERT INTO autor(nombre) values('"+nombre+"');";
                    ConnectionSql con = new ConnectionSql(sql,3);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    con.desconectar();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void borrarQuery(String sql) throws InterruptedException {
        ConnectionSql con = new ConnectionSql(sql,2);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        con.desconectar();
    }


}
