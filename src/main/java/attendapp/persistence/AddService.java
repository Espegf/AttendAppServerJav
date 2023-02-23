package attendapp.persistence;

import attendapp.persistence.dao.GenericDAO;
import attendapp.persistence.dao.GenericDAOImpl;
import attendapp.persistence.dao.StudentDAO;
import attendapp.persistence.dao.StudentDAOImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author espeg
 */
public class AddService {
    private final GenericDAO<Student> genericDAO;
    private final StudentDAO studentDAO;
    private final GenericDAO<Arrivals> arrivalsGenericDAO;

    public AddService() {
        this.genericDAO = new GenericDAOImpl<>(Student.class);
        this.studentDAO = new StudentDAOImpl();
        this.arrivalsGenericDAO = new GenericDAOImpl<>(Arrivals.class);
    }

    public void addStudent(String name, String surname, String mac, String place ,LocalDateTime fecha){
        Student s = studentDAO.findByMac(mac);
        if (s!=null){
            addArrival(s,fecha, place);
        }else{
            s = new Student(name, surname,mac);
            genericDAO.create(s);
            Student st = studentDAO.findByMac(mac);
            addArrival(st,fecha, place);
        }
    }


    public void addArrival(Student s, LocalDateTime fecha, String place){
        Arrivals a = new Arrivals(Timestamp.valueOf(fecha),place,s);
        arrivalsGenericDAO.create(a);
    }
}
