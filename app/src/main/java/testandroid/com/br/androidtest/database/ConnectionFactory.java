package testandroid.com.br.androidtest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import testandroid.com.br.androidtest.utils.SessionUtil;

/**
 * Retonar uma connection válida do BD
 */
public class ConnectionFactory {

    public static SQLiteDatabase getConnection(Context context){
        MyDB myDB = new MyDB(context);
        SQLiteDatabase conn = null;

        try{
            conn = myDB.getWritableDatabase();
        }catch (Exception e){
            Log.i(SessionUtil.LOG, "NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS: "+ e.getMessage());
        }

        return conn;
    }
}
