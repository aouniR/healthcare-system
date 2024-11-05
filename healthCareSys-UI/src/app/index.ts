import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app.routes';
import { AppComponent } from './app.component';
import { LandingPageModule } from './pages/landing-page/landing-page.module';
import { SharedModule } from './shared/shared.module';
import { ToastrModule } from 'ngx-toastr';
import { AuthInterceptorService } from './shared/services/auth-interceptor.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

export const COMPONENTS = [
  AppComponent
];

export const IMPORTS = [
  BrowserModule,
  BrowserAnimationsModule,
  ToastrModule.forRoot({ 
    positionClass: 'toast-bottom-right',
    preventDuplicates: true, 
  }),
  LandingPageModule,
  AppRoutingModule,
  SharedModule
];

export const SERVICES = [  {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptorService,
  multi: true
}];
