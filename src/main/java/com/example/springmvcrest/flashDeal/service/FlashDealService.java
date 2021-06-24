package com.example.springmvcrest.flashDeal.service;

import com.example.springmvcrest.flashDeal.api.dto.FlashDealCreationDto;
import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.flashDeal.api.mapper.FlashDealMapper;
import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.flashDeal.repository.FlashDealRepository;
import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.domain.NotificationType;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlashDealService {
    private final FlashDealRepository flashDealRepository;
    private final FlashDealMapper flashDealMapper;
    private final NotificationService notificationService;
    private final SimpleUserService simpleUserService;

    @Transactional
    public Response<String> createFlashDeal(FlashDealCreationDto flashDealCreationDto){
        flashDealCreationDto.setId(null);

        Optional.of(flashDealCreationDto)
                .map(flashDealMapper::toModel)
                .map(this::setCreatAt)
                .map(flashDealRepository::save)
                .map(this::prepareNotification)
                .map(this::setFlashDealUser);
        return new Response<>("created.");
    }

    public List<FlashDealDto> getFlashDealsStore(Long providerId){
        return flashDealRepository.findByStore_Provider_Id(providerId).stream()
                .map(flashDealMapper::toDto)
                .collect(Collectors.toList());
    }

    private FlashDeal setCreatAt(FlashDeal flashDeal){
        flashDeal.setCreateAt(LocalDateTime.now());
        return flashDeal;
    }

    private FlashDeal prepareNotification(FlashDeal flashDeal){
        flashDeal.getStore().getDefaultCategories()
                .forEach(category -> sendNotification(category,flashDeal));
        return flashDeal;
    }

    private void sendNotification(Category category,FlashDeal flashDeal){
        notificationService.sendNotification(
                Notification.builder()
                        .title(flashDeal.getTitle())
                        .message(flashDeal.getContent())
                        .type(NotificationType.FLASH)
                        .topic(category.getName())
                        .build()
        );
    }

    private FlashDeal setFlashDealUser(FlashDeal flashDeal){
        simpleUserService.findSimpleUserByInterestCenter(flashDeal.getStore().getDefaultCategories())
                .forEach(user -> simpleUserService.setFlashDeal(user,flashDeal));
        return flashDeal;
    }
}
