import api from "./axios";

/* ─── Auth ─── */
export const login = async (dto) => (await api.post("/api/users/login", dto)).data;
export const register = async (dto) => (await api.post("/api/users/register", dto)).data;

/* ─── Profile ─── */
export const fetchMe = async () => (await api.get("/api/users/profile")).data;

/* ─── Training settings ─── */
export const saveSettings = async (dto) => (await api.post("/api/settings", dto)).data;
export const getSettings = async () => (await api.get("/api/settings")).data;

/* ─── Tasks ─── */
export const genTasks = async (type) =>
  (await api.get("/api/tasks/generate", { params: { type } })).data;

/* ─── Marathon ─── */
export const startMarathon = async (difficulty) =>
  (await api.get("/api/marathon/start", { params: { difficulty } })).data;

export const answerMarathon = async (dto) =>
  (await api.post("/api/marathon/answer", dto)).data;

export const getMarathonRecord = async () =>
  (await api.get("/api/marathon/record")).data;

export const finishMarathon = async (score) =>
  (await api.post("/api/marathon/finish", { score })).data;

/* ─── Learning ─── */
export const fetchTheory = async (topic) =>
  (await api.get(`/api/theory/${topic}`)).data;
