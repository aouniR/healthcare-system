import { Component, OnInit } from '@angular/core';
import { profSanteService } from '../../shared/services/profSante.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FilterPipe } from '../../shared/pipes/filterPipeforPatients';
import { Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';
import { agentService } from '../../shared/services/agent.service';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipe],
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.scss']
})
export default class PatientsComponent implements OnInit {
  patients: any[] | null = null;
  searchTerm: string = '';
  showProfSanteModal: boolean = false;
  showAgentModal: boolean = false;
  patientId: any |null = null;
  isProfSante: boolean =false;
  isAgent: boolean =false;
  constructor(private psSer: profSanteService,
              private agSer: agentService,
              private authServ: AuthService,
              private router: Router
              ) {}

  ngOnInit(): void {
    this.extractPatientData();
    this.isProfSante=this.authServ.isPorfSante();
    this.isAgent=this.authServ.isAgent();
  }

  async extractPatientData() {
    try {
      const patientData = await this.psSer.getPatients();
      if (patientData) {
        this.patients = patientData.map((record: { patientId: string; patientData: any; }) => {
          return {
            patientId: record.patientId,
            ...record.patientData
          };
        });
      }
    } catch (error) {
      console.error('Error extracting patient data:', error);
    }
  }
  formatPatientId(patientId: string): string {
    return patientId.substring(0, 8);
  }  

  getKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  addTreatment(patientId: string) {
    this.showProfSanteModal = true; 
    this.patientId = patientId;
  }

  addMonitoring(patientId: string) {
    this.showProfSanteModal = true; 
    this.patientId = patientId;
  }
  closeModal() {
    this.showProfSanteModal = false;
    this.showAgentModal = false;
  }

  async confirmAddTreatment() {
    this.closeModal(); 
    if(this.patientId){
      const requestData = {
        patientId: this.patientId
      };
      const isValidStatus = await this.psSer.createProcedure(requestData);
      console.log(isValidStatus); 
      if(isValidStatus==200)
        this.router.navigate([`/profSante/patients/treatment/${this.patientId}`]);
      else alert('Error: Unable to add treatment, Patient-Id Error');
    }else
    alert('Error: Unable to add treatment, Patient-Id Error.')
  }
  async confirmAddMonitoring(){
    this.closeModal(); 
    if(this.patientId){
      const requestData = {
        patientId: this.patientId
      };
      const isValidStatus = await this.agSer.createMonitoring(requestData)
      console.log(isValidStatus); 
      if(isValidStatus==200)
        this.router.navigate([`/agent/patients/monitoring/${this.patientId}`]);
      else alert('Error: Unable to add monitoring, Patient-Id Error');
    }else
    alert('Error: Unable to add monitoring, Patient-Id Error.')
  }
  async seeTreatments(patientId: string){
    if(this.isProfSante)
      this.router.navigate([`/profSante/patients/treatment/${patientId}`]);
    if(this.isAgent)
      this.router.navigate([`/agent/patients/treatment/${patientId}`]);
  }

  async seeMonitoring(patientId: string){
    if(this.isAgent)
      this.router.navigate([`/agent/patients/monitoring/${patientId}`]);
  }

  async updateRecord(patientId: string){
    this.router.navigate([`/agent/patients/${patientId}`]);
  }
}
