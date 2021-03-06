

-- Table: server_group

-- DROP TABLE server_group;

CREATE TABLE server_group
(
  id integer NOT NULL,
  name varchar(255) NOT NULL,
  id_user varchar(50),
  CONSTRAINT server_group_pkey PRIMARY KEY (id),
  CONSTRAINT fk_server_group_user FOREIGN KEY (id_user)
      REFERENCES users (username)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
-- Table: connection

-- DROP TABLE connection;


CREATE TABLE connection
(
  id integer NOT NULL,
  database varchar(255),
  host varchar(255),
  label varchar(255),
  password varchar(255),
  port varchar(255),
  username varchar(255),
  id_server_group integer,
  id_user varchar(50),
  CONSTRAINT connection_pkey PRIMARY KEY (id),
  CONSTRAINT fk_connection_user FOREIGN KEY (id_user)
      REFERENCES users (username)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_connection_server_group FOREIGN KEY (id_server_group)
      REFERENCES server_group (id)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: server_group_connections

-- DROP TABLE server_group_connections;

CREATE TABLE server_group_connections
(
  server_group_id integer NOT NULL,
  connections_id integer NOT NULL,
  CONSTRAINT fk_server_group FOREIGN KEY (server_group_id)
      REFERENCES server_group (id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_connection FOREIGN KEY (connections_id)
      REFERENCES connection (id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_connection UNIQUE (connections_id)
);
