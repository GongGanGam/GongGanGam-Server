package site.gonggangam.gonggangam_server.config.swagger;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ResponseDto;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class OperationCustomizerWithEnums implements OperationCustomizer {

    @SuppressWarnings("rawtypes")
    private final Schema errorEntitySchema = ModelConverters.getInstance()
            .readAllAsResolvedSchema(ErrorResponseDto.class).schema;


    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses apiResponses = operation.getResponses();
        apiResponses.clear();
        Type dtoType = handlerMethod.getReturnType().getGenericParameterType();

        ApiResponseCodes responseCodes = handlerMethod.getMethodAnnotation(ApiResponseCodes.class);
        if (responseCodes != null) {
            Arrays.stream(responseCodes.value()).forEach(
                    code -> putApiResponseCode(apiResponses, code.value(), dtoType)
            );
        }

        return operation;
    }

    private void putApiResponseCode(ApiResponses apiResponses, ResponseCode code, Type dtoType) {
        if (code.getCode() < 0) {
            apiResponses.put(code.toString(), convertErrorResponse(code));
        } else {
            apiResponses.put(code.toString(), convertDataResponse(code, dtoType));
        }
    }


    private ApiResponse convertErrorResponse(ResponseCode code) {
        return convertResponseInner(
                errorEntitySchema.description(code.getMessage()),
                code,
                ErrorResponseDto.of(code)
        );
    }

    private ApiResponse convertDataResponse(ResponseCode code, Type dtoType) {
        return convertResponseInner(
                customizeSchema(code, dtoType),
                code
        );
    }

    @SuppressWarnings("rawtypes")
    private Schema customizeSchema(ResponseCode responseCode, Type dtoType) {
        Schema schema = ModelConverters.getInstance().readAllAsResolvedSchema(dtoType).schema;
        @SuppressWarnings("unchecked") Map<String, Schema> properties = schema.getProperties();
        Boolean success = responseCode.getHttpStatus().is2xxSuccessful();
        Integer status = responseCode.getHttpStatus().value();
        Integer code = responseCode.getCode();
        String message = responseCode.getMessage();

        properties.get("success").setDefault(success);
        properties.get("status").setDefault(status);
        properties.get("code").setDefault(code);
        properties.get("message").setDefault(message);

        return schema;
    }

    private ApiResponse convertResponseInner(@SuppressWarnings("rawtypes") Schema schema, ResponseCode code) {
        return convertResponseInner(schema, code, null);
    }

    private ApiResponse convertResponseInner(@SuppressWarnings("rawtypes") Schema schema, ResponseCode code, ResponseDto example) {
        MediaType mediaType = new MediaType()
                .schema(schema);

        if (example != null) {
            mediaType.addExamples(code.name(), new Example().value(example));
        }

        return new ApiResponse()
                .content(
                        new Content()
                                .addMediaType(
                                        APPLICATION_JSON_VALUE,
                                        mediaType
                                )
                )
                .description(code.getMessage());
    }

}
