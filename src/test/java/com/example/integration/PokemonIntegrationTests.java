package com.example.integration;

import com.example.dto.PokemonDto;
import com.example.dto.ReviewDto;
import com.example.entity.Pokemon;
import com.example.entity.Review;
import com.example.repository.PokemonRepository;
import com.example.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokemonIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();

    }

    private Pokemon pokemon;
   // private PokemonDto pokemonDto;
    private Review review;
   // private ReviewDto reviewDto;

     @BeforeEach
     public void beforeSetUp(){
         baseURL=baseURL + ":" +port + "/api/pokemon/create";
             pokemon = Pokemon.builder()
                     .name("pikachu")
                     .type("electric")
                     .build();
             pokemon= Pokemon.builder()
                     .name("Shad")
                     .type("Boys")

                     .build();

             review = Review.builder()
                     .title("title")
                     .content("content")
                     .stars(5).build();
           /*  reviewDto = ReviewDto.builder()
                     .title("review title")
                     .content("review content")
                     .stars(5).build();*/

             pokemon=pokemonRepository.save(pokemon);
             review=reviewRepository.save(review);


         }
    @AfterEach
    public void afterSetUp() {
        pokemonRepository.deleteAll();
        reviewRepository.deleteAll();
    }
    @Test
    public void PokemonIntegrationTest_CreatePokemon(){

         Pokemon savedPokemon=restTemplate.postForObject(baseURL,pokemon, Pokemon.class);
         assertNotNull(savedPokemon);
         assertThat(savedPokemon.getId()).isNotNull();

    }


    @Test
    public void PokemonIntegrationTest_ShouldFetchPokemonList(){
        List<Pokemon> pokemonList=restTemplate.getForObject(baseURL,List.class);
        assertThat(pokemonList.size()).isEqualTo(2);


    }





}
