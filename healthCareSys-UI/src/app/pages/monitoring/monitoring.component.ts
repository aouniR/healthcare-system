import { Component } from '@angular/core';
import { agentService } from '../../shared/services/agent.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-monitoring',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],  
  templateUrl: './monitoring.component.html',
  styleUrl: './monitoring.component.scss'
})
export default class MonitoringComponent {
  
  patientId: string | null = null;
  patientMonitorings: any[] = [];
  selectedMonitoring: any | null = null; 
  showInfoModal: boolean = false;
  showDeleteModal: boolean = false;  
  MonitoringToDelete:string | null = null;

  constructor(
    private agSer: agentService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientId = this.route.snapshot.paramMap.get('id');
    this.getPatientMonitorings();
  }

  async getPatientMonitorings() {
    if (this.patientId) {
      this.patientMonitorings = await this.agSer.getPatientMonitorings(this.patientId);
    }
  }

  openModal(Monitoring: any) {
    this.selectedMonitoring = Monitoring;
    this.showInfoModal = true;
  }

  openDeleteModal(MonitoringId: any) {
    this.MonitoringToDelete = MonitoringId;
    this.showDeleteModal = true;
  }

  closeModal() {
    this.showInfoModal = false;
    this.selectedMonitoring = null;
  }

  closeDeleteModal() {
    this.showDeleteModal = false;
    this.MonitoringToDelete = null;
  }

  async deleteMonitoring(MonitoringId: string) {
    this.MonitoringToDelete = MonitoringId;
    this.showDeleteModal = true;
  }


  async confirmDelete() {
    if (this.MonitoringToDelete !== null) {
      try {
        const status = await this.agSer.deleteMedicalMonitoringById(this.MonitoringToDelete);
        
        if (status === 204) {
          this.patientMonitorings = this.patientMonitorings.filter(patientMonitoring => patientMonitoring.id !== this.MonitoringToDelete);
        } else {
          alert('Error: MedicalMonitoring deletion was not successful.');
        }
        
        this.MonitoringToDelete = null; 
      } catch (error) {
        console.error('Error deleting MedicalMonitoring:', error);
        alert('Error: Unable to delete the MedicalMonitoring. Please try again later.');
      }
    }
    this.showDeleteModal = false; 
  }

  formatId(Id: string): string {
    return Id.substring(0, 8);
  }  

  navigateToUpdateMonitoring(MonitoringId: string) {
    this.router.navigate(['/agent/patients/updateMonitoring', MonitoringId]);
  }
}