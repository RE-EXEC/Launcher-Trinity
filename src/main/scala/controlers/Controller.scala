package net.k3nder.launchertrinity
package controlers

import json.parser.profile

import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.effect.DropShadow
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input.MouseEvent
import javafx.scene.layout
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.{Font, Text}
import net.k3nder.launchertrinity.controlers.Log.LT
import net.kender.Kjson.ConfigFile
import net.kender.core.sample
import net.kender.core.sample.{MC, VMC}
import net.kender.logger.log5k.Logger
import scalafx.collections.ObservableBuffer
import scalafxml.core.macros.sfxml

import java.io.File
import java.nio.file.Paths
import java.util
import java.util.Properties
import scala.jdk.CollectionConverters._

@sfxml
class Controller(@FXML private var imageView: ImageView,
                 @FXML private var MENU_PANE: Pane,
                 @FXML private var VERSION_NUMBER: Text,
                 @FXML private var SETTINGS_BUTTON_PANE: Pane,
                 @FXML private var SETTINGS_ICON: ImageView,
                 @FXML private var SETTINGS_TEXT: Text,
                 @FXML private var OPTIONS_MENU: Pane,
                 @FXML private var TEXT_PLAY: Text,
                 @FXML private var TEXT_INSTALATIONS: Text,
                 @FXML private var TEXT_SKINS: Text,
                 @FXML private var TEXT_UPDATE_PATCH: Text,
                 @FXML private var SCROLL_PANE_GAME: ScrollPane,
                 @FXML private var PANE_GAME: Pane,
                 @FXML private var IMG_GAME: ImageView,
                 @FXML private var BOTTOM_PANE: Pane,
                 @FXML private var PLAY_BUTTON: Button,
                 @FXML private var PLAY_INDICATOR: Rectangle,
                 @FXML private var INSTALL_INDICATOR: Rectangle,
                 @FXML private var SKINS_INDICATOR: Rectangle,
                 @FXML private var UPPAT_INDICATOR: Rectangle,
                 @FXML private var PLAY_BUTTON_TEXT: Text,
                 @FXML private var QUICK_OPTIONS: Button,
                 @FXML private var QUIKPLAYS: HBox,
                 @FXML private var PROFILE_SELECTOR: ComboBox[String],
                 @FXML private var PANE_INSTALLATIONS: Pane,
                 @FXML private var SEARCH_PROFILE: TextField,
                 @FXML private var PROFILE_SEARCH_TEXT: Text,
                 @FXML private var ORDER_BY: ChoiceBox[String],
                 @FXML private var PROFILE_ORDERBY_TEXT: Text,
                 @FXML private var VERSIONS_STABLE: CheckBox,
                 @FXML private var VERSIONS_SNAPSHOTS: CheckBox,
                 @FXML private var VERSIONS_MODs: CheckBox,
                 @FXML private var PROFILE_VERSIONS_TEXT: Text,
                 /*@FXML private var PROFILE_LIST: ListView[Pane]*/
                 @FXML private var PROFILE_NEW: Button,
                 @FXML private var USERNAME: Text,
                 @FXML private var PROFILE_GET: Pane,
                 @FXML private var PROFILE_ICON_GET: ComboBox[ImageView],
                 @FXML private var PROFILE_NAME_GET: TextField,
                 @FXML private var PROFILE_VERSION_GET: ComboBox[String],
                 @FXML private var PROFILE_GAMEDIR_GET: TextField,
                 @FXML private var PROFILE_GET_SAVE: Button,
                 @FXML private var PROFILE_GET_CANCEL: Button,
                 @FXML private var SC_PANE_PROFILE: ScrollPane
                )
                {
                  val log: Logger = new Logger(Controller.this.getClass)
                  log.log("INIT THE INTERFACE",LT)



                  private val property: Properties = new Properties
                  property.load(getClass.getResourceAsStream("/net/k3nder/launchertrinity/settings/settings.properties"))

                  val config: ConfigFile = new ConfigFile(new File(property.getProperty("assets")));

                  SC_PANE_PROFILE.setFitToHeight(true)
                  private val PROFILE_LIST: VBox = new VBox
                  PROFILE_LIST.setSpacing(10)
                  SC_PANE_PROFILE.setContent(PROFILE_LIST)

                  PROFILE_SELECTOR.setItems(new ObservableBuffer[String])

                  private val GAME_PANE: CPane = new CPane(PANE_GAME)
                  private val INSTALATIONS_PANE: CPane = new CPane(PANE_INSTALLATIONS)
                  private val GET_PROFILE: CPane = new CPane(PROFILE_GET)

                  setDefaultProfileGET()

                  VERSION_NUMBER.setText(property.getProperty("version"))

                  val Profiles: ConfigFile = new ConfigFile(new File("C:\\Users\\krist\\AppData\\Roaming\\.minecraft\\trinity_profiles.json"))
                  private val prof: java.util.List[profile] = Profiles.getArrayList("profiles",classOf[profile])
                  val profi: ObservableBuffer[String] = ObservableBuffer()
                  for(p: profile <- prof.asScala){
                    val pane: Pane = new ObservableProfile(p).getPane()
                    layout.HBox.setHgrow(pane,javafx.scene.layout.Priority.ALWAYS)
                    pane.setId(p.toJson())
                    profi.add(p.name)
                    PROFILE_LIST.getChildren.add(pane)
                    println(p.toJson())
                  }
                  PROFILE_SELECTOR.setItems(profi)
                  PROFILE_NEW.setOnAction(_ => {
                    GET_PROFILE.open(true)
                  })


                  PROFILE_GET_SAVE.setOnAction(_ => {
                    generateProfileToRead()
                    val Profiles: ConfigFile = new ConfigFile(new File("C:\\Users\\krist\\AppData\\Roaming\\.minecraft\\trinity_profiles.json"))
                    val prof: java.util.List[profile] = Profiles.getArrayList("profiles",classOf[profile])
                    val profi: ObservableBuffer[String] = ObservableBuffer()
                    for(p: profile <- prof.asScala){
                      val pane: Pane = new ObservableProfile(p).getPane()
                      layout.HBox.setHgrow(pane,javafx.scene.layout.Priority.ALWAYS)
                      pane.setId(p.toJson())
                      profi.add(p.name)
                      PROFILE_LIST.getChildren.add(pane)
                      println(p.toJson())
                    }
                    PROFILE_SELECTOR.setItems(profi)
                    PROFILE_SELECTOR.setValue(profi.get(1))
                  })

                  private val customFont: Font = Font.loadFont(getClass.getResourceAsStream(property.getProperty("font")), 30)
                  PLAY_BUTTON_TEXT.setFont(customFont)
                  PLAY_BUTTON_TEXT.setFill(Color.WHITE)

                  private val dropShadow = new DropShadow()
                  dropShadow.setOffsetY(2.0)
                  dropShadow.setRadius(0.0)
                  dropShadow.setColor(javafx.scene.paint.Color.BLACK)

                  PLAY_BUTTON_TEXT.setEffect(dropShadow)

                  //val SETTING_ICON_URL = "https://raw.githubusercontent.com/k3nder/data_img_launcher/main/settings.png";
                  SETTINGS_ICON.setImage(new Image(getClass.getResourceAsStream(config.getValue("settings_button",classOf[String]))))

                  PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                    new Image(getClass.getResourceAsStream(config.getValue("play_button",classOf[String]))),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(100, 100, true, true, true, false)
                  )))
                  PLAY_BUTTON.setOnMouseEntered(_ => {
                    println("play drag")
                    PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                      new Image(getClass.getResourceAsStream(config.getValue("play_button_pressed",classOf[String]))),
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.DEFAULT,
                      new BackgroundSize(100, 100, true, true, true, false)
                    )))
                  })
                  PLAY_BUTTON.setOnMouseExited(_ =>{
                    PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                      new Image(getClass.getResourceAsStream(config.getValue("play_button",classOf[String]))),
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.DEFAULT,
                      new BackgroundSize(100, 100, true, true, true, false)
                    )))
                  })
                  PROFILE_GET_CANCEL.setOnAction(_ => {INSTALATIONS_PANE.open(true)})
                  IMG_GAME.setPreserveRatio(false)
                  IMG_GAME.setFitHeight(600)


                  IMG_GAME.setImage(new Image(getClass.getResourceAsStream(config.getValue("panorama_img",classOf[String]))))
                  SETTINGS_BUTTON_PANE.setOnMouseEntered(_ => {
                    println("drag")
                    SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #373537;")
                  })
                  SETTINGS_BUTTON_PANE.setOnMouseExited(_ => {
                    SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #1e1e1e;")
                  })



                  setEnteredExited(TEXT_SKINS)
                  setEnteredExited(TEXT_INSTALATIONS)
                  setEnteredExited(TEXT_PLAY)
                  setEnteredExited(TEXT_UPDATE_PATCH)

                  preparateIndicators()

                  PROFILE_SELECTOR.setStyle("-fx-background-color: TRANSPARENT;")

                  private def setEnteredExited(text: Text): Unit = {
                    text.setFill(Color.LIGHTGRAY)

                    text.setOnMouseEntered(_ =>{
                      text.setFill(Color.WHITE)
                    })

                    text.setOnMouseExited(_ =>{
                      text.setFill(Color.LIGHTGRAY)
                    })
                  }
                  private def preparateIndicators(): Unit = {
                    // creamos lo indicadores para indicar en uqe sitio estamos
                    val skins_i: Indicator = new Indicator(SKINS_INDICATOR)
                    val play_i: Indicator = new Indicator(PLAY_INDICATOR)
                    val instalations_i: Indicator = new Indicator(INSTALL_INDICATOR)
                    val updp_i: Indicator = new Indicator(UPPAT_INDICATOR)

                    // les añadimos acciones
                    skins_i.setOnAction(() => {
                      log.info("open skins pane")
                    })

                    play_i.setOnAction(() => {
                      log.info("open play pane")
                      GAME_PANE.open(true)
                    })

                    instalations_i.setOnAction(() => {
                      INSTALATIONS_PANE.open(true)
                    })

                    updp_i.setOnAction(() => {

                    })

                    // los vinculamos
                    Indicator.LinkIndicator(TEXT_SKINS, skins_i)
                    Indicator.LinkIndicator(TEXT_PLAY, play_i)
                    Indicator.LinkIndicator(TEXT_INSTALATIONS, instalations_i)
                    Indicator.LinkIndicator(TEXT_UPDATE_PATCH,updp_i)
                  }
                  private def generateProfileToRead(): Unit = {
                    val prof: json.parser.profile = new profile();
                    prof.lastVersionId = PROFILE_VERSION_GET.getValue
                    prof.name = PROFILE_NAME_GET.getText
                    prof.gameDir = PROFILE_GAMEDIR_GET.getText

                    val Profiles: ConfigFile = new ConfigFile(new File("C:\\Users\\krist\\AppData\\Roaming\\.minecraft\\trinity_profiles.json"))
                    val lis: util.List[profile] = Profiles.getArrayList("profiles",classOf[profile])
                    lis.add(prof)
                    Profiles.putValue("profiles",lis)
                    Profiles.save();
                    PROFILE_LIST.getChildren.removeAll()

                  }
                  private def setDefaultProfileGET(): Unit = {
                    val versions: ObservableBuffer[String] = ObservableBuffer()
                    val convertedScalaList: List[VMC] = new net.kender.core.sample.Manifest().getList.toArray(Array[VMC]()).toList
                    for(version: VMC <- convertedScalaList){
                      versions.add(version.getID)
                      println(version)
                    }
                    PROFILE_VERSION_GET.setStyle("-fx-text-fill: WHITE;")
                    PROFILE_VERSION_GET.setItems(versions)
                    GET_PROFILE.setOnOpen(() => {
                      PROFILE_GAMEDIR_GET.setText(MC.MINECRAFT_PATH)
                      PROFILE_NAME_GET.setText("undnamed")
                      PROFILE_VERSION_GET.setValue(new sample.Manifest().getLastVersion.getID)
                    })

                  }

                  @FXML
                  def PLAYA(): Unit = {
                    val mc: MC = new MC(Paths.get(MC.MINECRAFT_PATH),new sample.Manifest().getLastVersion)
                    println("play reer")
                    mc.Run()
                  }
                }


