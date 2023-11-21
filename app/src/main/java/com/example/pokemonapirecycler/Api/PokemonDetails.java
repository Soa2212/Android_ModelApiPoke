package com.example.pokemonapirecycler.Api;

import java.util.List;

public class PokemonDetails {
    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public static class Type {
        private TypeData type;

        public TypeData getType() {
            return type;
        }
    }

    public static class TypeData {
        private String name;

        public String getName() {
            return name;
        }
    }

    public String getFirstType() {
        if (types != null && !types.isEmpty()) {
            return types.get(0).getType().getName();
        }
        return null;
    }
}
