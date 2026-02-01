import React from "react";
import {
  FlatList,
  Text,
  TouchableOpacity,
  SafeAreaView,
  StyleSheet,
} from "react-native";
import { fetchTheory } from "../../api";
import C from "../../theme/colors";

export default function BlogList({ navigation }) {
  const topics = ["trachtenberg", "chinese", "factorial", "vedic"];

  return (
    <SafeAreaView style={styles.container}>
      <FlatList
        data={topics}
        keyExtractor={(t) => t}
        contentContainerStyle={styles.list}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.item}
            onPress={async () => {
              const content = await fetchTheory(item);
              navigation.navigate("BlogArticle", {
                article: { topic: item, content },
              });
            }}
          >
            <Text style={styles.itemText}>{item}</Text>
          </TouchableOpacity>
        )}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, paddingTop: 50, backgroundColor: C.background },
  list: { padding: 20 },
  item: { padding: 16, borderBottomWidth: 1, borderBottomColor: C.border },
  itemText: { fontSize: 18, color: C.text },
});
