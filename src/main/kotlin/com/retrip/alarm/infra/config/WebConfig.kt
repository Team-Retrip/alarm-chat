package com.retrip.alarm.infra.config

import com.retrip.alarm.infra.adapter.`in`.presentation.resolver.UserContextArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val userContextArgumentResolver: UserContextArgumentResolver
) : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/swagger-ui.html**")
            .addResourceLocations("classpath:/static/")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userContextArgumentResolver)
    }
}
