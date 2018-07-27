package udh.task_2.db_connect

import java.security.{KeyStore, SecureRandom}
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

import akka.http.scaladsl.{ConnectionContext, HttpsConnectionContext}

object SSLConfig {

  private val password = "vlad".toCharArray

  private val ks: KeyStore = KeyStore.getInstance("PKCS12")
  private val keystore = getClass.getClassLoader.getResourceAsStream("keystore.pkcs12")

  require(keystore != null, "Keystore require!")
  ks.load(keystore, password)

  private val factory = KeyManagerFactory.getInstance("SunX509")
  factory.init(ks, password)

  private val trustFactory = TrustManagerFactory.getInstance("SunX509")
  trustFactory.init(ks)

  private val sslContext: SSLContext = SSLContext.getInstance("TLS")
  sslContext.init(factory.getKeyManagers, trustFactory.getTrustManagers, new SecureRandom())
  val hhtps: HttpsConnectionContext = ConnectionContext.https(sslContext)
}
