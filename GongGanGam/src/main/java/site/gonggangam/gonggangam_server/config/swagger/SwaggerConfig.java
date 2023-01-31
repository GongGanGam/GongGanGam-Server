package site.gonggangam.gonggangam_server.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.gonggangam.gonggangam_server.config.auth.JwtProvider;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    public static final String TITLE = "GongGanGam API";

    private static final String TEMP_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGlmaWNhdGlvbiI6IjIzNTQ2NDg4MjQiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiZ29uZ2dhbmdhbSIsImlkIjoxLCJleHAiOjE3MDY1OTM4MDgsImlhdCI6MTY3NTA1NzgwOH0.jW_oJxRo02_rqRzkonUzGk6nWFpZNV0HFlnOOxPWAIc";
    public static final String DESCRIPTION = String.format("GongGanGam API 명세서입니다. refreshToken은 현재 아무거나 입력하시면 됩니다. 테스트용 계정 (userId = 1) accessToken: %s", TEMP_ACCESS_TOKEN);
    public static final String VERSION = "0.3.1";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/api/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    @Bean
    public OpenAPI springOpenAPI() {

        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION));
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .name(JwtProvider.ACCESS_TOKEN_HEADER)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);

        return OpenApi -> OpenApi
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(JwtProvider.ACCESS_TOKEN_HEADER)
                )
                .getComponents()
                    .addSecuritySchemes(JwtProvider.ACCESS_TOKEN_HEADER, accessTokenScheme);
    }

}