// In your auth-service.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../storage/stoarge.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  BASE_URL = 'http://localhost:8080/api/v1/auth';

  constructor(
    private http: HttpClient, 
    private storage: StorageService
  ) {}

  login(credentials: { email: string, password: string }): Observable<any> {
    return this.http.post(`${this.BASE_URL}/login`, credentials);
  }

  register(userData: { name: string, email: string, password: string }): Observable<any> {
    return this.http.post(`${this.BASE_URL}/register`, userData);
  }

  forgetPassword(email: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/forget-password?email=${email}`);
  }

  verifyEmail(email: string | null, token: string | null): Observable<any> {
    return this.http.get(`${this.BASE_URL}/verify-email?email=${email}&token=${token}`);
  } 

  resetPassword(token: string, email: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.BASE_URL}/reset-password`, { token, email, newPassword });
  }

  refreshToken(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/refresh-token`);
  }

  loginWithGoogle(): void {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }

saveAuthData(response: any) {
  
  const responseData = response.data || response; 
  const roles = responseData.role || []; 
  const primaryRole = roles.length > 0 ? roles[0] : 'USER';
  
  this.storage.setItem('token', responseData.accessToken);
  this.storage.setItem('name', responseData.name);
  this.storage.setItem('username', responseData.email);
  this.storage.setItem('userRole', primaryRole);
  this.storage.setItem('roles', roles);
  
  console.log('Saved to storage:', {
    token: responseData.accessToken,
    name: responseData.name,
    email: responseData.email,
    roles: roles,
    primaryRole: primaryRole
  });
}

isAdmin(): boolean {
  const role = this.storage.getItem('userRole');
  return role === 'ADMIN' || role === 'admin';
}

isUser(): boolean {
  const role = this.storage.getItem('userRole');
  return role === 'USER' || role === 'user';
}

// Optional: Check if user has a specific role
hasRole(roleName: string): boolean {
  const roles = this.storage.getItem('roles') || [];
  return roles.includes(roleName) || roles.includes(roleName.toUpperCase());
}
  logout() {
    this.storage.clear();
  }

  isLoggedIn(): boolean {
    return !!this.storage.getItem('token');
  }

}