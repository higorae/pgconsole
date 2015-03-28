SELECT table_name, table_schema, table_type  FROM information_schema.tables 
WHERE table_type in ( 'BASE TABLE' , 'VIEW' )
 AND table_schema not in ( 
				'pg_toast',
				'pg_temp_1',
				'pg_toast_temp_1',
				'pg_catalog',
				'information_schema' )
AND table_name ilike '%' || ? || '%' order by table_type