import com.thinkaurelius.titan.core.{TitanFactory, TitanGraph}
import org.apache.commons.configuration.PropertiesConfiguration

object Main extends App {

  import gremlin.scala._
  val conf = new PropertiesConfiguration("titan-conf.properties")
  val graph = TitanFactory.open(conf).asScala


  val Name = Key[String]("name")
  val Planet = "planet"
  val Saturn = "saturn"

  (1 to 5) foreach { i ⇒
    graph + (Planet, Name -> s"vertex $i")
  }
  graph + (Saturn, Name -> Saturn)

  println (graph.V.count.head)

  val traversal = graph.V.value(Name)
  println (traversal.toList.size)

  println(graph.V.hasLabel(Saturn).count.head)

  val saturnQ = graph.V.hasLabel(Saturn).head
  println(saturnQ.value2(Name))

  graph.close

}