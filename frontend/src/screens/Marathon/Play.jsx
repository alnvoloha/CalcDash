import React, { useEffect, useRef, useState } from "react";
import { View, Text, StyleSheet, Alert } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import CalcPad from "../../components/CalcPad";
import Button from "../../components/Button";
import { startMarathon, answerMarathon, getMarathonRecord } from "../../api";
import C from "../../theme/colors";

export default function MarathonPlay({ navigation }) {
  const [started, setStarted] = useState(false);
  const [difficulty, setDifficulty] = useState("EASY");
  const [tasks, setTasks] = useState([]);
  const [idx, setIdx] = useState(0);
  const [score, setScore] = useState(0);
  const scoreRef = useRef(0);

  const [input, setInput] = useState("");
  const [timeLeft, setTimeLeft] = useState(0);
  const timerRef = useRef(null);

  const [record, setRecord] = useState(0);

  useEffect(() => {
    getMarathonRecord()
      .then(setRecord)
      .catch(() => {});
  }, []);

  const stopTimer = () => {
    if (timerRef.current) clearInterval(timerRef.current);
    timerRef.current = null;
  };

  const endGame = () => {
    stopTimer();
    navigation.replace("MarathonSummary", { score: scoreRef.current });
  };

  const startTimer = () => {
    stopTimer();
    timerRef.current = setInterval(() => {
      setTimeLeft((prev) => {
        if (prev <= 1) {
          stopTimer();
          endGame();
          return 0;
        }
        return prev - 1;
      });
    }, 1000);
  };

  const startGame = async () => {
    try {
      setIdx(0);
      setScore(0);
      scoreRef.current = 0;
      setInput("");

      const data = await startMarathon(difficulty);
      setTasks(data.tasks);
      setTimeLeft(data.timePerTask);
      setStarted(true);
      startTimer();
    } catch (e) {
      Alert.alert("Error", e.response?.data || e.message);
    }
  };

  const submit = async () => {
    const task = tasks[idx];
    if (!task) return;

    try {
      const correct = await answerMarathon({
        taskId: task.id,
        userAnswer: Number(input),
      });

      setInput("");

      if (correct) {
        const newScore = scoreRef.current + 1;
        scoreRef.current = newScore;
        setScore(newScore);
        setTimeLeft((t) => t + 1);
      }

      if (idx + 1 < tasks.length) setIdx(idx + 1);
      else endGame();
    } catch (e) {
      Alert.alert("Error", e.response?.data || e.message);
    }
  };

  if (!started) {
    return (
      <SafeAreaView style={s.safe}>
        <Text style={s.title}>Marathon</Text>
        <Text style={s.record}>Record: {record}</Text>
        <Button title="Play" onPress={startGame} />
      </SafeAreaView>
    );
  }

  const current = tasks[idx];

  return (
    <SafeAreaView style={s.safe}>
      <View style={s.topBox}>
        <Text style={s.time}>⏱ {timeLeft}s</Text>
        <Text style={s.score}>Score: {score}</Text>
      </View>

      <Text style={s.task}>{current.expression}</Text>
      <Text style={s.input}>{input || "·"}</Text>

      <CalcPad value={input} setValue={setInput} onSubmit={submit} />
    </SafeAreaView>
  );
}

const s = StyleSheet.create({
  safe: { flex: 1, padding: 24, backgroundColor: C.background },
  title: {
    fontSize: 30,
    fontWeight: "700",
    textAlign: "center",
    color: C.text,
  },
  record: {
    fontSize: 18,
    textAlign: "center",
    marginBottom: 16,
    color: C.primary,
  },
  topBox: {
    padding: 12,
    borderRadius: 12,
    alignItems: "center",
    marginBottom: 12,
  },
  task: {
    fontSize: 28,
    fontWeight: "700",
    textAlign: "center",
    marginBottom: 12,
    color: C.text,
  },
  time: { fontSize: 20, color: C.primary },
  score: { fontSize: 18, color: C.text },
  input: {
    fontSize: 26,
    textAlign: "center",
    color: C.primary,
    marginBottom: 8,
  },
});
