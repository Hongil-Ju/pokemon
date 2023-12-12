package com.pokemon.partner.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {
	
	@Autowired
	private PokemonDAO pokemonDAO;

	public void insertPokemon(PokemonVO pokemonVO) throws Exception {
		pokemonDAO.insertPokemon(pokemonVO);	
	}

}
