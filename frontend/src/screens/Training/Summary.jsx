import React from "react";
import { View, Text, StyleSheet } from "react-native";
import Screen from "../../components/Screen";
import Button from "../../components/Button";
import C from "../../theme/colors";

export default function TrainingSummary({ route, navigation }) {
  const score = route?.params?.score ?? 0;
  const total = route?.params?.total ?? 0;
  const accuracy = total > 0 ? Math.round((score / total) * 100) : 0;

  return (
    <Screen>
      <View style={s.box}>
        <Text style={s.h}>Round complete!</Text>
        <Text style={s.t}>
          Score: {score}/{total}
        </Text>
        <Text style={s.t}>Accuracy: {accuracy}%</Text>

        <Button
          title="Try again"
          onPress={() => navigation.popToTop()}
          style={s.btn}
        />
      </View>
    </Screen>
  );
}

const s = StyleSheet.create({
  box: { flex: 1, justifyContent: "center", alignItems: "center" },
  h: { fontSize: 28, fontWeight: "700", color: C.text, marginBottom: 16 },
  t: { fontSize: 18, color: C.text, marginBottom: 8 },
  btn: { marginTop: 24, width: "70%" },
});
