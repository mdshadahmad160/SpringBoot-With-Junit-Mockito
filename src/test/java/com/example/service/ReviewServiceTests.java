package com.example.service;

import com.example.dto.PokemonDto;
import com.example.dto.ReviewDto;
import com.example.entity.Pokemon;
import com.example.entity.Review;
import com.example.repository.PokemonRepository;
import com.example.repository.ReviewRepository;
import com.example.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.when;

/**
 * @author Shad Ahmad
 */

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;


    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonDto = PokemonDto.builder()
                .name("pikachu")
                .type("electric")
                .build();
        review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewDto = ReviewDto.builder()
                .title("review title")
                .content(" test content")
                .stars(5)
                .build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnReviewDto() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);
        Assertions.assertThat(savedReview).isNotNull();

    }
}
