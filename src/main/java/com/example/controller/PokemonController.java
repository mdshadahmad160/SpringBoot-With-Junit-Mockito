package com.example.controller;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shad Ahmad
 */

@RestController
@RequestMapping("/api/")
public class PokemonController {
    @Autowired
    private PokemonService pokemonService;

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<PokemonResponse> getPokemon(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);

    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetails(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));

    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int pokemonId) {
        PokemonDto updatePokemon = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(updatePokemon, HttpStatus.OK);

    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("Pokemon Deleted ", HttpStatus.OK);
    }


}
