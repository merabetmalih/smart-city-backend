package com.example.springmvcrest.processor;

import com.example.springmvcrest.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@AllArgsConstructor
public class AutoConfirmReceiveOrder implements Tasklet {
    OrderService orderService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        orderService.toConfirmedOrders()
                .forEach(order -> {
                    order.getOrderState().setReceived(true);
                    System.out.println(order.getId());
                });
        System.out.println("AutoConfirmReceiveOrder FINISHED");
        orderService.toSendUserNotification();
        System.out.println("SendUserNotification FINISHED");
        return RepeatStatus.FINISHED;
    }
}
