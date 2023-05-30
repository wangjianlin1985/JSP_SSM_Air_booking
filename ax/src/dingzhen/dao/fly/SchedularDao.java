package dingzhen.dao.fly;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.fly.HB;
import dingzhen.entity.fly.Schedular;

@Repository
public interface SchedularDao extends BaseDao<Schedular>{
	public void deleteByFlightAndDate(Schedular schedular) throws Exception;
	public List<Schedular> findGroupList(Schedular schedular) throws Exception;
	public int countGroupList(Schedular schedular) throws Exception;
	public List<HB> findHB(Map map) throws Exception;
	public List<Schedular> findSchedularByFlightidAndDate(Map map);
}
