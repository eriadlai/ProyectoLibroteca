package com.example.libroapp;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class detailAct extends Fragment {

    private DetailViewModel mViewModel;

    public static detailAct newInstance() {
        return new detailAct();
    }


    View view;
    String isv;
    String idLibro;
    String idUsuario;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        int value1 = 0;
        value1 = bundle.getInt("INDEX", -1);
        isv = bundle.getString("ISV");
        System.out.println(isv);

        view = inflater.inflate(R.layout.activity_detail, container, false);

        TextView nombre = view.findViewById(R.id.nombrelibroL_textView);
        TextView genero = view.findViewById(R.id.generoL_textView);
        TextView Fecha = view.findViewById(R.id.fechaL_textView);
        TextView noPaginas = view.findViewById(R.id.paginasL_textView);
        TextView pais = view.findViewById(R.id.paisL_textView);
        TextView editorial = view.findViewById(R.id.editorialL_textView);
        TextView edicion = view.findViewById(R.id.edicionL_textView);
        TextView autores = view.findViewById(R.id.autoresL_textView);
        TextView ISBN = view.findViewById(R.id.isbnL_textView);
        TextView apa = view.findViewById(R.id.apaL_textView);

        Button favorito = (Button) view.findViewById(R.id.agregarfavoritos);
        Button coleccion = (Button) view.findViewById(R.id.agregarcoleccion_button);

        try {
            obtenerIdlibro();
            obtenerIdUsuario();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        Motor motor = Motor.getInstance();
        String username = motor.getUsuario();
        String idUsuario  = motor.getIDusuario();




        try {
            String sql = "Select * from login l, usuario u, usuario_libro ul, libro li where l.idlogin = u.idlogin and u.idusuario = ul.idusuario and ul.idlibro = li.idlibro and li.ISBN = '" + ISBN + "' and l.username like binary '" + username + "' ;";
            ConnectionSql con = new ConnectionSql(sql, 1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();


            if (output.next() == false) {//Si no existe la coneccion
                System.out.println(idLibro+"--------------------------------------------------------------------------"+idUsuario);
                sql = "Insert into usuario_libro(IDusuario,Favorito,IDlibro) values("+idUsuario+",0,"+idLibro+") ;";
                System.out.println(sql);
                con = new ConnectionSql(sql, 3);
                threadQuery = new Thread(con);
                threadQuery.start();
                threadQuery.join();
                con.desconectar();

            } else {// SI existe la coneccion
                sql = "Update usuario_libro set favorito=favorito;";
                con = new ConnectionSql(sql, 3);
                threadQuery = new Thread(con);
                threadQuery.start();
                threadQuery.join();
                con.desconectar();

            }

            con.desconectar();

            sql = "SELECT li.ISBN, li.nombre, li.fechapublicacion, li.no_paginas, li.pais, li.edicion, e.nombre,group_concat(DISTINCT a.nombre) as autor, group_concat(DISTINCT g.nombre) AS genero FROM libro AS li INNER JOIN editorial AS e ON e.IDeditorial = li.IDeditorial INNER JOIN autor_libro AS la ON la.IDlibro = li.IDlibro INNER JOIN autor AS a ON a.IDautor = la.IDautor INNER JOIN genero_libro_relacion AS gl ON gl.IDlibro = li.IDlibro INNER JOIN genero_libro AS g ON g.IDgenero = gl.IDgenero Where li.ISBN = '" + isv + "' GROUP BY li.IDlibro;";


            con = new ConnectionSql(sql, 1);
            threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            output = con.getOutput();
            if (output.next() == false) {
            } else {
                nombre.setText("Autoria: " + output.getString(2));
                genero.setText("Genero: " + output.getString(9));
                Fecha.setText("Fecha de publicacion: " + output.getString(3));
                noPaginas.setText("Numero de paginas: " + output.getString(4));
                pais.setText("Pais de publicacion: " + output.getString(5));
                editorial.setText("Editorial: " + output.getString(7));
                edicion.setText("Edicion: " + output.getString(6));
                autores.setText("Autoria: " + output.getString(8));
                ISBN.setText("ISBN: " + output.getString(1));
                apa.setText("");
            }


            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        coleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Motor motor = Motor.getInstance();
                String idColeccion = motor.getColeccionActiva();
                String sql = "Insert into coleccion_libros(IDcolecciones,IDLibro) values("+idColeccion+","+idLibro +");";
                ConnectionSql con = new ConnectionSql(sql, 3);
                Thread threadQuery = new Thread(con);
                threadQuery.start();
                try {
                    threadQuery.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                con.desconectar();
            }
        });

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Motor motor = Motor.getInstance();
                String idUsuario = motor.getIDusuario();

                String sql = "Update usuario_libro set favorito=1 where idusuario = "+idUsuario+" and idlibro ="+idLibro +";";
                ConnectionSql con = new ConnectionSql(sql, 3);
                Thread threadQuery = new Thread(con);
                threadQuery.start();
                try {
                    threadQuery.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                con.desconectar();


            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        int value1 = 0;

        if (value1 > -1) {
            int pic = getImg(value1);
            ImageView img = (ImageView) getActivity().findViewById(R.id.imageView2);
            scaleImg(img, pic);

        }

    }

    private int getImg(int value1) {
        switch (value1) {
            case 0:
                return R.drawable.img1;
            case 1:
                return R.drawable.img2;
            case 2:
                return R.drawable.img3;
            default:
                return -1;
        }
    }

    private void scaleImg(ImageView img, int pic) {
        Display screen = getActivity().getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();
        if (imgWidth > screenWidth) {
            int ratio = Math.round((float) imgWidth / (float) screenWidth);
            options.inSampleSize = ratio;

        }
        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);


    }

    private void obtenerIdlibro() throws InterruptedException, SQLException {
        String sql = "select idlibro from libro where isbn = '"+isv+"' ;";
        ConnectionSql con = new ConnectionSql(sql, 1);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        ResultSet output = con.getOutput();
        if(!output.next()){

        }else{
            idLibro = output.getString(1);
        }
    }

    private void obtenerIdUsuario() throws InterruptedException, SQLException {
        Motor motor = Motor.getInstance();
        String Usuario = motor.getUsuario();
        String sql = "select u.idUsuario from usuario u, login l  where u.idlogin =l.idlogin and l.username like binary '" + Usuario + "';";
        ConnectionSql con = new ConnectionSql(sql, 1);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        ResultSet output = con.getOutput();
        output.next();
        String idUsuario = output.getString(1);
        motor.setIDusuario(idUsuario);

        con.desconectar();
    }
}
