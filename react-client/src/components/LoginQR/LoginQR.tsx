import {useEffect, useState} from "react";
import {QRCodeCanvas} from "qrcode.react";
import axios from "axios";
import {Box} from "@mui/material";
import CircularProgress from "@mui/material/CircularProgress";
// Define API base URL
const API_BASE_URL = "http://localhost:4003"; // Change to your actual API URL
const API_BASE_URL_PROD = "http://192.168.88.172:4003"; // Change to your production API URL

// Define session status type
type SessionStatus = "PENDING" | "APPROVED" | "EXPIRED";

// Define session response type
interface SessionResponse {
  sessionId: string;
  status: SessionStatus;
  userId?: string;
  createdAt: string;
  expiresAt: string;
}

const LoginQR = () => {
  const [sessionId, setSessionId] = useState<string | null>(null);
  const [status, setStatus] = useState<SessionStatus>("PENDING");
  const [userId, setUserId] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Function to create a new session
  const createNewSession = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.post(`${API_BASE_URL}/qr-login`);
      setSessionId(response.data);
      setStatus("PENDING");
      setUserId(null);
    } catch (err) {
      console.error("Error creating session:", err);
      setError("Failed to create login session. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // Create session on component mount
  useEffect(() => {
    createNewSession();
  }, []);

  // Poll for session status updates
  useEffect(() => {
    if (!sessionId) return;

    const intervalId = setInterval(async () => {
      try {
        const response = await axios.get<SessionResponse>(
          `${API_BASE_URL}/qr-login/${sessionId}`
        );

        setStatus(response.data.status);

        if (response.data.userId) {
          setUserId(response.data.userId);
        }

        // If approved, we could redirect here or take other actions
        if (response.data.status === "APPROVED") {
          console.log("Login approved!");
          // You might want to save user information to local storage here
          // localStorage.setItem("user", JSON.stringify({ id: response.data.userId }));
        }
      } catch (err) {
        console.error("Error checking session status:", err);
        // Don't set error here as it could be temporary network issue
      }
    }, 2000); // Poll every 2 seconds

    return () => clearInterval(intervalId); // Cleanup on unmount
  }, [sessionId]);

  // Function to download QR code as image
  const downloadQR = () => {
    const canvas = document.getElementById("qrcode") as HTMLCanvasElement;
    if (!canvas) return;

    const url = canvas.toDataURL("image/png");
    const link = document.createElement("a");
    link.href = url;
    link.download = "login-qr.png";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (

    <Box sx={{textAlign: 'center', width: '100%'}}>
      {error && (
        <Box className="error-message">
          <p>{error}</p>
          <button onClick={createNewSession}>Try Again</button>
        </Box>
      )}

      {loading ? (
        <Box className="loading">
          <CircularProgress size={40} />
        </Box>
      ) : (
        <Box sx={{display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column"}}>
          {sessionId ? (
            <>
              {status === "PENDING" && (
                <Box>
                  <Box>
                    <QRCodeCanvas
                      id="qrcode"
                      value={sessionId} // Use session ID as QR value
                      size={290}
                      level={"H"}
                      includeMargin={true}
                    />
                  </Box>
                  <Box>
                    <button  onClick={downloadQR}>
                      Download QR
                    </button>
                  </Box>
                </Box>
              )}

              {status === "APPROVED" && (
                <Box >
                  <h2>Login Successful!</h2>
                  <p>Welcome, User: {userId}</p>
                  <p>Redirecting to dashboard...</p>
                  {/* In a real app, you would redirect to dashboard or main app */}
                </Box>
              )}

              {status === "EXPIRED" && (
                <Box >
                  <h2>QR Code Expired</h2>
                  <p>This QR code has expired for security reasons.</p>
                  <button onClick={createNewSession} className="new-qr-btn">
                    Generate New QR Code
                  </button>
                </Box>
              )}
            </>
          ) : (
            <p>Unable to create session. Please try again.</p>
          )}
        </Box>
      )}
    </Box>

  );
}

export default LoginQR
