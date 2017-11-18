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

    @Column(name = "description", length = 200)
	private String description;

    @Column(name = "twitch_utl", length = 2000)
	private String twitchUrl;

	@Column(name = "twitter_utl", length = 2000)
	private String twitterUrl;

	@Column(name = "youtube_utl", length = 2000)
	private String youtubeUrl;

    User(){
		/* For Hibernate */
    }
	
	public User(final String name, final String password) {
		this.name = name;
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTwitchUrl() {
		return twitchUrl;
	}

	public void setTwitchUrl(String twitchUrl) {
		this.twitchUrl = twitchUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
