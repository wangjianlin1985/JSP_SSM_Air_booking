package dingzhen.service.fly;

import java.util.List;
import java.util.Map;

import dingzhen.common.base.BaseService;
import dingzhen.entity.fly.HB;
import dingzhen.entity.fly.Schedular;

public interface SchedularService extends BaseService<Schedular>{
	public void deleteByFlightAndDate(Schedular schedular) throws Exception;
	public List<Schedular> findGroupList(Schedular schedular) throws Exception;
	public int countGroupList(Schedular schedular) throws Exception;
	public List<HB> findHB(Map map) throws Exception;
	public List<Schedular> findSchedularByFlightidAndDate(Map map);
}
