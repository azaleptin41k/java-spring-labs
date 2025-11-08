-- Заполняем команды
INSERT INTO teams (name, city) VALUES ('Real Madrid', 'Madrid') ON CONFLICT (name) DO NOTHING;
INSERT INTO teams (name, city) VALUES ('FC Barcelona', 'Barcelona') ON CONFLICT (name) DO NOTHING;

-- Заполняем игроков
INSERT INTO players (name, position, team_id) VALUES ('Karim Benzema', 'Forward', 1);
INSERT INTO players (name, position, team_id) VALUES ('Luka Modric', 'Midfielder', 1);
INSERT INTO players (name, position, team_id) VALUES ('Robert Lewandowski', 'Forward', 2);