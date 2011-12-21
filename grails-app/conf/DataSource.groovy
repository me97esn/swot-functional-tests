dataSource {
    pooled = true
    database.driver = "com.mysql.jdbc.Driver"
    database.dbname = "swot-brain"
    username = "root"
    password = "root"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://localhost:3306/swot"
            database.dbname = "swot"
            username = "root"
            password = "root"

        }
        dataSource_swotbrain {
            url = "jdbc:mysql://localhost:3306/swot-brain"
            dbCreate = "create" // one of 'create', 'create-drop', 'update', 'validate', ''
            database.dbname = "swot-brain"
            username = "root"
            password = "root"
        }
    }
    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:mysql://swottest.e3ecloud.com/swot"
            database.dbname = "swot"
            username = "dbadminuser"
            password = "dbadminpwd"

        }
        dataSource_swotbrain {
            dbCreate = "create" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://swottest.e3ecloud.com/swot-brain"
            database.dbname = "swot-brain"
            username = "dbadminuser"
            password = "dbadminpwd"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE"
            // For MySQL production scenarios enable the following settings
            //          pooled = true
            //          properties {
            //               minEvictableIdleTimeMillis=1800000
            //               timeBetweenEvictionRunsMillis=1800000
            //               numTestsPerEvictionRun=3
            //               testOnBorrow=true
            //               testWhileIdle=true
            //               testOnReturn=true
            //               validationQuery="SELECT 1"
            //          }
        }
    }
}
