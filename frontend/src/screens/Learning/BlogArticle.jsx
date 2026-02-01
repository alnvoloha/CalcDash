import React from 'react';
import { ScrollView, Text, SafeAreaView, StyleSheet } from 'react-native';
import C from '../../theme/colors';

export default function BlogArticle({ route }) {
  const { article } = route.params; // { topic, content }

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>{article.topic}</Text>
        <Text style={styles.text}>{article.content}</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 50, // Отступ для Dynamic Island
    backgroundColor: C.background,
  },
  content: {
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: '700',
    marginBottom: 16,
    color: C.text,
  },
  text: {
    fontSize: 16,
    lineHeight: 22,
    color: C.text,
  },
});