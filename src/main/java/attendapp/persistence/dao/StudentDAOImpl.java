package attendapp.persistence.dao;

import attendapp.persistence.Student;
import attendapp.persistence.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Optional;

/**
 * @author espeg
 */
public class StudentDAOImpl extends GenericDAOImpl<Student> implements StudentDAO{
    public StudentDAOImpl() {
        super(Student.class);
    }

    @Override
    public boolean findByMac(String mac) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            if(session.find(Student.class, mac)!=null){
                return true;
            }else{
                return false;
            }
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
