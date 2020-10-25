package com.example.libroapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.sql.*;

public class LoginActivity extends AppCompatActivity {
//variables ncesarias qdel login


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Cosas de la view, botones etc
        Button Iniciar = (Button) findViewById(R.id.Iniciar);
        final Button Registrar = (Button) findViewById(R.id.Registrar);

        final EditText editUser, editPass;
        final TextView view;

        editUser = (EditText) findViewById(R.id.usuarioNombre);
        editPass = (EditText) findViewById(R.id.contraseñaUsuario);
        view = (TextView) findViewById(R.id.textViewErrores);


        final UtilitiesHashSalt uHS = new UtilitiesHashSalt();
        final Motor motor = Motor.getInstance();
        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editUser.getText().toString();
                String password = editPass.getText().toString();
                String sql = "SELECT PasswordHash FROM login where username like BINARY '" + username + "';";

                try {
                    ConnectionSql con = new ConnectionSql(sql, 1);
                    Thread threadQuery = new Thread(con);
                    threadQuery.start();
                    threadQuery.join();
                    ResultSet output = con.getOutput();

                    if (output.next() == false) {
                        view.setText("No existe este usuario");
                    } else {
                        if (uHS.verifyPassword(password, output.getString(1))) {
                            motor.setUsuario(username);
                            if (username.equals("ADMINISTRADOR")) {
                                Intent admin = new Intent(getApplicationContext(), administrador.class);
                                startActivity(admin);
                            } else {
                                Intent InicioMain = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(InicioMain);
                            }
                        } else {
                            view.setText("Contraseña incorrecta");
                        }
                    }

                    con.desconectar();
                } catch (SQLException | InterruptedException e) {
                    System.out.println(e);
                } catch (UtilitiesHashSalt.CannotPerformOperationException e) {
                    e.printStackTrace();
                } catch (UtilitiesHashSalt.InvalidHashException e) {
                    e.printStackTrace();
                }

            }
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegistrarActivity = new Intent(getApplicationContext(), Registroactivity.class);
                startActivity(RegistrarActivity);
            }
        });
    }


}
