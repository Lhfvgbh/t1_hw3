package com.example.t1_hw3.filter;

import com.example.t1_hw3.logger.MethodDetailsLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MethodLoggingFilterTest {

    MockHttpServletRequest request;

    MockHttpServletResponse response;

    MockFilterChain chain;

    @Mock
    MethodDetailsLogger logger;

    @InjectMocks
    MethodLoggingFilter methodLoggingFilter;

    @BeforeEach
    void setUp() {
        this.request = new MockHttpServletRequest("GET", "/api/v1/user");
        this.response = new MockHttpServletResponse();
        this.chain = new MockFilterChain();
    }

    @Test
    void doFilterInternal_shouldCallLogger() {
        methodLoggingFilter.doFilterInternal(this.request, this.response, this.chain);

        verify(logger).buildRequestLogInfo(any(ContentCachingRequestWrapper.class));
        verify(logger).buildResponseLogInfo(any(ContentCachingResponseWrapper.class));
    }

    //@Test
    void doFilterInternal_shouldCallLogger_onError() throws ServletException, IOException {
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);
        doThrow(new RuntimeException("Something went wrong")).when(mockedFilterChain)
                .doFilter(Mockito.any(ServletRequest.class), Mockito.any(ServletResponse.class));

        Assertions.assertThrows(Exception.class,
                () -> this.methodLoggingFilter.doFilterInternal(this.request, this.response, mockedFilterChain));

        verify(logger).buildRequestLogInfo(any(ContentCachingRequestWrapper.class));
        verify(logger).buildResponseLogInfo(any(ContentCachingResponseWrapper.class));
    }

}