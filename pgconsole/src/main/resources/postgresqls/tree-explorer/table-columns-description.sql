     
SELECT
    a.attname,
    a.attnum,
    pg_catalog.format_type(a.atttypid,
    a.atttypmod) as type,
    a.atttypmod,
    a.attnotnull,
    a.atthasdef,
    pg_catalog.pg_get_expr(adef.adbin,
    adef.adrelid,
    true) as adsrc,
    a.attstattarget,
    a.attstorage,
    t.typstorage,
    (    SELECT
        1 
    FROM
        pg_catalog.pg_depend pd,
        pg_catalog.pg_class pc    
    WHERE
        pd.objid=pc.oid    
        AND pd.classid=pc.tableoid    
        AND pd.refclassid=pc.tableoid    
        AND pd.refobjid=a.attrelid    
        AND pd.refobjsubid=a.attnum    
        AND pd.deptype='i'    
        AND pc.relkind='S'    ) IS NOT NULL AS attisserial,
    pg_catalog.col_description(a.attrelid,
    a.attnum) AS comment    
FROM
    pg_catalog.pg_attribute a 
LEFT JOIN
    pg_catalog.pg_attrdef adef    
        ON a.attrelid=adef.adrelid    
        AND a.attnum=adef.adnum    
LEFT JOIN
    pg_catalog.pg_type t 
        ON a.atttypid=t.oid    
WHERE
    a.attrelid = (
        SELECT
            oid 
        FROM
            pg_catalog.pg_class 
        WHERE
            relname = :param_table    
            AND relnamespace = (
                SELECT
                    oid 
                FROM
                    pg_catalog.pg_namespace 
                WHERE
                    nspname = :param_schema
            )
        )    
        AND a.attnum > 0 
        AND NOT a.attisdropped    
    ORDER BY
        a.attnum; 