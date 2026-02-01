// ui/Container.jsx
import React from "react";
import { View, StyleSheet, Platform } from "react-native";

/**
 * Центрирует контент и ограничивает ширину на Web (до 1000 px).
 * На мобильных стилевые свойства maxWidth/alignSelf не мешают.
 */
export default function Container({ children, style, ...rest }) {
  return (
    <View style={[styles.root, style]} {...rest}>
      {children}
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
    width: "100%",
    // Ограничиваем только на web
    ...(Platform.OS === "web" && {
      maxWidth: 1000,
      alignSelf: "center", // чтобы прилипнуть к центру экрана
    }),
  },
});