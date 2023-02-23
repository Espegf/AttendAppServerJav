package attendapp.persistence;

import attendapp.persistence.dao.GenericDAO;
import attendapp.persistence.dao.StudentDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author espeg
 */
public class AddService {
    private final GenericDAO<Student> genericDAO;
    private final StudentDAO studentDAO;
    private final GenericDAO<Arrivals> arrivalsGenericDAO;

    public AddService(GenericDAO<Student> genericDAO, StudentDAO studentDAO, GenericDAO<Arrivals> arrivalsGenericDAO) {
        this.genericDAO = genericDAO;
        this.studentDAO = studentDAO;
        this.arrivalsGenericDAO = arrivalsGenericDAO;
    }

    public void addStudent(String name, String surname, String mac, String place ,LocalDateTime fecha){
        if (studentDAO.findByMac(mac)){
            Student s = studentDAO.findByMacToStudent(mac);
            addArrival(s,fecha, place);
        }else{
            Student s = new Student(name, surname,mac);
            genericDAO.create(s);
            Student st = studentDAO.findByMacToStudent(mac);
            addArrival(st,fecha, place);
        }
    }


    public void addArrival(Student s, LocalDateTime fecha, String place){
        Arrivals a = new Arrivals(Timestamp.valueOf(fecha),place,s);
        arrivalsGenericDAO.create(a);
    }
}
