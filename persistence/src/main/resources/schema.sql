CREATE TABLE IF NOT EXISTS users (
user_id SERIAL PRIMARY KEY,
user_name varchar(100) UNIQUE,
password varchar(100)
);

ALTER TABLE users ADD COLUMN IF NOT EXISTS description varchar(200);

CREATE TABLE IF NOT EXISTS game (
  game_id SERIAL PRIMARY KEY,
  name varchar(60) UNIQUE NOT NULL,
  user_generated BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS game_url_image (
  game_id BIGINT PRIMARY KEY REFERENCES game(game_id),
  url_image TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS game_image(
  game_id BIGINT PRIMARY KEY REFERENCES game(game_id),
  image BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS tournament (
  tournament_id SERIAL PRIMARY KEY,
  status VARCHAR(10) DEFAULT 'NEW',
  name varchar(100) NOT NULL,
  game_id BIGINT REFERENCES game(game_id),
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS player (
  player_id SERIAL PRIMARY KEY,
  name varchar(25) NOT NULL,
  user_id BIGINT REFERENCES users(user_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  seed INTEGER,
  standing INTEGER
);

INSERT INTO player (player_id,name)
SELECT -1, 'BYE'
WHERE
  NOT EXISTS (
      SELECT player_id FROM player WHERE player_id = -1
  );

INSERT INTO game (game_id,name)
  SELECT -1, 'Game not specified'
  WHERE
    NOT EXISTS (
        SELECT game_id FROM game WHERE game_id = -1
    );


CREATE TABLE IF NOT EXISTS match (
  match_id BIGINT NOT NULL,
  tournament_id BIGINT REFERENCES tournament(tournament_id) ON DELETE CASCADE,
  home_player_id BIGINT REFERENCES player(player_id),
  away_player_id BIGINT REFERENCES player(player_id),
  home_player_score INTEGER DEFAULT 0,
  away_player_score INTEGER DEFAULT 0,
  standing INTEGER,
  next_match_id BIGINT,
  next_match_home BOOLEAN,
  UNIQUE (match_id, tournament_id),
  FOREIGN KEY (next_match_id,tournament_id) REFERENCES match (match_id,tournament_id)
);

CREATE TABLE IF NOT EXISTS ranking (
  ranking_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL,
  game_id BIGINT REFERENCES game(game_id),
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS ranking_tournaments (
  ranking_id BIGINT NOT NULL REFERENCES ranking(ranking_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
   awarded_points BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS ranking_players (
  ranking_id BIGINT NOT NULL REFERENCES ranking(ranking_id),
  user_id BIGINT REFERENCES users(user_id),
  points BIGINT
);

CREATE TABLE IF NOT EXISTS user_image (
  user_id BIGINT PRIMARY KEY REFERENCES users(user_id),
  image BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS user_favorite_game (
  user_id BIGINT NOT NULL REFERENCES users(user_id),
  game_id BIGINT NOT NULL REFERENCES game(game_id),
  PRIMARY KEY (user_id, game_id)
);

CREATE TABLE IF NOT EXISTS user_follow (
  user_follower_id BIGINT REFERENCES users(user_id),
  user_followed_id BIGINT REFERENCES users(user_id),
  PRIMARY KEY (user_follower_id, user_followed_id)
);

CREATE TABLE IF NOT EXISTS notification (
  notification_id SERIAL PRIMARY KEY,
  user_id BIGINT REFERENCES users(user_id),
  type VARCHAR(40) NOT NULL,
  is_read BOOLEAN DEFAULT FALSE,
  date TIMESTAMP,
  description TEXT
);

CREATE TABLE comment (
  comment_id SERIAL PRIMARY KEY,
  creator_id BIGINT REFERENCES users(user_id),
  date TIMESTAMP,
  parent BIGINT REFERENCES comment(comment_id),
  comment VARCHAR(280)
);

CREATE TABLE IF NOT EXISTS inscription (
  user_id BIGINT REFERENCES users(user_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  PRIMARY KEY (user_id, tournament_id)
);