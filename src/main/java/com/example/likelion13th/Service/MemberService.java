package com.example.likelion13th.Service;
import com.example.likelion13th.Repository.MemberRepository;
import com.example.likelion13th.dto.request.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.likelion13th.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService
{
    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDto joinRequestDto)
    {
        // 해당 이름이 존재하는 경우
        if(memberRepository.existsByName(joinRequestDto.getName()))
        {
            // 예외 처리 필요
            throw new IllegalStateException("이미 존재하는 이름입니다: " + joinRequestDto.getName());
        }

        // 유저 객체 생성
        Member member = joinRequestDto.toEntity(bCryptPasswordEncoder);

        // 유저 정보 저장
        memberRepository.save(member);
    }

    public Page<Member> getMembersByPage(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findAll(pageable);
    }

    public Page<Member> getMembersAge20OrAbove(Pageable pageable)
    {
        return memberRepository.findByAgeGreaterThanEqualOrderByNameAsc(20, pageable);
    }

    public Page<Member> getMembersByNamePrefix(String prefix, Pageable pageable)
    {
        return memberRepository.findByNameStartingWithOrderByNameAsc(prefix, pageable);
    }
}
