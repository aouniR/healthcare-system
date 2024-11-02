import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MetaModelsService } from '../../shared/services/metaModels.service';

@Component({
  selector: 'app-add-fields-meta-model',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-fields-meta-model.component.html',
  styleUrls: ['./add-fields-meta-model.component.scss']
})
export default class AddFieldsMetaModelComponent implements OnInit {
  metaModelId: string | null = null;
  addFieldsForm: FormGroup;
  existingMetaModel: any; 
  loading: boolean = true; 

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private metaModelService: MetaModelsService
  ) {
    this.addFieldsForm = this.fb.group({
      fields: this.fb.array([]) 
    });
  }

  ngOnInit(): void {
    this.metaModelId = this.route.snapshot.paramMap.get('id');
    this.fetchMetaModel();
  }

  async fetchMetaModel() {
    if (this.metaModelId) {
      try {
        this.loading = true; 
        this.existingMetaModel = await this.metaModelService.getMetaModelById(this.metaModelId);
      } catch (error) {
        console.error('Error fetching meta model:', error);
        alert('Error fetching meta model. Please try again later.');
      } finally {
        this.loading = false; 
      }
    }
  }

  get fields(): FormArray {
    return this.addFieldsForm.get('fields') as FormArray;
  }

  addField() {
    const fieldGroup = this.fb.group({
      fieldName: ['', Validators.required],
      fieldType: ['', Validators.required]
    });
    this.fields.push(fieldGroup);
  }

  removeField(index: number) {
    this.fields.removeAt(index);
  }

  cancel(){
    this.metaModelId= null;
    this.router.navigate(['/admin/metaModel/listAll']); 
  }

  async submit() {
    if (this.addFieldsForm.valid && this.metaModelId != null) {
      const fieldsArray = this.addFieldsForm.value.fields;

      const fieldsObject: { [key: string]: string } = {};
      
      fieldsArray.forEach((field: { fieldName: string; fieldType: string }) => {
        fieldsObject[field.fieldName] = field.fieldType;
      });
      
      const requestData = {
        fields: fieldsObject
      };
  
      try {
        await this.metaModelService.addFieldsToMetaModel(this.metaModelId, requestData);
        this.router.navigate(['/admin/metaModel/listAll']); 
      } catch (error) {
        console.error('Error adding fields:', error);
        alert('Error adding fields. Please try again later.');
      }
    }
  }  
}
