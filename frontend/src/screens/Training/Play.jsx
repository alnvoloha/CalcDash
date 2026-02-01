import React, { useEffect, useRef, useState } from "react";
import { Text, StyleSheet, View } from "react-native";
import Screen from "../../components/Screen";
import CalcPad from "../../components/CalcPad";
import C from "../../theme/colors";
import { genTasks, answerMarathon } from "../../api";

export default function TrainingPlay({ navigation }) {
  const [tasks, setTasks] = useState([]);
  const [idx, setIdx] = useState(0);
  const [input, setInput] = useState("");
  const [score, setScore] = useState(0);
  const scoreRef = useRef(0);

  useEffect(() => {
    genTasks("TRAINING")
      .then(setTasks)
      .catch(() => setTasks([]));
  }, []);

  const current = tasks[idx];

  const submit = async () => {
    if (!current) return;

    const correct = await answerMarathon({
      taskId: current.id,
      userAnswer: Number(input),
    });

    setInput("");

    if (correct) {
      scoreRef.current += 1;
      setScore(scoreRef.current);
    }

    if (idx + 1 >= tasks.length) {
      navigation.replace("TrainingSummary", {
        score: scoreRef.current,
        total: tasks.length,
      });
    } else {
      setIdx(idx + 1);
    }
  };

  if (!current) {
    return (
      <Screen>
        <Text style={s.loading}>Loading…</Text>
      </Screen>
    );
  }

  return (
    <Screen>
      <View style={s.header}>
        <Text style={s.small}>
          {idx + 1}/{tasks.length}
        </Text>
        <Text style={s.small}>Score: {score}</Text>
      </View>

      <Text style={s.expr}>{current.expression}</Text>
      <Text style={s.input}>{input || "·"}</Text>

      <CalcPad value={input} setValue={setInput} onSubmit={submit} />
    </Screen>
  );
}

const s = StyleSheet.create({
  header: { flexDirection: "row", justifyContent: "space-between" },
  small: { color: C.text, opacity: 0.8 },
  expr: {
    fontSize: 36,
    fontWeight: "800",
    textAlign: "center",
    marginVertical: 20,
    color: C.text,
  },
  input: {
    fontSize: 26,
    textAlign: "center",
    color: C.primary,
    marginBottom: 8,
  },
  loading: { textAlign: "center", fontSize: 18 },
});
