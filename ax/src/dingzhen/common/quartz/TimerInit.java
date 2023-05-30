package dingzhen.common.quartz;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class TimerInit extends Thread{

	public void run() {
		while(true){
			List<TimerData> tdList = new ArrayList<TimerInit.TimerData>();

			TimerData td = new TimerData();
			td.setId(1);
			td.setCronExpression("0 0/3 * * * ? *");
			td.setName("每三分钟查询一次，取消30分钟未付款的订单");
			td.setService("dingzhen.common.quartz.UpdateOrder");
			td.setCronTime("每三分钟查询一次，取消30分钟未付款的订单");
			tdList.add(td);

		
			
			if(null != tdList && tdList.size() > 0){
				TimerData timerData = null;
				for (int i = 0; i < tdList.size(); i++) {
					timerData = tdList.get(i);
					initTimer(timerData);
				}
			}
			break;
		}
	
	}
	
	
	
	
	
	private void initTimer(TimerData timerData) {
		try {
			Logger.getLogger(TimerInit.class).error("开始加载定时任务："+timerData.getName()+"     "+timerData.getCronExpression() + timerData.getCronTime());
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail("job"+timerData.getId(),"group"+timerData.getId(),Class.forName(timerData.getService())); 
			CronTrigger trigger = new CronTrigger("trigger"+timerData.getId(), "group"+timerData.getId(), "job"+timerData.getId(), "group"+timerData.getId(), timerData.getCronExpression());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	class TimerData{
		private Integer id;
		protected String name; // 名称
		protected String describe; // 描述
		private String service;
		private String cronTime;
		private String cronExpression;
		
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		public String getCronTime() {
			return cronTime;
		}
		public void setCronTime(String cronTime) {
			this.cronTime = cronTime;
		}
		public String getCronExpression() {
			return cronExpression;
		}
		public void setCronExpression(String cronExpression) {
			this.cronExpression = cronExpression;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
	}
	
}
