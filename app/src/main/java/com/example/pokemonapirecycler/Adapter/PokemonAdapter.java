package com.example.pokemonapirecycler.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapirecycler.Api.ApiClient;
import com.example.pokemonapirecycler.Api.Pokemon;
import com.example.pokemonapirecycler.Api.PokemonApiService;
import com.example.pokemonapirecycler.Api.PokemonDetails;
import com.example.pokemonapirecycler.Api.PokemonResponse;
import com.example.pokemonapirecycler.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    List<Pokemon> pokemons;
    private Activity activity;

    public PokemonAdapter(List<Pokemon> pokemons, Activity activity) {
        this.pokemons = pokemons;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View listitem = lf.inflate(R.layout.pokemons_item, parent, false);
        return new ViewHolder(listitem);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {
        Pokemon g = pokemons.get(position);
        holder.setData(g);
        holder.setPokemonTypeColor(g.getId());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vwnombrebtn;
        ImageView imagenPokemon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vwnombrebtn = itemView.findViewById(R.id.vwnombrebtn);
            imagenPokemon = itemView.findViewById(R.id.ImagenPokemon);

        }

        public void setData(Pokemon g) {
            vwnombrebtn.setText(g.getName());
            String id = g.getId();
            Glide.with(itemView.getContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/"+id+".png").into(imagenPokemon);
        }

        public void setPokemonTypeColor(String id) {
            PokemonApiService apiService = ApiClient.getPokemonApi();
            Call<PokemonResponse> detailsCall = apiService.getPokemonDetails(id);

            detailsCall.enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                    if (response.isSuccessful()) {
                        PokemonResponse pokemonDetails = response.body();
                        if (pokemonDetails != null) {
                            String tipo = PokemonDetails.getFirstType();
                            if (tipo != null) {
                                setItemViewColor(tipo);
                            }
                        }
                    } else {
                        Log.e(TAG, "Error en la respuesta de detalles del Pokémon: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<PokemonResponse> call, Throwable t) {
                    Log.e(TAG, "Error en la llamada de detalles del Pokémon: " + t.getMessage());

                }
            });
        }

        private void setItemViewColor(String tipo) {
            int color = getColorForPokemonType(tipo);
            itemView.setBackgroundColor(color);
        }

        private int getColorForPokemonType(String tipo) {
            // Aquí puedes definir colores para cada tipo de Pokémon
            // Este es un ejemplo simple, ajusta según tus necesidades
            switch (tipo) {
                case "grass":
                    return Color.GREEN;
                case "fire":
                    return Color.RED;
                // Añade más casos según los tipos que necesites manejar
                default:
                    return Color.GRAY; // Color por defecto
            }
        }
    }
}
