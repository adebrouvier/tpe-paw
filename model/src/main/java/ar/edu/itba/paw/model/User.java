package ar.edu.itba.paw.model;

public class User {
	
	private long id;
	private String name;
	private String password;
	
	public User(long id, String name, String password) {
		super();
		this.id = id;
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
