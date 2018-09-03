package com.wd.cloud.reportanalysis.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * es TransportClient配置
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@Component
@Configuration
public class EsClientConfig implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(EsClientConfig.class);

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes ;
	
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	
	@Value("${spring.data.elasticsearch.client-transport-sniff}")
	private String clientTransportSniff;
	
	private TransportClient client;
	
	@Override
	public void destroy() throws Exception {
		try {
			logger.info("Closing elasticSearch client");
			if (client != null) {
				client.close();
			}
		} catch (final Exception e) {
			logger.error("Error closing ElasticSearch client: ", e);
		}
	}
	
	@Override
	public TransportClient getObject() throws Exception {
		return client;
	}
	
	@Override
	public Class<?> getObjectType() {
		return TransportClient.class;
	}
	
	@Override
	public boolean isSingleton() {
		return false;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		buildClient();
	}
	
	protected void buildClient()  {
		try {
			PreBuiltTransportClient  preBuiltTransportClient = new PreBuiltTransportClient(settings());
			if (!"".equals(clusterNodes)){
				for (String nodes:clusterNodes.split(",")) {
					String InetSocket [] = nodes.split(":");
					String  Address = InetSocket[0];
					Integer  port = Integer.valueOf(InetSocket[1]);
					preBuiltTransportClient.addTransportAddress(new
						InetSocketTransportAddress(InetAddress.getByName(Address),port ));
				}
				client = preBuiltTransportClient;
			}
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 初始化默认的client
	 */
	private Settings settings(){
		Settings settings = Settings.builder()
			.put("cluster.name",clusterName)
			.put("client.transport.sniff",clientTransportSniff)
			.build();
		return settings;
	}
}
