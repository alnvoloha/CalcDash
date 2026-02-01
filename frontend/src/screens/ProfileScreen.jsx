import React, { useEffect, useState, useContext } from "react";
import { Text, StyleSheet, Alert } from "react-native";
import Screen from "../components/Screen";
import { fetchMe } from "../api";
import { AuthContext } from "../context/AuthContext";
import C from "../theme/colors";
import Button from "../components/Button";

export default function ProfileScreen() {
  const [user, setUser] = useState(null);
  const { logout } = useContext(AuthContext);

  useEffect(() => {
    fetchMe()
      .then(setUser)
      .catch((e) => {
        if (e?.response?.status === 401) {
          logout();
          return;
        }
        Alert.alert("Error", e?.response?.data?.error || e.message);
      });
  }, [logout]);

  if (!user) {
    return (
      <Screen>
        <Text style={s.loading}>Loading…</Text>
      </Screen>
    );
  }

  return (
    <Screen>
      <Text style={s.name}>{user.username}</Text>
      <Text style={s.email}>{user.email}</Text>

      <Text style={s.section}>Stats:</Text>
      <Text>Tasks solved: {user.statistics.totalTasks}</Text>
      <Text>Accuracy: {Math.round(user.statistics.accuracy)}%</Text>

      <Text style={s.section}>Training preset:</Text>
      <Text>
        {user.trainingSettings.difficulty} · {user.trainingSettings.operations}
      </Text>

      <Button title="Logout" onPress={logout} style={s.logout} />
    </Screen>
  );
}

const s = StyleSheet.create({
  name: { fontSize: 28, fontWeight: "700", color: C.text },
  email: { marginBottom: 24, color: C.text },
  section: { marginTop: 24, fontWeight: "600", color: C.primary },
  loading: {
    flex: 1,
    textAlign: "center",
    textAlignVertical: "center",
    fontSize: 18,
  },
  logout: { marginTop: 32 },
});
