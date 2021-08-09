package com.example.springmvcrest.flashDeal.service;

import com.example.springmvcrest.flashDeal.api.dto.FlashDealCreationDto;
import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.flashDeal.api.mapper.FlashDealMapper;
import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.flashDeal.domain.PeriodicityFlash;
import com.example.springmvcrest.flashDeal.repository.FlashDealRepository;
import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.domain.NotificationType;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.FlashDealException;
import com.example.springmvcrest.utils.Response;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.springmvcrest.flashDeal.domain.PeriodicityFlash.*;

@Service
@AllArgsConstructor
public class FlashDealService {
    private final FlashDealRepository flashDealRepository;
    private final FlashDealMapper flashDealMapper;
    private final NotificationService notificationService;
    private final SimpleUserService simpleUserService;
    private final StoreService storeService;


    @Transactional
    public Response<String> createFlashDeal(FlashDealCreationDto flashDealCreationDto){
        flashDealCreationDto.setId(null);

        Long maxFlash=2L;
        PeriodicityFlash periodicityFlash=WEEK;

        Optional.of(flashDealCreationDto)
                .map(flashDealMapper::toModel)
                .filter(flash ->checkValidFlash(flash,periodicityFlash,maxFlash))
                .map(this::setCreatAt)
                .map(flashDealRepository::save)
                .map(flashDeal -> setFlash(flashDeal,periodicityFlash))
                .map(this::prepareNotification)
                .map(this::setFlashDealUser)
                .orElseThrow(() -> new FlashDealException("you can not send more then "+maxFlash));
        return new Response<>("created.");
    }

