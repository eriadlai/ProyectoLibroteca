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
 * Use the {@link administradorbiblioteca#newInstance} factory method to
 * create an instance of this fragment.
 */
public class administradorbiblioteca extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public administradorbiblioteca() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment administradorbiblioteca.
     */
    // TODO: Rename and change types and number of parameters
    public static administradorbiblioteca newInstance(String param1, String param2) {
        administradorbiblioteca fragment = new administradorbiblioteca();
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
        view = inflater.inflate(R.layout.fragment_administradorbiblioteca, container, false);


        final EditText editNombreBusqueda = (EditText) view.findViewById(R.id.busquedaNombre_editText);
        final EditText editNombre = (EditText) view.findViewById(R.id.nombreBiblioteca_editText);
        final EditText editID = (EditText) view.findViewById(R.id.idBiblioteca_editText);
        final EditText editDireccion = (EditText) view.findViewById(R.id.direccionBiblioteca_editText);
        final EditText editCiudad = (EditText) view.findViewById(R.id.nombreCiudad_editText);
        final EditText editPublica = (EditText) view.findViewById(R.id.tipoBiblioteca_editText);



        Button botonEditar = (Button) view.findViewById(R.id.editarBiblioteca_button);
        Button botonBorrar = (Button) view.findViewById(R.id.borrarBiblioteca_button);
        Button botonCrear = (Button) view.findViewById(R.id.crearBiblioteca_button);
        Button botonConsulta = (Button) view.findViewById(R.id.buscarBiblioteca_button);


        botonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombreBusqueda.getText().toString();
                String sql = "Select * from biblioteca where Nombre like '%"+nombre+"%' limit 1;";
                try {
                    ConnectionSql con = new ConnectionSql(sql,1);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    ResultSet output = con.getOutput();
                    if (output.next() == false) {

                    } else {

                        editNombre.setText(output.getString(1));
                        editDireccion.setText(output.getString(2));
                        editCiudad.setText(output.getString(3));
                        editPublica.setText(output.getString(4));
                        editID.setText(output.getString(5));
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
                    String direccion = editDireccion.getText().toString();
                    String ciudad = editCiudad.getText().toString();
                    String publica = editPublica.getText().toString();
                    String ID = editID.getText().toString();

                    String sql = "update biblioteca set nombre='"+nombre+"', direccion='"+direccion+"', ciudad='"+ciudad+"',publica='"+publica+"' where IDBiblioteca = "+ID+";";
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


                    String sql = "delete from biblioteca_libro where IDBiblioteca='"+ID+"' ;";
                    borrarQuery(sql);
                    sql = "delete from biblioteca where IDBiblioteca='"+ID+"' ;";
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
                    String direccion = editDireccion.getText().toString();
                    String ciudad = editCiudad.getText().toString();
                    String publica = editPublica.getText().toString();

                    String sql = "INSERT INTO biblioteca(nombre,direccion,ciudad,publica) values('"+nombre+"','"+direccion+"','"+ciudad+"',"+publica+");";
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

        // Inflate the layout for this fragment
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
