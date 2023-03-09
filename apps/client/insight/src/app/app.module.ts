import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {GraphQLModule} from './graphql.module';
import {HttpClientModule} from '@angular/common/http';
import {DashboardComponent} from './dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";

// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GraphQLModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    MatSlideToggleModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
