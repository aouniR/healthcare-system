import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { agentService } from '../../shared/services/agent.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface MedicalRecord {
  id: string;
  patientId: string;
  agentId: string;
  patientData: { [key: string]: string | null };
  creationTimestamp: string;
  updateTimestamp: string;
}

@Component({
  selector: 'app-update-record',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule ],
  templateUrl: './update-record.component.html',
  styleUrl: './update-record.component.scss'
})
export default class UpdateRecordComponent implements OnInit {
  recordId: string | null = null;
  form: FormGroup;
  medicalrecord: MedicalRecord | null = null;

  constructor(private fb: FormBuilder,
              private agSer: agentService,
              private route: ActivatedRoute,
              private router: Router) {
    this.form = this.fb.group({});
  }

  ngOnInit(): void {
    this.recordId = this.route.snapshot.paramMap.get('id');
    this.fetchMedicalrecord();
  }

  async fetchMedicalrecord() {
    if(this.recordId)
    this.medicalrecord = await this.agSer.getPatientRecordById(this.recordId);
    if(this.medicalrecord)
    this.createFormControls(this.medicalrecord.patientData);
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
    if(this.recordId){
      const status = await this.agSer.updateRecord(updatedMedicalData, this.recordId);
      console.log('status:', status);
      if (status === 200) {
        this.router.navigate([`/agent/patients`]); 
      } else {
        alert('Error: record update was not successful.');
      }
      console.log('Submitting data:', updatedMedicalData);
    }
  }

  cancel(){
    this.router.navigate(['/agent/patients']); 
  }

  formatId(Id: string): string {
    return Id.substring(0, 8);
  }  
}