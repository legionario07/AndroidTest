package testandroid.com.br.androidtest.model;

import java.io.Serializable;

/**
 * Created by Paulo on 13/08/2018.
 */

public class Usuario implements Serializable {

    private Integer id;
    private String login;
    private String senha;

    public Usuario(String login, String senha){
        this.login =login;
        this.senha = senha;
    }

    public Usuario(Integer id){
        this.id = id;
    }

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return login;
    }
}
