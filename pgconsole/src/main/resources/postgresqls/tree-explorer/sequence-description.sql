
SELECT c.relname AS seqname, s.*,
pg_catalog.obj_description(s.tableoid, 'pg_class') AS seqcomment,
u.usename AS seqowner, n.nspname
FROM public.sequencia_teste AS s, pg_catalog.pg_class c, pg_catalog.pg_user u, pg_catalog.pg_namespace n
WHERE c.relowner=u.usesysid AND c.relnamespace=n.oid
AND c.relname = :param_sequence  AND c.relkind = 'S' AND n.nspname= :param_schema
AND n.oid = c.relnamespace;