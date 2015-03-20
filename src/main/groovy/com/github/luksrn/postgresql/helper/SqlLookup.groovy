package com.github.luksrn.postgresql.helper

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.Cache.ValueWrapper
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver

class SqlLookup {
	private static final String UNDERNEATH_SQL_PATTERN = File.separator + "**" + File.separator + "*.sql";
	
	private String rootDirectory;
	 
	private Cache cache;
	
	private ResourcePatternResolver resourcePatternResolver;
	
	private final static Logger LOG = LoggerFactory.getLogger(SqlLookup.class);
	
	/**
	 *
	 * @param cache some org.springframework.cache.Cache impl.
	 * @param rootPath sql queries should be stored in src/main/resources. If src/main/sql folder is used, so rootPath is 'sql'.
	 */
	SqlLookup(Cache cache, String root){
		this.cache = cache;
		this.rootDirectory = root;
		resourcePatternResolver = new PathMatchingResourcePatternResolver();
		internalScanSqlFiles();
		
	}
	
	private void internalScanSqlFiles() {
		try { 
			Resource[] resources = resourcePatternResolver.getResources(rootDirectory + UNDERNEATH_SQL_PATTERN );
			String baseResourceAsString = resourcePatternResolver.getResource(rootDirectory).getURI().toString() + File.separator;
			
			for ( Resource r : resources ){
				String key = findKeyNameForResource(r, baseResourceAsString);
				String query = r.getFile().text;
				if ( LOG.isInfoEnabled() ){
					LOG.info( "Putting " + key + " key at Sql cache"  );
				}
				cache.put(key , query );
			}
			LOG.info("SqlCache size: " + resources.length );  
		} catch (IOException e){
			throw new RuntimeException(e.getMessage());
		}
	}
 
		private String findKeyNameForResource(Resource r, String baseResourceAsString) throws IOException {
		return r.getURI().toString().replaceFirst( baseResourceAsString, "") ;
	}
 
	String lookup( String path ) {
		
		if ( path == null ){
			throw new FileNotFoundException("Null path is not valid!");
		}
		
		ValueWrapper queryWrapped = cache.get( path );
		
		if( queryWrapped != null ){
			return (String) queryWrapped.get();
		} else {
			throw new FileNotFoundException("No Sql found at " + rootDirectory  + UNDERNEATH_SQL_PATTERN + " with name " + path + "");
		}
	}
}
