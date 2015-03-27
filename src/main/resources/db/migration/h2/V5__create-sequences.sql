-- Add sequence for server_group
create sequence server_group_seq start with 5;
ALTER TABLE server_group ALTER COLUMN id SET DEFAULT nextval('server_group_seq');

-- add sequence for connections

create sequence connection_seq start with 5;
ALTER TABLE connection ALTER COLUMN id SET DEFAULT nextval('connection_seq');

 
-- add sequence for userwip

create sequence user_wip_seq start with 5;
ALTER TABLE user_wip ALTER COLUMN id SET DEFAULT nextval('user_wip_seq');