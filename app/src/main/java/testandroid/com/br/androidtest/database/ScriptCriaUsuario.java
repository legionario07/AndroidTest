package testandroid.com.br.androidtest.database;

import android.content.Context;

import testandroid.com.br.androidtest.dao.UsuarioDAO;
import testandroid.com.br.androidtest.model.Usuario;

/**
 * Created by Paulo on 13/08/2018.
 */

public class ScriptCriaUsuario {

    private static UsuarioDAO usuarioDAO;
    public static void criarUsuarios(Context context){

        usuarioDAO = new UsuarioDAO(context);

        Usuario u1 = new Usuario("paulo", "123");
        Usuario u2 = new Usuario("joao", "joao");

        usuarioDAO.create(u1);
        usuarioDAO.create(u2);


    }

}
