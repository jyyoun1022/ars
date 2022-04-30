package com.may.ars.service;

import com.may.ars.common.advice.ExceptionCode;
import com.may.ars.common.advice.exception.EntityNotFoundException;
import com.may.ars.common.advice.exception.UserAuthenticationException;
import com.may.ars.dto.review.ReviewRequestDto;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.domain.review.ReviewRepository;
import com.may.ars.mapper.ReviewMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private ReviewMapper reviewMapper;

    final Member member = Member.builder()
            .id(1L)
            .email("test")
            .nickname("test")
            .build();

    void setup() {
        final Problem problem = Problem.builder()
                .writer(member)
                .build();

        given(problemRepository.findById(1L)).willReturn(Optional.of(problem));
    }

    @Test
    @DisplayName("리뷰 등록 테스트")
    void 리뷰_등록_성공_테스트() {
        setup();
        // given
        Long problemId = 1L;

        final ReviewRequestDto requestDto = ReviewRequestDto.builder()
                .content("리뷰 등록 테스트")
                .build();

        // when
        reviewService.registerReview(problemId, requestDto, member);
    }

    @Test
    @DisplayName("리뷰 등록 실패 테스트 - 문제 존재하지 않는 경우")
    void 리뷰_등록_문제X_테스트() {
        //given
        given(problemRepository.findById(2L)).willReturn(Optional.empty());

        Long problemId = 2L;

        final ReviewRequestDto requestDto = ReviewRequestDto.builder()
                .content("리뷰 등록 테스트")
                .build();

        // when
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> reviewService.registerReview(problemId, requestDto, member)); // 예외가 발생해야 한다.

        //then
        assertThat(e.getMessage(), is(ExceptionCode.ENTITY_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("리뷰 등록 실패 테스트 - 권한 없는 경우")
    void 리뷰_등록_권한X_테스트() {
        setup();
        //given
        final Member member = Member.builder()
                .build();

        Long problemId = 1L;

        final ReviewRequestDto requestDto = ReviewRequestDto.builder()
                .content("리뷰 등록 테스트")
                .build();

        // when
        UserAuthenticationException e = assertThrows(UserAuthenticationException.class,
                () -> reviewService.registerReview(problemId, requestDto, member)); // 예외가 발생해야 한다.

        //then
        assertThat(e.getMessage(), is(ExceptionCode.NOT_VALID_USER.getMessage()));
    }
}
