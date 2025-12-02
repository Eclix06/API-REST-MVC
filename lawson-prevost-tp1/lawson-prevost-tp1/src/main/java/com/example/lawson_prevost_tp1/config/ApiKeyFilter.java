package com.example.lawson_prevost_tp1.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.time.Instant;


@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-key}")
    private String expectedApiKey;

@Override
protected void doFilterInternal(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull FilterChain filterChain)
        throws ServletException, IOException {

    String method = request.getMethod();

    System.out.println(">>> ApiKeyFilter : method=" + method
            + ", header='" + request.getHeader("X-API-KEY") + "'"
            + ", expected='" + expectedApiKey + "'");

    // le reste de ton code...
        // POST / PUT / PATCH doivent être protégés
        boolean requiresApiKey =
                "POST".equalsIgnoreCase(method) ||
                "PUT".equalsIgnoreCase(method) ||
                "PATCH".equalsIgnoreCase(method);

        if (requiresApiKey) {
            String apiKey = request.getHeader("X-API-KEY");

            if (apiKey == null || !apiKey.equals(expectedApiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                String body = """
                        {
                          "status": 401,
                          "message": "Missing or invalid API key",
                          "path": "%s",
                          "timestamp": "%s"
                        }
                        """.formatted(request.getRequestURI(), Instant.now().toString());

                response.getWriter().write(body);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
