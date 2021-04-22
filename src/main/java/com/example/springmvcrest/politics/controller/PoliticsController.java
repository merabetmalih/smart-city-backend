package com.example.springmvcrest.politics.controller;

import com.example.springmvcrest.politics.api.dto.PoliticsDto;
import com.example.springmvcrest.politics.service.PoliticsService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/politics")
public class PoliticsController {
    private final PoliticsService politicsService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> createPolitics(@ModelAttribute PoliticsDto politicsDto) {
        return  politicsService.createPolitics(politicsDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PoliticsDto getPolitics(@PathVariable Long id) {
        return  politicsService.getPolitics(id);
    }
}
