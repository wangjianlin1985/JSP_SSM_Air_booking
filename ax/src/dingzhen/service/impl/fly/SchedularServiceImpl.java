package dingzhen.service.impl.fly;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.fly.SchedularDao;
import dingzhen.entity.fly.HB;
import dingzhen.entity.fly.Schedular;
import dingzhen.service.fly.SchedularService;

@Service("schedularService")
public class SchedularServiceImpl extends BaseServiceImpl<Schedular> implements SchedularService{

	@Autowired
	private SchedularDao dao;
	
	public void deleteByFlightAndDate(Schedular schedular) throws Exception {
		dao.deleteByFlightAndDate(schedular);
	}

	public List<Schedular> findGroupList(Schedular schedular) throws Exception {
		return dao.findGroupList(schedular);
	}

	public int countGroupList(Schedular schedular) throws Exception {
		return dao.countGroupList(schedular);
	}

	public List<HB> findHB(Map map) throws Exception {
		return dao.findHB(map);
	}

	public List<Schedular> findSchedularByFlightidAndDate(Map map) {
		return dao.findSchedularByFlightidAndDate(map);
	}

}
