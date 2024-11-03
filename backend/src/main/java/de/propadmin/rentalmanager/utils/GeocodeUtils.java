package de.propadmin.rentalmanager.utils;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeocodeUtils {
    
    public static GeocodeResponse geocodeAddress(String apiKey, String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s", 
                apiKey, encodedAddress);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            
            if (root.has("features") && root.get("features").size() > 0) {
                JsonNode coordinates = root.get("features").get(0).get("geometry").get("coordinates");
                return new GeocodeResponse(
                    coordinates.get(1).asDouble(), // latitude
                    coordinates.get(0).asDouble()  // longitude
                );
            }
            
            throw new RuntimeException("No geocoding results found for address: " + address);
            
        } catch (Exception e) {
            throw new RuntimeException("Error geocoding address: " + address, e);
        }
    }
} 