package com.example.libroapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class resultadosBusqueda extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public resultadosBusqueda() {
        // Required empty public constructor
    }

    public resultadosBusqueda(String busqueda, String filtro) {
    bus =busqueda;
    fil =filtro;
    }
    String bus;
    String fil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resultados_busqueda, container, false);


        Resources res = getResources();

        String[] Libros;
        final String[] ISVarray;
        final String[] Autor;
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> autor = new ArrayList<>();

        String sql = "select distinct l.nombre , l.ISBN , group_concat(distinct a.nombre)as AUTOR from libro as l inner join genero_libro_relacion glr on glr.idlibro=l.idlibro inner join genero_libro gl on gl.idgenero=glr.idgenero inner join autor_libro al on al.idlibro=l.idlibro inner join autor a on a.idautor=al.idautor where l.nombre like \"%"+bus+"%\" and gl.nombre like \"%"+fil+"%\" group by l.idlibro;";

        try {
            ConnectionSql con = new ConnectionSql(sql, 1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            while (output.next()) {
                titulos.add(output.getString(1));
                System.out.println(output.getString(1));
                ISBN.add(output.getString(2));
                autor.add(output.getString(3));
            }
            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        ListView listview = root.findViewById(R.id.resultados_listview);


        Libros = titulos.toArray(new String[0]);
        ISVarray = ISBN.toArray(new String[0]);
        Autor = autor.toArray(new String[0]);

        ItemAdapterBusquedaR itemAdapterBusquedaR=new ItemAdapterBusquedaR(requireActivity(),Libros,ISVarray, Autor);
        listview.setAdapter(itemAdapterBusquedaR);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager manager = getFragmentManager();
                String autor=ISVarray[position];
                detailAct nuevo=new detailAct();
                Bundle Arguments=new Bundle();
                Arguments.putInt("INDEX",position);
                Arguments.putString("ISV",autor);
                nuevo.setArguments(Arguments);
                manager.beginTransaction().replace(R.id.nav_host_fragment, nuevo).commit();



            }
        });
        return root;
    }
}

