package attendapp.persistence;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Arrivals.class)
public abstract class Arrivals_ {

	public static volatile SingularAttribute<Arrivals, Timestamp> date;
	public static volatile SingularAttribute<Arrivals, Student> student;
	public static volatile SingularAttribute<Arrivals, Long> id;
	public static volatile SingularAttribute<Arrivals, String> place;

	public static final String DATE = "date";
	public static final String STUDENT = "student";
	public static final String ID = "id";
	public static final String PLACE = "place";

}

