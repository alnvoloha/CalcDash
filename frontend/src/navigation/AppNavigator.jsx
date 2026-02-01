import React, { useContext } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";
import { AuthContext } from "../context/AuthContext";

/* ——— Auth ——— */
import LoginScreen from "../screens/LoginScreen";
import RegisterScreen from "../screens/RegisterScreen";

/* ——— Profile ——— */
import ProfileScreen from "../screens/ProfileScreen";

/* ——— Training ——— */
import TrainingSetup from "../screens/Training/Setup";
import TrainingPlay from "../screens/Training/Play";
import TrainingSummary from "../screens/Training/Summary";

/* ——— Learning ——— */
import BlogList from "../screens/Learning/BlogList";
import BlogArticle from "../screens/Learning/BlogArticle";

/* ——— Marathon ——— */
import MarathonPlay from "../screens/Marathon/Play";
import MarathonSummary from "../screens/Marathon/Summary";

/* ——— Stack navigators ——— */

const AuthStack = createNativeStackNavigator();
function AuthStackScreens() {
  return (
    <AuthStack.Navigator screenOptions={{ headerShown: false }}>
      <AuthStack.Screen name="Login" component={LoginScreen} />
      <AuthStack.Screen name="Register" component={RegisterScreen} />
    </AuthStack.Navigator>
  );
}

const TrainingStack = createNativeStackNavigator();
function TrainingStackScreens() {
  return (
    <TrainingStack.Navigator screenOptions={{ headerShown: false }}>
      <TrainingStack.Screen name="TrainingSetup" component={TrainingSetup} />
      <TrainingStack.Screen name="TrainingPlay" component={TrainingPlay} />
      <TrainingStack.Screen
        name="TrainingSummary"
        component={TrainingSummary}
      />
    </TrainingStack.Navigator>
  );
}

const LearnStack = createNativeStackNavigator();
function LearnStackScreens() {
  return (
    <LearnStack.Navigator screenOptions={{ headerShown: false }}>
      <LearnStack.Screen name="BlogList" component={BlogList} />
      <LearnStack.Screen name="BlogArticle" component={BlogArticle} />
    </LearnStack.Navigator>
  );
}

const MarathonStack = createNativeStackNavigator();
function MarathonStackScreens() {
  return (
    <MarathonStack.Navigator screenOptions={{ headerShown: false }}>
      <MarathonStack.Screen name="MarathonPlay" component={MarathonPlay} />
      <MarathonStack.Screen
        name="MarathonSummary"
        component={MarathonSummary}
      />
    </MarathonStack.Navigator>
  );
}

/* ——— Tabs (Main app) ——— */
const Tab = createBottomTabNavigator();
function MainTabs() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        headerShown: false,
        tabBarActiveTintColor: "#3DD598",
        tabBarInactiveTintColor: "#9CA3AF",
        tabBarStyle: { backgroundColor: "#FFFFFF" },
        tabBarIcon: ({ color, size }) => {
          const map = {
            Training: "stats-chart",
            Profile: "person-circle",
            Learning: "book",
            Marathon: "flash",
          };
          return <Ionicons name={map[route.name]} size={size} color={color} />;
        },
      })}
    >
      <Tab.Screen name="Marathon" component={MarathonStackScreens} />
      <Tab.Screen name="Training" component={TrainingStackScreens} />
      <Tab.Screen name="Profile" component={ProfileScreen} />
      <Tab.Screen name="Learning" component={LearnStackScreens} />
    </Tab.Navigator>
  );
}

/* ——— Root ——— */
export default function AppNavigator() {
  const { token } = useContext(AuthContext);
  return (
    <NavigationContainer>
      {token ? <MainTabs /> : <AuthStackScreens />}
    </NavigationContainer>
  );
}