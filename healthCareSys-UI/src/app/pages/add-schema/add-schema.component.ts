import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { adminDashboardService } from '../../shared/services/adminDashboard.service';
import { ToastrModule, ToastrService } from 'ngx-toastr'; 

@Component({
  selector: 'app-add-schema',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ToastrModule],
  templateUrl: './add-schema.component.html',
  styleUrls: ['./add-schema.component.scss'] 
})
export default class AddSchemaComponent {
  schemaForm: FormGroup;
  schemaTypes = ['ACTE_MEDICAL', 'DOSSIER_MEDICAL', 'SUIVI'];
  metamodels: any[] = [];

  constructor(private fb: FormBuilder, private dashService: adminDashboardService, private toastr: ToastrService) {
    this.schemaForm = this.fb.group({
      schemaType: ['', Validators.required],
      metamodelId: ['', Validators.required]
    });
  }

  async fetchMetaModels() {
    const type = this.schemaForm.get('schemaType')?.value;
    let metamodels;

    switch (type) {
      case 'ACTE_MEDICAL':
        metamodels = await this.dashService.updateMetaMedicalProcedureCount();
        break;
      case 'DOSSIER_MEDICAL':
        metamodels = await this.dashService.updateMetaMedicalRecordCount();
        break;
      case 'SUIVI':
        metamodels = await this.dashService.updateMetaMedicalMonitoringCount();
        break;
      default:
        metamodels = [];
    }

    this.metamodels = metamodels || [];
  }

  async addSchema() {
    if (this.schemaForm.valid) {
      const requestData = {
        metamodelId: this.schemaForm.value.metamodelId
      };
      console.log('requestData:',requestData); 
      let isValidId;
      switch (this.schemaForm.value.schemaType) {
        case 'ACTE_MEDICAL':
          isValidId = await this.dashService.createMedicalProcedureSchema(requestData);
          break;
        case 'DOSSIER_MEDICAL':
          isValidId = await this.dashService.createMedicalRecordSchema(requestData);
          break;
        case 'SUIVI':
          isValidId = await this.dashService.createMedicalMonitoringSchema(requestData);
          break;
        default:
          isValidId = false;
      }
      console.log('isValidId:',isValidId); 
      if (isValidId) {
        console.log('Schema created successfully:',isValidId); 
        this.toastr.success('Schema created successfully!', 'Success'); 
        this.schemaForm.reset(); 
        this.metamodels = []; 
      } else {
        console.log('Failed to create schema:',isValidId); 
        this.toastr.error('Failed to create schema', 'Error'); 
      }
    } else {
      this.toastr.warning('Please fill in all required fields', 'Warning'); 
    }
  }

  cancel() {
    this.schemaForm.reset();
    this.metamodels = [];
  }
}
