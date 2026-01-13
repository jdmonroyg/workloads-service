package com.epam.workloads.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
class TransactionLoggingFilterTest {

    private TransactionLoggingFilter filter;

    @BeforeEach
    void setUp() {
        filter = new TransactionLoggingFilter();
        MDC.clear();
    }

    @Test
    void shouldGenerateTransactionIdWhenHeaderMissing() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        String headerValue = response.getHeader("X-Transaction-ID");
        assertNotNull(headerValue);
        assertFalse(headerValue.isEmpty());

        // Clear because setUp
        assertNull(MDC.get("transactionId"));
    }

    @Test
    void shouldReuseTransactionIdWhenHeaderPresent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Transaction-ID", "fixed-id-123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        String headerValue = response.getHeader("X-Transaction-ID");
        assertEquals("fixed-id-123", headerValue);

        assertNull(MDC.get("transactionId"));
    }

}