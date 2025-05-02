import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { History } from "@/components/ui/history";
// import { v4 as uuidv4 } from "uuid";

import {
    useChatInteract,
    useChatMessages,
    IStep,
} from "@chainlit/react-client";
import { useState } from "react";

interface PlaygroundProps {
    onLogout: () => void;
}

export function Playground({ onLogout }: PlaygroundProps) {
    const [inputValue, setInputValue] = useState("");
    const { sendMessage } = useChatInteract();
    const { messages } = useChatMessages();

    const testMessages = [
        "Hello, this is a test message.",
        "Here is another message for the history.",
        "This is a third test message."
    ];

    const handleSendMessage = () => {
        const content = inputValue.trim();
        if (content) {
            const message = {
                name: "user",
                type: "user_message" as const,
                output: content,
            };
            sendMessage(message, []);
            setInputValue("");
        }
    };

    const renderMessage = (message: IStep) => {
        const dateOptions: Intl.DateTimeFormatOptions = {
            hour: "2-digit",
            minute: "2-digit",
        };
        const date = new Date(message.createdAt).toLocaleTimeString(
            undefined,
            dateOptions
        );
        return (
            <div key={message.id} className="flex items-start space-x-2">
                <div className="w-20 text-sm text-green-500">{message.name}</div>
                <div className="flex-1 border rounded-lg p-2">
                    <p className="text-black dark:text-white">{message.output}</p>
                    <small className="text-xs text-gray-500">{date}</small>
                </div>
            </div>
        );
    };

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900 flex">
            <div className="w-1/5 border-r p-4">
                <History messages={testMessages} />
            </div>
            {/* Bouton de déconnexion */}
            <div className="flex-1 flex flex-col">
                <div className="p-4 flex justify-end">
                    <Button onClick={onLogout}>Déconnexion</Button>
                </div>
                <div className="flex-1 overflow-auto p-6">
                    <div className="space-y-4">
                        {messages.map((message) => renderMessage(message))}
                    </div>
                </div>
                <div className="border-t p-4 bg-white dark:bg-gray-800">
                    <div className="flex items-center space-x-2">
                        <Input
                            autoFocus
                            className="flex-1"
                            id="message-input"
                            placeholder="Type a message"
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onKeyUp={(e) => {
                                if (e.key === "Enter") {
                                    handleSendMessage();
                                }
                            }}
                        />
                        <Button onClick={handleSendMessage} type="submit">
                            Send
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}
