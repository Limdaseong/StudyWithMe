package com.spring.swm.stopwatch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.swm.stopwatch.model.TimeVO;

@Mapper
public interface StopWatchMapper {
	public int insStartTime(TimeVO vo);
	public int insEndTime(TimeVO vo);

	public int delStartTime(TimeVO vo);
	
	public List<TimeVO> selStartTime(TimeVO vo);


 }
