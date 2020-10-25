package com.example.libroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.libroapp.ui.genero;

public class administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        Button autor = (Button) findViewById(R.id.autor_button);
        Button editorial = (Button) findViewById(R.id.administrar_Editorial);
        Button biblioteca = (Button) findViewById(R.id.administar_biblioteca);
        Button Libro = (Button) findViewById(R.id.administrar_libro);
        Button Genero = (Button) findViewById(R.id.genero_button);
        Button Link = (Button) findViewById(R.id.link_button);
        Button BorrarLink = (Button) findViewById(R.id.borrarLink_button);
        autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new administrarAutor()).commit();
            }

        });
        editorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new administradorEditorial()).commit();

            }
        });
        biblioteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new administradorlibro()).commit();
            }
        });
        Libro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new administradorbiblioteca()).commit();
            }
        });
        Genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new genero()).commit();
            }
        });
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new Link()).commit();
            }
        });
        BorrarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentLayout = new FrameLayout(getApplicationContext());
                fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fragmentLayout.setId(R.id.layoutadmin);
                setContentView(fragmentLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.layoutadmin, new borrarLink()).commit();
            }
        });
    }
}

