import jakarta.persistence.*;
import jpql.Member;
import jpql.MemberDTO;
import jpql.MemberType;
import jpql.Team;

import java.util.List;

public class JpqMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // code
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(28);
            member.setMemberType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("findMember = " + findMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
