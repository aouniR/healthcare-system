import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class profSanteService {

    async getPatients() {
        try {
          const response = await fetch('http://localhost:8080/medicalrecords/getAllMedicalRecord', {
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

      async createProcedure(request: any) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/createMedicalProcedure`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+localStorage.getItem('authToken'),
            },
            body: JSON.stringify(request) 
          });
    
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return await response.status;
        } catch (error) {
          console.error('Error fetching agent count:', error);
          return null;
        }
      }


  async getPatientProcedureById(procedureId: string) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalProcedureById/${procedureId}`, {
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


  async getPatientProcedures(patientId: string) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalProcedureByPatientId/${patientId}`, {
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

  async deleteMedicalProcedureById(medicalProcedureId: any ){
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/deleteMedicalProcedureById/${medicalProcedureId}`, {
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

  
    async updateProcedure(request: any, id: any) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/addFieldsToMedicalProcedureById/${id}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+localStorage.getItem('authToken'),
            },
            body: JSON.stringify(request) 
          });
    
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return await response.status;

        } catch (error) {
          console.error('Error fetching agent count:', error);
          return null;
        }
      }
    
}
