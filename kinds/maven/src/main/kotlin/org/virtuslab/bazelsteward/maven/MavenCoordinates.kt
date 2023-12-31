package org.virtuslab.bazelsteward.maven

import org.virtuslab.bazelsteward.core.library.Library
import org.virtuslab.bazelsteward.core.library.LibraryId
import org.virtuslab.bazelsteward.core.library.SimpleVersion
import org.virtuslab.bazelsteward.core.library.Version

data class MavenLibraryId(val group: String, val artifact: String) : LibraryId() {
  override fun associatedStrings(): List<List<String>> {
    return Regex("""_[\d.]+$""").find(artifact)?.let {
      listOf(listOf(group, artifact), listOf(group, artifact.removeRange(it.range)))
    } ?: listOf(listOf(group, artifact))
  }

  override val name: String
    get() = "$group:$artifact"

  companion object {
    fun fromString(coordinates: String): MavenLibraryId {
      val (group, artifact) = coordinates.split(":")
      return MavenLibraryId(group, artifact)
    }
  }
}

data class MavenCoordinates(
  override val id: MavenLibraryId,
  override val version: Version,
) : Library {

  companion object {
    fun of(
      group: String,
      artifact: String,
      version: String,
    ): MavenCoordinates = MavenCoordinates(MavenLibraryId(group, artifact), SimpleVersion(version))
  }
}
