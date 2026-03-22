import { Injectable } from '@angular/core';
import { Jwt } from '../../../model/jwt.model';


@Injectable({
  providedIn: 'root'
})
export class JwtService {

  private readonly TOKEN_KEY = 'token';
  private readonly USERNAME_KEY = 'username';
  private readonly ROLE_KEY = 'userRole';

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.USERNAME_KEY);
  }

  getUserRole(): string | null {
    return localStorage.getItem(this.ROLE_KEY);
  }

  getJwt(): Jwt | null {
    const token = this.getToken();
    const username = this.getUsername();
    const userRole = this.getUserRole();

    if (!token || !username || !userRole) return null;

    return { token, username, userRole };
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  saveJwt(jwt: Jwt): void {
    localStorage.setItem(this.TOKEN_KEY, jwt.token);
    localStorage.setItem(this.USERNAME_KEY, jwt.username);
    localStorage.setItem(this.ROLE_KEY, jwt.userRole);
  }

  clearJwt(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USERNAME_KEY);
    localStorage.removeItem(this.ROLE_KEY);
  }
}