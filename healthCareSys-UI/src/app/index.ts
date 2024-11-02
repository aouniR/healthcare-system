import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app.routes';
import { AppComponent } from './app.component';
import { LandingPageModule } from './pages/landing-page/landing-page.module';
import { SharedModule } from './shared/shared.module';

export const COMPONENTS = [
  AppComponent
];

export const IMPORTS = [
  BrowserModule,
  BrowserAnimationsModule,
  LandingPageModule,
  AppRoutingModule,
  SharedModule
];

export const SERVICES = [];
