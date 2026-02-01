import React from "react";
import { TouchableOpacity, View, Text, StyleSheet } from "react-native";
import C from "../theme/colors";

const keys = ["1","2","3","4","5","6","7","8","9","0","←","OK"];

export default function CalcPad({ value, setValue, onSubmit }) {
  const press = (k) => {
    if (k === "←") {
      setValue((v) => v.slice(0, -1));
      return;
    }
    if (k === "OK") {
      onSubmit?.();
      return;
    }
    setValue((v) => v + k);
  };

  return (
    <View style={s.grid}>
      {keys.map((k) => (
        <TouchableOpacity key={k} style={s.key} onPress={() => press(k)}>
          <Text style={s.txt}>{k}</Text>
        </TouchableOpacity>
      ))}
    </View>
  );
}

const s = StyleSheet.create({
  grid: {
    flexDirection: "row",
    flexWrap: "wrap",
    marginTop: 32,
    maxWidth: 500,
  },
  key: {
    width: "30%",
    margin: "1.66%",
    aspectRatio: 1,
    borderRadius: 14,
    backgroundColor: "#F3F4F6",
    alignItems: "center",
    justifyContent: "center",
  },
  txt: {
    fontSize: 22,
    fontWeight: "600",
    color: C.text,
  },
});
