
import sbt.ModuleID
import sbt._

object Dependencies {

    lazy val ZioVersion = "1.0.4"

    lazy val zio: Seq[ModuleID] = Seq(
      "dev.zio" %% "zio" % ZioVersion
    )
}
