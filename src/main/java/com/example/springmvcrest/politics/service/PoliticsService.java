package com.example.springmvcrest.politics.service;

import com.example.springmvcrest.politics.api.dto.PoliticsDto;
import com.example.springmvcrest.politics.api.mapper.PoliticsMapper;
import com.example.springmvcrest.politics.domain.Politics;
import com.example.springmvcrest.politics.domain.TaxRange;
import com.example.springmvcrest.politics.repository.PoliticsRepository;
import com.example.springmvcrest.politics.repository.TaxRangeRepository;
import com.example.springmvcrest.utils.Errorhandler.PoliticsException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.springmvcrest.politics.domain.SelfPickUpOptions.*;

@Service
@AllArgsConstructor
public class PoliticsService {
    private final PoliticsMapper politicsMapper;
    private final PoliticsRepository politicsRepository;
    private final TaxRangeRepository taxRangeRepository;


    public PoliticsDto getPolitics(Long providerId){
        return politicsRepository.findByStore_Provider_Id(providerId)
                .map(politicsMapper::toDto)
                .orElseThrow(() -> new PoliticsException("error.politics.notFound"));
    }

    @Transactional
    public Response<String> createPolitics(PoliticsDto politicsDto){
        Optional.of(politicsDto)
                .map(this::checkPolitics)
                .map(this::updateTaxRange)
                .map(politicsRepository::save)
                .map(this::setTaxRange);
        return new Response<>("created.");
    }

    @Transactional
    public Politics updateTaxRange(Politics politics){
        if(politics.getStore().getPolitics()!=null){
            List<TaxRange> collect = politics.getStore().getPolitics().getTaxRanges().stream()
                    .peek(taxRangeRepository::delete)
                    .collect(Collectors.toList());
            politics.setId(politics.getStore().getPolitics().getId());
        }
        return politics;
    }

    public Politics setTaxRange(Politics politics){
        Set<TaxRange> collect = politics.getTaxRanges().stream()
                .peek(taxRange -> taxRange.setPolitics(politics))
                .map(taxRangeRepository::save)
                .collect(Collectors.toSet());
        politics.setTaxRanges(collect);
        return politics;
    }

    private Politics checkPolitics(PoliticsDto politicsDto){
        return Optional.of(politicsDto)
                .map(politicsMapper::toModel)
                .map(this::politicsDispatcher)
                .orElseThrow(() -> new PoliticsException("error.politics.checkPolitics"));
    }

    private Politics politicsDispatcher(Politics politics){
        switch (politics.getSelfPickUpOption()){
            case SELF_PICK_UP:
                return checkSelfPickUp(politics);

            case SELF_PICK_UP_EXTEND_RANGE:
                return checkSelfPickUpExtendRange(politics);

            case SELF_PICK_UP_EXTEND_TAX:
                return checkSelfPickUpExtendTax(politics);

            case SELF_PICK_UP_PART_RANGE:
                return checkSelfPickUpPartRange(politics);

            case SELF_PICK_UP_PART_TAX:
                return checkSelfPickUpPartTax(politics);

            case SELF_PICK_UP_TOTAL:
                return checkSelfPickUpTotal(politics);
        }
        return null;
    }

    private Politics checkSelfPickUp(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP);
        politics.setTax(0);
        politics.setTaxRanges(Collections.emptySet());
        return politics;
    }

    private Politics checkSelfPickUpExtendRange(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP_EXTEND_RANGE);
        politics.setTax(0);
        return politics;
    }

    private Politics checkSelfPickUpExtendTax(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP_EXTEND_TAX);
        politics.setTaxRanges(Collections.emptySet());
        return politics;
    }

    private Politics checkSelfPickUpPartRange(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP_PART_RANGE);
        politics.setTax(0);
        return politics;
    }

    private Politics checkSelfPickUpPartTax(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP_PART_TAX);
        politics.setTaxRanges(Collections.emptySet());
        return politics;
    }

    private Politics checkSelfPickUpTotal(Politics politics){
        politics.setSelfPickUpOption(SELF_PICK_UP_TOTAL);
        politics.setTax(0);
        politics.setTaxRanges(Collections.emptySet());
        return politics;
    }
}
