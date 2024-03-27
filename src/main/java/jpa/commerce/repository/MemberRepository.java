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

    // 멤버 1명 조회
    public Member findMemberById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAllMembers() {
        return em.createQuery(
                "select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findMemberByName(String name) {
        return em.createQuery(
                "select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
