package jpa.commerce.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.commerce.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
