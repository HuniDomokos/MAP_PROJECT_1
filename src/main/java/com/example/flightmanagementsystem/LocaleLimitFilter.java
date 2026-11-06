package com.example.flightmanagementsystem;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Some browsers send an Accept-Language header with more than 10 entries.
 * Tomcat then throws "More than the maximum allowed number of locales".
 * This filter runs before Spring's RequestContextFilter and hides the
 * long header, so the application always sees a single locale.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LocaleLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new SingleLocaleRequest(request), response);
    }

    // Also run when Tomcat forwards to /error after an exception,
    // otherwise the error page hits the locale limit again.
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    @Override
    protected void doFilterNestedErrorDispatch(HttpServletRequest request,
                                               HttpServletResponse response,
                                               FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new SingleLocaleRequest(request), response);
    }

    private static class SingleLocaleRequest extends HttpServletRequestWrapper {

        SingleLocaleRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Locale getLocale() {
            return Locale.ENGLISH;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return Collections.enumeration(Collections.singletonList(Locale.ENGLISH));
        }

        @Override
        public String getHeader(String name) {
            if ("Accept-Language".equalsIgnoreCase(name)) {
                return "en";
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if ("Accept-Language".equalsIgnoreCase(name)) {
                return Collections.enumeration(Collections.singletonList("en"));
            }
            return super.getHeaders(name);
        }
    }
}
