package testandroid.com.br.androidtest.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import testandroid.com.br.androidtest.R;
import testandroid.com.br.androidtest.adapters.HomeAdapter;
import testandroid.com.br.androidtest.model.Home;
import testandroid.com.br.androidtest.utils.SessionUtil;
import testandroid.com.br.androidtest.utils.VerificaConexaoStrategy;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lstHome;
    private WebView wvHome;
    private HomeAdapter homeAdapter;
    private List<Home> listaHome;
    private Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView txtUsuarioLogado = headerView.findViewById(R.id.txtUsuarioLogado);
        txtUsuarioLogado.setText(SessionUtil.getInstance().getUsuario().getLogin());
        lstHome = findViewById(R.id.lstHomes);
        wvHome = findViewById(R.id.vwHome);

//


        listaHome = SessionUtil.getInstance().getHomes();
        atualizarView();

        lstHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                home = (Home) lstHome.getItemAtPosition(position);
                if (home.getIdImagem() == R.drawable.ic_public_black_24dp) {
                    //Tem Internet?
                    if (!VerificaConexaoStrategy.verificarConexao(HomeActivity.this)) {
                        Toast.makeText(HomeActivity.this, "Necessário Conexão com a Internet para prosseguir!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    construirWebView();
                } else {
                    construirNotificacao();
                }
            }
        });

    }

    private void construirNotificacao() {


        Intent intent = new Intent("AVISO_TESTE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pendingIntent);


    }

    private void construirWebView() {

        wvHome.loadUrl(home.getUrlAction());
        WebSettings webSettings = wvHome.getSettings();
        webSettings.setSupportZoom(false);

        wvHome.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               return false;
            }
        });


    }

    private void atualizarView() {

        if (homeAdapter == null) {
            homeAdapter = new HomeAdapter(this, listaHome);
            lstHome.setAdapter(homeAdapter);
        } else {
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exit) {
            SessionUtil.getInstance().clear();
            Intent i = new Intent(HomeActivity.this, UserLogin.class);
            startActivity(i);
        }
        // Handle the camera action

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
