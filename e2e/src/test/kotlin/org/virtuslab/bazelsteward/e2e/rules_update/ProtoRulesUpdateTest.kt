package org.virtuslab.bazelsteward.e2e.rules_update

import org.junit.jupiter.api.Disabled
import org.virtuslab.bazelsteward.e2e.RulesUpdate

@Disabled("All releases are actually prereleases.")
class ProtoRulesUpdateTest : RulesUpdate(
  "rules/trivial/rules_proto",
  "rules_proto" to "5.3.0-21.7"
)