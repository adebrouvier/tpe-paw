CREATE TABLE IF NOT EXISTS users (
  user_id IDENTITY PRIMARY KEY,
  user_name varchar(100) UNIQUE,
  password varchar(100),
  description varchar(200),
  twitch_url VARCHAR(2000),
  twitter_url VARCHAR(2000),
  youtube_url VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS game (
  game_id IDENTITY PRIMARY KEY,
  name varchar(60) UNIQUE ,
  user_generated BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS game_url_image (
  game_id BIGINT PRIMARY KEY REFERENCES game(game_id),
  url_image varchar(200) 
);

CREATE TABLE IF NOT EXISTS game_image(
  game_id BIGINT PRIMARY KEY REFERENCES game(game_id),
  image varchar(200) 
);

CREATE TABLE IF NOT EXISTS tournament (
  tournament_id IDENTITY PRIMARY KEY,
  status VARCHAR(10) DEFAULT 'NEW',
  name varchar(100) ,
  game_id BIGINT REFERENCES game(game_id),
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS player (
  player_id IDENTITY PRIMARY KEY,
  name varchar(25) ,
  user_id BIGINT REFERENCES users(user_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  seed INTEGER,
  standing INTEGER
);


CREATE TABLE IF NOT EXISTS match (
  match_id BIGINT ,
  tournament_id BIGINT,
  home_player_id BIGINT REFERENCES player(player_id),
  away_player_id BIGINT REFERENCES player(player_id),
  home_player_score INTEGER DEFAULT 0,
  away_player_score INTEGER DEFAULT 0,
  standing INTEGER,
  next_match_id BIGINT,
  next_match_home BOOLEAN,
  map varchar(200),
  home_player_character VARCHAR(2000),
  away_player_character VARCHAR(2000),
  vod_link VARCHAR(2000),
  UNIQUE (match_id, tournament_id),
  FOREIGN KEY (next_match_id,tournament_id) REFERENCES match (match_id,tournament_id)
);


CREATE TABLE IF NOT EXISTS ranking (
  ranking_id IDENTITY PRIMARY KEY,
  name varchar(100) ,
  game_id BIGINT REFERENCES game(game_id),
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS ranking_tournaments (
  ranking_id BIGINT  REFERENCES ranking(ranking_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  awarded_points BIGINT 
);

CREATE TABLE IF NOT EXISTS ranking_players (
  ranking_id BIGINT  REFERENCES ranking(ranking_id),
  user_id BIGINT REFERENCES users(user_id),
  points BIGINT
);

CREATE TABLE IF NOT EXISTS user_image (
  user_id BIGINT PRIMARY KEY REFERENCES user(user_id),
  image varchar(200) 
);

CREATE TABLE IF NOT EXISTS user_favorite_game (
  user_id BIGINT  REFERENCES user(user_id),
  game_id BIGINT  REFERENCES game(game_id),
  PRIMARY KEY (user_id, game_id)
);

CREATE TABLE IF NOT EXISTS user_follow (
  user_follower_id BIGINT REFERENCES user(user_id),
  user_followed_id BIGINT REFERENCES user(user_id),
  PRIMARY KEY (user_follower_id, user_followed_id)
);

CREATE TABLE IF NOT EXISTS notification (
  notification_id IDENTITY PRIMARY KEY,
  user_id BIGINT REFERENCES user(user_id),
  type VARCHAR(40) ,
  is_read BOOLEAN DEFAULT FALSE,
  date TIMESTAMP,
  description VARCHAR(2000)
);