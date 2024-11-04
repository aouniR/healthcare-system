import { Component, OnInit } from '@angular/core';
import { adminDashboardService } from '../../shared/services/adminDashboard.service';
import { FormBuilder, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FilterPipe } from '../../shared/pipes/filterPipeforPatients';

@Component({
  selector: 'app-list-schemas',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipe],
  templateUrl: './list-schemas.component.html',
  styleUrl: './list-schemas.component.scss'
})
export default class ListSchemasComponent implements OnInit{

  schemas: any[] = [];
  searchTerm: string = '';
  showFieldsIndex: number | null = null;
  showModal: boolean = false;
  schemaToDelete: string | null = null;

  constructor(private dashService: adminDashboardService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.fetchMonitoringSchema();
    this.fetchProcedureSchema();
    this.fetchMedicalRecordSchema();
  }

  async fetchMonitoringSchema() {
    const rawSchema = await this.dashService.getMedicalMonitoringSchema();
    
    if (rawSchema && typeof rawSchema === 'object') {
      this.schemas.push({
        id: rawSchema.id,
        metamodelId: rawSchema.metamodelId,
        type:"SUIVI",
        fields: rawSchema.fields
      });
    } else {
      console.error('Expected an object, but got:', rawSchema);
    }
  }

  async fetchProcedureSchema() {
    const rawSchema = await this.dashService.getMedicalProcedureSchema();
    console.log('fetchProcedureSchema:', rawSchema);
    if (rawSchema && typeof rawSchema === 'object') {
      this.schemas.push({
        id: rawSchema.id,
        metamodelId: rawSchema.metamodelId,
        type:"ACTE_MEDICAL",
        fields: rawSchema.fields
      });
    } else {
      console.error('Expected an object, but got:', rawSchema);
    }
  }

  async fetchMedicalRecordSchema() {
    const rawSchema = await this.dashService.getMedicalRecordSchema();
    console.log('fetchMedicalRecordSchema:', rawSchema);
    if (rawSchema && typeof rawSchema === 'object') {
      this.schemas.push({ 
        id: rawSchema.id,
        metamodelId: rawSchema.metamodelId,
        type: "DOSSIER_MEDICAL",
        fields: rawSchema.fields
      });
    } else {
      console.error('Expected an object, but got:', rawSchema);
    }
  }

  toggleFields(index: number) {
    this.showFieldsIndex = this.showFieldsIndex === index ? null : index;
  }


  confirmDelete() {
    if (this.schemaToDelete) {
      console.log(`Deleting schema with ID: ${this.schemaToDelete}`);
      this.schemaToDelete = null; 
      this.closeModal();
    }
  }

  deleteschema(schemaId: string) {
    this.schemaToDelete = schemaId;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.schemaToDelete = null;
  }
}
