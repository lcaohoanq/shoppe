import { Profile } from "@/types";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet, FlatList } from "react-native";
import { GestureHandlerRootView } from "react-native-gesture-handler";

export default function SettingsScreen() {
  const [sampleData, setSampleData] = useState<Profile[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get<Profile[]>(
        `https://6512cbd2b8c6ce52b3963937.mockapi.io/api/v1/profiles`
      );
      setSampleData(response.data); // Set data directly to the state
    };

    fetchData();
  }, []);

  // Log outside useEffect to see updated state
  console.log("Current Data:", sampleData);

  return (
    <GestureHandlerRootView style={{ flex: 1, marginTop: 30, padding: 10 }}>
      <FlatList
        data={sampleData}
        ListHeaderComponent={
          <View>
            <View style={styles.container}>
              <Text style={styles.text}>Settings Screen</Text>
            </View>
            <Text>Item 1</Text>
            <Text>Item 2</Text>
            <Text>Item 3</Text>
          </View>
        }
        renderItem={({ item }) => (
          <View>
            <Text>{item.name}</Text>
            <Text>{item.role}</Text>
            <Text>{item.githubAccount}</Text>
            <Text>{item.avatarUrl}</Text>
          </View>
        )}
        keyExtractor={(item) => item.githubAccount}
      />
    </GestureHandlerRootView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: 20,
    fontWeight: "bold",
  },
});
