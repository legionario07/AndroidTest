package testandroid.com.br.androidtest.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import testandroid.com.br.androidtest.database.ConnectionFactory;
import testandroid.com.br.androidtest.model.Home;
import testandroid.com.br.androidtest.utils.SessionUtil;

public class HomeDAO implements IDAO<Home> {

    private SQLiteDatabase conn = null;
    private Context context = null;

    public HomeDAO(Context context) {
        this.context = context;
    }


    @Override
    public Long create(Home p) {

        //Sql de Inserção no BD
        StringBuffer sql = new StringBuffer();
        sql.append("insert into HOME ");
        sql.append("(idImagem, urlAction) ");
        sql.append("values ( ?, ?)");

        Long iResult = null;

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        try {
            SQLiteStatement pstm = conn.compileStatement(sql.toString());
            int i = 0;

            pstm.bindLong(++i, p.getIdImagem());
            pstm.bindString(++i, p.getUrlAction());

            iResult = pstm.executeInsert();

        } catch (Exception ex) {
            Log.i(SessionUtil.LOG, "ERRO AO REALIZAR INSERÇÃO NO BD: " + ex.getMessage());
        }

        conn.close();

        return iResult;
    }


    @Override
    public long update(Home p) {

        long retorno = 0;

        //Sql de Inserção no BD
        StringBuffer sql = new StringBuffer();
        sql.append("update HOME ");
        sql.append("set idImagem = ?, urlAction = ? ");
        sql.append("where id = ?");

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        try {
            SQLiteStatement pstm = conn.compileStatement(sql.toString());
            int i = 0;
            pstm.bindLong(++i, p.getIdImagem());
            pstm.bindString(++i, p.getUrlAction());
            pstm.bindLong(++i, p.getId());

            retorno = pstm.executeUpdateDelete();


        } catch (Exception ex) {
            Log.i(SessionUtil.LOG, "ERRO AO REALIZAR UPDATE NO BD: " + ex.getMessage());
            retorno = 0l;
        }

        conn.close();

        return retorno;
    }


    @Override
    public void delete(Home p) {

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from HOME where id = ?");

            //Verifica se a connection é null
            if (conn == null || !conn.isOpen()) {
                conn = ConnectionFactory.getConnection(context);
            }

            SQLiteStatement stm = conn.compileStatement(sql.toString());
            stm.bindLong(1, p.getId());

            stm.executeUpdateDelete();

        } catch (Exception e) {
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        conn.close();

    }

    @Override
    public Home find(Home j) {

        //sql de select para o BD
        StringBuffer sql = new StringBuffer();
        sql.append("select * from HOME where id = ?");

        Home p = null;

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }
        try {
            //Cursor que recebe todos as entidades cadastradas
            String[] arg = {String.valueOf(j.getId())};
            Cursor cursor = conn.rawQuery(sql.toString(), arg);

            //Se houver primeiro mova para ele
            if (cursor.moveToFirst()) {
                do {
                    p = getHome(cursor);

                } while (cursor.moveToNext()); //se existir proximo mova para ele
            }

        } catch (Exception e) {
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        conn.close();
        return p;
    }


    public  List<Home> findAll() {

        List<Home> lista = new ArrayList<>();

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        //sql de select para o BD
        StringBuffer sql = new StringBuffer();
        sql.append("select * from HOME");

        //Cursor que recebe todas as entidades cadastradas
        Cursor cursor = conn.rawQuery(sql.toString(), null);

        //Existe Dados?
        if (cursor.moveToFirst()) {
            do {

                //recebendo os dados do banco de dados e armazenando do dominio contato
                Home p = getHome(cursor);

                //add a entidade na lista
                lista.add(p);


            } while (cursor.moveToNext()); //se existir proximo mova para ele
        }

        conn.close();
        return lista;
    }


    private Home getHome(Cursor cursor){

        Home u = new Home();
        int i = 0;

        u.setId((int) cursor.getLong(i));
        u.setIdImagem(cursor.getInt(++i));
        u.setUrlAction(cursor.getString(++i));

        return u;
    }


}
