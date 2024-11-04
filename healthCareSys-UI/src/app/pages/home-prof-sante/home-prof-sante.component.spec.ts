import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeProfSanteComponent } from './home-prof-sante.component';

describe('HomeProfSanteComponent', () => {
  let component: HomeProfSanteComponent;
  let fixture: ComponentFixture<HomeProfSanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeProfSanteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeProfSanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
