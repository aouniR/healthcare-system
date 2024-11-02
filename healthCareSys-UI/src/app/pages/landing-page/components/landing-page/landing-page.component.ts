import { Component, OnInit} from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';


@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html'
})
export class LandingPageComponent implements OnInit {

  public pageData = {
    body: [
      {
        type: 'hero',
        fields: {
          title: 'Medical Records Management System',
          subtitle: 'Streamlining Healthcare Management',
          headline: 'Welcome to Your Comprehensive HealthCare System!',
          subheadline: 'A proof of concept for a dynamic and efficient healthcare ERP solution.',
          button_url: '/login',
          button_label: 'Access the Platform',
          image: 'assets/images/hero/rb_21.png',
          scroll_anchor_id: '1'
        }
      },
      {
        type: 'two_column_with_image',
        fields: {
          scroll_anchor_id: '2',
          image_position: 'left',
          image: 'assets/images/two-col/first.svg',
          headline: 'Efficient Integration of Services',
          subheadline: 'Seamlessly connects various hospital services for improved patient care.',
          button_label: 'Explore Features'
        }
      },
      {
        type: 'features',
        fields: {
          scroll_anchor_id: '3',
          headline: 'Core Features of Our Healthcare Platform',
          subheadline: 'Discover how our system enhances healthcare delivery.',
          features: [
            {
              icon: 'assets/images/icons/medical-report.png', 
              headline: 'Customizable Medical Record Models',
              description: 'Easily configure medical record models to meet your institution\'s unique needs.'
            },
            {
              icon: 'assets/images/icons/asynchronous.png',
              headline: 'Asynchronous Communication',
              description: 'Leverage Apache Kafka for efficient, fault-tolerant service interactions.'
            },
            {
              icon: 'assets/images/icons/social-media.png', 
              headline: 'User-Friendly Interfaces',
              description: 'Intuitive UI for healthcare professionals to manage records effectively.'
            },
            {
              icon: 'assets/images/icons/gdpr.png', 
              headline: 'High Data Security',
              description: 'Ensures compliance with healthcare data security standards to protect patient information.'
            }
          ]
        }
      },
      {
        type: 'testimonials',
        fields: {
          scroll_anchor_id: '4',
          headline: 'What Our Users Say',
          testimonial: [
            {
              quote: 'A revolutionary system that simplifies healthcare management!',
              name: 'A. Smith',
              title: 'Healthcare Administrator'
            },
            {
              quote: 'The integration of services has drastically improved our workflow.',
              name: 'J. Doe',
              title: 'Doctor'
            }
          ]
        }
      }
    ]
  };
  
  public posts = {
    data: [
      {
        featured_image: 'assets/images/icons/idea.png', 
        featured_image_alt: 'Healthcare Administrator',
        title: 'Healthcare Administrator\'s Insight',
        summary: 'Discover the benefits of integrated healthcare management.',
        slug: 'healthcare-administrator-insight'
      },
      {
        featured_image: 'assets/images/icons/football-pitch.png', 
        featured_image_alt: 'Doctor',
        title: 'Doctor\'s Perspective on Efficiency',
        summary: 'Learn how our system has transformed patient record management.',
        slug: 'doctors-perspective-on-efficiency'
      }
    ]
  };  

  constructor(public meta: Meta, public title: Title ) {

  }

  ngOnInit(): void {
    this.title.setTitle('HealthCare System');
    this.meta.addTag({ name: 'description', content: 'POC of ERP Multi-Metamodel platform' });
  }

  onMenuItemClick(url: string) {
    const element = document.getElementById(url);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
      element.focus(); 
    }
  }
}
