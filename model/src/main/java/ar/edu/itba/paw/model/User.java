package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
	@SequenceGenerator(sequenceName = "users_user_id_seq",
			name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
	private Long id;

    @Column(name = "user_name", length = 100, nullable = false, unique = true)
	private String name;

    @Column(name = "password", length = 100, nullable = false)
	private String password;

    User(){
		/* For Hibernate */
    }
	
	public User(final String name, final String password) {
		this.name = name;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
