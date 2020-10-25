package com.example.libroapp;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favoritos extends Fragment {

    public Favoritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);

        Resources res = getResources();

        Motor motor = Motor.getInstance();
        String username = motor.getUsuario();

        String sql = "select u.IDUsuario from usuario u, login l where l.IDlogin = u.IDLogin and l.username like BINARY '" + username + "';";
        String IDusuario = null;

        try {
            ConnectionSql con = new ConnectionSql(sql, 1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            while (output.next()) {
                IDusuario =output.getString(1);
            }
            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }


        sql = "select l.nombre, group_concat(distinct a.nombre) as autor, l.isbn FROM libro AS l INNER JOIN autor_libro as al on al.IDlibro = l.IDlibro INNER JOIN autor as a ON a.IDautor = al.IDautor INNER JOIN usuario_libro AS ul ON ul.IDlibro =l.IDlibro where ul.favorito = 1 and ul.idusuario = "+IDusuario+" group by l.isbn;";
        String[] Libros;
        final String[] ISVarray;
        final String[] Autor;
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> autor = new ArrayList<>();



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




        Libros = titulos.toArray(new String[0]);
        ISVarray = ISBN.toArray(new String[0]);
        Autor = autor.toArray(new String[0]);

        ListView listview = root.findViewById(R.id.listfavoritos);


        ItemAdapterFav itemadapterFav=new ItemAdapterFav(requireActivity(),Libros,ISVarray,Autor);
        listview.setAdapter(itemadapterFav);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager manager = getFragmentManager();
                detailAct nuevo=new detailAct();
                Bundle Arguments=new Bundle();
                Arguments.putInt("INDEX",position);
                nuevo.setArguments(Arguments);
                manager.beginTransaction().replace(R.id.nav_host_fragment, nuevo).commit();



            }
        });
        return root;
    }
}
