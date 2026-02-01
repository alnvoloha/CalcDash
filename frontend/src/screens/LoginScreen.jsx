import React, { useState, useContext } from "react";
import { View, Text, StyleSheet, Alert } from "react-native";
import { AuthContext } from "../context/AuthContext";
import TextInputField from "../components/TextInputField";
import Button from "../components/Button";
import api from "../api/axios";
import C from "../theme/colors";
import { useNavigation } from "@react-navigation/native";

export default function LoginScreen() {
  const { login } = useContext(AuthContext);
  const nav = useNavigation();

  const [username, setU] = useState("");
  const [password, setP] = useState("");
  const [loading, setL] = useState(false);

  const submit = async () => {
    try {
      setL(true);
      console.log("Sending login data", { username, password });

      const { data } = await api.post("/api/users/login", {
        username,
        password,
      });

      if (data.token) {
        await login(data.token); // кладём JWT в SecureStore
      } else {
        Alert.alert("Error", data.error || "Unknown error");
      }
    } catch (e) {
      const msg = e.response?.data?.error || e.response?.data || e.message;
      Alert.alert("Error", msg);
    } finally {
      setL(false);
    }
  };

  return (
    <View style={s.container}>
      <View style={s.content}>
        <Text style={s.title}>CalcDash</Text>
        <TextInputField label="Username" value={username} onChangeText={setU} />
        <TextInputField
          label="Password"
          value={password}
          onChangeText={setP}
          secure
        />
      </View>
      <View style={s.buttonContainer}>
        <Button title="Login" onPress={submit} loading={loading} style={s.button} />
        <Text style={s.link} onPress={() => nav.navigate("Register")}>
          Create account
        </Text>
      </View>
    </View>
  );
}

const s = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: C.background,
    justifyContent: 'space-between', // Распределяем пространство между верхним контентом и кнопками
  },
  content: {
    flex: 1,
    justifyContent: 'center',
    padding: 24,
  },
  title: {
    fontSize: 32,
    fontWeight: "700",
    textAlign: "center",
    marginBottom: 40,
    color: C.text,
  },
  buttonContainer: {
    padding: 24,
  },
  button: {
    width: '100%', // Растягиваем кнопку на всю ширину
    marginBottom: 16,
  },
  link: {
    marginTop: 24,
    textAlign: "center",
    color: C.primary,
    width: '100%', // Растягиваем ссылку на всю ширину
  },
});