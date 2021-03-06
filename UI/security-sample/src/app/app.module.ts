import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UsersComponent } from './users/users.component';
const rout:Routes=[
  {path:'login',component:LoginComponent},
  {path:"sign-up",component:SignUpComponent},
  {path:"users",component:UsersComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    UsersComponent
  ],
  
  imports: [
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(rout),
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
