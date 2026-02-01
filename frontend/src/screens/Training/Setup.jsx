import React, { useState } from "react";
import { View, Text, Pressable, StyleSheet, Alert } from "react-native";
import Screen from "../../components/Screen";
import C from "../../theme/colors";
import { saveSettings } from "../../api";

const levels = ["EASY", "MEDIUM", "HARD", "EXTREME"];
const amounts = [10, 20, 30];
const ops = ["+", "-", "×", "÷"];

export default function TrainingSetup({ navigation }) {
  const [difficulty, setDiff] = useState("EASY");
  const [count, setCnt] = useState(10);
  const [operation, setOp] = useState("+");

  const start = async () => {
    try {
      await saveSettings({
        difficulty,
        numberOfExamples: count,
        operations: operation,
        minOperand: 1,
        maxOperand: 999,
      });
      navigation.replace("TrainingPlay");
    } catch (e) {
      Alert.alert("Error", e.response?.data || e.message);
    }
  };

  const Chip = ({ active, label, onPress }) => (
    <Pressable onPress={onPress} style={[s.chip, active && s.active]}>
      <Text style={[s.chipTxt, active && { color: "#fff" }]}>{label}</Text>
    </Pressable>
  );

  return (
    <Screen>
      <View style={s.container}>
        <View style={s.content}>
          <Text style={s.h}>Training Setup</Text>

          <Text style={s.lbl}>Difficulty</Text>
          <View style={s.row}>
            {levels.map((l) => (
              <Chip
                key={l}
                label={l[0]}
                active={l === difficulty}
                onPress={() => setDiff(l)}
              />
            ))}
          </View>

          <Text style={s.lbl}>Examples per round</Text>
          <View style={s.row}>
            {amounts.map((a) => (
              <Chip
                key={a}
                label={a}
                active={a === count}
                onPress={() => setCnt(a)}
              />
            ))}
          </View>

          <Text style={s.lbl}>Operation</Text>
          <View style={s.row}>
            {ops.map((o) => (
              <Chip
                key={o}
                label={o}
                active={o === operation}
                onPress={() => setOp(o)}
              />
            ))}
          </View>
        </View>
        <View style={s.buttonContainer}>
          <Pressable style={s.btn} onPress={start}>
            <Text style={s.btnTxt}>Start</Text>
          </Pressable>
        </View>
      </View>
    </Screen>
  );
}

const s = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between', // Распределяем пространство между контентом и кнопкой
  },
  content: {
    flex: 1,
    padding: 24,
  },
  h: {
    fontSize: 26,
    fontWeight: "700",
    textAlign: "center",
    marginBottom: 24,
    color: C.text,
  },
  lbl: { marginTop: 18, marginBottom: 8, fontWeight: "600", color: C.text },
  row: { flexDirection: "row", flexWrap: "wrap" },
  chip: {
    paddingHorizontal: 16,
    paddingVertical: 10,
    backgroundColor: "#F3F4F6",
    borderRadius: 20,
    marginRight: 8,
    marginBottom: 8,
  },
  active: { backgroundColor: C.primary },
  chipTxt: { fontWeight: "600", color: C.text },
  buttonContainer: {
    padding: 24,
  },
  btn: {
    marginTop: 32,
    backgroundColor: C.primary,
    padding: 16,
    borderRadius: 14,
    width: '100%', // Растягиваем кнопку на всю ширину
  },
  btnTxt: {
    color: "#fff",
    textAlign: "center",
    fontSize: 18,
    fontWeight: "600",
  },
});