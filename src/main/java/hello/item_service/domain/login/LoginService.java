package hello.item_service.domain.login;

import hello.item_service.domain.member.Member;
import hello.item_service.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null -> 로그인 실패
     */
    public Member login(String loginId, String password) {
//        Optional<Member> findMemberOpt = memberRepository.findByLoginId(loginId);
//        Member findMember = findMemberOpt.get();
//        if (findMember.getPassword().equals(password)) {
//            return findMember;
//        } else {
//            return null;
//        }

        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
