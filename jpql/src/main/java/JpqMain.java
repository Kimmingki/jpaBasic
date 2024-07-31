import jakarta.persistence.*;
import jpql.Member;
import jpql.MemberDTO;

import java.util.List;

public class JpqMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // code
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//            Member findMember = result.get(0);
//            findMember.setAge(20);

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            for (MemberDTO memberDTO : resultList) {
                System.out.println("memberDTO = " + memberDTO.getUsername());
                System.out.println("memberDTO = " + memberDTO.getAge());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
