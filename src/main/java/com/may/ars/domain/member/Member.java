package com.may.ars.domain.member;

import com.may.ars.enums.RoleType;
import com.may.ars.enums.SocialType;
import com.may.ars.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(unique = true)
    private String socialId;

    @Column
    private String nickname;

    @Setter
    @Column(unique = true)
    private String slackId;

    @Setter
    private boolean checkSlack;

}
