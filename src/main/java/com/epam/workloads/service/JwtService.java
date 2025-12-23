package com.epam.workloads.service;

/**
 * @author jdmon on 21/12/2025
 * @project workloads-service
 */
public interface JwtService {

    boolean isTokenValid(String token);

    String getUsername(String token);
}
