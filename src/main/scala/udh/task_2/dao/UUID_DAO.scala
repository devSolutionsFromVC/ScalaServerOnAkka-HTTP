package udh.task_2.dao


import javax.persistence.{NoResultException, NonUniqueResultException}

import udh.task_2.db_connect.HibernateUtils
import udh.task_2.model.UUID

object UUID_DAO {

  private val session = HibernateUtils.getSessionFactory.openSession()

  def addNewKeyAndUUID(uuid_entity: UUID): Unit = {
    session.beginTransaction()
    session.persist(uuid_entity)
    session.getTransaction.commit()
  }


  def getKeyByUUID(uuid: String): String = {
    var res = "Incorrect UUID!"
    val query = session.createSQLQuery(s"SELECT doc_key FROM uuid_keys WHERE uuid='$uuid'")
    //.createQuery(s"SELECT doc_key FROM uuid_keys WHERE uuid=$uuid")
    //.createNamedQuery("get_key_by_uuid", classOf[UUID])
    //.setParameter("uuid", uuid)
    try {
      res = if(query.getSingleResult != null) query.getSingleResult.toString else "No such result!"
    } catch {
      case e @ (_ : NoResultException | _ : NonUniqueResultException | _: NullPointerException) => println(e)
    }
    res
  }
}
