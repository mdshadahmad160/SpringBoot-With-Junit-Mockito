package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shad Ahmad
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDto {
    private int id;
    private String name;
    private String type;
}
