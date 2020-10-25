package com.example.libroapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
@RequiresApi(api = Build.VERSION_CODES.O)
public class Registroactivity extends AppCompatActivity {

    private String username = null;
    private String password = null;
    private String email = null;
    private String sexo = null;
    private String nombre = null;
    private String apellido = null;
    private String Fecha = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button boton = (Button) findViewById(R.id.registroUsuarioButton);

        final TextView view = (TextView) findViewById(R.id.editText14);

        final EditText editUser = (EditText) findViewById(R.id.nombreUsuarioTextview);
        final EditText editPass = (EditText) findViewById(R.id.contraseñaTextview);
        final EditText editCorreo = (EditText) findViewById(R.id.correoElectronicoTextview);
        final EditText editNombre = (EditText) findViewById(R.id.nombreTextview);
        final EditText editApellido = (EditText) findViewById(R.id.apellidosTextview);
        final EditText editFechaNacimiento = (EditText) findViewById(R.id.FechaNacimientoTextview);
        final EditText editSexo = (EditText) findViewById(R.id.SexoTextview);




        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editUser.getText().toString();
                password = editPass.getText().toString();
                email = editCorreo.getText().toString();
                sexo = editSexo.getText().toString();
                nombre = editNombre.getText().toString();
                apellido = editApellido.getText().toString();
                Fecha = editFechaNacimiento.getText().toString();

                if(comprobacionUsuarioExistente()==false){
                    crearUsuario();
                    view.setText("Usuario creado exitosamente");
                }else{
                    view.setText("Ya existe un usuario con este username");
                }

            }
        });
    }

    private boolean comprobacionUsuarioExistente(){

        try {
            String sql = "SELECT username FROM login where username ='" + username + "';";
            ConnectionSql con = new ConnectionSql(sql,1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            if (output.next() == false) {
                return false; //El usuario no existe - Se puede crear nueva cuenta con el
            } else {

            }
            con.desconectar();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void crearUsuario(){

        String id;

        try {
            UtilitiesHashSalt uHS = new UtilitiesHashSalt();
            String HashPass = uHS.createHash(password);

            String sql = "INSERT INTO login (username, passwordhash) values ('"+username+ "', '"+HashPass+"');";
            ConnectionSql con = new ConnectionSql(sql,3);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            con.desconectar();

        } catch (InterruptedException | UtilitiesHashSalt.CannotPerformOperationException e) {
            e.printStackTrace();
        }

        try {

            String sql = "SELECT IDLogin FROM login where username ='" + username + "';";
            ConnectionSql con = new ConnectionSql(sql,1);
            Thread threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            ResultSet output = con.getOutput();
            output.next();
            id = "1";
            id = output.getString(1);
            System.out.println(id);
            con.desconectar();

            sql = "INSERT INTO usuario (nombre, apellido, sexo, fecha_nacimiento, email, IDLogin) values ('"+nombre+"','"+apellido+"','"+sexo+"', '"+Fecha+"','"+email+"',"+id+");";
            con = new ConnectionSql(sql,3);
            threadQuery = new Thread(con);
            threadQuery.start();
            threadQuery.join();
            con.desconectar();

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

    }





    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
