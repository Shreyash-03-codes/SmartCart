import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router'; // Add Router
import { AuthService } from '../../../core/services/auth/auth-service.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  showPassword = false;
  showConfirmPassword = false;
  isLoading = false; // Add loading state
  fb: FormBuilder = new FormBuilder();

  ngOnInit(): void {
    this.createForm();
  }

  constructor(
    private authService: AuthService,
    private router: Router // Inject Router
  ) {}

  private createForm() {
    this.signupForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(6), // Changed to 6 to match strength indicator
        Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d).+$/) // At least one letter and one number
      ]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  passwordMatchValidator(form: FormGroup) {
    return form.get('password')?.value === form.get('confirmPassword')?.value
      ? null
      : { passwordMismatch: true };
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  get passwordStrength(): string {
    const value = this.signupForm?.get('password')?.value || '';
    if (value.length < 6) return 'Weak';
    if (/[A-Z]/.test(value) && /\d/.test(value) && /[@$!%*?&]/.test(value))
      return 'Strong';
    return 'Medium';
  }

  get nameErrors() {
    const name = this.signupForm.get('name');
    if (name?.touched) {
      if (name.errors?.['required']) return 'Name is required';
      if (name.errors?.['minlength']) return 'Name must be at least 3 characters';
    }
    return null;
  }

  get emailErrors() {
    const email = this.signupForm.get('email');
    if (email?.touched) {
      if (email.errors?.['required']) return 'Email is required';
      if (email.errors?.['email']) return 'Enter a valid email';
    }
    return null;
  }

  get passwordErrors() {
    const password = this.signupForm.get('password');
    if (password?.touched && password.errors) {
      if (password.errors['required']) return 'Password is required';
      if (password.errors['minlength']) return 'Password must be at least 6 characters';
      if (password.errors['pattern']) return 'Password must contain at least one letter and one number';
    }
    return null;
  }

  get confirmPasswordErrors() {
    const confirm = this.signupForm.get('confirmPassword');
    if (confirm?.touched) {
      if (confirm.errors?.['required']) return 'Please confirm your password';
      if (this.signupForm.errors?.['passwordMismatch']) return 'Passwords do not match';
    }
    return null;
  }

  onSubmit() {
    Object.keys(this.signupForm.controls).forEach(key => {
      this.signupForm.get(key)?.markAsTouched();
    });

    if (this.signupForm.valid) {
      this.isLoading = true; // Set loading true
      
      this.authService.register(this.signupForm.value).subscribe({
        next: (response) => {
          this.isLoading = false;
          alert(response.data?.message || 'Registration successful! Please verify your email.');
          this.router.navigate(['/auth/login']); // Use router.navigate
        },
        error: (error) => {
          this.isLoading = false;
          console.error('Registration error:', error);
          alert(error.error?.message || 'Registration failed. Please try again.');
        }
      });
    }
  }
  callOAuth2() {
  this.authService.loginWithGoogle();
  }
}