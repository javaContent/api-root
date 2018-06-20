package com.wd.cloud.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableTurbine
@EnableAdminServer
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
	
	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	http.authorizeRequests().anyRequest().permitAll()  
	                .and().csrf().disable();
		}
	}
	
	/**
	 * SBA通知
	 * @author Administrator
	 *
	 */
	@Configuration
	@EnableScheduling
	public class NotifierConfiguration {
	    @Autowired
	    private Notifier notifier;
	    
	    @Autowired
	    private InstanceRepository instanceRepository;

	    @Bean
	    @Primary
	    public RemindingNotifier remindingNotifier() {
	        RemindingNotifier remindingNotifier = new RemindingNotifier(notifier, instanceRepository);
			//服务下线
	        remindingNotifier.setReminderStatuses(new String[]{"DOWN"});
	        return remindingNotifier;
	    }
	   
	}
}
