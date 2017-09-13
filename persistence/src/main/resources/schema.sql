CREATE TABLE IF NOT EXISTS users (
userid SERIAL PRIMARY KEY,
username varchar(100),
password varchar(100)
);

CREATE TABLE IF NOT EXISTS tournament (
tournament_id SERIAL PRIMARY KEY,
name varchar(100) NOT NULL,
maxParticipants INTEGER NOT NULL,
cantParticipants INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS player (
  player_id SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS participates_in (
  player_id SERIAL REFERENCES player(player_id),
  tournament_id SERIAL REFERENCES tournament(tournament_id),
  position INTEGER DEFAULT 0;
);

CREATE TABLE IF NOT EXISTS match (
  match_id INTEGER NOT NULL,
  tournament_id BIGINT REFERENCES tournament(tournament_id) ON DELETE CASCADE,
  local_player_id BIGINT REFERENCES player(player_id),
  visitor_player_id BIGINT REFERENCES player(player_id),
  local_player_score INTEGER,
  visitor_player_score INTEGER,
  next_match_id INTEGER,
  PRIMARY KEY (match_id, tournament_id)
);

ALTER TABLE match add FOREIGN KEY (next_match_id,tournament_id) REFERENCES match (match_id,tournament_id);
