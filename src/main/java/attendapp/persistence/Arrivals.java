package attendapp.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author espeg
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "arrivals")
public class Arrivals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Timestamp date;

    @Column
    String place;

    @ManyToOne
    private Student student;

    public Arrivals(Timestamp date, String place, Student student) {
        this.date = date;
        this.place = place;
        this.student = student;
    }

    @Override
    public String toString() {
        return "Lugar: " + place + "Estudiante" + student.getName() + " " + student.getSurname() + " " + student.getMac() + " ";
    }
}
