package com.example.pokemonapirecycler.Api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApiService {
    @GET("pokemon?limit=100&offset=0")
    Call<PokemonResponse> getPokemonList();

    @GET("pokemon/{id}/")
    Call<PokemonResponse> getPokemonDetails(@Path("id") String id);
}
