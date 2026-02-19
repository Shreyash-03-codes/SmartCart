import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth/auth-service.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  loading = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  showPassword = false; // Add for password toggle
  showConfirmPassword = false; // Add for confirm password toggle

  email!: string;
  token!: string;
  
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
      this.token = params['token'];
      
      // Check if email and token exist
      if (!this.email || !this.token) {
        this.errorMessage = 'Invalid or expired password reset link.';
      }
    });
  }

  private initializeForm(): void {
    this.resetPasswordForm = this.formBuilder.group(
      {
        newPassword: ['', [
          Validators.required, 
          Validators.minLength(6),
          Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d).+$/) // At least one letter and one number
        ]],
        confirmPassword: ['', Validators.required]
      },
      { validators: this.passwordMatchValidator }
    );
  }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const newPassword = control.get('newPassword');
    const confirmPassword = control.get('confirmPassword');

    if (!newPassword || !confirmPassword) {
      return null;
    }

    return newPassword.value === confirmPassword.value ? null : { passwordMismatch: true };
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  // Helper getters for error messages
  get newPasswordErrors() {
    const control = this.resetPasswordForm.get('newPassword');
    if (control?.touched && control.errors) {
      if (control.errors['required']) return 'Password is required';
      if (control.errors['minlength']) return 'Password must be at least 6 characters';
      if (control.errors['pattern']) return 'Password must contain at least one letter and one number';
    }
    return null;
  }

  get confirmPasswordErrors() {
    const confirm = this.resetPasswordForm.get('confirmPassword');
    if (confirm?.touched) {
      if (confirm.errors?.['required']) return 'Please confirm your password';
      if (this.resetPasswordForm.errors?.['passwordMismatch']) return 'Passwords do not match';
    }
    return null;
  }

  get passwordStrength(): string {
    const value = this.resetPasswordForm?.get('newPassword')?.value || '';
    if (value.length < 6) return 'Weak';
    if (/[A-Z]/.test(value) && /\d/.test(value) && /[@$!%*?&]/.test(value))
      return 'Strong';
    return 'Medium';
  }

  onSubmit(): void {
    // Mark all fields as touched to show errors
    Object.keys(this.resetPasswordForm.controls).forEach(key => {
      this.resetPasswordForm.get(key)?.markAsTouched();
    });

    if (this.resetPasswordForm.invalid) {
      return;
    }

    if (!this.email || !this.token) {
      this.errorMessage = 'Invalid reset link. Please request a new one.';
      return;
    }

    this.loading = true;
    this.successMessage = null;
    this.errorMessage = null;

    this.authService.resetPassword(this.token, this.email, this.resetPasswordForm.get('newPassword')?.value).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = response.message || 'Password reset successful! You can now log in with your new password.';
        
        // Auto redirect after 3 seconds
        setTimeout(() => {
          this.router.navigate(['/auth/login']);
        }, 3000);
      },
      error: (error) => {
        this.loading = false;
        console.error('Error resetting password:', error);
        this.errorMessage = error.error?.message || 'An error occurred while resetting your password. Please try again.';
      }
    });
  }
}