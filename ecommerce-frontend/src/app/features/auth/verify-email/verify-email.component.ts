import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth-service.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './verify-email.component.html',
  styleUrl: './verify-email.component.css'
})
export class VerifyEmailComponent {
  message: string = '';
  showLoginButton: boolean = false;
  showVerifyButton: boolean = true;
  isLoading: boolean = false;  // Add loading state

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}  // Constructor is empty - good!
  
  verifyEmail() {
    if (this.isLoading) return;
    
    this.isLoading = true;
    this.showVerifyButton = false;
    
    const urlParams = new URLSearchParams(window.location.search);
    const email = urlParams.get('email');
    const token = urlParams.get('token');
    
      this.authService.verifyEmail(email, token).subscribe({
        next: (response) => {
          this.message = '✅ Email verified successfully! You can now login.';
          this.showLoginButton = true;
          this.isLoading = false;
        },
        error: (error) => {
          this.message = '❌ Email verification failed. Please try again.';
          this.showLoginButton = true;
          this.isLoading = false;
        }
      });

    
  }

  goToLogin() {
    this.router.navigate(['/auth/login']);
  }
}