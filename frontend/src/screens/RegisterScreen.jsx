import React, { useState, useContext } from "react";
import { View, Text, StyleSheet, Alert } from "react-native";
import TextInputField from "../components/TextInputField";
import Button from "../components/Button";
import api from "../api/axios";
import C from "../theme/colors";
import { AuthContext } from "../context/AuthContext";

export default function RegisterScreen() {
  const { login } = useContext(AuthContext);
  const [f, setF] = useState({ username: "", email: "", password: "" });
  const [loading, setL] = useState(false);
  const change = (k) => (v) => setF((p) => ({ ...p, [k]: v }));

  const submit = async () => {
    try {
      setL(true);
      await api.post("/api/users/register", f);
      const { data: jwt } = await api.post("/api/users/login", {
        username: f.username,
        password: f.password,
      });
      await login(jwt.token);
    } catch (e) {
      Alert.alert("Error", e.response?.data || "Server");
    } finally {
      setL(false);
    }
  };

  return (
    <View style={s.c}>
      <Text style={s.title}>Register</Text>
      <TextInputField
        label="Username"
        value={f.username}
        onChangeText={change("username")}
      />
      <TextInputField
        label="Email"
        value={f.email}
        onChangeText={change("email")}
        keyboardType="email-address"
      />
      <TextInputField
        label="Password"
        value={f.password}
        onChangeText={change("password")}
        secure
      />
      <Button title="Create" onPress={submit} loading={loading} />
    </View>
  );
}
const s = StyleSheet.create({
  c: {
    flex: 1,
    backgroundColor: C.background,
    justifyContent: "center",
    padding: 24,
  },
  title: {
    fontSize: 32,
    fontWeight: "700",
    textAlign: "center",
    marginBottom: 40,
    color: C.text,
  },
});
