package testandroid.com.br.androidtest.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import testandroid.com.br.androidtest.database.ConnectionFactory;
import testandroid.com.br.androidtest.model.Usuario;
import testandroid.com.br.androidtest.utils.EncryptUtil;
import testandroid.com.br.androidtest.utils.SessionUtil;

public class UsuarioDAO implements IDAO<Usuario> {

    private SQLiteDatabase conn = null;
    private Context context = null;

    public UsuarioDAO(Context context) {
        this.context = context;
    }


    @Override
    public Long create(Usuario p) {

        p.setSenha(EncryptUtil.getEncrypt(p.getSenha()));

        //Sql de Inserção no BD
        StringBuffer sql = new StringBuffer();
        sql.append("insert into USUARIO ");
        sql.append("(login, senha) ");
        sql.append("values ( ?, ?)");

        Long iResult = null;

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        try {
            SQLiteStatement pstm = conn.compileStatement(sql.toString());
            int i = 0;

            pstm.bindString(++i, p.getLogin());
            pstm.bindString(++i, p.getSenha());

            iResult = pstm.executeInsert();

        } catch (Exception ex) {
            Log.i(SessionUtil.LOG, "ERRO AO REALIZAR INSERÇÃO NO BD: " + ex.getMessage());
        }

        conn.close();

        return iResult;
    }


    @Override
    public long update(Usuario p) {

        long retorno = 0;
        p.setSenha(EncryptUtil.getEncrypt(p.getSenha()));

        //Sql de Inserção no BD
        StringBuffer sql = new StringBuffer();
        sql.append("update USUARIO ");
        sql.append("set login = ?, senha = ? ");
        sql.append("where id = ?");

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        try {
            SQLiteStatement pstm = conn.compileStatement(sql.toString());
            int i = 0;
            pstm.bindString(++i, p.getLogin());
            pstm.bindString(++i, p.getSenha());
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
    public void delete(Usuario p) {

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from USUARIO where id = ?");

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

    public void deleteAll() {

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from USUARIO");

            //Verifica se a connection é null
            if (conn == null || !conn.isOpen()) {
                conn = ConnectionFactory.getConnection(context);
            }

            SQLiteStatement stm = conn.compileStatement(sql.toString());

            stm.executeUpdateDelete();

        } catch (Exception e) {
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        conn.close();

    }

    @Override
    public Usuario find(Usuario j) {

        //sql de select para o BD
        StringBuffer sql = new StringBuffer();
        sql.append("select * from USUARIO where id = ?");

        Usuario p = null;

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
                    p = getUsuario(cursor);

                } while (cursor.moveToNext()); //se existir proximo mova para ele
            }

        } catch (Exception e) {
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        conn.close();
        return p;
    }


    public  List<Usuario> findAll() {

        List<Usuario> lista = new ArrayList<>();

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }

        //sql de select para o BD
        StringBuffer sql = new StringBuffer();
        sql.append("select * from USUARIO");

        //Cursor que recebe todas as entidades cadastradas
        Cursor cursor = conn.rawQuery(sql.toString(), null);

        //Existe Dados?
        if (cursor.moveToFirst()) {
            do {

                //recebendo os dados do banco de dados e armazenando do dominio contato
                Usuario p = getUsuario(cursor);

                //add a entidade na lista
                lista.add(p);


            } while (cursor.moveToNext()); //se existir proximo mova para ele
        }

        conn.close();
        return lista;
    }

    public Usuario login(Usuario j) {

        //sql de select para o BD
        StringBuffer sql = new StringBuffer();
        sql.append("select * from USUARIO where login = ? and senha = ?");

        Usuario p = null;

        //abrindo a conexao com o Banco de DAdos
        if (conn == null || !conn.isOpen()) {
            conn = ConnectionFactory.getConnection(context);
        }
        try {
            //Cursor que recebe todos as entidades cadastradas
            String[] arg = {j.getLogin(),j.getSenha()};
            Cursor cursor = conn.rawQuery(sql.toString(), arg);

            //Se houver primeiro mova para ele
            if (cursor.moveToFirst()) {
                do {
                    p = getUsuario(cursor);

                } while (cursor.moveToNext()); //se existir proximo mova para ele
            }

        } catch (Exception e) {
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        conn.close();
        return p;
    }

    private Usuario getUsuario(Cursor cursor){

        Usuario u = new Usuario();
        int i = 0;

        u.setId((int) cursor.getLong(i));
        u.setLogin(cursor.getString(++i));
        u.setSenha(cursor.getString(++i));

        return u;
    }


}
