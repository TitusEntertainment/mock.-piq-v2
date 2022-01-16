package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{JsPath, JsValue, Json}
import play.api.Logger
import play.api.mvc._
import models.VerifyUser
import play.api.libs.json.JsValue.jsValueToJsLookup

import java.text.SimpleDateFormat
import java.util.Calendar
import scala.io.{BufferedSource, Source}
import org.joda.time.DateTimeZone
import java.util.Date
import org.joda.time.DateTime
import util.loggerUtils

@Singleton
class PaymentIQController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val source: BufferedSource = Source.fromFile("app/config/responses.json")
  val jsonString: String = source.getLines().mkString
  val responses: JsValue = Json.parse(jsonString)
  // Close the stream once we have the data
  source.close()
  val logger: Logger = Logger(this.getClass)

  def verifyUser(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming verifyUser request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())

    val data = req.body.validate[VerifyUser]
    val userData = (responses \ "verifyUser").get
    val configUserId = (responses \ "verifyUser" \ "userId").toString
    val inputUserId = (req.body \ "userId").toString

    if ((configUserId.compareTo(inputUserId) != 0)) {
          logger.warn("We're in ")
          BadRequest(Json.obj("errCode" -> 400, "errMsg" -> "No such user configured", "success" -> false))
    } else {

    data.fold(
      errors => {
         logger.error("Bad request from PaymentIQ possibly spoofed API call: " + loggerUtils.getFormatedUTCTime() + " " + userData.toString() + " " + req.body.toString())
        BadRequest(Json.obj("msg" -> "Invalid API call"))
      },
      _ => {
        logger.warn("Outgoing verifyUser response: " + loggerUtils.getFormatedUTCTime() + " " + userData.toString())
        Ok(userData)
      }
    )
  }
  }

  def lookupUser(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming lookupUser request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())
    val userData = (responses \ "lookupUser").get
    val configUserId = (responses \ "lookupUser" \ "userId").toString
    val inputUserId = (req.body \ "userId").toString

   if (configUserId.compareTo(inputUserId) != 0) {
      logger.error("No such user configured: " + loggerUtils.getFormatedUTCTime() + " " + userData.toString())
      BadRequest(Json.obj("errCode" -> 400, "errMsg" -> "No such user configured", "success" -> false))

   } else {
      logger.warn(new SimpleDateFormat().format("Outgoing lookupUser response: " + Calendar.getInstance().getTime) + " " + userData.toString())
    Ok(userData)
   }
  }

  def authorize(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming authorize request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())
    val response = (responses \ "authorize").get
    logger.warn("Outgoing authorize response: " + loggerUtils.getFormatedUTCTime() + " " + response.toString())
    Ok(response)
  }

  def transfer(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming transfer request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())
    val response = (responses \ "transfer").get
    logger.warn("Outgoing transfer response: " + loggerUtils.getFormatedUTCTime() + " " + response.toString())
    Ok(response)
  }

  def cancel(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming cancel request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())
    val response = (responses \ "cancel").get
    logger.warn("Outgoing transfer response: " + loggerUtils.getFormatedUTCTime() + " " + response.toString())
    Ok(response)
  }

  def notification(): Action[JsValue] = Action(parse.json) { implicit req: Request[JsValue] =>
    logger.warn("Incoming notification request: " + loggerUtils.getFormatedUTCTime() + " " + req.body.toString())
    logger.warn("Outgoing notification response: " + loggerUtils.getFormatedUTCTime() + " " + Json.obj("success" -> true))
    Ok(Json.obj("success" -> true))
  }
}
