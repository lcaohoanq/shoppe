const isDev = process.env.NODE_ENV === 'development';

export const config = {
  url: {
    KEYCLOAK_BASE_URL: "http://localhost:8080",
    API_BASE_URL: isDev ? "http://localhost:9080/api" : "/api",
    OMDB_BASE_URL: "https://www.omdbapi.com",
    AVATARS_DICEBEAR_URL: "https://api.dicebear.com/6.x"
  }
};
