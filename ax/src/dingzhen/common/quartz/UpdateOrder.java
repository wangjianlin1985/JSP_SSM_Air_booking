package dingzhen.common.quartz;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dingzhen.entity.fly.Order;
import dingzhen.service.fly.OrderService;

public class UpdateOrder implements ITask{
	
	private static ApplicationContext ac = new ClassPathXmlApplicationContext("spring_config/applicationContext.xml");
	private static OrderService orderService = (OrderService) ac.getBean("orderService");
	private static SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Order order = new Order();
			order.setStatus("0");
			List<Order> orderList = orderService.findList(order);
			for(Order o : orderList){
				long time = sdfAll.parse(o.getTime()).getTime();
				long now = new Date().getTime();
				System.out.println("下单时间：" + time);
				System.out.println("当前时间：" + now);
				System.out.println("时间差：" + (now- time) + "    " + ((now - time) > (30 * 60 * 1000)));
				if((now - time) > (30 * 60 * 1000)){ // 超时30分钟
					orderService.delete(o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
