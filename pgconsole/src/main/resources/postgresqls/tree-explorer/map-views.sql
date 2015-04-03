SELECT table_name
    FROM information_schema.tables 
WHERE table_type = 'VIEW' 
    AND table_schema = :param_schema