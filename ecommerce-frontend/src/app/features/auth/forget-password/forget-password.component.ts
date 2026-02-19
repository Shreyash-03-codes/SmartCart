import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth/auth-service.service';

@Component({
  selector: 'app-forget-password',
  standalone: true, // Add for Angular 19
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.css'
})
export class ForgetPasswordComponent implements OnInit {
  forgetPasswordForm!: FormGroup;
  isLoading = false;
  successMessage: string | null = null;
  errorMessage: string | null = null; // Add error message

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.forgetPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get email() {
    return this.forgetPasswordForm.get('email')!;
  }

  onSubmit(): void {
    if (this.forgetPasswordForm.invalid) {
      // Mark all fields as touched to show validation errors
      Object.keys(this.forgetPasswordForm.controls).forEach(key => {
        this.forgetPasswordForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isLoading = true;
    this.successMessage = null;
    this.errorMessage = null;
    
    const email = this.forgetPasswordForm.get('email')?.value;
    
    this.authService.forgetPassword(email).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = response.message || 'If an account with that email exists, a reset link has been sent.';
        // Optionally clear the form
        // this.forgetPasswordForm.reset();
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error sending reset link:', error);
        this.errorMessage = error.error?.message || 'An error occurred. Please try again later.';
      }
    });
  }
}