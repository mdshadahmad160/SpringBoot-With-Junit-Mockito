package com.example.repository;

import com.example.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon,Integer> {

    Optional<Pokemon> findByType(String type);
}
