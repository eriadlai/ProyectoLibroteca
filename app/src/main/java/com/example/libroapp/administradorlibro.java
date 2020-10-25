package com.example.libroapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link administradorlibro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class administradorlibro extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public administradorlibro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment administradorlibro.
     */
    // TODO: Rename and change types and number of parameters
    public static administradorlibro newInstance(String param1, String param2) {
        administradorlibro fragment = new administradorlibro();
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

    private static final int RESULT_LOAD_IMAGE = 1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administradorlibro, container, false);


        final EditText editISVBusqueda = (EditText) view.findViewById(R.id.isvconsulta_editText);
        final EditText editISV = (EditText) view.findViewById(R.id.ISV_editText);
        final EditText editNombre = (EditText) view.findViewById(R.id.nombreL_editText);
        final EditText editFecha = (EditText) view.findViewById(R.id.fecha_editText);
        final EditText editNoPag = (EditText) view.findViewById(R.id.noPag_editText);
        final EditText editPais = (EditText) view.findViewById(R.id.pais_editText);
        final EditText editIDLibro = (EditText) view.findViewById(R.id.IDLibro_editText);
        final EditText editEdicion = (EditText) view.findViewById(R.id.edicion_editText);

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSubir);


        Button botonEditar = (Button) view.findViewById(R.id.editar_buttonLibro);
        Button botonBorrar = (Button) view.findViewById(R.id.borrar_buttonLibro);
        Button botonCrear = (Button) view.findViewById(R.id.crear_buttonLibro);
        Button botonConsulta = (Button) view.findViewById(R.id.button_buscaridLibro);
        Button botonimagen = (Button) view.findViewById(R.id.imagen_buttonLibro);

        botonimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                } else {
                }
            }
        });

        botonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ISBN = editISVBusqueda.getText().toString();
                String sql = "Select IDLibro, Nombre, FechaPublicacion, No_paginas, Pais, Edicion, ISBN from libro where ISBN = '" + ISBN + "';";
                try {
                    ConnectionSql con = new ConnectionSql(sql, 1);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    ResultSet output = con.getOutput();
                    if (output.next() == false) {
                    } else {
                        utilitiesConsultasEImagenes u = new utilitiesConsultasEImagenes();
                        editIDLibro.setText(output.getString(1));
                        editNombre.setText(output.getString(2));
                        editFecha.setText(output.getString(3));
                        editNoPag.setText(output.getString(4));
                        editPais.setText(output.getString(5));
                        editEdicion.setText(output.getString(6));
                        editISV.setText(output.getString(7));

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
                    String fecha = editFecha.getText().toString();
                    String no_paginas = editNoPag.getText().toString();
                    String pais = editPais.getText().toString();
                    String ISBN = editISV.getText().toString();
                    String edicion = editEdicion.getText().toString();
                    String ID = editIDLibro.getText().toString();

                    String sql = "update libro set nombre = '" + nombre + "', fechaPublicacion = '" + fecha + "', no_paginas =" + no_paginas + ",Pais = '" + pais + "',ISBN = '" + ISBN + "',Edicion = '" + edicion + "' where IDLibro = " + ID + " ;";
                    ConnectionSql con = new ConnectionSql(sql, 3);
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

                    String ID = editIDLibro.getText().toString();

                    String sql;
                    sql = "delete from autor_libro where IDLibro='" + ID + "' ;";
                    borrarQuery(sql);
                    sql = "delete from biblioteca_libro where IDLibro='" + ID + "' ;";
                    borrarQuery(sql);
                    sql = "delete from usuario_libro where IDLibro='" + ID + "' ;";
                    borrarQuery(sql);
                    sql = "delete from genero_libro_relacion where IDLibro='" + ID + "' ;";
                    borrarQuery(sql);
                    sql = "delete from coleccion_libros where IDLibro='" + ID + "' ;";
                    borrarQuery(sql);
                    sql = "delete from libro where IDLibro='" + ID + "' ;";
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
                    String fecha = editFecha.getText().toString();
                    String no_paginas = editNoPag.getText().toString();
                    String pais = editPais.getText().toString();
                    String ISBN = editISV.getText().toString();
                    String edicion = editEdicion.getText().toString();
                    byte[] portada = getBytesDeBitmap(imageView.getDrawingCache());
                    String sql = "INSERT INTO libro (nombre, fechapublicacion, no_paginas, pais, ISBN, edicion, portada) values ('" + nombre + "','" + fecha + "'," + no_paginas + ",'" + pais + "','" + ISBN + "','" + edicion + "','"+ portada +"');";
                    ConnectionSql con = new ConnectionSql(sql, 3);
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
        ConnectionSql con = new ConnectionSql(sql, 2);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        con.desconectar();
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSubir);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getBytesDeBitmap(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            return bArray;
        }
        return null;
    }


    @Override
    public void onClick(View v) {

    }
}
