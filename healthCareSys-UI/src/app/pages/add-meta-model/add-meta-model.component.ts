import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MetaModelsService } from '../../shared/services/metaModels.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-meta-model',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-meta-model.component.html',
  styleUrl: './add-meta-model.component.scss'
})
export default class AddMetaModelComponent {
  metaModelForm: FormGroup;
  typeOptions = [
    { value: 'ACTE_MEDICAL', label: 'Acte Médical' },
    { value: 'DOSSIER_MEDICAL', label: 'Dossier Médical' },
    { value: 'SUIVI', label: 'Suivi' },
  ];

  constructor(private fb: FormBuilder, private metaModelsService: MetaModelsService,  private router: Router) {
    this.metaModelForm = this.fb.group({
      description: ['', Validators.required],
      type: ['', Validators.required]
    });
  }


  async addMetaModel(): Promise<void> {
    if (this.metaModelForm.valid) {
      try {
        const metamodelData = this.metaModelForm.value;
        await this.metaModelsService.createMetaModel(metamodelData);
        this.router.navigate(['/admin/metaModel/listAll']);
      } catch (error) {
        console.error('Error adding user:', error);

      }
    }
  }

  cancel(){
    this.router.navigate(['/admin/metaModel/listAll']); 
  }
}
