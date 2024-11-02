import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

    async getAllNotifications() {
        const notifications = localStorage.getItem("notifications");
        if (notifications) return JSON.parse(notifications); 
        try {
          const response = await fetch('http://localhost:8080/notifications/getAll', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
            }
          });
    
          if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to fetch notification: ${response.statusText} - ${JSON.stringify(errorData)}`);
          }
          const newNotifications = await response.json();
          localStorage.setItem("notifications", JSON.stringify(newNotifications));
          return newNotifications; 
        } catch (error) {
          console.error('Error fetching notification:', error);
          throw error;
        }
      }

    async getNotificationById(notificationId: string) {
        const notifications = localStorage.getItem("notifications");
        if (notifications) {
          const parsedNotifications = JSON.parse(notifications);
          const notification = parsedNotifications.find((n: { id: string; }) => n.id === notificationId);
          if (notification) {
            return notification;
          }
        }
      
        try {
          const response = await fetch(`http://localhost:8080/notifications/${notificationId}`, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${localStorage.getItem('authToken')}`
            }
          });
      
          if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to fetch notification: ${response.statusText} - ${JSON.stringify(errorData)}`);
          }
          console.log("notf. fetched from backend!")
          return await response.json(); 
        } catch (error) {
          console.error('Error fetching notification:', error);
          throw error;
        }
      }
      
    async deleteNotificationById(notificationId: any ){
        try {
          const response = await fetch(`http://localhost:8080/notifications/deleteNotificationById/${notificationId}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
            }
          });
    
          if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to delete notification: ${response.statusText} - ${JSON.stringify(errorData)}`);
          }
          return await response.status; 
        } catch (error) {
          console.error('Error deleting notification:', error);
          throw error;
        }
      }    
}