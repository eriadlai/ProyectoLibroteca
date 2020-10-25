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
 * Use the {@link Link#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Link extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Link() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Link.
     */
    // TODO: Rename and change types and number of parameters
    public static Link newInstance(String param1, String param2) {
        Link fragment = new Link();
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
        view = inflater.inflate(R.layout.fragment_link, container, false);


        final EditText editIDLibro = (EditText) view.findViewById(R.id.ID1Crear_editText);
        final EditText editIDX = (EditText) view.findViewById(R.id.ID2Crear_editText);


        Button botonCrearAutor = (Button) view.findViewById(R.id.libroautor_button);
        Button botonCrearBiblioteca = (Button) view.findViewById(R.id.librobiblioteca_button);
        Button botonCrearGenero = (Button) view.findViewById(R.id.librogenero_button);
        Button botonCrearEditorial = (Button) view.findViewById(R.id.libroeditorial_button);




        botonCrearAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id1 = editIDLibro.getText().toString();
                    String id2 = editIDX.getText().toString();

                    String sql1 = "Select * from libro where IDLibro = "+id1+";";
                    String sql2 = "Select * from autor where IDAutor = "+id2+";";
                    String sql3 = "Select * from autor_libro where IDLibro = "+id1+" and IDAutor = "+id2+";";

                    if(comprobarExistencia(sql1)|| comprobarExistencia(sql2)||!comprobarExistencia(sql3)){
                        String sql ="Insert into autor_libro values("+id2+", "+id1+")";
                        hacerQuery(sql);
                    }

                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        botonCrearBiblioteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id1 = editIDLibro.getText().toString();
                    String id2 = editIDX.getText().toString();

                    String sql1 = "Select * from libro where IDLibro = "+id1+";";
                    String sql2 = "Select * from Biblioteca where IDbiblioteca = "+id2+";";
                    String sql3 = "Select * from biblioteca_libro where IDLibro = "+id1+" and IDBiblioteca = "+id2+";";

                    if(comprobarExistencia(sql1)|| comprobarExistencia(sql2)|| !comprobarExistencia(sql3)){
                        String sql ="Insert into biblioteca_libro values("+id1+", "+id2+")";
                        hacerQuery(sql);
                    }

                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        botonCrearGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id1 = editIDLibro.getText().toString();
                    String id2 = editIDX.getText().toString();

                    String sql1 = "Select * from libro where IDLibro = "+id1+";";
                    String sql2 = "Select * from Genero where IDgenero = "+id2+";";
                    String sql3 = "Select * from genero_libro_relacion where IDLibro = " + id1 + " and IDGenero = " + id2 + ";";

                    if(comprobarExistencia(sql1)|| comprobarExistencia(sql2)||!comprobarExistencia(sql3)){
                        String sql ="Insert into genero_libro_relacion values("+id1+", "+id2+")";
                        hacerQuery(sql);
                    }

                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        botonCrearEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id1 = editIDLibro.getText().toString();
                    String id2 = editIDX.getText().toString();

                    String sql1 = "Select * from libro where IDLibro = "+id1+";";
                    String sql2 = "Select * from Editorial where IDeditorial = "+id2+";";
                    String sql3 = "Select * from libro where IDLibro = " + id1 + " and IDEditorial = " + id2 + ";";

                    if(comprobarExistencia(sql1)|| comprobarExistencia(sql2)||!comprobarExistencia(sql3)){
                        String sql ="update libro set IDeditorial="+id2+" where IDLibro="+id1+";";
                        hacerQuery(sql);
                    }

                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private boolean comprobarExistencia(String sql) throws InterruptedException, SQLException {

        ConnectionSql con = new ConnectionSql(sql,1);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        ResultSet output = con.getOutput();
        if (output.next() == false) {
            con.desconectar();
            return false; //no existe
        } else {
            con.desconectar();
            return true; //si existe
        }

    }

    private void hacerQuery(String sql) throws InterruptedException {
        ConnectionSql con = new ConnectionSql(sql,3);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        con.desconectar();
    }
}
