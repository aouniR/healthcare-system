import { RouterModule } from '@angular/router';
import {CardComponent, LoaderComponent, SpinnerComponent} from './components';
import {CommonModule} from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbCollapseModule, NgbDropdownModule, NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { NgScrollbarModule } from 'ngx-scrollbar';
import { provideHttpClient } from '@angular/common/http';

export const COMPONENTS = [
  SpinnerComponent,
  LoaderComponent,
  CardComponent,
];

export const IMPORTS = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  NgbDropdownModule,
  NgbNavModule,
  NgbModule,
  NgbCollapseModule,
  NgScrollbarModule,
  RouterModule,
  
];

export const SERVICES = [provideHttpClient()];

export const EXPORTS = [
  SpinnerComponent,
  LoaderComponent,
  CardComponent,
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  NgbModule,
  NgbDropdownModule,
  NgbNavModule,
  NgbCollapseModule,
  NgScrollbarModule
];