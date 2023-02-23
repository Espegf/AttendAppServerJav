package attendapp.persistence.dao;

import attendapp.persistence.Student;

/**
 * @author espeg
 */
public interface StudentDAO {

    public boolean findByMac(String mac);
    public Student findByMacToStudent(String mac);
}
