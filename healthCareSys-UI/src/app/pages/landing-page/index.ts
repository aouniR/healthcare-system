import {
  BlogSectionComponent,
  FeaturesComponent,
  HeroComponent,
  LandingPageComponent,
  TestimonialsComponent,
  TwoColumnWithImageComponent
} from './components';

import {SharedModule} from '../../shared/shared.module';
import { CommonModule } from '@angular/common';
import { LayoutModule } from '../../layout/layout.module';

export const COMPONENTS = [
  LandingPageComponent,
  HeroComponent,
  TwoColumnWithImageComponent,
  FeaturesComponent,
  TestimonialsComponent,
  BlogSectionComponent,
];

export const IMPORTS = [
  CommonModule,
  SharedModule,
  LayoutModule
];

export const SERVICES = [];

export const EXPORTS = [LandingPageComponent];
