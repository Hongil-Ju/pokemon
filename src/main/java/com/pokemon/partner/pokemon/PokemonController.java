package com.pokemon.partner.pokemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pokemon/*")
public class PokemonController {
	
	@Autowired
	private PokemonService pokemonService;
	
	@GetMapping("list") 
	public String pokemonList(PokemonVO pokemonVO) throws Exception{
		
		// 1. URL을 만들기 위한 StringBuilder.
        StringBuilder urlBuilder = new StringBuilder("https://pokeapi.co/api/v2/pokemon/1"); /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET. 
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());
        
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(sb.toString());
        JSONObject jsonObj = (JSONObject) obj;      
        
        
        
        // 도감 번호 
        pokemonVO.setId((Long)jsonObj.get("id"));
        
        // 영어 이름
        pokemonVO.setEngName((String)jsonObj.get("name"));
        
        JSONArray typeArr = (JSONArray)jsonObj.get("types");
        // 1 타입 
        pokemonVO.setType1((String)((JSONObject)((JSONObject)typeArr.get(0)).get("type")).get("name"));
        // 2 타입 
        pokemonVO.setType2((String)((JSONObject)((JSONObject)typeArr.get(1)).get("type")).get("name"));
		
        // 키, 몸무게 (100g 단위)
        pokemonVO.setHeight((Long)jsonObj.get("height"));
        pokemonVO.setWeight((Long)jsonObj.get("weight"));
        
        // 능력치(HABCDS)
        JSONArray statsArr = (JSONArray)jsonObj.get("stats");
        Long h = (Long)((JSONObject)statsArr.get(0)).get("base_stat");
        Long a = (Long)((JSONObject)statsArr.get(1)).get("base_stat");
        Long b = (Long)((JSONObject)statsArr.get(2)).get("base_stat");
        Long c = (Long)((JSONObject)statsArr.get(3)).get("base_stat");
        Long d = (Long)((JSONObject)statsArr.get(4)).get("base_stat");
        Long s = (Long)((JSONObject)statsArr.get(5)).get("base_stat");
        Long total = h+a+b+c+d+s;
        pokemonVO.setHp(h);
        pokemonVO.setAttack(a);
        pokemonVO.setDefense(b);
        pokemonVO.setSAttack(c);
        pokemonVO.setSDefense(d);
        pokemonVO.setSpeed(s);
        pokemonVO.setTotal(total);
        
        // 이미지
        pokemonVO.setImg((String)((JSONObject)jsonObj.get("sprites")).get("front_default"));
        pokemonVO.setSImg((String)((JSONObject)jsonObj.get("sprites")).get("front_shiny"));
        System.out.println(pokemonVO.toString());
        
        pokemonService.insertPokemon(pokemonVO);
        
		return "pokemon/list";
	}
	
}
