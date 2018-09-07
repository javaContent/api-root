package com.wd.cloud.reportanalysis.util;

import javax.annotation.PostConstruct;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class WebserviceClient {
	
	private Client client;
	
	@PostConstruct 
	public void init(){
		 JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//		 client = dcf.createClient("http://v4-test.api.hnlat.com/yunscholar/api/instAchievement?wsdl");
//	     client = dcf.createClient("http://v3-test.api.hnlat.com/api/instAchievement?wsdl");
		 client = dcf.createClient("http://localhost:8080/api/instAchievement?wsdl");
	}

	public Client getTransportClient() {
		return client;
	}

}
