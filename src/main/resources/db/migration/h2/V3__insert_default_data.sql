-- Admin Admin
INSERT INTO users(
            enabled, username, password )
    VALUES (true, 'admin', '$2a$10$TjyhAQ6EFeDRNkzkMm.WZ.kLMtVBR8mhYuPFeKFSHOep66MRVis8a');
     
    
INSERT INTO server_group(
            id, name, id_user)
    VALUES (1, 'Servers', 'admin');

      
   