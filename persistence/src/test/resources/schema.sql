CREATE TYPE TEXT AS VARCHAR (1000000);

CREATE TABLE IF NOT EXISTS users (
user_id IDENTITY PRIMARY KEY,
user_name varchar(100) UNIQUE,
password varchar(100)
);

CREATE TABLE IF NOT EXISTS game (
  game_id IDENTITY PRIMARY KEY,
  name varchar(60) UNIQUE NOT NULL,
  user_generated BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS game_url_image (
  game_id BIGINT REFERENCES game(game_id),
  url_image TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS game_image(
  game_id BIGINT REFERENCES game(game_id),
  image binary NOT NULL
);


CREATE TABLE IF NOT EXISTS tournament (
  tournament_id IDENTITY PRIMARY KEY,
  status VARCHAR(10) DEFAULT 'NEW',
  name varchar(100) NOT NULL,
  game_id BIGINT REFERENCES game(game_id),
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS player (
  player_id IDENTITY PRIMARY KEY,
  name varchar(25) NOT NULL,
  user_id BIGINT REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS participates_in (
  player_id BIGINT REFERENCES player(player_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  seed INTEGER,
  standing INTEGER
);


MERGE INTO player AS P USING (VALUES -1, 'bye') AS S (player_id, name) ON ( P.player_id = S.player_id)
WHEN MATCHED THEN UPDATE SET player_id = -1
  WHEN NOT MATCHED THEN
  INSERT (player_id, name) values (-1, 'bye');
  
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
  ranking_id IDENTITY PRIMARY KEY,
  name varchar(100) NOT NULL,
  game_id BIGINT REFERENCES game(game_id)
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
