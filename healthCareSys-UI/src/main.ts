import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

platformBrowserDynamic().bootstrapModule(AppModule, {
  providers: [
    { provide: BrowserAnimationsModule, useValue: {} }
  ]
})
  .catch(err => console.error(err));
