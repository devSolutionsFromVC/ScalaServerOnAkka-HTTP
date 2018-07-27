package udh.task_2.json


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import udh.task_2.model.UUID

object JsonUUIDSupport extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val uuidFormat: RootJsonFormat[UUID] = jsonFormat2(UUID)
}
