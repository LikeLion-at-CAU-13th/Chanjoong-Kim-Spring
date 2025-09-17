package com.example.likelion13th.Service;
import com.example.likelion13th.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.likelion13th.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService
{
    private final MemberRepository memberRepository;

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
