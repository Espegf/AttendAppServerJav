package attendapp.persistence.dao;

import attendapp.persistence.Student;
import attendapp.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

/**
 * @author espeg
 */
public class StudentDAOImpl extends GenericDAOImpl<Student> implements StudentDAO{
    public StudentDAOImpl() {
        super(Student.class);
    }

    @Override
    public Student findByMac(String mac) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery("FROM Student WHERE mac = :mac", Student.class);
            query.setParameter("mac", mac);
            return query.uniqueResult();
        }
    }

    @Override
    public Student findByMacToStudent(String mac) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Student s = session.find(Student.class, mac);
            return s;
        }
    }
}
