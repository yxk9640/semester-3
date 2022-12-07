import org.apache.spark.graphx.{Graph,Edge,EdgeTriplet,VertexId}
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object ConnectedComponents {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Connected Components")
    val sc = new SparkContext(conf)

    // A graph G is a dataset of vertices, where each vertex is (id,adj),
    // where id is the vertex id and adj is the list of outgoing neighbors
    var G: RDD[ ( Long, List[Long] ) ]
    // read the graph from the file args(0)
       = sc.textFile(args(0)).map(
      a => a.split(",")).map(
      vertex => (vertex(0).toLong, vertex.drop(1).map(_.toLong).toList))
    /* ... */


//Construct a graph
    // graph edges have attribute values 0
    val edges: RDD[Edge[Int]] =  G.map(
      vertex => vertex._2.map(destination => Edge(vertex._1, destination, 0))).flatMap(
      edge => edge)/* ... */

    // a vertex (id,group) has initial group equal to id
    val vertices: RDD[(Long,Long)] = G.map(vertex => (vertex._1,vertex._1))/* ... */

    // the GraphX graph
    val graph: Graph[Long,Int] = Graph(vertices,edges,0L)

    // find the vertex new group # from its current group # and the group # from the incoming neighbors
    def newValue ( id: VertexId, currentGroup: Long, incomingGroup: Long ): Long
      = {
      if (currentGroup > incomingGroup)
        incomingGroup
      else
        currentGroup
    }/* ... */

    // send the vertex group # to the outgoing neighbors
    def sendMessage ( triplet: EdgeTriplet[Long,Int]): Iterator[(VertexId,Long)]
      = Iterator((triplet.dstId,triplet.srcAttr)) /* ... */

    def mergeValues ( x: Long, y: Long ): Long
      = {
      if (x > y)
        y
        else
        x

    }/* ... */

    // derive connected components using pregel
    val comps = graph.pregel (Long.MaxValue,5) (   // repeat 5 times
                      newValue,
                      sendMessage,
                      mergeValues
                   )

    // print the group sizes (sorted by group #)
    comps.vertices.map( group =>
      (group._2,1)).reduceByKey((a,b)=> a+b).sortByKey().collect().foreach(println) /* ... */

  }
}
