package com.example.springmvcrest.bill.service;

import com.example.springmvcrest.bill.api.BillTotalDto;
import com.example.springmvcrest.bill.doamin.Bill;
import com.example.springmvcrest.bill.repository.BillRepository;
import com.example.springmvcrest.policy.domain.Policies;
import com.example.springmvcrest.policy.domain.TaxRange;
import com.example.springmvcrest.policy.service.PoliciesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.springmvcrest.order.domain.OrderType.DELIVERY;

@Service
@AllArgsConstructor
public class BillService {
    private final PoliciesService policiesService;
    private final BillRepository billRepository;

    public Bill saveBill(Bill bill){
        return billRepository.save(bill);
    }

    public BillTotalDto getTotalToPay(BillTotalDto billTotalDto){

        if(billTotalDto.getOrderType().equals(DELIVERY)){
            return BillTotalDto.builder()
                    .total(billTotalDto.getTotal())
                    .build();
        }else {
            Policies policies=policiesService.findPoliciesById(billTotalDto.getPolicyId());
            switch (policies.getSelfPickUpOption()){
                case SELF_PICK_UP_TOTAL:

                case SELF_PICK_UP_EXTEND_PERCENTAGE:
                    return BillTotalDto.builder()
                            .total(billTotalDto.getTotal())
                            .build();

                case SELF_PICK_UP_PART_PERCENTAGE:
                    return BillTotalDto.builder()
                            .total(percentageCalculate(billTotalDto.getTotal(),policies.getTax()))
                            .build();

                case SELF_PICK_UP_PART_RANGE:
                    return BillTotalDto.builder()
                            .total(rangeCalculate(billTotalDto.getTotal(),policies.getTaxRanges()))
                            .build();

                case SELF_PICK_UP:
                    return BillTotalDto.builder()
                            .total(0.0)
                            .build();
            }
        }

        return BillTotalDto.builder()
                .total(billTotalDto.getTotal())
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
