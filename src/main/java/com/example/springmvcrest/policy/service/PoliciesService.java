package com.example.springmvcrest.policy.service;

import com.example.springmvcrest.policy.api.dto.PoliciesDto;
import com.example.springmvcrest.policy.api.mapper.PoliciesMapper;
import com.example.springmvcrest.policy.domain.Policies;
import com.example.springmvcrest.policy.domain.TaxRange;
import com.example.springmvcrest.policy.repository.PoliciesRepository;
import com.example.springmvcrest.policy.repository.TaxRangeRepository;
import com.example.springmvcrest.utils.Errorhandler.PoliticsException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.springmvcrest.policy.domain.SelfPickUpOptions.*;

@Service
@AllArgsConstructor
public class PoliciesService {
    private final PoliciesMapper policiesMapper;
    private final PoliciesRepository policiesRepository;
    private final TaxRangeRepository taxRangeRepository;


    public PoliciesDto getPolitics(Long providerId){
        return policiesRepository.findByStore_Provider_Id(providerId)
                .map(policiesMapper::toDto)
                .orElseThrow(() -> new PoliticsException("error.politics.notFound"));
    }

    @Transactional
    public Response<String> createPolitics(PoliciesDto policiesDto){
        Optional.of(policiesDto)
                .map(this::checkPolitics)
                .map(this::updateTaxRange)
                .map(policiesRepository::save)
                .map(this::setTaxRange);
        return new Response<>("created.");
    }

    @Transactional
    public Policies updateTaxRange(Policies policies){
        if(policies.getStore().getPolicies()!=null){
            List<TaxRange> collect = policies.getStore().getPolicies().getTaxRanges().stream()
                    .peek(taxRangeRepository::delete)
                    .collect(Collectors.toList());
            policies.setId(policies.getStore().getPolicies().getId());
        }
        return policies;
    }

    public Policies setTaxRange(Policies policies){
        Set<TaxRange> collect = policies.getTaxRanges().stream()
                .peek(taxRange -> taxRange.setPolicies(policies))
                .map(taxRangeRepository::save)
                .collect(Collectors.toSet());
        policies.setTaxRanges(collect);
        return policies;
    }

    private Policies checkPolitics(PoliciesDto policiesDto){
        return Optional.of(policiesDto)
                .map(policiesMapper::toModel)
                .map(this::politicsDispatcher)
                .orElseThrow(() -> new PoliticsException("error.politics.checkPolitics"));
    }

    private Policies politicsDispatcher(Policies policies){
        switch (policies.getSelfPickUpOption()){
            case SELF_PICK_UP:
                return checkSelfPickUp(policies);

            case SELF_PICK_UP_EXTEND_PERCENTAGE:
                return checkSelfPickUpExtendPercentage(policies);

            case SELF_PICK_UP_PART_RANGE:
                return checkSelfPickUpPartRange(policies);

            case SELF_PICK_UP_PART_PERCENTAGE:
                return checkSelfPickUpPartPercentage(policies);

            case SELF_PICK_UP_TOTAL:
                return checkSelfPickUpTotal(policies);
        }
        return null;
    }

    private Policies checkSelfPickUp(Policies policies){
        policies.setSelfPickUpOption(SELF_PICK_UP);
        policies.setTax(0);
        policies.setTaxRanges(Collections.emptySet());
        return policies;
    }

    private Policies checkSelfPickUpExtendPercentage(Policies policies){
        policies.setSelfPickUpOption(SELF_PICK_UP_EXTEND_PERCENTAGE);
        policies.setTaxRanges(Collections.emptySet());
        return policies;
    }

    private Policies checkSelfPickUpPartRange(Policies policies){
        policies.setSelfPickUpOption(SELF_PICK_UP_PART_RANGE);
        policies.setTax(0);
        return policies;
    }

    private Policies checkSelfPickUpPartPercentage(Policies policies){
        policies.setSelfPickUpOption(SELF_PICK_UP_PART_PERCENTAGE);
        policies.setTaxRanges(Collections.emptySet());
        return policies;
    }

    private Policies checkSelfPickUpTotal(Policies policies){
        policies.setSelfPickUpOption(SELF_PICK_UP_TOTAL);
        policies.setTax(0);
        policies.setTaxRanges(Collections.emptySet());
        return policies;
    }
}