    public List<FlashDealDto> getRecentFlashDealsStore(Long providerId){
        return flashDealRepository.findByStore_Provider_Id(providerId).stream()
                .sorted(Comparator.comparing(FlashDeal::getCreateAt, Comparator.reverseOrder()))
                .limit(10)
                .map(flashDealMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FlashDealDto> searchFlashDealsStore(Long providerId,String startDate, String endDate){
        if(!DateUtil.isValidDate(startDate) || !DateUtil.isValidDate(endDate)){
            throw new DateException("error.date.invalid");
        }

        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);

        return flashDealRepository.findByStore_Provider_IdAndCreateAtBetween(providerId,startOfDate,endOfDate)
                .stream()
                .map(flashDealMapper::toDto)
                .collect(Collectors.toList());
    }

    private FlashDeal setCreatAt(FlashDeal flashDeal){
        flashDeal.setCreateAt(LocalDateTime.now());
        return flashDeal;
    }

    private FlashDeal prepareNotification(FlashDeal flashDeal){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        flashDeal.getStore().getDefaultCategories()
                .forEach(category -> threadPool.submit(() -> sendNotification(category,flashDeal)));
        threadPool.shutdown();
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
        simpleUserService.findSimpleUserByInterestCenterAndAround(flashDeal.getStore())
                .forEach(user -> simpleUserService.setFlashDeal(user,flashDeal));
        return flashDeal;
    }

    private static Supplier<Pair<LocalDate,LocalDate>> WeekStartEnd= () -> {
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.ENGLISH).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        return new Pair<>(
                LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek)),
                LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
                );
    };

    private static Supplier<Pair<LocalDate,LocalDate>> MonthStartEnd= () -> {
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        return new Pair<>(
                start,
                end
        );
    };

    private static Supplier<Pair<LocalDate,LocalDate>> YearStartEnd= () -> {
        LocalDate initial = LocalDate.now();
        LocalDate startYear=initial.withDayOfYear(1);
        LocalDate endYear = initial.withDayOfYear(initial.lengthOfYear());
        return new Pair<>(
                startYear,
                endYear
        );
    };

    private static Supplier<List<Pair<PeriodicityFlash,Supplier<Pair<LocalDate,LocalDate>>>>> GetPeriodicity= ()->{
        List<Pair<PeriodicityFlash,Supplier<Pair<LocalDate,LocalDate>>>> rules=new ArrayList<>();
        rules.add(new Pair<>(WEEK,WeekStartEnd));
        rules.add(new Pair<>(MONTH,MonthStartEnd));
        rules.add(new Pair<>(YEAR,YearStartEnd));
        return rules;
    };

    private static Function<PeriodicityFlash,Function<List<Pair<PeriodicityFlash,Supplier<Pair<LocalDate,LocalDate>>>>,//two inputs
            //output
            Pair<PeriodicityFlash, Supplier<Pair<LocalDate,LocalDate>>>>> GetPeriodicityQualifier = periodicityFlash ->(periodicityList ->{
        return periodicityList.stream()
                .filter(periodicity -> periodicity.getKey().equals(periodicityFlash))
                .findFirst()
                .orElseThrow(()-> new FlashDealException("error.flash.periodicity.notFound"));
    });

    private Boolean checkValidFlash(FlashDeal flashDeal,PeriodicityFlash periodicityFlash,Long maxFlash){
        initStoreFlash(flashDeal.getStore(),periodicityFlash);
        Pair<LocalDate,LocalDate> periodicityQualifier = GetPeriodicityQualifier
                .apply(periodicityFlash)
                .apply(GetPeriodicity.get()).getValue().get();
        Store store=flashDeal.getStore();
        LocalDate now = LocalDate.now();
        return ((now.isAfter(periodicityQualifier.getKey().minusDays(1))) && (now.isBefore(periodicityQualifier.getValue().plusDays(1))) && (store.getTransmittedFlash() < maxFlash));
    }

    private FlashDeal setFlash(FlashDeal flashDeal,PeriodicityFlash periodicityFlash){
        Store store=flashDeal.getStore();
        Pair<LocalDate,LocalDate> periodicityQualifier = GetPeriodicityQualifier
                .apply(periodicityFlash)
                .apply(GetPeriodicity.get()).getValue().get();

        if (!store.getLastFlashStart().equals(periodicityQualifier.getKey())){
            store.setTransmittedFlash(0L);
            store=storeService.saveStore(store);
        }

        store.setTransmittedFlash(
                store.getTransmittedFlash()+1
        );
        store.setLastFlashStart(
                periodicityQualifier.getKey()
        );

        storeService.saveStore(store);
        return flashDeal;
    }

    private void initStoreFlash(Store store,PeriodicityFlash periodicityFlash){
        if(store.getTransmittedFlash()==null){
            store.setTransmittedFlash(0L);
            storeService.saveStore(store);
        }
        if(store.getLastFlashStart()==null || store.getPeriodicityFlash()==null){
            Pair<LocalDate,LocalDate> periodicityQualifier = GetPeriodicityQualifier
                    .apply(periodicityFlash)
                    .apply(GetPeriodicity.get()).getValue().get();

            store.setLastFlashStart(periodicityQualifier.getKey());
            store.setPeriodicityFlash(periodicityFlash);
            storeService.saveStore(store);
        }
    }

    public List<FlashDealDto> searchFlashByPosition(Double lat,Double lon,String date){
        if(!DateUtil.isValidDate(date)){
            throw new DateException("error.date.invalid");
        }
        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.MAX);
        return flashDealRepository.findAll().stream()
                .filter(flash -> isAround(lat,lon,flash.getStore()))
                .filter(flash -> flash.getCreateAt().isAfter(startOfDate) && flash.getCreateAt().isBefore(endOfDate))
                .map(flashDealMapper::toDto)
                .collect(Collectors.toList());
    }

    private Boolean isAround(Double latitude,Double longitude,Store store){
        double distance=12.0;
        return distFrom(
                latitude,
                longitude,
                store.getStoreAddress().getLatitude(),
                store.getStoreAddress().getLongitude()) < distance;
    }

    private Double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return  (earthRadius * c)/1000;
    }
}
