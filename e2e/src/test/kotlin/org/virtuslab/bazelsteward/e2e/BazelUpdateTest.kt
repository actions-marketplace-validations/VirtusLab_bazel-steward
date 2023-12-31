package org.virtuslab.bazelsteward.e2e

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.virtuslab.bazelsteward.e2e.fixture.E2EBase
import java.nio.file.Path

class BazelUpdateTest : E2EBase() {

  // This may actually fail depending on the version of Bazel on PATH
  @Test
  fun `Project without Bazel version`(@TempDir tempDir: Path) {
    val project = "bazel/trivial"
    runBazelSteward(tempDir, project)
    val expectedBranches = expectedBranches()

    checkBranchesWithVersions(tempDir, project, expectedBranches)
  }

  @Test
  fun `Project with Bazel version in bazeliskrc file`(@TempDir tempDir: Path) {
    val project = "bazel/bazeliskrc"
    runBazelSteward(tempDir, project)
    val expectedBranches = expectedBranches("bazel" to "5.4.1")

    checkBranchesWithVersions(tempDir, project, expectedBranches)
  }

  @Test
  fun `Project without dependencies`(@TempDir tempDir: Path) {
    val project = "bazel/bazelOnly"
    runBazelSteward(tempDir, project)
    val expectedBranches = expectedBranches("bazel" to "5.4.1")
    checkBranchesWithVersions(tempDir, project, expectedBranches)
  }
}
