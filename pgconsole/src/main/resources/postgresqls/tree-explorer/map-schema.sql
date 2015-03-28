SELECT pn.nspname, pu.rolname AS nspowner,
pg_catalog.obj_description(pn.oid, 'pg_namespace') AS nspcomment
FROM pg_catalog.pg_namespace pn
LEFT JOIN pg_catalog.pg_roles pu ON (pn.nspowner = pu.oid)
WHERE nspname NOT LIKE 'pg@_%' ESCAPE '@' AND nspname != 'information_schema'