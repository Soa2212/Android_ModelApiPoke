package com.example.pokemonapirecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pokemonapirecycler.Adapter.PokemonAdapter;
import com.example.pokemonapirecycler.Api.ApiClient;
import com.example.pokemonapirecycler.Api.Pokemon;
import com.example.pokemonapirecycler.Api.PokemonApiService;
import com.example.pokemonapirecycler.Api.PokemonResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Pokemon> pokemons = new ArrayList<>();
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemons, MainActivity.this);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        PokemonApiService apiService = ApiClient.getPokemonApi();
        Call<PokemonResponse> call = apiService.getPokemonList();

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    if (pokemonResponse != null) {
                        List<Pokemon> pokemonList = pokemonResponse.getResults();
                        pokemons.addAll(pokemonList);
                        pokemonAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "Error en la llamada: " + t.getMessage());
            }
        });
    }
}
