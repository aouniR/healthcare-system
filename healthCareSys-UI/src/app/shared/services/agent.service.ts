import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class agentService {
    
    async createMonitoring(request: any) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/createMedicalMonitoring`, {
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

    async createRecord() {
      try {
        const response = await fetch(`http://localhost:8080/medicalrecords/createMedicalRecord`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+localStorage.getItem('authToken'),
          }
        });
  
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return await response.json();
      } catch (error) {
        console.error('Error fetching agent count:', error);
        return null;
      }
  }

    async getPatientMonitorings(patientId: string) {
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalMonitoringByPatientId/${patientId}`, {
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

    
  async getPatientMonitoringById(monitoringId: string) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalMonitoringById/${monitoringId}`, {
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

  async getPatientRecordById(recordId: string) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalRecordById/${recordId}`, {
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
  async updateRecord(request: any, id: any) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/addFieldsToMedicalRecordById/${id}`, {
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

  async updateMonitoring(request: any, id: any) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/addFieldsToMedicalMonitoringById/${id}`, {
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


    async deleteMedicalMonitoringById(medicalMonitoringId: any ){
        try {
          const response = await fetch(`http://localhost:8080/medicalrecords/deleteMedicalMonitoringById/${medicalMonitoringId}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${localStorage.getItem('authToken')}`
            }
          });
    
          if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to delete medicalMonitoring: ${response.statusText} - ${JSON.stringify(errorData)}`);
          }
          return await response.status; 
        } catch (error) {
          console.error('Error deleting medicalMonitoring:', error);
          throw error;
        }
    }
}