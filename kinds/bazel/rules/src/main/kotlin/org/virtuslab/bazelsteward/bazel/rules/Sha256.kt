package org.virtuslab.bazelsteward.bazel.rules

import mu.KotlinLogging
import java.net.URL
import java.security.DigestInputStream
import java.security.MessageDigest

private val logger = KotlinLogging.logger {}

internal fun computeSha256(url: URL): String {
  logger.info { "Computing sha256 for $url" }
  val digest = MessageDigest.getInstance("SHA-256")
  var messageDigest: MessageDigest
  DigestInputStream(url.openStream(), digest).use { dis ->
    while (dis.read() != -1);
    messageDigest = dis.messageDigest
  }

  val result = StringBuilder()
  for (b in messageDigest.digest()) {
    result.append(String.format("%02x", b))
  }
  return result.toString()
}
