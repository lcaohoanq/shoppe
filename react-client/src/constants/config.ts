const config = {
  // Base protocol and host (e.g., 'http://localhost')
  baseHost: import.meta.env.VITE_REACT_APP_BASE_HOST || 'http://localhost',

  // Default port and path
  basePort: import.meta.env.VITE_REACT_APP_BASE_PORT || '4000',

  // Full default base URL
  baseUrl: import.meta.env.VITE_REACT_APP_API_URL || 'http://localhost:8080/api/v1',

  // Service-specific URLs
  services: {
    auth: import.meta.env.VITE_REACT_APP_AUTH_SERVICE_URL || 'http://localhost:4006/api',
    users: import.meta.env.VITE_REACT_APP_USER_SERVICE_URL || 'http://localhost:4006/api',
    notifications: import.meta.env.VITE_REACT_APP_NOTIFICATION_SERVICE_URL || 'http://localhost:4005/api',
    products: import.meta.env.VITE_REACT_APP_PRODUCT_SERVICE_URL || 'http://localhost:4003/api',
    // Add other services as needed
  },

  maxSizeUploadAvatar: 1048576 // bytes
}

export default config
