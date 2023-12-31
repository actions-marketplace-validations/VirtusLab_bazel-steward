package org.virtuslab.bazelsteward.core.library

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VersionTest {

  private val strictSemVersions = listOf(
    "1.2.3",
    "2.0.0-alpha+beta",
    "1.0.1",
    "1.0.0-alpha",
    "1.0.0-0.3.7",
    "1.0.0-x.7.z.92",
    "1.0.0-alpha+001",
    "1.0.0+20130313144700",
    "1.0.0-beta+exp.sha.5114f85",
  )

  @Test
  fun `check toSemVer with SemVer`() {
    strictSemVersions.forEach {
      SimpleVersion(it).toSemVer(VersioningSchema.SemVer) shouldNotBe null
    }
  }

  @Test
  fun `check toSemVer with Loose`() {
    strictSemVersions.forEach {
      SimpleVersion(it).toSemVer(VersioningSchema.Loose) shouldNotBe null
    }
  }

  @ParameterizedTest
  @MethodSource("argumentsForReturnNullWithStrictSemVer")
  fun `toSemVer should return null when versioningSchema is SEMVER but versions follow only LOOSE `(value: String) {
    val versioningSchema = VersioningSchema.SemVer
    val ver = SimpleVersion(value)
    ver.toSemVer(versioningSchema) shouldBe null
  }

  private fun argumentsForReturnNullWithStrictSemVer(): List<String> = listOf(
    "1.0+whatever",
    "1.0a1-SNAPSHOT+whatever",
    "1.final-2a",
    "1.final",
    "1.final1+whatever",
    "1.0-0",
    "1.0beta1-SNAPSHOT",
    "1.0-alpha1",
  )
}
