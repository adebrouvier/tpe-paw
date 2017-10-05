/*CREATE TABLE IF NOT EXISTS users (
userid SERIAL PRIMARY KEY,
username varchar(100),
password varchar(100)
);*/

CREATE TABLE IF NOT EXISTS tournament (
  tournament_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS player (
  player_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS participates_in (
  player_id BIGINT REFERENCES player(player_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id),
  seed INTEGER
);

INSERT INTO player (player_id,name)
SELECT -1, 'BYE'
WHERE
  NOT EXISTS (
      SELECT player_id FROM player WHERE player_id = -1
  );

CREATE TABLE IF NOT EXISTS match (
  match_id BIGINT NOT NULL,
  tournament_id BIGINT REFERENCES tournament(tournament_id) ON DELETE CASCADE,
  home_player_id BIGINT REFERENCES player(player_id),
  away_player_id BIGINT REFERENCES player(player_id),
  home_player_score INTEGER DEFAULT 0,
  away_player_score INTEGER DEFAULT 0,
  next_match_id BIGINT,
  next_match_home BOOLEAN,
  UNIQUE (match_id, tournament_id),
  FOREIGN KEY (next_match_id,tournament_id) REFERENCES match (match_id,tournament_id)
);

CREATE TABLE IF NOT EXISTS ranking (
  ranking_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL,
  awarded_points BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS ranking_tournaments (
  ranking_id BIGINT NOT NULL REFERENCES ranking(ranking_id),
  tournament_id BIGINT REFERENCES tournament(tournament_id)
);

CREATE TABLE IF NOT EXISTS ranking_players (
  ranking_id BIGINT NOT NULL REFERENCES ranking(ranking_id),
  name varchar(100) NOT NULL,
  points BIGINT
);
