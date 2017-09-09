CREATE TABLE IF NOT EXISTS users (
userid SERIAL PRIMARY KEY,
username varchar(100),
password varchar(100)
);

CREATE TABLE IF NOT EXISTS tournament (
tournament_id SERIAL PRIMARY KEY,
name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS player (
  player_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS participates_in (
  player_id SERIAL REFERENCES player(player_id),
  tournament_id SERIAL REFERENCES tournament(tournament_id)
);

CREATE TABLE IF NOT EXISTS match (
  match_id SERIAL PRIMARY KEY,
  name varchar(10) NOT NULL,
  tournament_id SERIAL REFERENCES tournament(tournament_id),
  local_player_id SERIAL REFERENCES player(player_id),
  visitor_player_id SERIAL REFERENCES player(player_id),
  local_player_score INTEGER,
  visitor_player_score INTEGER
);