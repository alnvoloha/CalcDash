import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import C from '../theme/colors';

export default function Screen({ children }) {
  return (
    <View style={s.c}>
      <StatusBar barStyle="dark-content" backgroundColor={C.background} />
      {children}
    </View>
  );
}

const s = StyleSheet.create({
  c: { flex: 1, padding: 24, backgroundColor: C.background },
});
