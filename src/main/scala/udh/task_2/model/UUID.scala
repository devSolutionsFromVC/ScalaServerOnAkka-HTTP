package udh.task_2.model

import javax.persistence._

@Entity
@Table(name = "UUID_KEYS")
//@NamedQuery(name = "get_key_by_uuid", query = "SELECT doc_key FROM uuid_keys u WHERE u.uuid = :uuid")
case class UUID(var uuid: String, var doc_key: String){

  @Id
  def getUUID: String = {
    uuid
  }

  def setUUID(uuid: String): Unit = {
    this.uuid = uuid
  }

  def getDoc_key: String = {
    doc_key
  }

  def setDoc_key(doc_key: String): Unit = {
    this.doc_key = doc_key
  }

  override def toString = s"UUID: $uuid, key: $doc_key"
}

