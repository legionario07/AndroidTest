package testandroid.com.br.androidtest.database;

import android.util.Log;

import testandroid.com.br.androidtest.utils.SessionUtil;

public class ScriptDropTable {


    public static String excluirTabelaUSUARIO() {

        StringBuffer sql = new StringBuffer();
        sql.append("DROP TABLE IF EXISTS USUARIO");

        Log.i(SessionUtil.LOG, sql.toString());

        return sql.toString();
    }

}


