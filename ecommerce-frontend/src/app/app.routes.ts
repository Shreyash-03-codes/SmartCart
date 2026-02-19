import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './features/auth/login/login.component';
import { SignupComponent } from './features/auth/signup/signup.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { WelcomeComponent } from './features/pages/welcome/welcome.component';
import { HomeComponent } from './features/pages/home/home.component';
import { ForgetPasswordComponent } from './features/auth/forget-password/forget-password.component';
import { ResetPasswordComponent } from './features/auth/reset-password/reset-password.component';
import { VerifyEmailComponent } from './features/auth/verify-email/verify-email.component';

export const routes: Routes = [
    {
        path:"auth",
        component:AuthLayoutComponent,
        children:[
            {
                path:"login",
                component:LoginComponent
            },
            {
                path:"register",
                component:SignupComponent
            },
            {
                path:"forget-password",
                component:ForgetPasswordComponent
            },
            {
                path:"reset-password",
                component:ResetPasswordComponent
            },
            {
                path:"verify-email",
                component:VerifyEmailComponent
            }
        ]
    },
    {
        path:"",
        component:MainLayoutComponent,
        children:[
            {
                path:"",
                component:WelcomeComponent
            },
            {
                path:"home",
                component:HomeComponent
            }
        ]
    }
];
