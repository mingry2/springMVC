package hello.item_service.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save member : {}", member);
        store.put(member.getId(), member);

        return member;
    }

    public Member findById(Long id) {

        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
//        List<Member> members = findAll();
//        for (Member member : members) {
//            if (member.getLoginId().equals(loginId)) {
//                return Optional.of(member);
//            }
//        }
//
//        return Optional.empty();
        // 스트림, 람다 사용
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }


}
