import useProfiles from "@/hooks/useProfiles";
import React from "react";
import {
  ActivityIndicator,
  FlatList,
  Image,
  Linking,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";

export default function ProfileScreen() {
  const { data, isLoading, error } = useProfiles();

  if (isLoading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#0000ff" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {data?.map((userProfile) => (
        <View key={userProfile.githubAccount} style={styles.profileContainer}>
          {/* User Avatar */}
          <Image
            source={{ uri: userProfile.avatarUrl }}
            style={styles.avatar}
          />

          {/* User Name */}
          <Text style={styles.name}>{userProfile.name}</Text>

          {/* User Role */}
          <Text style={styles.role}>{userProfile.role}</Text>

          {/* GitHub Account */}
          <Text style={styles.github}>GitHub: {userProfile.githubAccount}</Text>

          {/* Social Links */}
          <FlatList
            data={userProfile.socialLinks}
            keyExtractor={(item) => item.type}
            renderItem={({ item }) => (
              <TouchableOpacity onPress={() => Linking.openURL(item.url)}>
                <Text style={styles.socialLink}>{item.type}</Text>
              </TouchableOpacity>
            )}
          />
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#f9f9f9",
  },
  profileContainer: {
    alignItems: "center",
    marginBottom: 40,
  },
  avatar: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 20,
  },
  name: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 10,
  },
  role: {
    fontSize: 16,
    color: "#555",
    textAlign: "center",
    marginBottom: 10,
  },
  github: {
    fontSize: 16,
    color: "#007acc",
    marginBottom: 20,
  },
  socialLink: {
    fontSize: 16,
    color: "#1e90ff",
    marginBottom: 10,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
