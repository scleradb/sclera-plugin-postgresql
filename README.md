# Sclera - PostgreSQL Connector

[![Build Status](https://travis-ci.org/scleradb/sclera-plugin-postgresql.svg?branch=master)](https://travis-ci.org/scleradb/sclera-plugin-postgresql)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.scleradb/sclera-plugin-postgresql_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.scleradb/sclera-plugin-postgresql_2.13)
[![scaladoc](https://javadoc.io/badge2/com.scleradb/sclera-plugin-postgresql_2.13/scaladoc.svg)](https://javadoc.io/doc/com.scleradb/sclera-plugin-postgresql_2.13)

This component enables Sclera to work with your data stored in [PostgreSQL](http://www.postgresql.org).

You just need to link your PostgreSQL database with Sclera, then import the metadata of select tables within the database. All this gets done in a couple of commands -- and enables you to include these tables within your Sclera queries.

The link uses the [PostgreSQL JDBC Driver](http://jdbc.postgresql.org), which is downloaded as a part of the installation of this component.

Details on how to link your PostgreSQL source to with Sclera can be found in the [Sclera Database System Connection Reference](https://www.scleradb.com/docs/setup/dbms/#connecting-to-postgresql) document.
