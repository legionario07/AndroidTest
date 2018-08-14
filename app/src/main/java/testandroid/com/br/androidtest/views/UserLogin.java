package testandroid.com.br.androidtest.views;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import testandroid.com.br.androidtest.R;
import testandroid.com.br.androidtest.dao.HomeDAO;
import testandroid.com.br.androidtest.dao.UsuarioDAO;
import testandroid.com.br.androidtest.database.ScriptCriaHome;
import testandroid.com.br.androidtest.database.ScriptCriaUsuario;
import testandroid.com.br.androidtest.model.Home;
import testandroid.com.br.androidtest.model.Usuario;
import testandroid.com.br.androidtest.utils.EncryptUtil;
import testandroid.com.br.androidtest.utils.SessionUtil;
import testandroid.com.br.androidtest.utils.VerificaConexaoStrategy;


public class UserLogin extends Activity implements android.view.View.OnClickListener {

    private Usuario usuario;
    public static final String NOME_PREFERENCE = "INFORMACOES_LOGIN_AUTOMATICO";
    private ProgressDialog dialog;
    private Handler handler = new Handler();
    private UsuarioDAO usuarioDAO;

    //creating view components
    private EditText inpUsuario;
    private EditText inpSenha;
    private Button btnEntrar;
    private CheckBox chkSenha;

    private String mensagem = null;

    private Boolean usuarioWasFinded = false;
    private Intent intent;
    private boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        //Initializing view components
        inpUsuario = findViewById(R.id.inpUsuario);
        inpSenha =   findViewById(R.id.inpSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        chkSenha = findViewById(R.id.chkSalvarSenha);

        if (savedInstanceState != null) {
            inpUsuario.setText((String) savedInstanceState.get("login"));
            inpSenha.setText((String) savedInstanceState.get("senha"));
        }

        SharedPreferences prefs = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE);
        String login = prefs.getString("login", null);
        String senha = prefs.getString("senha", null);
        isChecked = prefs.getBoolean("checked", false);
        if (login != null && isChecked) {
            inpUsuario.setText(login);
            inpSenha.setText(senha);
            chkSenha.setChecked(isChecked);
        }

        if (!VerificaConexaoStrategy.verificarConexao(this)) {
            Toast.makeText(this, R.string.verifique_internet, Toast.LENGTH_LONG).show();
            finish();
        }


        btnEntrar.setOnClickListener(this);

        usuarioDAO = new UsuarioDAO(UserLogin.this);
        usuarioDAO.deleteAll();
        //Cria usuario para teste
        ScriptCriaUsuario.criarUsuarios(UserLogin.this);
        ScriptCriaHome.criarHome(UserLogin.this);

        NotificationManager notificationManager = (NotificationManager) UserLogin.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("login", inpUsuario.getText().toString());
        outState.putString("senha", inpSenha.getText().toString());

    }

    @Override
    protected void onResume() {
        if (getIntent().getBooleanExtra(getString(R.string.sair), false)) {
            SessionUtil.getInstance().clear();
            finish();
        }
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onClick(android.view.View view) {

        switch (view.getId()) {
            case R.id.btnEntrar:
                login();
                break;
        }

    }

    private void login() {
        usuario = new Usuario();

        dialog = new ProgressDialog(UserLogin.this);
        dialog.setMessage("Processando...");
        dialog.setTitle("Android Test Iniciando...");
        dialog.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    usuario.setLogin(String.valueOf(inpUsuario.getText()));
                    usuario.setSenha(String.valueOf(inpSenha.getText()));

                    intent = new Intent(UserLogin.this, HomeActivity.class);
                    usuarioDAO = new UsuarioDAO(UserLogin.this);

                    usuario.setSenha(EncryptUtil.getEncrypt(usuario.getSenha()));
                    usuario = usuarioDAO.login(usuario);

                    //Did Find?
                    if (usuario == null) {

                        mensagem = "Usuário ou senha inválidos";
                        usuario = new Usuario();


                    } else {
                        mensagem = "Seja bem vindo " + usuario.getLogin();

                        SessionUtil.getInstance().setUsuario(usuario);

                        usuarioWasFinded = true;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(UserLogin.this, mensagem, Toast.LENGTH_LONG).show();

                        }
                    });

                } catch (Exception e) {
                    Log.d(SessionUtil.LOG, e.getMessage());
                } finally {
                    if (usuarioWasFinded) {

                        HomeDAO homeDAO = new HomeDAO(UserLogin.this);
                        List<Home> homes = homeDAO.findAll();
                        SessionUtil.getInstance().setHomes(homes);

                        SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();
                        editor.putString("login", usuario.getLogin());
                        editor.putString("senha", inpSenha.getText().toString());
                        editor.putBoolean("checked", chkSenha.isChecked());
                        editor.commit();

                        startActivity(intent);
                        dialog.dismiss();
                        finish();

                    }
                    dialog.dismiss();
                }
            }
        });
        t.start();
    }



}

