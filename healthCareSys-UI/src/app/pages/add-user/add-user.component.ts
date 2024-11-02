import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule ],
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.scss'
})
export default class AddUserComponent {
  userForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
    this.userForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$')]],
      role: ['', Validators.required]
    });
  }

  async addUser(): Promise<void> {
    if (this.userForm.valid) {
      try {
        const userData = this.userForm.value;
        await this.userService.createUser(userData);
        this.router.navigate(['/admin/users/listUsers']);
      } catch (error) {
        console.error('Error adding user:', error);

      }
    }
  }

  cancel(){
    this.router.navigate(['/admin/users/listUsers']); 
  }
}

