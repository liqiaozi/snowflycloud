package com.snowflycloud.gateway.config;

import com.google.common.collect.Lists;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @ClassName ApiDocumentationConfig
 * @Description TODO
 * @Author 25823
 * @Date 2019/10/13 23:32
 * @Version 1.0
 **/
@Primary
@Component
public class ApiDocumentationConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    public ApiDocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {

        List<SwaggerResource> swaggerResourceList = Lists.newArrayList();
        List<Route> routes = routeLocator.getRoutes();

        ArrayList<Route> routeList = routes.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(Route::getId))), ArrayList::new));

        routeList.removeIf(route -> route.getId().contains("consul"));

        routeList.forEach(route -> swaggerResourceList.add(
                swaggerResource(route.getId(), route.getFullPath().replace("/**", "/v2/api-docs"), "v2")
        ));
        return swaggerResourceList;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
