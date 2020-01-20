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

package com.scleradb.plugin.dbms.rdbms.postgresql.driver

import java.sql.{PreparedStatement, ResultSet, Blob}
import java.io.ByteArrayInputStream

import com.scleradb.dbms.location.Location
import com.scleradb.sql.mapper.SqlMapper
import com.scleradb.dbms.rdbms.driver.SqlDriver

class PostgreSQLDriver(
    override val location: Location,
    override val sqlMapper: SqlMapper,
    override val jdbcUrl: String,
    override val configProps: List[(String, String)]
) extends SqlDriver {
    private lazy val byteStore: String =
        location.annotTableName("SCLERA_BYTESTORE")

    override def createByteStore(): Unit = executeUpdate(
        "CREATE TABLE " + byteStore + "(ID VARCHAR(80), PAYLOAD BYTEA)"
    )

    override def dropByteStore(): Unit = executeUpdate(
        "DROP TABLE " + byteStore + ""
    )

    override def storeBytes(id: String, bytes: Array[Byte]): Unit = {
        val input: ByteArrayInputStream = new ByteArrayInputStream(bytes)

        conn.setAutoCommit(false)

        var sql: String = "INSERT INTO " + byteStore + " VALUES(?, ?)"
        val prepared: PreparedStatement = conn.prepareStatement(sql)

        try {
            prepared.setString(1, id)
            prepared.setBinaryStream(2, input, bytes.size)
            prepared.executeUpdate()
        } catch { case (e: Throwable) =>
            conn.rollback()
            throw e
        } finally {
            input.close()
            prepared.close()
        }

        conn.commit()
    }

    override def readBytes(id: String): Array[Byte] = {
        var sql: String = "SELECT PAYLOAD FROM " + byteStore + " WHERE ID = ?"
        val prepared: PreparedStatement = conn.prepareStatement(sql)

        try {
            prepared.setString(1, id)
            val rs: ResultSet = prepared.executeQuery()
            try {
                if( rs.next() ) rs.getBytes(1) else {
                    throw new IllegalArgumentException(
                        "Cannot read the bytes for \"" + id + "\""
                    )
                }
            } finally rs.close()
        } finally prepared.close()
    }

    override def removeBytes(id: String): Unit = {
        var sql: String = "DELETE FROM " + byteStore + " WHERE ID = ?"
        val prepared: PreparedStatement = conn.prepareStatement(sql)
        try {
            prepared.setString(1, id)
            prepared.executeUpdate()
        } finally {
            prepared.close()
        }
    }
}
