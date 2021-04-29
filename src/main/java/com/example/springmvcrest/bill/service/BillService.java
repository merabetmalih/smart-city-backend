package com.example.springmvcrest.bill.service;

import com.example.springmvcrest.bill.api.BillDto;
import com.example.springmvcrest.policy.domain.Policies;
import com.example.springmvcrest.policy.domain.TaxRange;
import com.example.springmvcrest.policy.service.PoliciesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class BillService {
    private final PoliciesService policiesService;

    public BillDto getTotalToPay(Long policyId, Double total){
        Policies policies=policiesService.findPoliciesById(policyId);
        switch (policies.getSelfPickUpOption()){
            case SELF_PICK_UP_TOTAL:

            case SELF_PICK_UP_EXTEND_PERCENTAGE:
                return BillDto.builder()
                        .total(total)
                        .build();

            case SELF_PICK_UP_PART_PERCENTAGE:
                return BillDto.builder()
                        .total(percentageCalculate(total,policies.getTax()))
                        .build();

            case SELF_PICK_UP_PART_RANGE:
                return BillDto.builder()
                        .total(rangeCalculate(total,policies.getTaxRanges()))
                        .build();

            case SELF_PICK_UP:
                return BillDto.builder()
                        .total(0.0)
                        .build();
        }
        return BillDto.builder()
                .total(total)
                .build();
    }

    private Double rangeCalculate(Double total, Set<TaxRange> ranges){
        for (TaxRange taxRange:ranges
        ) {
            if (total >= taxRange.getStartRange() && total <= taxRange.getEndRange()){
                return taxRange.getFixAmount().doubleValue();
            }
        }
        return total;
    }

    private Double percentageCalculate(Double total,Integer percentage){
        return total*percentage/100;
    }
}
