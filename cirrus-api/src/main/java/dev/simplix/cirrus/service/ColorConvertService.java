package dev.simplix.cirrus.service;

import java.awt.Color;

public interface ColorConvertService {

  String colorToString(Color color);

  /**
   * @param hexColor e.g. #FFFFFF
   * @return Color
   */
  Color stringToColor(String hexColor);

}
