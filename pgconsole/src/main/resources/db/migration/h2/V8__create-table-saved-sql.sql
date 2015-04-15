
CREATE TABLE saved_sql
(
  id integer NOT NULL,
  id_user varchar(50),
  id_connection integer NOT NULL,
  code text NULL,
  date_created timestamp NOT NULL,
  CONSTRAINT saved_sql_pkey PRIMARY KEY (id),
  CONSTRAINT fk_user FOREIGN KEY (id_user)
      REFERENCES users (username) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_saved_sql_user_connection FOREIGN KEY (id_connection)
      REFERENCES connection (id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 


create sequence saved_sql_seq start with 1;
ALTER TABLE saved_sql ALTER COLUMN id SET DEFAULT nextval('saved_sql_seq');



CREATE TABLE tag
(
  id integer NOT NULL,
  id_user varchar(50),
  name varchar(255),
  CONSTRAINT tag_pkey PRIMARY KEY (id),
  CONSTRAINT fk_tag_user FOREIGN KEY (id_user)
      REFERENCES users (username) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 


create sequence tag_seq start with 1;
ALTER TABLE tag ALTER COLUMN id SET DEFAULT nextval('tag_seq');
 


CREATE TABLE saved_sql_tag
(
  id_saved_sql integer not null,
  id_tag integer not null,
  CONSTRAINT fk_id_saved_sql FOREIGN KEY (id_saved_sql)
      REFERENCES saved_sql (id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_tag FOREIGN KEY (id_tag)
      REFERENCES tag (id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 
