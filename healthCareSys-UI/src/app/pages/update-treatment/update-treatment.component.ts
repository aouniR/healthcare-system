import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { profSanteService } from '../../shared/services/profSante.service';
import { ActivatedRoute, Router } from '@angular/router';

interface MedicalProcedure {
  id: string;
  patientId: string;
  medicalAgentId: string;
  medicalProcedureData: { [key: string]: string | null };
  creationTimestamp: string;
  updateTimestamp: string;
}

@Component({
  selector: 'app-update-treatment',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule ],
  templateUrl: './update-treatment.component.html',
  styleUrl: './update-treatment.component.scss'
})
export default class UpdateTreatmentComponent implements OnInit {
  treatementId: string | null = null;
  form: FormGroup;
  medicalProcedure: MedicalProcedure | null = null;

  constructor(private fb: FormBuilder,
              private psSer: profSanteService,
              private route: ActivatedRoute,
              private router: Router) {
    this.form = this.fb.group({});
  }

  ngOnInit(): void {
    this.treatementId = this.route.snapshot.paramMap.get('id');
    this.fetchMedicalProcedure();
  }

  async fetchMedicalProcedure() {
    if(this.treatementId)
    this.medicalProcedure = await this.psSer.getPatientProcedureById(this.treatementId);
    if(this.medicalProcedure)
    this.createFormControls(this.medicalProcedure.medicalProcedureData);
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
    if(this.treatementId){
      const status = await this.psSer.updateProcedure(updatedMedicalData, this.treatementId);
      console.log('status:', status);
      if (status === 200) {
        const patientId=this.medicalProcedure?.patientId;
        if(patientId)
          this.router.navigate([`/profSante/patients/treatment/${patientId}`]); 
      } else {
        alert('Error: Treatment update was not successful.');
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