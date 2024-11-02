import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MetaModelsService } from '../../shared/services/metaModels.service';
import { filterPipeforMetaModelsByType } from '../../shared/pipes/filterPipeforMetaModelsByType';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-meta-models',
  standalone: true,
  imports: [CommonModule, FormsModule, filterPipeforMetaModelsByType],
  templateUrl: './list-meta-models.component.html',
  styleUrl: './list-meta-models.component.scss'
})
export default class ListMetaModelsComponent {

  searchTerm: string = '';
  metamodels: any[] = []; 
  metamodelToDelete: number | null = null;
  showFieldsIndex: number | null = null;
  showModal: boolean = false;

  constructor(private metamodelsService: MetaModelsService,  private router: Router) {}

  ngOnInit(): void {
    this.fetchAllMetaModels();
  }


  async fetchAllMetaModels() {
    try {
      this.metamodels = await this.metamodelsService.getAllMetaModels();
    } catch (error) {
      console.error('Error fetching metamodels:', error);
    }
  }

  toggleFields(index: number) {
    this.showFieldsIndex = this.showFieldsIndex === index ? null : index; 
  }

  addFieldstoMetaModel(metamodelId: string) {
    this.router.navigate([`/admin/metaModel/addFields/${metamodelId}`]);
  }

  deleteMetaModel(metamodelId: number) {
    this.metamodelToDelete = metamodelId;
    this.showModal = true;
  }

  async confirmDelete() {
    if (this.metamodelToDelete !== null) {
      try {
        const status = await this.metamodelsService.deleteMetaModelById(this.metamodelToDelete);
        
        if (status === 204) {
          this.metamodels = this.metamodels.filter(metamodel => metamodel.id !== this.metamodelToDelete);
        } else {
          alert('Error: MetaModel deletion was not successful.');
        }
        
        this.metamodelToDelete = null; 
      } catch (error) {
        console.error('Error deleting metaModel:', error);
        alert('Error: Unable to delete the MetaModel. Please try again later.');
      }
    }
    this.showModal = false; 
  }


  closeModal() {
    this.showModal = false;
    this.metamodelToDelete = null; 
  }

}
