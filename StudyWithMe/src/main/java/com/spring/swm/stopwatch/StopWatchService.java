package com.spring.swm.stopwatch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.swm.Const;
import com.spring.swm.stopwatch.model.TimeVO;

@Service
public class StopWatchService {
	@Autowired
	private StopWatchMapper mapper;

	public int insStartTime(TimeVO vo) {
		return mapper.insStartTime(vo);
	}

	public int insEndTime(TimeVO vo) {
		List<TimeVO> dbTime = mapper.selStartTime(vo);
		
		for(TimeVO vo2 : dbTime) {
			vo.setStart_time(vo2.getStart_time());
			
			mapper.delStartTime(vo);
			mapper.insEndTime(vo);
		}
		return Const.TIME_ON;
	}
	
}
