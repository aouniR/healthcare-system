import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../shared/services/notification.service';
import { FilterPipeforNotificationByKey } from '../../shared/pipes/filterPipeforNotificationByKey';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-notifications',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipeforNotificationByKey],
  templateUrl: './list-notifications.component.html',
  styleUrl: './list-notifications.component.scss'
})
export default class ListNotificationsComponent implements OnInit {
  notifications: any[] = [];
  notificationToDelete: number | null = null;
  showModal: boolean = false;
  searchTerm: string = '';
  constructor(private notificationService: NotificationService,
              private router: Router) {}

  ngOnInit(): void {
    this.fetchAllNotifications(); 
  }

  async fetchAllNotifications(){
    try {
      this.notifications = await this.notificationService.getAllNotifications(); 
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  }


  parseMessage(message: string): any {
    try {
      return JSON.parse(message);
    } catch (e) {
      return message; 
    }
  }

  viewDetails(notificationId: any) {
    this.router.navigate([`/admin/notifications/${notificationId}`]);
  }

  deleteNotification(notificationId: number) {
      this.notificationToDelete = notificationId;
      this.showModal = true;
  }

  async ConfirmedDeleteNotification() {
    if (this.notificationToDelete !== null) {
      try {
        const status = await this.notificationService.deleteNotificationById(this.notificationToDelete);
        
        if (status === 204) {
          localStorage.removeItem("notifications");
          this.notifications = this.notifications.filter(notification => notification.id !== this.notificationToDelete);
        } else {
          alert('Error: Notification deletion was not successful.');
        }
        
        this.notificationToDelete = null; 
      } catch (error) {
        console.error('Error deleting notification:', error);
        alert('Error: Unable to delete the notification. Please try again later.');
      }
    }
    this.showModal = false; 
  }

  closeModal() {
    this.showModal = false;
    this.notificationToDelete = null; 
  }
}
