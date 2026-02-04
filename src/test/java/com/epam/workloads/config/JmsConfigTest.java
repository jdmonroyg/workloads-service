package com.epam.workloads.config;

import jakarta.jms.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jms.autoconfigure.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * @author jdmon on 13/01/2026
 * @project workloads-service
 */
class JmsConfigTest {

    private ConnectionFactory connectionFactory;
    private DefaultJmsListenerContainerFactoryConfigurer configurer;

    @BeforeEach
    public void setUp(){
        connectionFactory = mock(ConnectionFactory.class);
        configurer = mock(DefaultJmsListenerContainerFactoryConfigurer.class);

    }

    @Test
    void messageConverterShouldBeConfigured() {
        JmsConfig config = new JmsConfig();
        MessageConverter messageConverter = config.messageConverter();

        assertNotNull(messageConverter);
        assertInstanceOf(JacksonJsonMessageConverter.class, messageConverter);

        // Validar configuración interna con ReflectionTestUtils
        assertEquals(MessageType.TEXT, ReflectionTestUtils.getField(messageConverter, "targetType"));
        assertEquals("_type", ReflectionTestUtils.getField(messageConverter, "typeIdPropertyName"));
    }

    @Test
    void jmsListenerContainerFactoryShouldBeConfigured() {
        JmsConfig config = new JmsConfig();
        DefaultJmsListenerContainerFactory factory = config.jmsListenerContainerFactory(connectionFactory, configurer);

        assertNotNull(factory);

        Boolean sessionTransacted = (Boolean) ReflectionTestUtils.getField(factory, "sessionTransacted");
        assertEquals(Boolean.TRUE, sessionTransacted);

        assertNotNull(ReflectionTestUtils.getField(factory, "errorHandler"));
    }


}