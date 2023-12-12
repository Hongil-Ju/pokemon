package com.pokemon.partner.pokemon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PokemonVO {
	
	private long id;
	private String engName;
	private String korName;
	private String type1;
	private String type2;
	private long height;
	private long weight;
	private long hp;
	private long attack;
	private long defense;
	private long sAttack;
	private long sDefense;
	private long speed;
	private long total;
	private String img;
	private String sImg;

}
