package com.example.libroapp.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.libroapp.ConnectionSql;
import com.example.libroapp.ItemAdapter;
import com.example.libroapp.R;
import com.example.libroapp.detailAct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Resources res = getResources();

        String[] Libros;
        final String[] ISVarray;
        final String[] Autor;
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> autor = new ArrayList<>();

        String sql = "select libro.nombre,libro.isbn, group_concat(autor.nombre) as autores from libro join autor_libro on libro.idlibro = autor_libro.idlibro join autor on autor.idautor = autor_libro.idautor group by libro.idlibro, libro.nombre;";

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

        ListView listview = root.findViewById(R.id.myListView);


        Libros = titulos.toArray(new String[0]);
        ISVarray = ISBN.toArray(new String[0]);
        Autor = autor.toArray(new String[0]);

        ItemAdapter itemadapter = new ItemAdapter(requireActivity(), Libros, ISVarray,Autor);
        listview.setAdapter(itemadapter);
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
