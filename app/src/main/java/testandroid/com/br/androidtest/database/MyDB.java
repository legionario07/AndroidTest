package testandroid.com.br.androidtest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paulo on 13/08/2018.
 */

public class MyDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";

    private static Integer VERSION_BD = 1;

    public MyDB(Context context){

        super(context, NOME_BANCO,null, VERSION_BD);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDropTable.excluirTabelaUSUARIO());
        db.execSQL(ScriptCreateTable.criarTabelaUSUARIO());
        db.execSQL(ScriptCreateTable.criarTabelaHOME());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScriptDropTable.excluirTabelaUSUARIO());
        db.execSQL(ScriptCreateTable.criarTabelaUSUARIO());
        db.execSQL(ScriptCreateTable.criarTabelaHOME());
    }
}
