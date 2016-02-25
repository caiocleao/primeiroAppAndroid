package caiocleaoca.in_loco_media_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caioc_000 on 23/02/2016.
 */
public class CitiesActivity extends FragmentActivity {

    ListView listcities = null;


    @Override
    public void onCreate ( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);

        // Cria o botao para voltar e pega os enderecos de MapsActivity
        Button voltar = ( Button ) findViewById(R.id.voltar);
        final Context context = this;
        Intent intent = getIntent();
        String[] test = intent.getExtras().getStringArray("endereco");
        Toast.makeText(CitiesActivity.this, "Local: " + test[0], Toast.LENGTH_SHORT).show();

        // Inicializa listview e cosntroi.
        listcities = ( ListView )findViewById(R.id.listacities);
        List<String> citiesAdapter = new ArrayList<String>();

        citiesAdapter.add(test[0]);

        ListAdapter adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, citiesAdapter );

        listcities.setAdapter(adapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                startActivity(intent);

            }
        });

    }



}
