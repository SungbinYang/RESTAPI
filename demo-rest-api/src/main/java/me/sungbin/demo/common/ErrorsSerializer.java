package me.sungbin.demo.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.BindingResult;

import java.io.IOException;

/**
 * packageName : me.sungbin.demo.common
 * fileName : ErrorsSerializer
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<BindingResult> {

    @Override
    public void serialize(BindingResult bindingResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        bindingResult.getFieldErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("field", e.getField());
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("codes", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());

                Object rejectedValue = e.getRejectedValue();

                if (rejectedValue != null) {
                    jsonGenerator.writeStringField("rejectValue", rejectedValue.toString());
                }

                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        bindingResult.getGlobalErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("codes", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());
                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
    }
}
