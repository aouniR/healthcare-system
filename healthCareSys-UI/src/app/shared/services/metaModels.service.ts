import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MetaModelsService {

  async createMetaModel(metaModelData: any) {
    try {
      const response = await fetch('http://localhost:8080/metamodels/createMetaModel', {
        method: 'POST', 
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
        body: JSON.stringify(metaModelData) 
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to create metamodel: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }

      return await response.json(); 
    } catch (error) {
      console.error('Error creating metamodel:', error);
      throw error;
    }
  }


  async getAllMetaModels() {
    try {
      const response = await fetch('http://localhost:8080/metamodels/getAllMetaModels', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to fetch metamodels: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }

      return await response.json(); 
    } catch (error) {
      console.error('Error fetching metamodels:', error);
      throw error;
    }
  }

  async updateMetaModelCountPerMonth(year :number) {
    try {
      const response = await fetch(`http://localhost:8080/metamodels/getNumberMetaModelByMonth/${year}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
      } catch (error) {
        console.error('Error fetching agent count:', error);
      }
    }


  async addFieldsToMetaModel(metamodelId: string, request: any) {
    try {
      const response = await fetch(`http://localhost:8080/metamodels/addFieldsToMetaModelById/${metamodelId}`, {
        method: 'PUT', 
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
        body: JSON.stringify(request) 
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to create metamodel: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }

      return await response.status; 
    } catch (error) {
      console.error('Error creating metamodel:', error);
      throw error;
    }
  }

  async getMetaModelById(metamodelId: string) {
    try {
      const response = await fetch(`http://localhost:8080/metamodels/getMetaModelById/${metamodelId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to fetch metamodel: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }
      const res =await response.json();
      console.log(res)
      return res; 
    } catch (error) {
      console.error('Error fetching metamodel:', error);
      throw error;
    }
  }

  async deleteMetaModelById(metaModelId: any ){
    try {
      const response = await fetch(`http://localhost:8080/metamodels/deleteMetaModelById/${metaModelId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to delete metaModel: ${response.statusText} - ${JSON.stringify(errorData)}`);
      }
      return await response.status; 
    } catch (error) {
      console.error('Error deleting metamodel:', error);
      throw error;
    }
  }

  
}
