package com.may.ars.domain.guest;

import lombok.*;

import javax.persistence.*;

import com.may.ars.domain.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class GuestBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;

    @Column
    private String nickname;

    @Column
    private String content;

}
