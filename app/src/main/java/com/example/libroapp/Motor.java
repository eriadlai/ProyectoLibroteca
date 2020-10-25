package com.example.libroapp;

import java.sql.Connection;

public class Motor {
        private static Motor instance;

        private Motor() {
                // nothing to do this time
        }

        static {
                instance = new Motor();
        }

        /** Static 'instance' method */
        public static Motor getInstance() {
                return instance;
        }
        /**Instances */
        Connection connection= null;
        String usuario = null;
        String coleccionActiva = null;
        String IDusuario = null;
        /** A simple demo method */

        public Connection getConnection() {
                return connection;
        }

        public void setConnection(Connection connection) {
                this.connection = connection;
        }


        public String getUsuario() {
                return usuario;
        }

        public void setUsuario(String usuario) {
                this.usuario = usuario;
        }

        public String getColeccionActiva() {
                return coleccionActiva;
        }

        public void setColeccionActiva(String coleccionActiva) {
                this.coleccionActiva = coleccionActiva;
        }

        public String getIDusuario() {
                return IDusuario;
        }

        public void setIDusuario(String IDusuario) {
                this.IDusuario = IDusuario;
        }
}
