package com.example.service;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo,int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto,int id);
    void  deletePokemonId(int id);





}
