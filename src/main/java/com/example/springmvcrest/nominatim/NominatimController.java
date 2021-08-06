package com.example.springmvcrest.nominatim;

import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/nominatim")
public class NominatimController {
    NominatimService nominatimService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Results<City> getCityInformation(
            @RequestParam(name = "country") String country,
            @RequestParam(name = "city") String city
    ) {
        return new Results<>(nominatimService.getCityInformation(country,city)) ;
    }
}
