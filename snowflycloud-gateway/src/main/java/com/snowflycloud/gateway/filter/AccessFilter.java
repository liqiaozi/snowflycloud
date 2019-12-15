package com.snowflycloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.snowflycloud.gateway.bean.ResultResponse;
import com.snowflycloud.gateway.utils.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
@Component
public class AccessFilter extends ZuulFilter {

    private static final String INVALID_TOKEN = "invalid token";


    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     *
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    @Override
    public boolean shouldFilter() {

        return true;
    }

    /**
     * 过滤逻辑
     *
     * @return 过滤结果
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

//        // 先从cookie 中获取token,cookie中获取失败的话，再从header 中获取，双重校验
//        Cookie tokenCookie = CookieUtil.getCookieByName(request, "token");
//        if (tokenCookie == null || StringUtils.isEmpty(tokenCookie.getValue())) {
//            readTokenFromHeader(currentContext, request);
//        } else {
//            verifyToken(currentContext, request, tokenCookie.getValue());
//        }

        return null;
    }

    public void readTokenFromHeader(RequestContext requestContext, HttpServletRequest request) {
        //从 header 中读取
        String headerToken = request.getHeader("token");
        if (StringUtils.isEmpty(headerToken)) {
            setUnauthorizedResponse(requestContext, INVALID_TOKEN);
        } else {
            verifyToken(requestContext, request, headerToken);
        }

    }

    /**
     * 设置 401 无权限状态
     */
    private void setUnauthorizedResponse(RequestContext requestContext, String msg) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ResultResponse response = new ResultResponse();
        response.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(msg);
        response.setSuccess(false);
        requestContext.setResponseBody(JSONObject.toJSONString(response));
    }

    /**
     * 校验token
     */
    public void verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {

    }
}
