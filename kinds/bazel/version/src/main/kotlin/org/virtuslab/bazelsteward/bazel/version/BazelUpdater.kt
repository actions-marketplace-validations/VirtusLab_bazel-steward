package org.virtuslab.bazelsteward.bazel.version

import mu.KotlinLogging
import org.virtuslab.bazelsteward.core.library.Library
import org.virtuslab.bazelsteward.core.library.LibraryId
import org.virtuslab.bazelsteward.core.library.Version
import org.virtuslab.bazelsteward.core.library.VersioningSchema

private val logger = KotlinLogging.logger {}

object BazelLibraryId : LibraryId() {
  override fun associatedStrings(): List<List<String>> = listOf(listOf("USE_BAZEL_VERSION"))

  override val name: String
    get() = "bazel"
}

data class BazelLibrary(
  override val version: Version,
) : Library {
  override val id: BazelLibraryId = BazelLibraryId
}

open class BazelUpdater {
  open suspend fun availableVersions(from: BazelVersion): List<BazelVersion> {
    val versionsExtractor = GcsVersionsExtractor()
    val versioning = VersioningSchema.SemVer
    val bazelSemVer = from.toSemVer(versioning)
      ?: run { logger.error { "Cannot parse Bazel Version" }; return emptyList() }
    val newerVersionPrefixes = versionsExtractor.getVersionPrefixes().filter { version ->
      version.toSemVer(versioning)?.let { it > bazelSemVer } ?: false
    }
    return newerVersionPrefixes.flatMap { versionPrefix -> versionsExtractor.getAllVersions(versionPrefix) }
  }
}
