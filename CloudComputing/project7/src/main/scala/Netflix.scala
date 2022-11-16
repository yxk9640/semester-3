import org.apache.spark.{SparkContext, _}
import org.apache.spark.sql._

object Netflix {

  def main ( args: Array[ String ]): Unit = {
    val conf = new SparkConf().setAppName("Netflix")
    val sparkContext = new SparkContext(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
/* ... */


    val line = sparkContext.textFile(args(0)).filter(line => !line.contains(":"))

    val dataFrame = line.map(
      line => {
        val in = line.split(",")
        (in(0).toInt , in(1).toDouble) }).toDF("user","ratings")

//    dataFrame.printSchema()
    dataFrame.createOrReplaceTempView("review")
//      SELECT user, sum(ratings) from dataFrame group by user asc
//    dataFrame.select($"user",$"ratings").f
    val avgRating = spark.sql(" SELECT  user, round(AVG(ratings),1) as rating FROM review GROUP BY user ")
    avgRating.show()
    avgRating.createOrReplaceTempView("reviewFreq")

    val res = spark.sql("SELECT rating,COUNT(rating) as count FROM reviewFreq GROUP BY rating ORDER BY rating ASC ")
    res.show()

    //    val freqRating = spark.sql()

  }
}
