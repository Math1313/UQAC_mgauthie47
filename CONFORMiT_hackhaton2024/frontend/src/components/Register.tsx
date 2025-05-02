import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

interface RegisterProps {
    onRegister: (token: string) => void;
    onSwitchToLogin: () => void;
}

export function Register({ onRegister, onSwitchToLogin }: RegisterProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");

    const handleRegister = async () => {
        if (password !== confirmPassword) {
            setError("Les mots de passe ne correspondent pas");
            return;
        }

        try {
            const res = await fetch("http://localhost:80/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (!res.ok) {
                const errorData = await res.json();
                throw new Error(errorData.error || "Erreur lors de l'inscription");
            }

            const data = await res.json();
            const newToken = `Bearer: ${data.token}`;
            onRegister(newToken);
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            } else {
                setError("An unknown error occurred");
            }
        }
    };

    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.key === 'Enter') {
            handleRegister();
        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900">
            <div className="w-full max-w-sm p-6 bg-white rounded-md shadow-md dark:bg-gray-800">
                <h1 className="text-2xl font-semibold text-center text-gray-700 dark:text-white">
                    Inscription
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
                    <Input
                        placeholder="Confirmer le mot de passe"
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        className="mt-2"
                    />
                    <Button onClick={handleRegister} className="mt-4 w-full">
                        S'inscrire
                    </Button>
                    <p className="mt-4 text-center text-gray-600 dark:text-gray-400">
                        Vous avez déjà un compte ?{" "}
                        <button
                            onClick={onSwitchToLogin}
                            className="text-blue-500 hover:underline"
                        >
                            Connectez-vous
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
}
