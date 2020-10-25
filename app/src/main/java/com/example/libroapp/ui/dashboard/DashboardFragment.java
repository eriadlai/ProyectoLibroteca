package com.example.libroapp.ui.dashboard;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.libroapp.ConnectionSql;
import com.example.libroapp.ItemAdapterColecciones;
import com.example.libroapp.Motor;
import com.example.libroapp.R;
import com.example.libroapp.detailAct;
import com.example.libroapp.librosColecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel dashboardViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_colecciones, container, false);
        final Motor motor = Motor.getInstance();


        final EditText et = (EditText) root.findViewById(R.id.nombreColeccion_editText);
        Button Iniciar = (Button) root.findViewById(R.id.crearColeccion_button);


        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = et.getText().toString();
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

                    sql = "INSERT INTO colecciones (nombre, IDUsuario) values ('" + nombre+ "','"+ IDusuario+"');";
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

        Resources res = getResources();

        String[] Libros;
        final String[] IDcoleccion;
        final String[] autorS;


        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> autor = new ArrayList<>();



        String username = motor.getUsuario();
        String sql = "select c.nombre, c.IDcolecciones from colecciones c, usuario u, login l where u.IDUsuario = c.IDUsuario and l.IDlogin = u.IDlogin and l.username like BINARY '" + username + "';";


        try {
            ConnectionSql con = new ConnectionSql(sql, 1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            while (output.next()) {
                titulos.add(output.getString(1));
                ISBN.add(output.getString(2));
                autor.add("");
            }
            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }


        ListView listview = root.findViewById(R.id.coleccion_listviewD);
        Libros = titulos.toArray(new String[0]);
        IDcoleccion = ISBN.toArray(new String[0]);
        autorS = autor.toArray(new String[0]);


        ItemAdapterColecciones itemAdapterColecciones = new ItemAdapterColecciones(requireActivity(), Libros, IDcoleccion, autorS);
        listview.setAdapter(itemAdapterColecciones);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String IDcol=IDcoleccion[position];
                FragmentManager manager = getFragmentManager();

                librosColecciones nuevo = new librosColecciones(IDcol);

                Bundle Arguments = new Bundle();
                Arguments.putInt("INDEX", position);
                nuevo.setArguments(Arguments);
                manager.beginTransaction().replace(R.id.nav_host_fragment, nuevo).commit();


            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

    }
}
