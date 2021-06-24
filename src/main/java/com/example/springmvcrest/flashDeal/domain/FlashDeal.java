package com.example.springmvcrest.flashDeal.domain;


import com.example.springmvcrest.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"store"})
@Builder
@Entity
public class FlashDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDateTime createAt;
}
