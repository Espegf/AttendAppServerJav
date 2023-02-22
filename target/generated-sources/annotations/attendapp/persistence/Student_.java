package attendapp.persistence;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile SingularAttribute<Student, String> surname;
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, Long> id;
	public static volatile SingularAttribute<Student, String> mac;

	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String MAC = "mac";

}

