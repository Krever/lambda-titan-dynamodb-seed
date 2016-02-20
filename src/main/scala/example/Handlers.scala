package example

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

import scala.beans.BeanProperty

class Node {
  @BeanProperty var id: String = ""
  @BeanProperty var label: String = ""
}

object Main {
  def main(args: Array[String]) {

    val node = new Node
    node.setLabel("myLabel")

    val addHandler = new AddNodeHandler()
    val n1 = addHandler.handleRequest(node, null)
    println(n1.id)

    val getHandler = new GetNodeHandler
    val n2 = getHandler.handleRequest(n1, null)
    println(n2.label)

    Titan.graph.close()
  }
}

class GetNodeHandler extends RequestHandler[Node, Node] {

  override def handleRequest(input: Node, context: Context): Node = {

    Titan.graph.transactional{ graph =>
      import gremlin.scala._
      val v = graph.V(input.id)

      val node = new Node
      node.setId(input.id)
      node.setLabel(v.head().label())
      node
    }.oneAndDone()
  }
}


class AddNodeHandler extends RequestHandler[Node, Node] {

  override def handleRequest(input: Node, context: Context): Node = {

    Titan.graph.transactional { graph =>
      val v = graph.addVertex(input.label)

      val node = new Node
      node.setId(v.id().toString)
      node.setLabel(input.label)
      node
    }.oneAndDone()
  }
}

