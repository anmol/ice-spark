import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType, ArrayType}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder.master("local[*]").getOrCreate()

val schema1 = StructType(
  Array(
    StructField("id", StringType),
    StructField("name", StringType),
    StructField("scores", ArrayType(StringType))
  )
)
val df1 = spark.createDataFrame(spark.sparkContext.parallelize(
  Seq(
    Row("1", "Berlin", Seq("20","30","40")),
    Row("2", "Tokyo", Seq("20","10","5")),
    Row("3", "Tokyo", Seq("2","20","3")),
    Row("4", "Tokyo", null))), schema1)

df1.show(false)
val df2 = df1.withColumn("sub1", col("scores")(0))
  .withColumn("sub2", col("scores")(1))
  .withColumn("sub3", col("scores")(2))

df2.printSchema

df2.show(false)