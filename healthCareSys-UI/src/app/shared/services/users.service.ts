import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  async createUser(userData: any) {
    try {
      const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST', 
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
        },
        body: JSON.stringify(userData) 
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to create user: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }

      return await response.json(); 
    } catch (error) {
      console.error('Error creating user:', error);
      throw error;
    }
  }


  async getAllUsers() {
    try {
      const response = await fetch('http://localhost:8080/users/getAllUsers', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to fetch users: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }

      return await response.json(); 
    } catch (error) {
      console.error('Error fetching users:', error);
      throw error;
    }
  }

  async getUserById(userId: string) {
     const user = localStorage.getItem("user");
    if (user) return JSON.parse(user); 
    try {
      const response = await fetch(`http://localhost:8080/users/getUserById/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
        }
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to fetch users: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }
      const newUser = await response.json();
      localStorage.setItem("user", JSON.stringify(newUser));
      return newUser; 
    } catch (error) {
      console.error('Error fetching users:', error);
      throw error;
    }
  }

  async updateUser(userId: string | null, userData: any) {
    try {
        const response = await fetch(`http://localhost:8080/users/updateByAdmin/${userId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
        },
        body: JSON.stringify(userData)
      });

      if (!response.ok) throw new Error('Error updating user');
      return await response.status;
    } catch (error) {
      console.error('Error deleting users:', error);
      throw error;
    }
  }


  async deleteUserById(userId: any ){
    try {
      const response = await fetch(`http://localhost:8080/users/deleteUserById/${userId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer  ${localStorage.getItem('authToken')}`
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to delete user: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }
      return await response.status; 
    } catch (error) {
      console.error('Error deleting users:', error);
      throw error;
    }
  }
}
