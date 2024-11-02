import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NotificationService } from '../../shared/services/notification.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export default class NotificationComponent implements OnInit {
  notificationId: string | null = null;
  notification: any = null;

  constructor(private route: ActivatedRoute, private notificationService: NotificationService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(async (params) => {
      this.notificationId = params.get('id');
      if (this.notificationId) {
        this.notification = await this.notificationService.getNotificationById(this.notificationId);
      }
    });
  }

  parsedMessage() {
    if (this.notification && this.notification.message) {
      return Object.entries(JSON.parse(this.notification.message)).map(([key, value]) => ({
        key,
        value: Array.isArray(value) ? value.join(', ') : value
      }));
    }
    return [];
  }
}
