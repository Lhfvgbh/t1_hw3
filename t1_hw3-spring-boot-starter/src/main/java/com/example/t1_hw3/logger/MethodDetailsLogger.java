package com.example.t1_hw3.logger;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Компонент для сбора карты метрик запросов и ответов
 */
@NoArgsConstructor
public class MethodDetailsLogger {

    public static final String URL = "Url";
    public static final String METHOD = "Method";
    public static final String REQUEST_HEADERS = "Request headers";
    public static final String REQUEST_PARAMETERS = "Request parameters";
    public static final String REQUEST_BODY = "Request body";
    public static final String RESPONSE_HEADERS = "Response headers";
    public static final String RESPONSE_STATUS = "Response status";
    public static final String RESPONSE_BODY = "Response body";


    /**
     * Собирает из запроса информацию для логирования
     *
     * @param request Запрос
     * @return Карта тип данных запроса-данные
     */
    @Nonnull
    public Map<String, String> buildRequestLogInfo(@Nonnull ContentCachingRequestWrapper request) {
        var map = new LinkedHashMap<String, String>();

        map.put(URL, String.format("%s%s", request.getRequestURL(), request.getRequestURI()));
        map.put(METHOD, request.getMethod());
        map.put(REQUEST_HEADERS, getRequestHeaders(request));
        map.put(REQUEST_PARAMETERS, getParameters(request));
        map.put(REQUEST_BODY, request.getContentAsString());

        return map;
    }

    /**
     * Собирает из ответа информацию для логирования
     *
     * @param response Ответ
     * @return Карта тип данных ответа-данные
     */
    @Nonnull
    public Map<String, String> buildResponseLogInfo(@Nonnull ContentCachingResponseWrapper response) {
        var map = new LinkedHashMap<String, String>();
        var status = response.getStatus();

        map.put(RESPONSE_STATUS, String.format("%d %s", status, HttpStatus.valueOf(status).name()));
        map.put(RESPONSE_HEADERS, getResponseHeaders(response));
        map.put(RESPONSE_BODY, new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

        return map;
    }

    /**
     * Извлекает и преобразует заголовки запроса в сроку
     *
     * @param request Запрос
     * @return Строка с картой заголовок запроса-значение заголовка
     */
    @Nonnull
    private String getRequestHeaders(@Nonnull ContentCachingRequestWrapper request) {
        var headerNames = Collections.list(request.getHeaderNames());
        return headerNames.stream()
                .map(header -> String.format("%s: %s",
                        header,
                        String.join(", ", Collections.list(request.getHeaders(header)))))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Извлекает и преобразует заголовки ответа в сроку
     *
     * @param response Ответ
     * @return Строка с картой заголовок запроса-значение заголовка
     */
    @Nonnull
    private String getResponseHeaders(@Nonnull ContentCachingResponseWrapper response) {
        var headerNames = response.getHeaderNames();
        return headerNames.stream()
                .map(header -> String.format("%s: %s",
                        header,
                        String.join(", ", response.getHeaders(header))))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Извлекает и преобразует параметры запроса в сроку
     *
     * @param request Запрос
     * @return Строка с картой параметр запроса-значение параметра
     */
    @Nonnull
    private String getParameters(@Nonnull ContentCachingRequestWrapper request) {
        return request.getParameterMap().entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), String.join(", ", entry.getValue())))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
