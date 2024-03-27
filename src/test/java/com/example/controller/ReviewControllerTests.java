package com.example.controller;

import com.example.dto.PokemonDto;
import com.example.dto.ReviewDto;
import com.example.entity.Pokemon;
import com.example.entity.Review;
import com.example.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Shad Ahmad
 */

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

     @BeforeEach
    public void init(){
         pokemon=Pokemon.builder()
                 .name("pikachu")
                 .type("electric")
                 .build();
         pokemonDto=PokemonDto.builder()
                 .name("pikachu")
                 .type("electric")
                 .build();
         review=Review.builder()
                 .title("title")
                 .content("content")
                 .stars(5)
                 .build();
         reviewDto=ReviewDto.builder()
                 .title("review title")
                 .content("test content")
                 .stars(5)
                 .build();


     }

     @Test
    public void ReviewController_GetReviewsByPokemonId_ReturnReviewDto() throws Exception{
         int pokemonId=1;
         when(reviewService.getReviewsByPokemonId(pokemonId))
                 .thenReturn(Arrays.asList(reviewDto));

         ResultActions resultResponse=mockMvc.perform(get("/api/pokemon/1/reviews")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(pokemonDto)));

         resultResponse.andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                         CoreMatchers.is(Arrays.asList(reviewDto).size())))
                 .andDo(print());
     }

     @Test
    public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
         int pokemonId=1;
         int reviewId=1;


         when(reviewService.updateReview(pokemonId,reviewId,reviewDto)).thenReturn(reviewDto);

         ResultActions response=mockMvc.perform(put("/api/pokemon/1/reviews/1")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(reviewDto)));

         response.andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())))
                 .andDo(print());


     }

     @Test
    public void ReviewController_CreateReview_ReturnReviewDto() throws Exception{
         int pokemonId=1;
         when(reviewService.createReview(pokemonId,reviewDto)).thenReturn(reviewDto);

         ResultActions response=mockMvc.perform(post("/api/pokemon/1/reviews")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(reviewDto)));

         response.andExpect(MockMvcResultMatchers.status().isCreated())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                         CoreMatchers.is(reviewDto.getTitle())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.content",
                         CoreMatchers.is(reviewDto.getContent())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.stars",
                         CoreMatchers.is(reviewDto.getStars())))
                 .andDo(print());

     }

     @Test
    public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {

         int pokemonId=1;

         int reviewId=1;

         when(reviewService.getReviewById(reviewId,pokemonId)).thenReturn(reviewDto);


         ResultActions response=mockMvc.perform(get("/api/pokemon/1/reviews/1")
                 .contentType(MediaType.APPLICATION_JSON));

         response.andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                         CoreMatchers.is(reviewDto.getTitle())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.content",
                         CoreMatchers.is(reviewDto.getContent())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.stars",
                         CoreMatchers.is(reviewDto.getStars())))
                 .andDo(print());
     }

     @Test
    public void ReviewController_DeleteReview_ReturnOk() throws Exception{
         int pokemonId=1;
         int reviewId=1;

         doNothing().when(reviewService).deleteReview(pokemonId,reviewId);
         ResultActions response=mockMvc.perform(delete("/api/pokemon/1/reviews/1")
                 .contentType(MediaType.APPLICATION_JSON));
         response.andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(print());
     }






}
