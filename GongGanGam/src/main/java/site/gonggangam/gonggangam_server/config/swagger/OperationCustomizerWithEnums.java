package site.gonggangam.gonggangam_server.config.swagger;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ResponseDto;


import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class OperationCustomizerWithEnums implements OperationCustomizer {

    @SuppressWarnings("rawtypes")
    private final Schema dataEntitySchema = ModelConverters.getInstance()
            .resolveAsResolvedSchema(new AnnotatedType(DataResponseDto.class)).schema;

    @SuppressWarnings("rawtypes")
    private final Schema errorEntitySchema = ModelConverters.getInstance()
            .resolveAsResolvedSchema(new AnnotatedType(ErrorResponseDto.class)).schema;


    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses apiResponses = operation.getResponses();

        ApiResponseCodes responseCodes = handlerMethod.getMethodAnnotation(ApiResponseCodes.class);
        if (responseCodes != null) {
            Arrays.stream(responseCodes.value()).forEach(
                    code ->
                            apiResponses.put(code.value().toString(), convertErrorResponse(code.value())));
        }

        return operation;
    }

    private ApiResponse convertErrorResponse(ResponseCode code) {
        return new ApiResponse()
                .content(
                        new Content().addMediaType(
                                APPLICATION_JSON_VALUE,
                                new MediaType()
                                        .schema(getSchema(code).description(code.getMessage()))
                                        .addExamples(code.name(), new Example().value(getDtoExample(code)))
                        )).description(code.getMessage())
                ;
    }

    @SuppressWarnings("rawtypes")
    private Schema getSchema(ResponseCode code) {
        if (code.getCode() < 0) {
            return this.errorEntitySchema;
        }
        return this.dataEntitySchema;
    }

    private ResponseDto getDtoExample(ResponseCode code) {
        if (code.getCode() < 0) {
            return ErrorResponseDto.of(code);
        }
        return DataResponseDto.of(code);
    }
}
