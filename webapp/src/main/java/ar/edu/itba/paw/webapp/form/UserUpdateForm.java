package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateForm {

    @Size(min = 0, max = 200)
    private String description;

    @Pattern(regexp = "|(https://www.twitch.tv/|https://twitch.tv/|twitch.tv/|www.twitch.tv/)[a-zA-Z0-9@:!$&'()*+,;=%\\-._~\\[\\]/?\"#]*")
    @Size(min = 0, max = 2000)
    private String twitchUrl;

    @Pattern(regexp = "|(https://www.twitter.com/|https://twitter.com/|twitter.com/|www.twitter.com/)[a-zA-Z0-9@:!$&'()*+,;=%\\-._~\\[\\]/?\"#]*")
    @Size(min = 0, max = 2000)
    private String twitterUrl;

    @Pattern(regexp = "|(https://www.youtube.com/|https://youtube.com/|youtube.com/|www.youtube.com/)[a-zA-Z0-9@:!$&'()*+,;=%\\-._~\\[\\]/?\"#]*")
    @Size(min = 0, max = 2000)
    private String youtubeUrl;

    @Size(min = 0, max = 60)
    private String game;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

}
