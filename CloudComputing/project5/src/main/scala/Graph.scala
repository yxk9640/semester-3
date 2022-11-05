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


    for ( i <- 1 to 5 ) {
       // For each vertex (group,id,adj) generate the candidate (id,group)
       //    and for each x in adj generate the candidate (x,group).
       // Then for each vertex, its new group number is the minimum candidate
      //------- use flatmap --------
       val groups: RDD[ ( Long, Long ) ]
          = graph.flatMap(node => node._3.map(adj=>( adj, Math.min(adj,node._1) ))).
         union(graph.map(vertex => (vertex._2,vertex._1 ))).distinct()



//      graph./* put your code here */ //created only group for ID not for adj


//---------      groups -> (group, vertexID) ----------
       // reconstruct the graph using the new group numbers - graphs._2
//       graph = groups.join(graph.map( ))
      graph=groups.map( createGraph => (createGraph._2,createGraph._1,List(createGraph._1)
       )) /* put your code here */


      groups.saveAsTextFile("forloop/"+i)
      graph.saveAsTextFile("graph/"+i)
    }

    // print the group sizes
//    graph.saveAsTextFile("graph/output")/* put your code here */
   val reduceG = graph.map( nodes => (nodes._1, 1 ) )
    reduceG.reduceByKey( (k,v)=> k + v ).saveAsTextFile("output")

    sc.stop()
  }
}

// How to update the graph with new group number and create a new one

/// Comments for update
// Unable to print minimum of the group ID


//graph._1 -> groupNum graph._2 -> Id
//    def Scratch(graph:RDD[( Long,Long,List[(Long)])] ): RDD[(Long,Long)] ={
//      val g:RDD[(Long,Long)] = graph.flatMap( node => node._3.map(adj=>( adj, Math.min(adj,node._1) ))
//
//         case node =>
//           if (node._3.length>0) node._3.map(adj => (adj, Math.min(adj,node._1) ))
//           else (node._2,node._1)
//     g
//    }

//    def getCandi(graph: RDD[(Long,Long,List[(Long)])]): RDD[(Long,Long)] = {
//      val res = graph.map(vertex => (vertex._2,vertex._1 )).union(graph.flatMap(node => node._3.map(adj=>( adj, Math.min(adj,node._1) ))))
//      res
//    }
