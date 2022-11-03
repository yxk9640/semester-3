import org.apache.spark._
import org.apache.spark.rdd._

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Graph {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Graph")
    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)

    // A graph is a dataset of vertices, where each vertex is a triple
    //   (group,id,adj) where id is the vertex id, group is the group id
    //   (initially equal to id), and adj is the list of outgoing neighbors
    var graph: RDD[ ( Long, Long, List[Long] ) ]
       = sc.textFile(args(0)).map(
        line => {
          val m = line.split(",")
          ( m(0).toLong, m(0).toLong, m.slice(1,m.length).map(_.toLong).toList )
        })/* put your code here */      // read the graph from the file args(0)


    //graph._1 -> groupNum graph._2 -> Id
//    def Scratch(graph:RDD[( Long,Long,List[(Long)])] ): RDD[(Long,Long)] ={
//      val g:RDD[(Long,Long)] = graph.flatMap( node => node._3.map(adj=>(adj,node._1) ))
//
//     g
//    }

    for ( i <- 1 to 5 ) {
       // For each vertex (group,id,adj) generate the candidate (id,group)
       //    and for each x in adj generate the candidate (x,group).
       // Then for each vertex, its new group number is the minimum candidate
      //------- use flatmap --------
       val groups: RDD[ ( Long, Long ) ]
          = graph.flatMap( node => node._3.map(adj=>(adj,Math.min(node._1,adj) )))
//      graph./* put your code here */ //created only group for ID not for adj



//---------      groups -> (group, vertexID) ----------
       // reconstruct the graph using the new group numbers
       graph = groups.map( createG => (createG._2,createG._1,List(createG._2)) )/* put your code here */
      groups.saveAsTextFile("forloop/"+i)
    }

    // print the group sizes
//    graph.saveAsTextFile("graph/output")/* put your code here */
   val reduceG = graph.map( nodes => (nodes._2, nodes._1 ) )
    reduceG.reduceByKey( (k,v)=> k + v ).saveAsTextFile("output")


    sc.stop()
  }
}


/// Comments for update
// Unable to print minimum of the group ID