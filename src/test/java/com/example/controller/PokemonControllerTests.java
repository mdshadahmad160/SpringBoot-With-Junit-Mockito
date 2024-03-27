package com.example.controller;
import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.dto.ReviewDto;
import com.example.entity.Pokemon;
import com.example.entity.Review;
import com.example.service.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Shad Ahmad
 */

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;
    private Pokemon pokemon;
    private Review review;
    private PokemonDto pokemonDto;
    private ReviewDto reviewDto;


    @BeforeEach
    private void init() {
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
                .stars(5).build();
        reviewDto = ReviewDto.builder()
                .title("review title")
                .content("review content")
                .stars(5).build();

    }

    @Test
    public void PokemonController_CreatePokemon_ReturnCreated() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer(
                (invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())))
                .andDo(print());
    }

    @Test
    public void PokemonController_GetAllPokemon_ReturnResponseDto() throws Exception {
        PokemonResponse responseDto=PokemonResponse.builder()
                .pageSize(10)
                .last(true)
                .pageNo(1)
                .content(Arrays.asList(pokemonDto))
                .build();
        when(pokemonService.getAllPokemon(1,10)).thenReturn(responseDto);
        ResultActions actionsResponse=mockMvc.perform(get("/api/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize","10"));


        actionsResponse.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(responseDto.getContent().size())))
                .andDo(print());

    }

    @Test
    public void PokemonController_PokemonDetail_ReturnPokemonDto() throws Exception {
        int pokemonId=1;
        when(pokemonService.getPokemonById(pokemonId)).thenReturn(pokemonDto);

        ResultActions resultResponse=mockMvc.perform(get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));


        resultResponse.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",
                        CoreMatchers.is(pokemonDto.getType())))
                .andDo(print());
    }
    @Test
    public void PokemonController_UpdatePokemon_ReturnPokemonDto() throws Exception{
        int pokemonId=1;
        when(pokemonService.updatePokemon(pokemonDto,pokemonId)).thenReturn(pokemonDto);


        ResultActions resultResponse=mockMvc.perform(put("/api/pokemon/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        resultResponse.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",
                        CoreMatchers.is(pokemonDto.getType())))
                .andDo(print());
    }

    @Test
    public void PokemonController_DeletePokemon_ReturnString() throws Exception
    {


        int pokemonId=1;
        doNothing().when(pokemonService).deletePokemonId(1);

        ResultActions resultResponse=mockMvc.perform(delete("/api/pokemon/1/delete")
                .contentType(MediaType.APPLICATION_JSON));


        resultResponse.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }


}
