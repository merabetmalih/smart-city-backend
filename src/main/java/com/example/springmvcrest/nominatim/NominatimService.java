package com.example.springmvcrest.nominatim;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NominatimService {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/?addressdetails=1&country={country}&city={city}&format=json&limit=3";
    private static final String NOMINATIM_REVERSE_URL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat={latitude}&lon={longitude}&zoom=10";
    private final RestTemplate restTemplate;

    public NominatimService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public NominatimCityNameResponse getCityName(Double latitude,Double longitude){
        String responseJson=restTemplate.getForObject(NOMINATIM_REVERSE_URL, String.class, latitude,longitude);
        Gson gson = new GsonBuilder().create();
        NominatimCityNameResponse response = gson.fromJson(responseJson , NominatimCityNameResponse.class);
        return response;
    }

    public List<City> getCityInformation(String country, String city) {
        String responseJson=restTemplate.getForObject(NOMINATIM_URL, String.class, country,city);
        Gson gson = new GsonBuilder().create();
        NominatimResponse[] response = gson.fromJson(responseJson , NominatimResponse[].class);
        ArrayList<City> nominatimServices=new ArrayList<>();
        for (NominatimResponse nominatimResponse:response) {
            nominatimServices.add(
                    City.builder()
                            .city(nominatimResponse.getDisplay_name() )
                            .lat(nominatimResponse.getLat())
                            .lon(nominatimResponse.getLon())
                            .importance(nominatimResponse.getImportance())
                            .build()
            );
        }
        List<City> result=new ArrayList<>();

        Map<String, List<City>> collect = nominatimServices.stream()
                .collect(Collectors.groupingBy(City::getCity));

        for (Map.Entry<String, List<City>> entry : collect.entrySet()) {
            result.add(
                    entry.getValue().stream()
                            .max(Comparator.comparingDouble(City::getImportance))
                            .orElse(null)
            );
        }
        return result.stream()
                .sorted(Comparator.comparing(City::getImportance,Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
