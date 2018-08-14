package testandroid.com.br.androidtest.utils;

import java.util.ArrayList;
import java.util.List;

import testandroid.com.br.androidtest.model.Home;
import testandroid.com.br.androidtest.model.Usuario;

/**
 * Created by PauLinHo on 13/08/2017.
 */

public class SessionUtil {

    public static String LOG = "ANDROID_TEST";;
    private static SessionUtil instance = null;
    private Usuario usuario;
    private List<Home> homes;

    private SessionUtil()
    {
        setUsuario(new Usuario());
        homes = new ArrayList<>();
    }

    public static SessionUtil getInstance() {
        if(instance==null) {
            instance = new SessionUtil();
        }

        return instance;
    }

    public static void setInstance(SessionUtil instance) {
        SessionUtil.instance = instance;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void clear(){
        instance = new SessionUtil();
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void setHomes(List<Home> homes) {
        this.homes = homes;
    }
}
