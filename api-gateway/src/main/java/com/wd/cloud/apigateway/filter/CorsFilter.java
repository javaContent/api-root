package com.wd.cloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public class CorsFilter extends ZuulFilter {
    /**
     * 前置过滤器。
     * 在 zuul 中定义了四种不同生命周期的过滤器类型：
     * 1、pre：可以在请求被路由之前调用；
     * 2、route：在路由请求时候被调用；
     * 3、post：在route和error过滤器之后被调用；
     * 4、error：处理请求时发生错误时被调用；
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的优先级，数字越大，优先级越低。
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 是否执行该过滤器。
     * true：说明需要过滤；
     * false：说明不要过滤；
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {

        return true;
    }

    /**
     * 过滤器的具体逻辑。
     *
     * @return
     */
    @Override
    public Object run() throws ZuulException {
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        return null;
    }
}
