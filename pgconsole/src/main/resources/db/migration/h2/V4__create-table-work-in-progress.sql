-- Table: user_wip

-- DROP TABLE user_wip;

CREATE TABLE user_wip
(
  id integer NOT NULL,
  id_user varchar(50),
  id_connection integer NOT NULL,
  code text NULL,
  last_updated timestamp NOT NULL,
  CONSTRAINT user_wip_pkey PRIMARY KEY (id),
  CONSTRAINT fk_user_wip_user FOREIGN KEY (id_user)
      REFERENCES users (username) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_user_wip_connection FOREIGN KEY (id_connection)
      REFERENCES connection (id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
