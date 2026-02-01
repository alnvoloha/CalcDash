import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8085",
  timeout: 15000,
  headers: { "Content-Type": "application/json" },
});

let cachedToken = null;

export function setAuthToken(token) {
  cachedToken = token || null;
  if (cachedToken) {
    api.defaults.headers.common.Authorization = `Bearer ${cachedToken}`;
  } else {
    delete api.defaults.headers.common.Authorization;
  }
}

api.interceptors.request.use((cfg) => {
  const method = (cfg.method || "GET").toUpperCase();
  console.log(`[AXIOS] -> ${method} ${cfg.url} | Auth: ${cachedToken ? "yes" : "no"}`);
  return cfg;
});

export default api;
