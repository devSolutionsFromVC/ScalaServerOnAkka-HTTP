package udh.task_2.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import spray.json.JsValue
import udh.task_2.dao.UUID_DAO
import udh.task_2.model.UUID

import scala.concurrent.{ExecutionContextExecutor, Future}

object KeysServer {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val jsonStreamingSupport: JsonEntityStreamingSupport = EntityStreamingSupport.json()

  import udh.task_2.json.JsonUUIDSupport._
  implicit val jsonProtocolUnmarshaller: Unmarshaller[JsValue, UUID] =
    Unmarshaller.strict(jsValue => uuidFormat.read(jsValue))


  def main(args: Array[String]): Unit = {
    val serverSource = Http().bind(interface = "localhost", port = 4547)
//    val serverSource = Http().bind(interface = "localhost", port = 8089, connectionContext = SSLConfig.hhtps)

    def requestHandler(request: HttpRequest): HttpResponse = request match {

      case HttpRequest(POST, Uri.Path("/send_file_info/"), _, _, _) =>
        val content = Unmarshal(request.entity).to[UUID]
        content.map {
          con =>
            UUID_DAO.addNewKeyAndUUID(UUID(con.uuid, con.doc_key))
        }
        HttpResponse(entity = HttpEntity("Received file info"))

      case HttpRequest(GET, _, _, _, _) =>
        val param = "/file_id/"
        val path = request.uri.path
        if(path.startsWith(Uri.Path(param))){
          val pathAfterSplit = path.toString().split(param)
          val uuid = pathAfterSplit(1)
          val res_uuid = UUID_DAO.getKeyByUUID(uuid.toString)
          HttpResponse(entity = HttpEntity(res_uuid))
        } else {
          HttpResponse(entity = HttpEntity("Incorrect uri"))
        }

      case r: HttpRequest =>
        r.discardEntityBytes()
        HttpResponse(404, entity = "Incorrect request")
    }


    val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach {
      connection => println("Accepted new connection from " + connection.remoteAddress)
        connection.handleWithSyncHandler(requestHandler)
    }).run()
  }
}