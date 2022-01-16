package models

import play.api.libs.json.{Json, OWrites, Reads}

case class VerifyUser (
                userId: String,
                sessionId: String
)

object VerifyUser {
  implicit  val verifyUserImplicitReads: Reads[VerifyUser] = Json.reads[VerifyUser]
  implicit  val verifyUserImplicitWrites: OWrites[VerifyUser] = Json.writes[VerifyUser]
}
