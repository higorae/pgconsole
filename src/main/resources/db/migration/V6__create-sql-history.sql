
-- Table: sql_history

-- DROP TABLE sql_history;

CREATE TABLE sql_history
(
  id integer NOT NULL,  
  id_user character varying(50),
  id_connection integer NOT NULL,
  sql TEXT NOT NULL,
  date_created timestamp not null default now(),
  CONSTRAINT sql_history_pkey PRIMARY KEY (id),
  CONSTRAINT fk_sql_history_user FOREIGN KEY (id_user)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sql_history_connection FOREIGN KEY (id_connection)
      REFERENCES connection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION      
)
WITH (
  OIDS=FALSE
); 

-- Add sequence for server_group
create sequence sql_history_seq start with 5;
ALTER TABLE sql_history ALTER COLUMN id SET DEFAULT nextval('sql_history_seq');

 