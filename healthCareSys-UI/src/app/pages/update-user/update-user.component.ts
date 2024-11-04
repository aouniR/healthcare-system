import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/users.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.scss'
})
export default class UpdateUserComponent implements OnInit{

  userForm: FormGroup;
  userId: string | null = null;
  existingUser: any; 

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('id'); 
    this.fetchUser();
  }

  async fetchUser() {
    if (this.userId) {
      this.existingUser = await this.userService.getUserById(this.userId); 
      this.userForm.patchValue(this.existingUser);
    }
  }

  async updateUser() {
    if (this.userForm.valid) {
      const updatedData = {
        username: this.userForm.value.username,
        email: this.userForm.value.email,
        role: this.userForm.value.role
      };

      try {
        const status = await this.userService.updateUserByAdmin(this.userId, updatedData);
        if (status === 200) {
          this.router.navigate(['/admin/users/listUsers']); 
        } else {
          alert('Error: User update was not successful.');
        }
      } catch (error) {
        console.error('Error updating user:', error);
        alert('Error updating user. Please try again later.');
      }
    }
  }
  cancel(){
    this.router.navigate(['/admin/users/listUsers']); 
  }
}
