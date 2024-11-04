import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { agentService } from '../../shared/services/agent.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface MedicalMonitoring {
  id: string;
  patientId: string;
  administrationAgentId: string;
  medicalMonitoringData: { [key: string]: string | null };
  creationTimestamp: string;
  updateTimestamp: string;
}

@Component({
  selector: 'app-update-monitoring',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule ],
  templateUrl: './update-monitoring.component.html',
  styleUrl: './update-monitoring.component.scss'
})
export default class UpdateMonitoringComponent implements OnInit {
  monitoringId: string | null = null;
  form: FormGroup;
  medicalMonitoring: MedicalMonitoring | null = null;

  constructor(private fb: FormBuilder,
              private agSer: agentService,
              private route: ActivatedRoute,
              private router: Router) {
    this.form = this.fb.group({});
  }

  ngOnInit(): void {
    this.monitoringId = this.route.snapshot.paramMap.get('id');
    this.fetchMedicalMonitoring();
  }

  async fetchMedicalMonitoring() {
    if(this.monitoringId)
    this.medicalMonitoring = await this.agSer.getPatientMonitoringById(this.monitoringId);
    if(this.medicalMonitoring)
    this.createFormControls(this.medicalMonitoring.medicalMonitoringData);
  }

  createFormControls(data: { [key: string]: string | null }): void {
    Object.keys(data).forEach(key => {
      this.form.addControl(key, this.fb.control(data[key]));
    });
  }

  async submit() {
    const updatedMedicalData = {
      medicalData: this.form.value,
    };
    if(this.monitoringId){
      const status = await this.agSer.updateMonitoring(updatedMedicalData, this.monitoringId);
      console.log('status:', status);
      if (status === 200) {
        const patientId=this.medicalMonitoring?.patientId;
        if(patientId)
          this.router.navigate([`/agent/patients/monitoring/${patientId}`]); 
      } else {
        alert('Error: Monitoring update was not successful.');
      }
      console.log('Submitting data:', updatedMedicalData);
    }
  }

  cancel(){
    this.router.navigate(['/admin/metaModel/listAll']); 
  }

  formatId(Id: string): string {
    return Id.substring(0, 8);
  }  
}