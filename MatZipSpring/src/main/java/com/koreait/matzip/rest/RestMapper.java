package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Mapper
public interface RestMapper {
	List<RestDMI> selRestList(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	List<RestRecMenuVO> selRestMenus(RestPARAM param);
	int insRest(RestPARAM param);
	int insRestMenus(RestRecMenuVO param);
	int insRestRecMenu(RestRecMenuVO param);
	int selRestChkUser(int param);
	// 성공했는지 안했는지 알기 위해 int형
	
	int updAddHits(RestPARAM param);
	
	RestDMI selRest(RestPARAM param);
	int delRestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
	int delRecMenu(RestPARAM param);
}
