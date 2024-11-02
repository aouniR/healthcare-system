import { isPlatformBrowser } from '@angular/common';
import {AfterViewInit, Component, Inject, Input, PLATFORM_ID} from '@angular/core';

@Component({
  selector: 'app-testimonials',
  templateUrl: './testimonials.component.html',
})
export class TestimonialsComponent implements AfterViewInit {

  @Input() fields:any;


  constructor(@Inject(PLATFORM_ID) private platformId: object) {}

  ngAfterViewInit(): void {
    // Check if the platform is the browser
    if (isPlatformBrowser(this.platformId)) {
      console.log("Loading tiny-slider...");

      import('tiny-slider').then(({ tns }) => {
        tns({
          container: '.testimonial-active',
          autoplay: true,
          autoplayTimeout: 5000,
          autoplayButtonOutput: false,
          mouseDrag: true,
          gutter: 0,
          nav: false,
          navPosition: 'bottom',
          controls: true,
          controlsText: [
            '<i class="lni lni-chevron-left"></i>',
            '<i class="lni lni-chevron-right"></i>',
          ],
          items: 1
        });
      }).catch(err => {
        console.error('Error loading tiny-slider:', err);
      });
    } else {
      console.log("Running on server; slider controls will not be initialized.");
    }
  }
}