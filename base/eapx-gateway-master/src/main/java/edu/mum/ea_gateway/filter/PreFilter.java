package edu.mum.ea_gateway.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import edu.mum.ea_gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.naming.spi.DirStateFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PreFilter extends ZuulFilter {
    @Autowired
    private AuthService authService;

    @Value("#{'${excludeUrls}'.split(',')}")
    private List<String> excludeUrls;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println(request.getRequestURL());
        for (String url : excludeUrls){
            if (request.getRequestURL().toString().contains(url)){
                return null;
            }
        }

        String header =  request.getHeader("Authorization");
        if (header != null){
            if (authService.verify(header)){
                return null;
            }
        }

        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ctx.getResponse().setCharacterEncoding("utf-8");
        ctx.getResponse().setContentType("application/json; charset=utf-8");
        try {
            PrintWriter writer = ctx.getResponse().getWriter();
            Map<String, String> map = new HashMap<>();
            map.put("status", "have no auth to visit this api!");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(map));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
