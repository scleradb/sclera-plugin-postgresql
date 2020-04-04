/**
* Sclera - PostgreSQL Connector
* Copyright 2012 - 2020 Sclera, Inc.
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.scleradb.plugin.dbms.rdbms.postgresql.location

import com.scleradb.exec.Schema

import com.scleradb.sql.mapper.SqlMapper

import com.scleradb.dbms.location.{LocationId, LocationPermit}
import com.scleradb.dbms.rdbms.driver.SqlDriver

import com.scleradb.dbms.rdbms.location.RdbmsLocation

import com.scleradb.plugin.dbms.rdbms.postgresql.driver.PostgreSQLDriver
import com.scleradb.plugin.dbms.rdbms.postgresql.mapper.PostgreSQLMapper

class PostgreSQL(
    override val schema: Schema,
    override val id: LocationId,
    override val dbName: String,
    override val dbSchemaOpt: Option[String],
    override val config: List[(String, String)],
    override val permit: LocationPermit
) extends RdbmsLocation {
    override val dbms: String = PostgreSQL.id
    override val isTemporary: Boolean = false

    private val jdbcDriverClass: Class[org.postgresql.Driver] =
        classOf[org.postgresql.Driver]

    override val param: String =
        if( dbName contains "/" ) dbName else "localhost/" + dbName

    override val sqlMapper: SqlMapper = new PostgreSQLMapper(this)
    override val url: String = "jdbc:postgresql://" + param

    override def driver: SqlDriver =
        new PostgreSQLDriver(this, sqlMapper, url, config)
}

object PostgreSQL {
    val id: String = "POSTGRESQL"
}
