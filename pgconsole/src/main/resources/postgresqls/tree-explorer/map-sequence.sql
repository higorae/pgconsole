
SELECT c.relname AS seqname, u.usename AS seqowner, pg_catalog.obj_description(c.oid, 'pg_class') AS seqcomment,
(SELECT spcname FROM pg_catalog.pg_tablespace pt WHERE pt.oid=c.reltablespace) AS tablespace
FROM pg_catalog.pg_class c, pg_catalog.pg_user u, pg_catalog.pg_namespace n
WHERE c.relowner=u.usesysid AND c.relnamespace=n.oid
AND c.relkind = 'S' AND n.nspname= :param_schema ORDER BY seqname;