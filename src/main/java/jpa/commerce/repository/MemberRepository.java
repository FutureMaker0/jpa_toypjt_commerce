package jpa.commerce.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.commerce.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    // 멤버 저장
    public void save(Member member) {
        em.persist(member);
    }

    // 멤버 1명 조회 - id값 사용
    public Member findMemberById(Long id) {
        return em.find(Member.class, id);
    }

    // 모든 멤버 조회
    public List<Member> findAllMembers() {
        return em.createQuery(
                "select m from Member m", Member.class)
                .getResultList();
    }

    // 멤버 조회 - 이름 사용
    public List<Member> findMemberByName(String name) {
        return em.createQuery(
                "select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
