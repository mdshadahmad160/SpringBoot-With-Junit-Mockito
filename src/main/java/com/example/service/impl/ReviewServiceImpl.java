package com.example.service.impl;

import com.example.dto.ReviewDto;
import com.example.entity.Pokemon;
import com.example.entity.Review;
import com.example.exception.PokemonNotFoundException;
import com.example.exception.ReviewNotFoundException;
import com.example.repository.PokemonRepository;
import com.example.repository.ReviewRepository;
import com.example.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new PokemonNotFoundException("Pokemon with associated review not found")
        );
        review.setPokemon(pokemon);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new PokemonNotFoundException("Pokemon with associated review not found: ")
        );
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review with associated pokemon not found: ")
        );
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belongs to a pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new PokemonNotFoundException("Pokemon with associated review not found:")
        );
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review with associated pokemon not found:")
        );
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belongs to a pokemon: ");
        }
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        Review updateReview = reviewRepository.save(review);
        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new PokemonNotFoundException("Pokemon with associated review not found: ")
        );
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("This review does not belongs to a pokemon")
        );
        reviewRepository.delete(review);

    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
