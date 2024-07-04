import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from "@angular/common/http";
import { ShowUsersComponent } from './components/show-users/show-users.component';
import { PermissionsPipe } from './pipes/permissions.pipe';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';
import { SearchVacuumsComponent } from './components/search-vacuums/search-vacuums.component';
import { CreateVacuumComponent } from './components/create-vacuum/create-vacuum.component';
import { SingleVacuumComponent } from './components/single-vacuum/single-vacuum.component';
import { MonthNamePipe } from './pipes/month-name.pipe';
import { WeekDayPipe } from './pipes/week-day.pipe';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ShowUsersComponent,
    CreateUserComponent,
    PermissionsPipe,
    UpdateUserComponent,
    SearchVacuumsComponent,
    CreateVacuumComponent,
    SingleVacuumComponent,
    MonthNamePipe,
    WeekDayPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
