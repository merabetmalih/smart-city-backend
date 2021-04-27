package com.example.springmvcrest.policy.controller;

import com.example.springmvcrest.policy.api.dto.PoliciesDto;
import com.example.springmvcrest.policy.service.PoliciesService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/policy")
public class PoliciesController {
    private final PoliciesService policiesService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> createPolitics(@RequestBody PoliciesDto policiesDto) {
        return  policiesService.createPolitics(policiesDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PoliciesDto getPolitics(@PathVariable Long id) {
        return  policiesService.getPolitics(id);
    }
}
