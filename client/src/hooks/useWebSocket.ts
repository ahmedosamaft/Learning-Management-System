import { useEffect, useRef } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { useNotificationStore } from "../store/notificationStore";

export const useWebSocket = (userId: string) => {
  const stompClient = useRef<Client | null>(null);
  const addNotification = useNotificationStore(
    (state) => state.addNotification
  );

  useEffect(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("Connected to WebSocket");

        // Subscribe to public notifications
        client.subscribe("/topic/notification.all", (message) => {
          try {
            const notification = JSON.parse(message.body);
            addNotification({
              id: notification.id,
              title: notification.title,
              body: notification.body,
              timestamp: new Date(),
            });
          } catch (error) {
            console.error("Error parsing notification:", error);
          }
        });

        // Subscribe to private notifications
        if (userId) {
          client.subscribe(`/topic/notification.${userId}`, (message) => {
            try {
              const notification = JSON.parse(message.body);
              addNotification({
                id: notification.id,
                title: notification.title,
                body: notification.body,
                timestamp: new Date(),
              });
            } catch (error) {
              console.error("Error parsing notification:", error);
            }
          });
        }
      },
      onStompError: (frame) => {
        console.error("STOMP error:", frame.headers["message"]);
        console.error("Details:", frame.body);
      },
      onWebSocketError: (error) => {
        console.error("WebSocket error:", error);
      },
      onDisconnect: () => {
        console.log("Disconnected from WebSocket");
      },
    });

    try {
      client.activate();
      stompClient.current = client;
    } catch (error) {
      console.error("Error activating WebSocket client:", error);
    }

    return () => {
      if (client.active) {
        client.deactivate();
      }
    };
  }, [userId, addNotification]);

  return stompClient.current;
};
