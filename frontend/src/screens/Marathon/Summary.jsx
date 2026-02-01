import React, { useEffect, useState } from "react";
import { SafeAreaView, View, Text, StyleSheet } from "react-native";
import Button from "../../components/Button";
import C from "../../theme/colors";
import { finishMarathon } from "../../api";

export default function MarathonSummary({ navigation, route }) {
  const { score = 0 } = route.params;
  const [record, setRecord] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await finishMarathon(score);
        setRecord(data);
      } catch (e) {
        console.warn("Не удалось сохранить рекорд:", e?.message);
      }
    })();
  }, [score]);

  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: C.background }}>
      <View style={s.container}>
        <View style={s.content}>
          <Text style={s.title}>Marathon over!</Text>
          <Text style={s.big}>Score {score}</Text>
          {record !== null && <Text style={s.record}>Record {record}</Text>}
        </View>
        <View style={s.buttonContainer}>
          <Button
            title="Try again"
            onPress={() => navigation.replace("MarathonPlay")}
            style={s.button}
          />
        </View>
      </View>
    </SafeAreaView>
  );
}

const s = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "space-between", // Распределяем пространство между контентом и кнопкой
  },
  content: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 24,
  },
  title: { fontSize: 28, fontWeight: "700", color: C.text, marginBottom: 24 },
  big: { fontSize: 24, fontWeight: "600", color: C.primary, marginBottom: 16 },
  record: {
    fontSize: 18,
    fontWeight: "600",
    color: C.text,
    opacity: 0.8,
    marginBottom: 24,
  },
  buttonContainer: {
    padding: 24,
  },
  button: {
    width: "100%", // Растягиваем кнопку на всю ширину
  },
});
