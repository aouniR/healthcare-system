import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { profSanteService } from '../../shared/services/profSante.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-treatment',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],  
  templateUrl: './treatment.component.html',
  styleUrls: ['./treatment.component.scss']
})
export default class TreatmentComponent implements OnInit {

  patientId: string | null = null;
  patientProcedures: any[] = [];
  selectedProcedure: any | null = null; 
  showInfoModal: boolean = false;
  showDeleteModal: boolean = false;  
  procedureToDelete:string | null = null;
  isProfSante: boolean = false;
  constructor(
    private psSer: profSanteService,
    private authSer: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientId = this.route.snapshot.paramMap.get('id');
    this.isProfSante= this.authSer.isPorfSante();
    this.getPatientProcedures();
  }

  async getPatientProcedures() {
    if (this.patientId) {
      this.patientProcedures = await this.psSer.getPatientProcedures(this.patientId);
    }
  }

  openModal(procedure: any) {
    this.selectedProcedure = procedure;
    this.showInfoModal = true;
  }

  openDeleteModal(procedureId: any) {
    this.procedureToDelete = procedureId;
    this.showDeleteModal = true;
  }

  closeModal() {
    this.showInfoModal = false;
    this.selectedProcedure = null;
  }

  closeDeleteModal() {
    this.showDeleteModal = false;
    this.procedureToDelete = null;
  }

  async deleteProcedure(procedureId: string) {
    this.procedureToDelete = procedureId;
    this.showDeleteModal = true;
  }


  async confirmDelete() {
    if (this.procedureToDelete !== null) {
      try {
        const status = await this.psSer.deleteMedicalProcedureById(this.procedureToDelete);
        
        if (status === 204) {
          this.patientProcedures = this.patientProcedures.filter(patientProcedure => patientProcedure.id !== this.procedureToDelete);
        } else {
          alert('Error: MetaModel deletion was not successful.');
        }
        
        this.procedureToDelete = null; 
      } catch (error) {
        console.error('Error deleting metaModel:', error);
        alert('Error: Unable to delete the MetaModel. Please try again later.');
      }
    }
    this.showDeleteModal = false; 
  }

  formatId(Id: string): string {
    return Id.substring(0, 8);
  }  

  navigateToUpdateProcedure(procedureId: string) {
    this.router.navigate(['/profSante/patients/updateTreatment', procedureId]);
  }
}