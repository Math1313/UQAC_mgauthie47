import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

interface LoginProps {
    onLogin: (token: string) => void;
    onSwitchToRegister: () => void;
}

export function Login({ onLogin, onSwitchToRegister }: LoginProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try {
            const res = await fetch("http://localhost:80/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (!res.ok) {
                const errorData = await res.json();
                throw new Error(errorData.error || "Erreur lors de la connexion");
            }

            const data = await res.json();
            const newToken = `Bearer: ${data.token}`;
            onLogin(newToken);
        }  catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            } else {
                setError("An unknown error occurred");
            }
        }
    };

    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.key === 'Enter') {
            handleLogin();
        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900">
            <div className="w-full max-w-sm p-6 bg-white rounded-md shadow-md dark:bg-gray-800">
                <h1 className="text-2xl font-semibold text-center text-gray-700 dark:text-white">
                    Connexion
                </h1>
                {error && <p className="text-red-500 text-center">{error}</p>}
                <div className="mt-4" onKeyDown={handleKeyDown}>
                    <Input
                        placeholder="Nom d'utilisateur"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="mt-2"
                    />
                    <Input
                        placeholder="Mot de passe"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="mt-2"
                    />
                    <Button onClick={handleLogin} className="mt-4 w-full">
                        Se connecter
                    </Button>
                    <p className="mt-4 text-center text-gray-600 dark:text-gray-400">
                        Pas encore de compte ?{" "}
                        <button
                            onClick={onSwitchToRegister}
                            className="text-blue-500 hover:underline"
                        >
                            Inscrivez-vous
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
}
