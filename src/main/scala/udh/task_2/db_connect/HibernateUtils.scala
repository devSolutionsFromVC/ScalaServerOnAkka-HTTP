package udh.task_2.db_connect

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration


object HibernateUtils {

  private var sessionFactory = buildSessionFactory()

  private[db_connect] def buildSessionFactory(): SessionFactory = {
    val configuration = new Configuration().configure("/hibernate.cfg.xml")
    sessionFactory = configuration.configure().buildSessionFactory()
    sessionFactory
  }

  def getSessionFactory: SessionFactory = {
    sessionFactory
  }

  def shutdown(){getSessionFactory.close()}
}
