package com.wd.cloud.searchserver.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.sniff.Sniffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * es RestClient配置
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.rest.client")
public class RestClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

    private String user;

    private String passwd;

    private String clusterNodes;

    private Sniffer sniffer;

    private RestClient restClient;

    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    @Bean
    public Sniffer getSniffer() {
        return sniffer;
    }

    public void setSniffer(Sniffer sniffer) {
        this.sniffer = sniffer;
    }

    @Bean
    public RestClient getRestClient() {
        return buildClient();
    }

    protected RestClient buildClient() {
        if (!"".equals(clusterNodes)) {
            String[] nodes = clusterNodes.split(",");
            HttpHost[] httpHosts = new HttpHost[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
                String InetSocket[] = nodes[i].split(":");
                String Address = InetSocket[0];
                Integer port = Integer.valueOf(InetSocket[1]);
                httpHosts[i] = new HttpHost(Address, port, "http");
            }
            RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
            //restClient = restClientBuilder.build();
            restClient = buildAuthentication(restClientBuilder).build();
            sniffer = Sniffer.builder(restClient).build();
        }
        return restClient;
    }

    /**
     * 添加认证
     *
     * @param restClientBuilder
     * @return
     */
    protected RestClientBuilder buildAuthentication(RestClientBuilder restClientBuilder) {
        if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(passwd)) {
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, passwd));
            restClientBuilder.setHttpClientConfigCallback(
                    new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching(); //Disable preemptive authentication
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    }).build();
        }
        return restClientBuilder;
    }

}