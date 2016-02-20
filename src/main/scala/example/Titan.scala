package example

import com.thinkaurelius.titan.core.TitanFactory
import org.apache.commons.configuration.PropertiesConfiguration

import scala.util.Random

object Titan {

  import gremlin.scala._

  val conf = new PropertiesConfiguration(this.getClass.getResource("/titan-conf.properties"))
  conf.setProperty("graph.unique-instance-id-suffix", Random.nextInt(1000).toString)
  val graph = TitanFactory.open(conf).asScala

  sys.addShutdownHook(graph.close())


}