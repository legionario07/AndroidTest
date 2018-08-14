package testandroid.com.br.androidtest.database;

import android.content.Context;

import testandroid.com.br.androidtest.R;
import testandroid.com.br.androidtest.dao.HomeDAO;
import testandroid.com.br.androidtest.model.Home;

/**
 * Created by Paulo on 13/08/2018.
 */

public class ScriptCriaHome {

    private static HomeDAO homeDAO;
    public static void criarHome(Context context){

        homeDAO = new HomeDAO(context);

        Home home1 = new Home();
        home1.setIdImagem(R.drawable.ic_public_black_24dp);
        home1.setUrlAction("http://www.google.com.br");

        Home home2 = new Home();
        home2.setIdImagem(R.drawable.ic_notifications_active_black_24dp);
        home2.setUrlAction("Ativar Notificação");

        homeDAO.create(home1);
        homeDAO.create(home2);


    }

}
