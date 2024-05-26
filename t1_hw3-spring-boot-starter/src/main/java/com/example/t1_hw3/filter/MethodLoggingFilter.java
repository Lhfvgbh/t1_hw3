package com.example.t1_hw3.filter;

import com.example.t1_hw3.logger.MethodDetailsLogger;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Фильтр для логирования запросов и ответов
 */
@Slf4j
@AllArgsConstructor
public class MethodLoggingFilter extends OncePerRequestFilter {

    private final MethodDetailsLogger methodDetailsLogger;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        var logMap = new LinkedHashMap<String, String>();
        var startTime = System.currentTimeMillis();

        try {
            var req = wrapRequest(request);
            var res = wrapResponse(response);

            chain.doFilter(req, res);
            var reqMap = methodDetailsLogger.buildRequestLogInfo(req);
            var resMap = methodDetailsLogger.buildResponseLogInfo(res);

            logMap.putAll(reqMap);
            logMap.putAll(resMap);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        } finally {
            logMap.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && !entry.getValue().isBlank())
                    .forEach(entry -> log.info("{}:\n {}", entry.getKey(), entry.getValue()));
            var endTime = System.currentTimeMillis();

            log.info("Executed method {}{} in {} ms", request.getRequestURL(), request.getRequestURI(), endTime - startTime);
        }
    }

    @Nonnull
    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    @Nonnull
    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}