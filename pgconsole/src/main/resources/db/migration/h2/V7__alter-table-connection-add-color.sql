ALTER TABLE connection ADD COLUMN color character varying(7);

update connection set color = 'ffffff';