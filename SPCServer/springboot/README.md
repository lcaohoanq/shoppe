# Error Handling Flow

```
Request Flow:

1. JwtTokenFilter (Authentication)
   ├── No token/Invalid token -> AuthenticationEntryPoint (401)
   └── Valid token -> Continue
   
2. Security Filter (Authorization)
   ├── Insufficient role -> AccessDeniedHandler (403)
   └── Sufficient role -> Continue to Controller
   
```

- Example scenario
```java
// Scenario 1: Public endpoint (no JWT needed)
@GetMapping("/api/v1/public")
public ResponseEntity<?> publicEndpoint() {
// No security checks needed
return ResponseEntity.ok("Public data");
}

// Scenario 2: Protected endpoint (JWT required, any role)
@GetMapping("/api/v1/protected")
@PreAuthorize("isAuthenticated()")  // Just needs valid JWT
public ResponseEntity<?> protectedEndpoint() {
// User must have valid JWT
return ResponseEntity.ok("Protected data");
}

// Scenario 3: Role-specific endpoint (JWT + specific role required)
@GetMapping("/api/v1/admin")
@PreAuthorize("hasRole('MANAGER')")  // Needs JWT + MANAGER role
public ResponseEntity<?> adminEndpoint() {
// User must have valid JWT and MANAGER role
return ResponseEntity.ok("Admin data");
}
```
- Request Flow
- 
```txt

GET /roles/1 (no token)
↓
JwtTokenFilter (no token found)
↓
AuthenticationEntryPoint (returns 401 Unauthorized)

GET /roles/1 (invalid token)
↓
JwtTokenFilter (invalid token)
↓
AuthenticationEntryPoint (returns 401 Unauthorized)

GET /roles/1 (valid token, wrong role)
↓
JwtTokenFilter (valid token)
↓
@PreAuthorize check fails
↓
AccessDeniedHandler (returns 403 Forbidden)

```