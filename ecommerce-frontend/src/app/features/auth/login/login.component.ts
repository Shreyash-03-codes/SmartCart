import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth/auth-service.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  showPassword = false;
  isLoading = false;
  fb: FormBuilder = new FormBuilder();

  constructor(
    private authService: AuthService, 
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object // Inject platform ID
  ) {}

  ngOnInit(): void {
    this.createForm(); // Create form FIRST
    
    // Only access localStorage in browser
    if (isPlatformBrowser(this.platformId)) {
      localStorage.clear();
      
      // Check if this is a redirect from OAuth2
      this.handleOAuth2Redirect();
    }
  }

 private handleOAuth2Redirect() {
  const urlParams = new URLSearchParams(window.location.search);
  const token = urlParams.get('token');
  const email = urlParams.get('email');
  const name = urlParams.get('name');
  const role = urlParams.get('role'); 

  if (token) {
    // For OAuth2, role is a single string
    const response = {
      accessToken: token,
      email: email,
      name: name,
      role: role ? [role] : ['USER']
    };
    
    this.authService.saveAuthData(response);
    this.router.navigate(['/home']);
  }
}

  private createForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  get emailErrors() {
    if (!this.loginForm) return null; // Add safe check
    const email = this.loginForm.get('email');
    if (email?.touched && email.errors) {
      if (email.errors['required']) return 'Email is required';
      if (email.errors['email']) return 'Enter a valid email';
    }
    return null;
  }

  get passwordErrors() {
    if (!this.loginForm) return null; // Add safe check
    const password = this.loginForm.get('password');
    if (password?.touched && password.errors) {
      if (password.errors['required']) return 'Password is required';
      if (password.errors['minlength']) return 'Password must be at least 6 characters';
    }
    return null;
  }

onSubmit() {
  if (!this.loginForm) return;
  
  Object.keys(this.loginForm.controls).forEach(key => {
    this.loginForm.get(key)?.markAsTouched();
  });

  if (this.loginForm.valid) {
    this.isLoading = true;
    
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        
        this.authService.saveAuthData(response); // Pass the whole response
        this.isLoading = false;
        this.router.navigate(['/home']);
      },
      error: (error) => {
        this.isLoading = false;
        alert('Login failed. Please check your credentials and try again.');
      }
    });
  }
}

  callOAuth2() {
    this.authService.loginWithGoogle();
  }
}