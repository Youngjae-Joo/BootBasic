package com.simple.basic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.basic.command.SimpleVO;


@RestController //Controller + ResponseBody
public class RestBasicController {
	
	
	//ResponseBody는 return이 view Resolver가 아닌 요청이 들어온 곳으로 되돌아간다.
	
	//@Value("${값}")
	//String Server;
	
	@GetMapping(value="/getText", produces="text/plain")
	public String getText() {
		return "hello world";
	}
	
	//객체를 담게 되면 application/json 형식으로 반환하게 된다.
	/*
	 * produces - 보내는 데이터에 대한 타입
	 * consumes - 받는 데이터에 대한 타입
	 */
	@GetMapping(value="/getObject", produces="application/json")
	public SimpleVO getObject() {
		SimpleVO vo = new SimpleVO(1,"aaa123", "홍길동");
		return vo;
	}
	
	//맵형식의 반환
	@GetMapping("/getObject2")
	public Map<String, Object>getObject2(){
		Map<String, Object> map=new HashMap<>();
		SimpleVO vo = new SimpleVO(1,"aaa123", "홍길동");
		map.put("total", 100);
		map.put("data", vo);
		
		return map;
	}
	
	//리스트 형식의 반환
	@GetMapping("/getObject3")
	public List<SimpleVO> getObject3(){
		List<SimpleVO> list = new ArrayList<>();
		
		for(int i=1;i<=10;i++) {
			list.add(new SimpleVO(i,"aaa123"+i,"홍길동"+i));
		}
		return list;
	}
	
	//get형식 값을 받는방법1 - 쿼리스트링 ?키=값
	//http://localhost:8383/getKey?id=aaaa&name=홍길동
	@GetMapping("/getKey")
	public String getKey(@RequestParam("id")String id,@RequestParam("name")String name) {
		System.out.println(id);
		System.out.println(name);
		
		return "success";
	}
	
	//get형식 값을 받는방법2 - 쿼리파람 URL/키/키
	//http://localhost:8383/getPath/솔트/에이피아이키
	@GetMapping("getPath/{sort}/{apiKey}")
	public String getPath(@PathVariable("sort") String sort, @PathVariable("apiKey") String apiKey) {
		System.out.println(sort);
		System.out.println(apiKey);
		return "seccess";
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////post형식의 처리
	
	//값을 받는 방법1 - post형은 VO로 맵핑
	//JSON형식의 데이터를 자바의 객체로 맵핑 ->@RequestBody
	//{"num":30,"id":"kier","name":"키에르"}
	
	//cors-기본적으로 서버가 다르면 요청을 거절하는데(특정 서버의 요청에 대하여 허용)
	//@CrossOrigin(*)을 하면 다 가능
	 @CrossOrigin("http://localhost:3000")
	 @PostMapping("/getJson")
	 public String getJson(@RequestBody SimpleVO vo) {
		 System.out.println(vo.toString());
		 return "success"; 
	 }
	 

	
	//값을 받는 방법2 -Map으로 맵핑
	@PostMapping("/getMap")
	public String getMap(@RequestBody Map<String, Object> map) {
		System.out.println(map.toString());
		
		return "success";
	}
	
	//consumer를 통한 데이터제한
	//consumes = "application/json" => 상대측에서 꼭 json형식으로 보내야 함
	//consumes = "text/plain" 로 하면 String으로 받아야 함, 보낼 때는 TEXT문서로(보내는 데이터의 타입을 text로 맞춘다는 뜻).
	//->HEADERS에선 Name칸에 Content-Type, Value칸에 text/plain으로. 이걸 명시하면 어떤 문서든간에 text형식이 된다.
	//consumes는 특정타입의 데이터를 받도록 처리하는 옵션(기본값 json)
	//클라이언트에는 Content-type을 이용해서 보내는 데이터에 대한 타입을 명시(반드시 필수)
	@PostMapping(value="/getResult",consumes = "text/plain")
	public String getResult(@RequestBody String data) {
		System.out.println(data);
		return "success";
	}
	
	//응답문서의 형태를 직접선언 - ResponseEntity
	@PostMapping("/createRes")
	public ResponseEntity createRes() {
		SimpleVO vo = new SimpleVO(4,"daei12","데이");
		HttpHeaders header = new HttpHeaders();//헤더정보
		header.add("Authorization", "token");
		HttpStatus status=HttpStatus.ACCEPTED;//상태코드(성공 or 실패)
		
		//제네릭은 데이터를 따라갑니다.
		ResponseEntity<SimpleVO> entity=new ResponseEntity<>(vo, header, status);
		return entity;
	}
	

}