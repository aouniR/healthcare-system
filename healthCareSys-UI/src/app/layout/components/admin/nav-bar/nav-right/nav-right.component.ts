// Angular import
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './../../../../../shared/services/auth.service';
import { UserService } from '../../../../../shared/services/users.service';
import { NotificationService } from '../../../../../shared/services/notification.service';

interface User {
  email: string;
  id: string;
  role: string;
  username: string;
}


@Component({
  selector: 'app-nav-right',
  templateUrl: './nav-right.component.html',
  styleUrls: ['./nav-right.component.scss']
})
export class NavRightComponent implements OnInit {
  user: User | null = null; 
  notifications: any[] = [];
  constructor(private authService: AuthService, 
              private router: Router, 
              private userService: UserService,
              private notficationService: NotificationService) {}
  
  logout(): void {
    this.authService.logout(); 
    this.router.navigate(['/login']);
  }

  async ngOnInit(): Promise<void> {
      const userId = await this.authService.getUserId();
      this.user = await this.userService.getUserById(userId);
      this.notifications = await this.notficationService.getAllNotifications();
  }

  editSettings(){
    const id = this.authService.getUserId();
    if(id){
      this.router.navigate([`/admin/settings/${id}`]);
    }else{
      alert("Can't go to settings!")
    }
  }
  showNotification(){
    this.router.navigate([`/admin/notifications`]);
  }
}
