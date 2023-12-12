package com.pokemon.partner.pokemon;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PokemonDAO {

	public void insertPokemon(PokemonVO pokemonVO) throws Exception;
	
}
