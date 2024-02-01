package net.k3nder.launchertrinity
package controlers

import com.sun.javafx.geom.BaseBounds.BoundsType
import javafx.animation.{FillTransition, Transition}
import javafx.beans.InvalidationListener
import javafx.collections.{ListChangeListener, ObservableList}
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.{Button, CheckBox, ChoiceBox, ComboBox, ListView, ScrollPane, TextField}
import javafx.scene.effect.DropShadow
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{AnchorPane, Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize, HBox, Pane, VBox}
import javafx.scene.paint.{Color, Paint}
import javafx.scene.shape.{Rectangle, StrokeType}
import javafx.scene.text.{Font, FontWeight, Text}
import javafx.util.Duration
import scalafx.collections.ObservableBuffer
import scalafxml.core.macros.sfxml
import net.kender.Kjson.ConfigFile
import net.k3nder.launchertrinity.json.parser.launcher.profiles
import net.k3nder.launchertrinity.json.parser.profile

import java.io.File
import scala.jdk.CollectionConverters._
import javafx.fxml.FXMLLoader
import javafx.scene.{Node, layout}

import java.util
import java.util.Properties

@sfxml
class Ccontroler(@FXML private var imageView: ImageView,
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
                 @FXML private var PROFILE_SELECTOR: ComboBox[Pane],
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
                 /*@FXML private var PROFILE_NEW: Button,*/
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
                  private var property: Properties = new Properties();
                  property.load(getClass.getResourceAsStream("/net/k3nder/launchertrinity/settings/settings.properties"))



                  SKINS_INDICATOR.setFill(Color.TRANSPARENT)
                  INSTALL_INDICATOR.setFill(Color.TRANSPARENT)
                  PLAY_INDICATOR.setFill(Color.TRANSPARENT)
                  UPPAT_INDICATOR.setFill(Color.TRANSPARENT)

                  SC_PANE_PROFILE.setFitToHeight(true)
                  val PROFILE_LIST: VBox = new VBox();
                  PROFILE_LIST.setSpacing(10)
                  SC_PANE_PROFILE.setContent(PROFILE_LIST)
                  //PROFILE_LIST.setStyle("-fx-background-color:  TRANSPARENT;")
                  //SC_PANE_PROFILE.setStyle("opacity: 0.9 ;")



                  VERSION_NUMBER.setText(property.getProperty("version"));

                  val Profiles: ConfigFile = new ConfigFile(new File("C:\\Users\\krist\\AppData\\Roaming\\.minecraft\\trinity_profiles.json"));
                  private var prof: java.util.List[profile] = Profiles.getArrayList("profiles",classOf[profile]);
                  for(p: profile <- prof.asScala){
                    val pane: AnchorPane = new AnchorPane();
                    println(p.lastVersionId)
                    pane.setPrefWidth(50)
                    pane.setPrefHeight(850)
                    pane.setStyle("-fx-background-color: #333333; -fx-border-color: #434343;")

                    val name: Text = new Text(p.name);
                    name.setFill(Color.WHITE)
                    name.setLayoutX(14)
                    name.setLayoutY(30)
                    name.setStrokeType(StrokeType.OUTSIDE)
                    name.setStrokeWidth(0.0)
                    name.setWrappingWidth(180.53671646118164)
                    name.setFont(new Font(18))

                    val version: Text = new Text(p.lastVersionId)
                    version.setLayoutX(56)
                    version.setLayoutY(48)
                    version.setStrokeType(StrokeType.OUTSIDE)
                    version.setStrokeWidth(0.0)
                    version.setWrappingWidth(69.33673477172852)

                    // Establecer el wrappingWidth a un valor grande para permitir el ajuste automático// Establecer el wrappingWidth a un valor grande para permitir el ajuste automático

                    version.setWrappingWidth(java.lang.Double.MAX_VALUE)
                    name.setWrappingWidth(java.lang.Double.MAX_VALUE)

                    // Establecer boundsType a VISUAL para tener en cuenta la posición de las letras
                    //texto.setBoundsType(BoundsType.VISUAL)

                    val buttons: Pane = new Pane();
                    buttons.setDisable(true)
                    buttons.setLayoutX(611)
                    buttons.setLayoutY(10)
                    buttons.prefHeight(34)
                    buttons.prefWidth(215)
                    buttons.setVisible(false)

                    val play_button:Button = new Button("PLAY")
                    play_button.setLayoutY(82.0)
                    play_button.setMnemonicParsing(false)
                    play_button.setPrefHeight(25)
                    play_button.setPrefWidth(69)
                    play_button.setStyle("-fx-background-color: cyan;")

                    // Configuración del segundo botón
                    val open_dir_button: Button = new Button()
                    open_dir_button.setLayoutX(122.0)
                    open_dir_button.setLayoutY(3.0)
                    open_dir_button.setPrefHeight(25.0)
                    open_dir_button.setPrefWidth(31.0)
                    open_dir_button.setRotate(90.0)
                    open_dir_button.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #434343;")
                    open_dir_button.setText(". . .")
                    open_dir_button.setTextFill(Paint.valueOf("#ffffff"))
                    //open_dir_button.setFont(new Font("System", FontWeight.BOLD, 12))

                    val button1: Button = new Button()
                    button1.setLayoutX(82.0)
                    button1.setLayoutY(3.0)
                    button1.setPrefHeight(25.0)
                    button1.setPrefWidth(31.0)
                    button1.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: #434343;")

                    buttons.getChildren.addAll(play_button,open_dir_button,button1)
                    pane.setOnMouseEntered((event: Event) => {
                      pane.setStyle("-fx-background-color: GREEN;")
                    })
                    pane.setOnMouseExited((event: Event) => {
                      pane.setStyle("-fx-background-color: #1e1e1e;")
                    })

                    pane.getChildren.addAll(name,version,buttons)
                    //PROFILE_LIST.setFillWidth(true)
                    layout.HBox.setHgrow(pane,javafx.scene.layout.Priority.ALWAYS)
                    PROFILE_LIST.getChildren.add(pane)

                  }

                  //PROFILE_LIST.setSpacing(10)




                  // Cargar la fuente desde el archivo .ttf// Cargar la fuente desde el archivo .ttf// Cargar la fuente desde el archivo .ttf

                  private val customFont: Font = Font.loadFont(getClass.getResourceAsStream(property.getProperty("font")), 30)
                  PLAY_BUTTON_TEXT.setFont(customFont)
                  PLAY_BUTTON_TEXT.setFill(Color.WHITE)

                  private val dropShadow = new DropShadow()
                  dropShadow.setOffsetY(2.0)
                  dropShadow.setRadius(0.0)
                  dropShadow.setColor(javafx.scene.paint.Color.BLACK)

                  PLAY_BUTTON_TEXT.setEffect(dropShadow)


                  //val SETTING_ICON_URL = "https://raw.githubusercontent.com/k3nder/data_img_launcher/main/settings.png";
                  SETTINGS_ICON.setImage(new Image(getClass.getResourceAsStream("/net/k3nder/launchertrinity/assets/settings.png")))

                  PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                    new Image(getClass.getResourceAsStream("/net/k3nder/launchertrinity/assets/buttons/play_button.png")),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(100, 100, true, true, true, false)
                  )))
                  PLAY_BUTTON.setOnMouseEntered((event: Event) => {
                    println("play drag");
                    PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                      new Image(getClass.getResourceAsStream("/net/k3nder/launchertrinity/assets/buttons/play_button_pressed.png")),
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.DEFAULT,
                      new BackgroundSize(100, 100, true, true, true, false)
                    )))
                  })
                  PLAY_BUTTON.setOnMouseExited((event: Event) =>{
                    PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                      new Image(getClass.getResourceAsStream("/net/k3nder/launchertrinity/assets/buttons/play_button.png")),
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.DEFAULT,
                      new BackgroundSize(100, 100, true, true, true, false)
                    )))
                  })
                 IMG_GAME.setPreserveRatio(false)
                  IMG_GAME.setFitHeight(600)


                  IMG_GAME.setImage(new Image(getClass.getResourceAsStream("/net/k3nder/launchertrinity/assets/minecraft.png")));
                  SETTINGS_BUTTON_PANE.setOnMouseEntered((event: MouseEvent) => {
                    println("drag");
                    SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #373537;")
                  });
                  SETTINGS_BUTTON_PANE.setOnMouseExited((event: MouseEvent) => {
                    SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #1e1e1e;")
                  })



                  setEnteredExited(TEXT_SKINS)
                  setEnteredExited(TEXT_INSTALATIONS)
                  setEnteredExited(TEXT_PLAY)
                  setEnteredExited(TEXT_UPDATE_PATCH)

                  setIndicator(TEXT_SKINS, SKINS_INDICATOR,() => {
                    print("skins")
                    PLAY_INDICATOR.setFill(Color.TRANSPARENT)
                    UPPAT_INDICATOR.setFill(Color.TRANSPARENT)
                    INSTALL_INDICATOR.setFill(Color.TRANSPARENT)
                  })
                  setIndicator(TEXT_PLAY, PLAY_INDICATOR, () => {
                    print("play");
                    SKINS_INDICATOR.setFill(Color.TRANSPARENT)
                    UPPAT_INDICATOR.setFill(Color.TRANSPARENT)
                    INSTALL_INDICATOR.setFill(Color.TRANSPARENT)

                    PANE_GAME.setDisable(false)
                    PANE_GAME.setVisible(true)

                    PANE_INSTALLATIONS.setDisable(true)
                    PANE_INSTALLATIONS.setVisible(false)

                    PROFILE_GET.setDisable(true)
                    PROFILE_GET.setVisible(false)
                  })
                  setIndicator(TEXT_INSTALATIONS, INSTALL_INDICATOR,() => {
                    print("Instalations")
                    SKINS_INDICATOR.setFill(Color.TRANSPARENT)
                    UPPAT_INDICATOR.setFill(Color.TRANSPARENT)
                    PLAY_INDICATOR.setFill(Color.TRANSPARENT)
                    PANE_GAME.setDisable(true)
                    PANE_GAME.setVisible(false)

                    PANE_INSTALLATIONS.setDisable(false)
                    PANE_INSTALLATIONS.setVisible(true)

                    PROFILE_GET.setDisable(true)
                    PROFILE_GET.setVisible(false)
                  })
                  setIndicator(TEXT_UPDATE_PATCH, UPPAT_INDICATOR, () => {
                    print("play");
                    SKINS_INDICATOR.setFill(Color.TRANSPARENT)
                    INSTALL_INDICATOR.setFill(Color.TRANSPARENT)
                    PLAY_INDICATOR.setFill(Color.TRANSPARENT)
                  })

                  //PROFILE_NEW.setOnAction((event: Event) => {
                  //  PANE_GAME.setDisable(true)
                  //  PANE_GAME.setVisible(false)
                  //  PANE_INSTALLATIONS.setDisable(true)
                  //  PANE_INSTALLATIONS.setVisible(false)
                  //  PROFILE_GET.setDisable(false)
                  //  PROFILE_GET.setVisible(true)
                  //})

                  PROFILE_SELECTOR.setStyle("-fx-background-color: TRANSPARENT;")
                  val observableList: ObservableBuffer[Pane] = ObservableBuffer()


                  PROFILE_SELECTOR.setItems(observableList);
                  private def setEnteredExited(text: Text): Unit = {
                    text.setFill(Color.LIGHTGRAY);

                    text.setOnMouseEntered((event: MouseEvent) =>{
                      text.setFill(Color.WHITE);
                    })

                    text.setOnMouseExited((event: MouseEvent) =>{
                      text.setFill(Color.LIGHTGRAY);
                    })
                  }
                  // Método auxiliar para convertir un objeto Color a un formato hexadecimal// Método auxiliar para convertir un objeto Color a un formato hexadecimal// Método auxiliar para convertir un objeto Color a un formato hexadecimal

                  //private def toHexString(color: Color) = String.format("#%02X%02X%02X", (color.getRed * 255).asInstanceOf[Int], (color.getGreen * 255).asInstanceOf[Int], (color.getBlue * 255).asInstanceOf[Int])
                  private def setIndicator(text: Text, indi: Rectangle,run: Runnable): Unit = {

                    val fillTransition: FillTransition = new FillTransition(Duration.millis(10), indi)

                    // Establecer el color de inicio y el color de destino
                    fillTransition.setFromValue(Color.TRANSPARENT)
                    fillTransition.setToValue(Color.CYAN)

                    text.setOnMouseClicked((event: MouseEvent) => {
                      fillTransition.play()
                      run.run()
                    })

                  }


                }


