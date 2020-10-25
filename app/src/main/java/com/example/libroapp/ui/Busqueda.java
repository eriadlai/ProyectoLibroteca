package com.example.libroapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.libroapp.R;
import com.example.libroapp.librosColecciones;
import com.example.libroapp.resultadosBusqueda;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Busqueda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Busqueda extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Busqueda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Busqueda.
     */
    // TODO: Rename and change types and number of parameters
    public static Busqueda newInstance(String param1, String param2) {
        Busqueda fragment = new Busqueda();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_busqueda, container, false);

        final EditText busqueda = (EditText) view.findViewById(R.id.busqueda_editText);
        final EditText filtro = (EditText) view.findViewById(R.id.filtro_editText);


        Button buscar=(Button) view.findViewById(R.id.buscar_button);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println(busqueda.getText());

                FragmentManager manager = getFragmentManager();
                resultadosBusqueda nuevo=new resultadosBusqueda(busqueda.getText().toString(),filtro.getText().toString());
                manager.beginTransaction().replace(R.id.nav_host_fragment, nuevo).commit();
            }
        });
        // Inflate the layout for this fragment
        return view;


    }
}
