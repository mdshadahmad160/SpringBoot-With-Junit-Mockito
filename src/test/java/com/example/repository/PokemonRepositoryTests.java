package com.example.repository;

import com.example.entity.Pokemon;
import com.example.repository.PokemonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

/**
 * @author Shad Ahmad
 * This Test is responsible to Test Only Repository layer for this respective projects
 */

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_saveAll_ReturnSavedPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").
                build();

        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThanOnePokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);

    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon findPokemon = pokemonRepository.findById(pokemon.getId()).get();
        Assertions.assertThat(findPokemon).isNotNull();

    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon findPokemon = pokemonRepository.findByType(pokemon.getType()).get();
        Assertions.assertThat(findPokemon).isNotNull();

    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        Pokemon savedPokemon = pokemonRepository.findById(pokemon.getId()).get();
        pokemon.setType("Electric");
        pokemon.setName("Raichu");
        Pokemon updatedPokemon = pokemonRepository.save(savedPokemon);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();

    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());
        Assertions.assertThat(pokemonReturn).isEmpty();
    }

}

