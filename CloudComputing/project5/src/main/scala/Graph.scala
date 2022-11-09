import org.apache.spark._
import org.apache.spark.rdd._

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Graph {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Graph")
//    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)

    // A graph is a dataset of vertices, where each vertex is a triple
    //   (group,id,adj) where id is the vertex id, group is the group id
    //   (initially equal to id), and adj is the list of outgoing neighbors
    var graph: RDD[(Long, Long, List[Long])]
    = sc.textFile(args(0)).map(
      line => {
        val m = line.split(",")
        (m(0).toLong, m(0).toLong, m.drop(1).map(_.toLong).toList)
      }) /* put your code here */      // read the graph from the file args(0)

//Not good
    val graphCache = graph.map(g => (g._2, g._3)).collectAsMap()
    sc.broadcast(graphCache)

    for ( i <- 1 to 5 ) {
       // For each vertex (group,id,adj) generate the candidate (id,group)
       //    and for each x in adj generate the candidate (x,group).
       // Then for each vertex, its new group number is the minimum candidate
      //------- use flatmap --------
//      = graph.flatMap(node => node._3.map(adj=>( adj, Math.min(adj,node._1) ))).
//       val groups: RDD[ ( Long, Long ) ]
//          = graph.flatMap(node => node._3.map(adj=>( adj, node._1 ))).
//         union(graph.map(vertex => (vertex._2,vertex._1 ))).distinct()/* put your code here */
//Not good 2
      val groups: RDD[(Long, Long)] = graph.map(g => {
        val vertices = g._3 ++ List(g._2)
        vertices.map(v => (v, g._1))
      }).flatMap(x => x)


//      val groupKey = groups.map(g => (g._1 -> g._2))  // transform groups as (Key,Value)
//      val graphKey = graph.map(gr => (gr._2 -> (gr._1,gr._3))) //transform graph as (Key,Value)
//      val join = groupKey.join(graphKey) // join groups and graph based on key
//      join.map( j => ( Math.min( j._2._1, j._2._2._1 ) , j._1, j._2._2._2 )) // create graph with updated group


      //reconstruct the graph//Not good 3
      graph = groups.groupByKey().map(v => (v._2.min, v._1, graphCache(v._1)))/* put your code here */
    }
    //    graph.map( nodes => (nodes._1, 1 ) ).reduceByKey( (k,v)=> k + v ).sortBy(k=>k._1).map( pri => println(pri._1,pri._2))
    graph.map( nodes => (nodes._1, 1 ) ).reduceByKey( (k,v)=> k + v ).foreach(println)

    sc.stop()
  }
}
