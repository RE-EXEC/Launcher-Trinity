
package net.k3nder.lt.controllers

import javafx.event.Event
import javafx.scene.control.Button
import javafx.scene.layout.{AnchorPane, Pane}
import javafx.scene.paint.{Color, Paint}
import javafx.scene.shape.StrokeType
import javafx.scene.text.{Font, Text}
import net.k3nder.lt.json.parser.profile

class ObservableProfile(var p: profile) {
  val pane: AnchorPane = new AnchorPane();
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
  def getPane(): Pane = {
    return pane;
  }
}
