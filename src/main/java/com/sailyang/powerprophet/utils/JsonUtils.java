package com.sailyang.powerprophet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sailyang.powerprophet.pojo.PreResult;
import java.util.List;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Jackson提供了JavaType，用来指明集合类型和泛型
     * @param collectonClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectonClass, Class<?>... elementClasses){
        return objectMapper.getTypeFactory().constructParametricType(collectonClass, elementClasses);
    }

    /**
     * 集合的反序列化
     * @param jsonArray
     * @return
     */
    public static List<PreResult> databinds(String jsonArray){
        JavaType type = getCollectionType(List.class, PreResult.class);
        List<PreResult> list = null;
        try {
            list = objectMapper.readValue(jsonArray, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }
}
