CREATE TABLE IF NOT EXISTS quotes(
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  isin  CHAR(12)  NOT NULL,
  bid   DECIMAL(12,6),
  ask   DECIMAL(12,6)   NOT NULL
);

CREATE TABLE IF NOT EXISTS elvls(
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  isin  CHAR(12)  NOT NULL UNIQUE,
  elvl  DECIMAL(12,6)  NOT NULL
);