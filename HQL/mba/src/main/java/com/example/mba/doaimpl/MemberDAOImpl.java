package com.example.mba.doaimpl;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.mba.dao.MemberDAO;
import com.example.mba.entity.Member;
import com.example.mba.util.HibernateUtil;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public void addMember(Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(member);

        tx.commit();
        session.close();
    }

    @Override
    public List<Member> showAllMembers() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Member> q = session.createQuery("from Member", Member.class);
        List<Member> list = q.list();

        session.close();
        return list;
    }

    @Override
    public Member searchMemberById(int memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Member> q = session.createQuery("from Member where memberId=:id", Member.class);
        q.setParameter("id", memberId);

        Member member = q.uniqueResult();

        session.close();
        return member;
    }

    @Override
    public int deleteMember(int memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("delete from Member where memberId=:id");
        q.setParameter("id", memberId);

        int count = q.executeUpdate();

        tx.commit();
        session.close();
        return count;
    }
}
