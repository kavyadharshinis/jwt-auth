package com.example.jwtauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoldRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${goldapi.key:goldapi-f7vpsmcaloicu-io}")
    private String goldApiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Fallback rates (used when API fails)
    private static final double FALLBACK_GOLD_RATE_INR = 6200.0; // INR per gram
    private static final double FALLBACK_SILVER_RATE_INR = 75.0;  // INR per gram

    @Cacheable("goldRate")
    public Map<String, Object> getGoldRate() {
        try {
            String url = "https://www.goldapi.io/api/XAU/INR";
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-access-token", goldApiKey);
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                double goldRate = jsonNode.get("price_gram_24k").asDouble();
                Map<String, Object> result = new HashMap<>();
                result.put("rate", goldRate);
                result.put("currency", "INR");
                result.put("unit", "per gram");
                result.put("source", "live");
                result.put("timestamp", System.currentTimeMillis());
                return result;
            }
        } catch (Exception e) {
            System.err.println("Error fetching gold rate from GoldAPI: " + e.getMessage());
        }

        // Fallback response
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("rate", FALLBACK_GOLD_RATE_INR);
        fallback.put("currency", "INR");
        fallback.put("unit", "per gram");
        fallback.put("source", "fallback");
        fallback.put("timestamp", System.currentTimeMillis());
        return fallback;
    }

    @Cacheable("silverRate")
    public Map<String, Object> getSilverRate() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("rate", FALLBACK_SILVER_RATE_INR);
        fallback.put("currency", "INR");
        fallback.put("unit", "per gram");
        fallback.put("source", "fallback");
        fallback.put("timestamp", System.currentTimeMillis());
        return fallback;
    }

    public double calculateGoldLoanAmount(double goldWeight, double purity) {
        Map<String, Object> goldRate = getGoldRate();
        double rate = (Double) goldRate.get("rate"); // INR per gram
        double pureGoldWeight = goldWeight * (purity / 24.0);
        double marketValue = pureGoldWeight * rate;
        return marketValue * 0.75;
    }

    public double calculateSilverLoanAmount(double silverWeight) {
        Map<String, Object> silverRate = getSilverRate();
        double rate = (Double) silverRate.get("rate"); // INR per gram
        double marketValue = silverWeight * rate;
        return marketValue * 0.65;
    }

    @Scheduled(fixedRate = 3600000) // every 1 hour
    @CacheEvict(value = {"goldRate", "silverRate"}, allEntries = true)
    public void clearRateCache() {
        // Automatically clears cached gold/silver rates every hour
    }
}
