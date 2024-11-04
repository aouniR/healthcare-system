import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; 
  private readonly AUTH_KEY = 'authToken';

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
      catchError(this.handleError)
    );
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem(this.AUTH_KEY);
    return token !== null && !this.isTokenExpired(token);
  }

  logout(): void {
    localStorage.removeItem(this.AUTH_KEY);
    localStorage.removeItem("notifications");
    localStorage.removeItem("user");
  }

  private savetoken(token: string): void {
    localStorage.setItem(this.AUTH_KEY, token);
  }

  async handleLogin(credentials: any): Promise<void> {
    try {
      const response = await this.login(credentials).toPromise();
      this.savetoken(response.token);
    } catch (error) {
      return Promise.reject(error);
    }
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(error);
  }

  isAdmin(): boolean {
    const token = localStorage.getItem(this.AUTH_KEY);
    if (!token) return false; 
  
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles && decodedToken.roles.includes('ROLE_ADMIN');
    } catch (error) {
      console.error('Failed to decode token:', error);
      return false; 
    }
  }

  isPorfSante(): boolean {
    const token = localStorage.getItem(this.AUTH_KEY);
    if (!token) return false; 
  
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles && decodedToken.roles.includes('ROLE_PROFESSIONNELDESANTE');
    } catch (error) {
      console.error('Failed to decode token:', error);
      return false; 
    }
  }

  isAgent(): boolean {
    const token = localStorage.getItem(this.AUTH_KEY);
    if (!token) return false; 
  
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles && decodedToken.roles.includes('ROLE_AGENTADMINISTRATIF');
    } catch (error) {
      console.error('Failed to decode token:', error);
      return false; 
    }
  }

  private isTokenExpired(token: string): boolean {
    try {
      const decodedToken: any = jwtDecode(token);
      if (!decodedToken.exp) return true; 

      const expirationTime = decodedToken.exp * 1000;
      return Date.now() >= expirationTime; 
    } catch (error) {
      console.error('Failed to decode token for expiration check:', error);
      return true; 
    }
  }

  getUserId(){
    const token = localStorage.getItem(this.AUTH_KEY);
    if (!token) return null; 
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.userId;
    } catch (error) {
      console.error('Failed to decode token:', error);
      return null; 
    }
  }
}
