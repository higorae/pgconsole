 SELECT
    pc.conname,
    pg_catalog.pg_get_constraintdef(pc.oid,
    true) AS consrc,
    pc.contype,
    CASE 
        WHEN pc.contype='u' 
        OR pc.contype='p' THEN ( SELECT
            indisclustered 
        FROM
            pg_catalog.pg_depend pd,
            pg_catalog.pg_class pl,
            pg_catalog.pg_index pi 
        WHERE
            pd.refclassid=pc.tableoid 
            AND pd.refobjid=pc.oid 
            AND pd.objid=pl.oid 
            AND pl.oid=pi.indexrelid ) 
        ELSE NULL 
    END AS indisclustered 
FROM
    pg_catalog.pg_constraint pc 
WHERE
    pc.conrelid = (
        SELECT
            oid 
        FROM
            pg_catalog.pg_class 
        WHERE
            relname=:param_table 
            AND relnamespace = (
                SELECT
                    oid 
                FROM
                    pg_catalog.pg_namespace 
                WHERE
                    nspname=:param_schema
            )
        ) 
    ORDER BY
        1;