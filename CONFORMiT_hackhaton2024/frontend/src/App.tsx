import { useEffect, useState } from "react";
import { sessionState, useChatSession } from "@chainlit/react-client";
import { Playground } from "@/components/playground.tsx";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { useRecoilValue } from "recoil";

const userEnv = {};

function App() {
  const { connect } = useChatSession();
  const session = useRecoilValue(sessionState);

  const [token, setToken] = useState<string | null>(null);
  const [authPage, setAuthPage] = useState<"login" | "register">("login");

  useEffect(() => {
    // Vérifie si un token est stocké et valide
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      fetch("http://localhost:80/validate-token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": storedToken,
        },
      })
          .then((res) => {
            if (!res.ok) {
              // Si le token est invalide, on le supprime et on affiche la page de connexion
              throw new Error("Token invalide");
            }
            return res.json();
          })
          .then(() => {
            // Si le token est valide, on le garde
            setToken(storedToken);
          })
          .catch(() => {
            // Token invalide, on le supprime
            localStorage.removeItem("token");
            setToken(null);
          });
    }
  }, []);

  useEffect(() => {
    if (!token) {
      return;
    }
    // Déconnecte la socket existante si elle est connectée
    if (session?.socket.connected) {
      session.socket.disconnect();
    }
    // Connecte à Chainlit avec le token valide
    connect({
      userEnv,
      accessToken: token,
    });
  }, [connect, token]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  const handleLogin = (newToken: string) => {
    localStorage.setItem("token", newToken);
    setToken(newToken);
  };

  if (!token) {
    // Affiche la page d'authentification par défaut
    return authPage === "login" ? (
        <Login onLogin={handleLogin} onSwitchToRegister={() => setAuthPage("register")} />
    ) : (
        <Register onRegister={handleLogin} onSwitchToLogin={() => setAuthPage("login")} />
    );
  }

  return (
      <div>
        <Playground onLogout={handleLogout} />
      </div>
  );
}

export default App;
