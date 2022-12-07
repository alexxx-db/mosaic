package com.databricks.labs.mosaic.expressions.raster

import com.databricks.labs.mosaic.core.geometry.api.GeometryAPI.JTS
import com.databricks.labs.mosaic.core.index.H3IndexSystem
import com.databricks.labs.mosaic.test.SparkSuite
import org.apache.spark.sql.QueryTest
import org.apache.spark.sql.catalyst.expressions.CodegenObjectFactoryMode
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.test.{SharedSparkSession, SharedSparkSessionGDAL}

import scala.util.Try

class ST_SubdatasetsTest extends QueryTest with SharedSparkSessionGDAL with ST_SubdatasetsBehaviors {

    //Hotfix for SharedSparkSession afterAll cleanup.
    override def afterAll(): Unit = Try(super.afterAll())

    private val noCodegen =
        withSQLConf(
            SQLConf.WHOLESTAGE_CODEGEN_ENABLED.key -> "false",
            SQLConf.CODEGEN_FACTORY_MODE.key -> CodegenObjectFactoryMode.NO_CODEGEN.toString,
        ) _


    // These tests are not index system nor geometry API specific.
    // Only testing one pairing is sufficient.
    if (System.getProperty("os.name") == "Linux") {
        test("Testing ST_Subdatasets with manual GDAL registration (H3, JTS).") { noCodegen { subdatasetsBehavior(H3IndexSystem, JTS) } }
    } else {
        logWarning("Skipping ST_Subdatasets test on non-Linux OS")
    }

}