import { Component, Input, Output } from '@angular/core';
import { Router } from '@angular/router'; 
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule ],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @Input() loginData = { username: '', password: '' };
  @Output() errorMessage: string = '';

  constructor(
    private authService: AuthService, 
    private router: Router) {}

  async login() {
    try {
      await this.authService.handleLogin(this.loginData).then(() => {
        this.router.navigate(['/admin']); 
      });
    } catch (error) {
      console.error('Login failed!');
      this.errorMessage = 'Login failed!'
    }
  }
}
