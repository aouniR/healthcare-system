import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';
@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export default class SettingsComponent implements OnInit{

  userForm: FormGroup;
  userId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    this.userForm = this.fb.group({
      password: ['', [Validators.required, Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$')]],
    });
  }

  ngOnInit(): void {
    if(this.authService.isAdmin()){
      console.log("Admin");
      this.userId = this.route.snapshot.paramMap.get('id'); 
    }
    else{
      this.userId = this.authService.getUserId();
      console.log("isNotAdmin, id:",this.userId);
    }
    
  }

  cancel(){
    if(this.authService.isAdmin())
    this.router.navigate(['/admin/dashboard']);
    else if(this.authService.isPorfSante())
    this.router.navigate(['/profSante/patients']);
    else
    this.router.navigate(['/agent/patients']);
  }

  async updateUser() {
    if (this.userForm.valid) {
      const updatedData = {
        password: this.userForm.value.password
      };
      
      try {
        let status;
        if(this.authService.isAdmin())
        status = await this.userService.updateUserByAdmin(this.userId, updatedData);
        else
        status = await this.userService.updateUserByAgent(this.userId, updatedData);
        if (status === 200) {
          this.authService.logout(); 
          this.router.navigate(['/login']);
        } else {
          alert('Error: User update was not successful.');
        }
      } catch (error) {
        console.error('Error updating user:', error);
        alert('Error updating user. Please try again later.');
      }
    }
  }
}
