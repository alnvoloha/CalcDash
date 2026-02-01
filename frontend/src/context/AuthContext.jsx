import React, { createContext, useEffect, useMemo, useState } from "react";
import { Platform } from "react-native";
import * as SecureStore from "expo-secure-store";
import { setAuthToken } from "../api/axios";

const TOKEN_KEY = "userToken";

export const AuthContext = createContext({
  token: null,
  ready: false,
  login: async () => {},
  logout: async () => {},
});

async function loadToken() {
  if (Platform.OS === "web") return localStorage.getItem(TOKEN_KEY);
  return await SecureStore.getItemAsync(TOKEN_KEY);
}

async function saveToken(token) {
  if (Platform.OS === "web") return localStorage.setItem(TOKEN_KEY, token);
  return await SecureStore.setItemAsync(TOKEN_KEY, token);
}

async function clearToken() {
  if (Platform.OS === "web") return localStorage.removeItem(TOKEN_KEY);
  return await SecureStore.deleteItemAsync(TOKEN_KEY);
}

export function AuthProvider({ children }) {
  const [token, setToken] = useState(null);
  const [ready, setReady] = useState(false);

  useEffect(() => {
    (async () => {
      const t = await loadToken();
      if (t) {
        setToken(t);
        setAuthToken(t);
      }
      setReady(true);
    })();
  }, []);

  const login = async (newToken) => {
    if (!newToken) return;
    setToken(newToken);
    setAuthToken(newToken);
    await saveToken(newToken);
  };

  const logout = async () => {
    setToken(null);
    setAuthToken(null);
    await clearToken();
  };

  const value = useMemo(
    () => ({ token, ready, login, logout }),
    [token, ready],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
