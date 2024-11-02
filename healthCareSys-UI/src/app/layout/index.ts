import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import {
  ScrollTopComponent,
  FooterComponent,
  HeaderComponent,
  AdminComponent
} from './components';
import { NgScrollbarModule } from 'ngx-scrollbar'; 
import {
  NavBarComponent,
  NavLogoComponent,
  NavRightComponent,
  NavLeftComponent,
  NavigationComponent,
  NavContentComponent,
  NavCollapseComponent,
  NavGroupComponent,
  NavItemComponent,
  NavigationItem
} from './components/admin';
import { SharedModule } from '../shared/shared.module';

export const COMPONENTS = [
  AdminComponent,
  NavItemComponent,
  NavGroupComponent,
  NavCollapseComponent,
  NavContentComponent,
  NavigationComponent,
  NavRightComponent,
  NavLeftComponent,
  NavLogoComponent,
  NavBarComponent,
  ScrollTopComponent,
  FooterComponent,
  HeaderComponent
];

export const IMPORTS = [
  CommonModule,
  RouterModule,
  NgScrollbarModule,
  SharedModule
];

export const SERVICES = [NavigationItem];

export const EXPORTS = [
  CommonModule,
  RouterModule,
  HeaderComponent,
  ScrollTopComponent,
  FooterComponent
];

