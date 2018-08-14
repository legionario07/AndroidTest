package testandroid.com.br.androidtest.database;

import android.util.Log;

import testandroid.com.br.androidtest.utils.SessionUtil;

public class ScriptCreateTable {


    public static String criarTabelaUSUARIO() {


        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE USUARIO( ");
        sql.append("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("login varchar(45) not null unique, ");
        sql.append("senha varchar(45)) ");

        Log.i(SessionUtil.getInstance().LOG, sql.toString());

        return sql.toString();
    }

    public static String criarTabelaHOME() {


        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE HOME( ");
        sql.append("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("idImagem INTEGER not null unique, ");
        sql.append("urlAction varchar(45)) ");

        Log.i(SessionUtil.getInstance().LOG, sql.toString());

        return sql.toString();
    }


}

