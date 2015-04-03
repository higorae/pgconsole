
SELECT c.relname, n.nspname, pg_catalog.pg_get_userbyid(c.relowner) AS relowner,
pg_catalog.pg_get_viewdef(c.oid, true) AS vwdefinition,
pg_catalog.obj_description(c.oid, 'pg_class') AS relcomment
FROM pg_catalog.pg_class c
LEFT JOIN pg_catalog.pg_namespace n ON (n.oid = c.relnamespace)
WHERE (c.relname = :param_view ) AND n.nspname=  :param_schema;