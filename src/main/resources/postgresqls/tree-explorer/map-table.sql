 
 SELECT
    c.relname,
    n.nspname,
    u.usename AS relowner,
    pg_catalog.obj_description(c.oid,
    'pg_class') AS relcomment,
    (SELECT
        spcname 
    FROM
        pg_catalog.pg_tablespace pt 
    WHERE
        pt.oid=c.reltablespace) AS tablespace    
FROM
    pg_catalog.pg_class c    
LEFT JOIN
    pg_catalog.pg_user u 
        ON u.usesysid = c.relowner    
LEFT JOIN
    pg_catalog.pg_namespace n 
        ON n.oid = c.relnamespace    
WHERE
    c.relkind = 'r'    
    AND n.nspname = :param_schema   
    AND n.oid = c.relnamespace
