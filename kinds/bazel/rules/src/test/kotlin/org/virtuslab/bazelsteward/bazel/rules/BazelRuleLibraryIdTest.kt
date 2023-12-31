package org.virtuslab.bazelsteward.rules

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.virtuslab.bazelsteward.bazel.rules.RuleLibraryId

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BazelRuleLibraryIdTest {

  @ParameterizedTest
  @MethodSource("argumentsForCreateBazelLibraryId")
  fun `should create BazelRuleLibraryId`(url: String, tag: String, artifactName: String) {
    Assertions.assertThat(RuleLibraryId.from(url))
      .hasFieldOrPropertyWithValue("repoName", "repo1")
      .hasFieldOrPropertyWithValue("ruleName", "name1")
      .hasFieldOrPropertyWithValue("tag", tag)
      .hasFieldOrPropertyWithValue("artifactName", artifactName)
  }

  private fun argumentsForCreateBazelLibraryId(): List<Arguments> = listOf(
    Arguments.of("https://github.com/repo1/name1/releases/download/v0.1/amazing.zip", "v0.1", "amazing.zip"),
    Arguments.of("https://github.com/repo1/name1/releases/download/0-0/amazing.tar.gz", "0-0", "amazing.tar.gz"),
    Arguments.of("https://github.com/repo1/name1/releases/download/tagName/amazing.tgz", "tagName", "amazing.tgz"),
    Arguments.of("https://github.com/repo1/name1/archive/amazing.tgz", "amazing", "amazing.tgz"),
    Arguments.of("https://github.com/repo1/name1/archive/refs/tags/v0.3.1.tar.gz", "v0.3.1", "v0.3.1.tar.gz"),
  )

  @ParameterizedTest
  @MethodSource("argumentsForThrowException")
  fun `should throw an exception when creating BazelRuleLibraryId`(url: String) {
    Assertions.assertThatThrownBy { RuleLibraryId.from(url) }
      .hasMessageContaining("Unrecognised artifact URL format")
  }

  private fun argumentsForThrowException(): List<Arguments> = listOf(
    Arguments.of("https://github.com/rep/o1/name1/releases/download/v0.1/amazing.zip"),
    Arguments.of("https://github.com/repo1/name1/releases/0-0/amazing.tar.gz"),
    Arguments.of("https://github.com/repo1/name1/releases/download/tagName/"),
    Arguments.of("https://github.com/repo1/name1/releases/download/tagName"),
    Arguments.of("https://github.com/repo1/name1/amazing.tgz"),
    Arguments.of("https://github.com/repo1/name1/archive/refs/tags"),
    Arguments.of("://github.com/repo1/name1/archive/refs/tags/v0.3.1.tar.gz"),
  )

  @Test
  fun `should throw an exception when creating BazelRuleLibraryId`() {
    Assertions.assertThatThrownBy { RuleLibraryId.from("https://github.com/repo1/name1/archive/refs/tags/v0.3.1.trr") }
      .hasMessageContaining("Unrecognised artifact URL format")
  }
}
