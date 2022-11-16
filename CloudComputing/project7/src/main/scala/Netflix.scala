import org.apache.spark.{SparkContext, _}
import org.apache.spark.sql._
import org.apache.spark.sql.functions


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
    //generate avg of ratings
    dataFrame.createOrReplaceTempView("review")
    val avgRating = spark.sql(" SELECT  user, floor((SUM(ratings)/COUNT(ratings))*10)/10 as rating FROM review GROUP BY user ")
//    avgRating.show()
    avgRating.createOrReplaceTempView("reviewFreq")

    //count ratings
    val res = spark.sql("SELECT rating,COUNT(rating) as count FROM reviewFreq GROUP BY rating ORDER BY rating ASC ")
//    res.show()
    res.collect.foreach(x=> println(x.get(0)+" "+x.get(1)))

    //    val freqRating = spark.sql()

  }
}
