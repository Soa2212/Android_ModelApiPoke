package com.example.pokemonapirecycler.Api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public static PokemonApiService getPokemonApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonApiService.class);
    }
}
