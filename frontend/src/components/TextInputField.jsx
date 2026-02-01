import React from "react";
import { View, Text, TextInput, StyleSheet } from "react-native";
import C from "../theme/colors";

export default function TextInputField({
  label,
  value,
  onChangeText,
  secure = false,
  error,
  keyboardType = "default",
}) {
  return (
    <View style={styles.wrapper}>
      {label && <Text style={styles.label}>{label}</Text>}
      <TextInput
        accessibilityLabel={label}
        style={[styles.input, error && styles.inputError]}
        value={value}
        onChangeText={onChangeText}
        secureTextEntry={secure}
        keyboardType={keyboardType}
        autoCapitalize="none"
      />
      {error && <Text style={styles.error}>{error}</Text>}
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: { width: "100%", marginBottom: 16 },
  label: { marginBottom: 4, fontSize: 14, color: C.text },
  input: {
    borderWidth: 1,
    borderColor: C.border,
    borderRadius: 6,
    padding: 12,
    fontSize: 16,
    backgroundColor: "#fff",
  },
  inputError: { borderColor: C.error },
  error: { marginTop: 4, color: C.error, fontSize: 12 },
});
