DROP TABLE IF EXISTS recipes;

CREATE OR REPLACE FUNCTION update_timestamp_column() 
RETURNS TRIGGER AS '
BEGIN
   NEW.updated_at = now(); 
   RETURN NEW;
END;
' language 'plpgsql';

CREATE TABLE IF NOT EXISTS recipes (
  id SERIAL PRIMARY KEY,
  title varchar(100) NOT NULL,
  making_time varchar(100) NOT NULL,
  serves varchar(100) NOT NULL,
  ingredients varchar(300) NOT NULL,
  cost integer NOT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER updatechangetimestamp BEFORE UPDATE 
ON recipes FOR EACH ROW EXECUTE PROCEDURE 
update_timestamp_column();