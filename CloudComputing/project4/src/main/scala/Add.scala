import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import java.io.PrintWriter

object Add {
  val rows = 100
  val columns = 100

  case class Block ( data: Array[Double] ) {

    override
    def toString (): String = {
      var s = "\n"
      for ( i <- 0 until rows ) {
        for ( j <- 0 until columns )
          s += "\t%.3f".format(data(i*rows+j))
        s += "\n"
      }
      s
    }
  }

  /* Convert a list of triples (i,j,v) into a Block */
  def toBlock(triples: List[(Int, Int, Double)]): Block = {
    /* ... */
    val data: Array[Double] = new Array[Double](rows*columns)
    for ((i,j,v) <- triples) {
                data(i*rows+j) = v
      }
    Block(data)
  }

  /* Add two Blocks */
  def blockAdd ( m: Block, n: Block ): Block = {
    /* ... */
     val dataAdd: Array[Double] = new Array[Double](rows*columns)

        for (i <- 0 until rows) {
          for (j <- 0 until columns){
              dataAdd(i*rows+j) = m.data(i*rows+j) + n.data(i*rows+j)
          }
        }
//    val update = Block.apply(dataAdd)
//    update
    Block(dataAdd)
  }

  /* Read a sparse matrix from a file and convert it to a block matrix */
  def createBlockMatrix ( sc: SparkContext, file: String ): RDD[((Int,Int),Block)] = {
    /* ... */
    val Sparse = sc.textFile(file).map( line =>{
              val m = line.split(",")
              (m(0).toInt, m(1).toInt, m(2).toDouble)
          })
     val gentriple = Sparse.map(
       Sparse => ((Sparse._1 / rows, Sparse._2 / columns),
        List((Sparse._1 % rows, Sparse._2 % columns, Sparse._3))))
    val reduce  = gentriple.reduceByKey( (k,v)=> k ++ v)
    val blockGen = reduce.map(listGen => (listGen._1,toBlock(listGen._2)))

    blockGen
  }


  def main ( args: Array[String] ) {
    /* ... */
    val conf = new SparkConf().setAppName("Add")
    val sc = new SparkContext(conf)

    //input matrix
    val createBlockMatrixM = createBlockMatrix(sc,args(0)) //return block matrix - M
    val createBlockMatrixN = createBlockMatrix(sc,args(1)) //return block matrix - N


    val JoinMN = createBlockMatrixM.join(createBlockMatrixN)
    val res = JoinMN.map(m=>(m._1,blockAdd(m._2._1,m._2._2)))

    res.saveAsTextFile(args(2))
//    createBlockMatrixM.saveAsTextFile(args(2))
    sc.stop()

  }
}
