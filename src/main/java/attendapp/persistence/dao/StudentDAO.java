package attendapp.persistence.dao;

import attendapp.persistence.Student;

/**
 * @author espeg
 */
public interface StudentDAO {

    Student findByMac(String mac);
    Student findByMacToStudent(String mac);
}
