package testandroid.com.br.androidtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import testandroid.com.br.androidtest.R;
import testandroid.com.br.androidtest.model.Home;

/**
 * Created by Paulo on 13/08/2018.
 */

public class HomeAdapter extends ArrayAdapter<Home> {

    private Context context;
    private List<Home> lista;

    public HomeAdapter(Context context, List<Home> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Home home = new Home();
        home = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_home, null);

        ImageView txtImagem = convertView.findViewById(R.id.img);
        TextView txtAcao = convertView.findViewById(R.id.txtAction);

        txtImagem.setImageResource(home.getIdImagem());
        txtAcao.setText(home.getUrlAction());

        return convertView;


    }
}

