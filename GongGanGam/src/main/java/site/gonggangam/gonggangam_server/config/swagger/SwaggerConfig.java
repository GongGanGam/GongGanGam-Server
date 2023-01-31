package site.gonggangam.gonggangam_server.config.swagger;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.auth.JwtProvider;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ResponseDto;
import site.gonggangam.gonggangam_server.service.dto.SuccessResponseDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    public static final String TITLE = "GongGanGam API";

    private static final String TEMP_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGlmaWNhdGlvbiI6IjIzNTQ2NDg4MjQiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiZ29uZ2dhbmdhbSIsImlkIjoxLCJleHAiOjE3MDY1OTM4MDgsImlhdCI6MTY3NTA1NzgwOH0.jW_oJxRo02_rqRzkonUzGk6nWFpZNV0HFlnOOxPWAIc";
    public static final String DESCRIPTION = String.format("GongGanGam API 명세서입니다. refreshToken은 현재 아무거나 입력하시면 됩니다. 테스트용 계정 (userId = 1) accessToken: %s", TEMP_ACCESS_TOKEN);
    public static final String VERSION = "0.3.2";

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
        Info info = new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION);

        return new OpenAPI()
                .info(info)
                .components(getResponseComponents());
    }

    @SuppressWarnings("rawtypes")
    private Components getResponseComponents() {
        Components components = new Components();
        Schema errorEntitySchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponseDto.class)).schema;

        for (ResponseCode code : ResponseCode.values()) {
            if (code.getCode() < 0) {
                components.addResponses(code.name(), convertErrorResponse(errorEntitySchema, code));
            }
        }

        return components;
    }

    @SuppressWarnings("rawtypes")
    private ApiResponse convertErrorResponse(Schema schema, ResponseCode code) {
        return new ApiResponse()
                .content(
                    new Content().addMediaType(
                        APPLICATION_JSON_VALUE,
                        new MediaType()
                                .schema(schema.description(code.getMessage()))
                                .addExamples(code.name(), new Example().value(ErrorResponseDto.of(code)))
                )).description(code.getMessage())
                ;
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
