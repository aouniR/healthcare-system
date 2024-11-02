import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FilterPipeforUserByRole } from '../../shared/pipes/filterPipeforUserByRole';
import { UserService } from '../../shared/services/users.service'; 
import { Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipeforUserByRole],
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss'] 
})
export default class ListUsersComponent implements OnInit {
  searchTerm: string = '';
  users: any[] = [];
  userToDelete: number | null = null;
  showModal: boolean = false;

  constructor(private userService: UserService, private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    this.fetchAllUsers(); 
  }

  async fetchAllUsers() {
    try {
      this.users = await this.userService.getAllUsers(); 
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  }

  editUser(userId: string) {
    if(userId==this.authService.getUserId()){
      console.log("can't update your Admin profile!");
      alert('Error: Unable to update your Admin profile.')
    }else{
      this.router.navigate([`/admin/users/updateUser/${userId}`]);
    }
  }

  deleteUser(userId: number) {
    if(userId==this.authService.getUserId()){
      console.log("can't delete your Admin profile!");
      alert('Error: Unable to delete your Admin profile.')
    }else{
      this.userToDelete = userId;
      this.showModal = true;
    }
  }

  async confirmDelete() {
    if (this.userToDelete !== null) {
      try {
        const status = await this.userService.deleteUserById(this.userToDelete);
        
        if (status === 204) {
          localStorage.removeItem("notifications")
          this.users = this.users.filter(user => user.id !== this.userToDelete);
        } else {
          alert('Error: User deletion was not successful.');
        }
        
        this.userToDelete = null; 
      } catch (error) {
        console.error('Error deleting user:', error);
        alert('Error: Unable to delete the user. Please try again later.');
      }
    }
    this.showModal = false; 
  }

  closeModal() {
    this.showModal = false;
    this.userToDelete = null; 
  }
}
