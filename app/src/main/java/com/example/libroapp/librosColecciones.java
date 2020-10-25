package com.example.libroapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class librosColecciones extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String idcol = null;

    public librosColecciones() {
        // Required empty public constructor
    }

    public librosColecciones(String id) {
        idcol = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_libros_colecciones, container, false);
        Resources res = getResources();

        final Motor motor = Motor.getInstance();
        Button borrarcole = (Button) root.findViewById(R.id.borrarColeccion_button) ;
        Button selcole = (Button) root.findViewById(R.id.seleccionarColeccion_button) ;

        borrarcole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sql = "delete from colecciones where IDColecciones = '" + idcol + "';";
                    borrarQuery(sql);
                    sql = "delete from colecciones where IDColecciones = '" + idcol + "';";
                    borrarQuery(sql);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        selcole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motor.setColeccionActiva(idcol);
            }
        });


        //-+---------------------------------------
        String[] Libros;
        final String[] ISVarray;
        final String[] Autor;
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> autor = new ArrayList<>();

        String sql = "select l.nombre, group_concat(distinct a.nombre) as autor, l.isbn FROM libro AS l INNER JOIN autor_libro as al on al.IDlibro = l.IDlibro INNER JOIN autor as a ON a.IDautor = al.IDautor INNER JOIN coleccion_libros as cl ON cl.IDlibro = l.IDlibro where cl.IDcolecciones = "+idcol+" group by l.isbn;";

        try {
            ConnectionSql con = new ConnectionSql(sql, 1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            while (output.next()) {
                titulos.add(output.getString(1));
                ISBN.add(output.getString(2));
                autor.add(output.getString(3));
            }
            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }


        ListView listview = root.findViewById(R.id.libroscolecciones_listview);

        Libros = titulos.toArray(new String[0]);
        ISVarray = ISBN.toArray(new String[0]);
        Autor = autor.toArray(new String[0]);


        ItemAdapterlibrosColecciones itemAdapterlibrosColecciones = new ItemAdapterlibrosColecciones(requireActivity(), Libros, ISVarray, Autor);
        listview.setAdapter(itemAdapterlibrosColecciones);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager manager = getFragmentManager();
                detailAct nuevo = new detailAct();
                String isv = ISVarray[position];
                Bundle Arguments = new Bundle();
                Arguments.putInt("INDEX", position);
                Arguments.putString("ISV", isv);
                nuevo.setArguments(Arguments);
                manager.beginTransaction().replace(R.id.nav_host_fragment, nuevo).commit();


            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

    }

    private void borrarQuery(String sql) throws InterruptedException {
        ConnectionSql con = new ConnectionSql(sql, 2);
        Thread threadQuery = new Thread(con);
        threadQuery.start();
        threadQuery.join();
        con.desconectar();
    }
}