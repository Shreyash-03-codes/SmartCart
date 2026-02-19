import { Component, OnInit } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StorageService } from '../../../core/services/storage/stoarge.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  userName: string = '';

  constructor(private router: Router,private storageService: StorageService) {}

  ngOnInit(): void {
    this.checkAuthStatus();
  }

  private checkAuthStatus(): void {
    // TODO: Implement authentication check using auth service
    // This should verify if user is logged in and get user details
    // Example:
    // this.authService.getCurrentUser().subscribe(
    //   (user) => {
    //     this.isLoggedIn = true;
    //     this.userName = user.name;
    //   },
    //   (error) => {
    //     this.isLoggedIn = false;
    //   }
    // );
  }

  logout(): void {

    this.storageService.clear();
    this.isLoggedIn = false;
    this.userName = '';
    this.router.navigate(['/auth/login']);
  }
}
